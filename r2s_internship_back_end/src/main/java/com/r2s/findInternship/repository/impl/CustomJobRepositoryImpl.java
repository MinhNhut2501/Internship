package com.r2s.findInternship.repository.impl;

import com.r2s.findInternship.entity.*;
import com.r2s.findInternship.repository.CustomJobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class CustomJobRepositoryImpl implements CustomJobRepository {
    @PersistenceContext
    private EntityManager entityManager;
    // kiểm tra string không null và không rỗng
    private boolean stringNotNullAndNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
    // kiểm tra list không null không rỗng
    private boolean listNotNullAndNotEmpty(List<String> list) {
        return list != null && !list.isEmpty();
    }
    // filter theo các tiêu chí đó
    @Override
    public Page<Job> filterByKeywords(String name, String province, List<String> jobPositionList, List<String> jobTypeList, List<String> majorList, String order, Pageable pageable) {
        // Để có thể implement Page thì ta cần 2 query, 1 query lấy kết quả, 1 query đếm
        // Câu query có thể sẽ thế này:
        // SELECT j FROM Job j
        // WHERE j.status.id = 1
        // AND j.name LIKE %:name%
        // AND j.location.id IN (SELECT l.id FROM Location l WHERE l.district.province.name = :province)
        // AND (j.jobPosition.name = :position0 OR j.jobPosition.name = :position1 OR ...)
        // AND (j.jobType.name = :type0 OR j.jobType.name = :type1 OR ...)
        // AND (j.major.name = :major0 OR j.major.name = :major1 OR ...)
        // ORDER BY j.createDate ASC/DESC
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Job> criteria = builder.createQuery(Job.class); // trình tạo query tự động trả ra danh sách các đối tượng
        CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class); // trình tạo query tự động trả ra số lượng các đối tượng trên

        Root<Job> job = criteria.from(Job.class); // query để lấy ra các đối tượng có class là job
        Root<Job> jobCount = criteriaCount.from(Job.class); // query vẫn phải lấy ra các đối tượng, sau đó mới đếm được

        // danh sách chứa các predicate chính (các câu lệnh điều kiện được tự động sinh ra)
        List<Predicate> finalPredicates = new ArrayList<>();
        List<Predicate> finalPredicatesCount = new ArrayList<>();

        // danh sách tạm chứa các predicate đơn giá trị
        List<Predicate> singlePredicates = new ArrayList<>();
        List<Predicate> singlePredicatesCount = new ArrayList<>();

        // để sinh ra câu lệnh với status thì ta phải join, thuộc tính join là thuộc tính status trong entity
        Join<Status, Job> status = job.join("status");
        Join<Status, Job> statusCount = jobCount.join("status");

        // thêm một predicate vào vào danh sách các predicate đơn giá trị
        // (j.status.id = 1)
        singlePredicates.add(builder.equal(status.get("id"), 1));
        singlePredicatesCount.add(builder.equal(statusCount.get("id"), 1));

        // thêm vào predicate nếu có name
        if (stringNotNullAndNotEmpty(name)) {
            // (j.name LIKE %:name%)
            singlePredicates.add(builder.like(job.get("name"), "%" + name + "%"));
            singlePredicatesCount.add(builder.like(jobCount.get("name"), "%" + name + "%"));
        }

        // join một loạt các bảng: job -> location -> district -> province
        Join<Province, Job> provinceJoin = job.join("locationjob").join("district").join("province");
        Join<Province, Job> provinceJoinCount = jobCount.join("locationjob").join("district").join("province");
        if (stringNotNullAndNotEmpty(province)) {
            // về ý nghĩa sẽ giống như sau:
            // j.location.id IN (SELECT l.id FROM Location l WHERE l.district.province.name = :province)
            singlePredicates.add(builder.equal(provinceJoin.get("name"), province));
            singlePredicatesCount.add(builder.equal(provinceJoinCount.get("name"), province));
        }

        // thêm predicate đơn trị vào predicate chính với từ nối là AND trong builder.and(...)
        // Nôm na sẽ như thế này:
        // (j.status.id = 1)
        // AND (j.name LIKE %:name%)
        // AND (j.location.id IN (SELECT l.id FROM Location l WHERE l.district.province.name = :province))
        finalPredicates.add(builder.and(singlePredicates.toArray(new Predicate[singlePredicates.size()])));
        finalPredicatesCount.add(builder.and(singlePredicatesCount.toArray(new Predicate[singlePredicatesCount.size()])));
        // các predicate đa giá trị (jobposition)
        List<Predicate> jobPositionPredicates = new ArrayList<>();
        List<Predicate> jobPositionPredicatesCount = new ArrayList<>();

        if (listNotNullAndNotEmpty(jobPositionList)) {
            // join với bảng job position
            Join<JobPosition, Job> jobPositionJoin = job.join("jobposition");
            Join<JobPosition, Job> jobPositionJoinCount = jobCount.join("jobposition");
            // kết quả sẽ giống thế này:
            // ["j.jobposition.name = position1", "j.jobposition.name = position2"]
            for (String jobPosition : jobPositionList) {
                jobPositionPredicates.add(builder.equal(jobPositionJoin.get("name"), jobPosition));
                jobPositionPredicatesCount.add(builder.equal(jobPositionJoinCount.get("name"), jobPosition));
            }
            // thêm or vào giữa các predicate
            // kết quả sẽ giống thế này:
            // j.jobposition.name = position1 OR j.jobposition.name = position2
            finalPredicates.add(builder.or(jobPositionPredicates.toArray(new Predicate[jobPositionPredicates.size()])));
            finalPredicatesCount.add(builder.or(jobPositionPredicatesCount.toArray(new Predicate[jobPositionPredicatesCount.size()])));
        }

        // các predicate đa giá trị còn lại thì tương tự
        List<Predicate> jobTypePredicates = new ArrayList<>();
        List<Predicate> jobTypePredicatesCount = new ArrayList<>();

        if (listNotNullAndNotEmpty(jobTypeList)) {
            Join<JobType, Job> jobTypeJoin = job.join("jobType");
            Join<JobType, Job> jobTypeJoinCount = jobCount.join("jobType");

            for (String jobType : jobTypeList) {
                jobTypePredicates.add(builder.equal(jobTypeJoin.get("name"), jobType));
                jobTypePredicatesCount.add(builder.equal(jobTypeJoinCount.get("name"), jobType));
            }
            finalPredicates.add(builder.or(jobTypePredicates.toArray(new Predicate[jobTypePredicates.size()])));
            finalPredicatesCount.add(builder.or(jobTypePredicatesCount.toArray(new Predicate[jobTypePredicatesCount.size()])));
        }

        List<Predicate> majorPredicates = new ArrayList<>();
        List<Predicate> majorPredicatesCount = new ArrayList<>();


        if (listNotNullAndNotEmpty(majorList)) {
            Join<Major, Job> majorJoin = job.join("major");
            Join<Major, Job> majorJoinCount = jobCount.join("major");
            for (String major : majorList) {
                majorPredicates.add(builder.equal(majorJoin.get("name"), major));
                majorPredicatesCount.add(builder.equal(majorJoinCount.get("name"), major));
            }
            finalPredicates.add(builder.or(majorPredicates.toArray(new Predicate[majorPredicates.size()])));
            finalPredicatesCount.add(builder.or(majorPredicatesCount.toArray(new Predicate[majorPredicatesCount.size()])));

        }
        // order được thêm trực tiếp vào trình tạo query động
        // nếu order là oldest thì sẽ sắp xếp theo ngày
        Order orderCriteria = builder.desc(job.get("createDate"));
        if (order.equals("oldest"))
            orderCriteria = builder.asc(job.get("createDate"));

        // sau khi đã có hết predicate thì ta sẽ thực hiện thêm AND vào giữa các predicate
        criteria.where(builder.and(finalPredicates.toArray(new Predicate[finalPredicates.size()])));
        criteria.orderBy(orderCriteria);

        // cuối cùng tạo câu query và thực thi nó để lấy list job
        List<Job> jobList = entityManager.createQuery(criteria).setFirstResult((int)pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        // tạo câu query và thực thi để lấy số lượng các job,
        // ta bắt buộc phải làm như thế vì nếu đưa count là jobList.size() thì khi truy vấn sẽ ra kết quả không đúng
        criteriaCount.select(builder.count(jobCount)).where(builder.and(finalPredicatesCount.toArray(new Predicate[finalPredicatesCount.size()])));
        Long count = entityManager.createQuery(criteriaCount).getSingleResult();

        return new PageImpl<>(jobList, pageable, count);
    }
}
