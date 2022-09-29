package com.r2s.findInternship.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer>, CustomJobRepository {

    // FIND JOB ACTIVE WITH NAME PAGINATION LATEST
    @Query("SELECT j FROM Job j WHERE j.name LIKE %:name% AND j.status.id = 1 ORDER BY j.createDate DESC")
    Page<Job> findByNameContainingActivePaging(@Param("name") String name, Pageable pageable);

    // FIND JOB ACTIVE BY NAME CONTAINING AND PROVINCE PAGINATION LATEST
    // Việc phân trang bằng custom query sẽ dễ dẫn đến lỗi nhất là với các câu query có đóng mở ngoặc
    // viết thêm countQuery để đảm bảo không có lỗi xảy ra
    @Query(value = "SELECT j FROM Job j WHERE j.name LIKE %:name% AND j.locationjob.district.province.name = :province AND j.status.id = 1 ORDER BY j.createDate DESC",
            countQuery = "SELECT count(j) FROM Job j WHERE j.name LIKE %:name% AND j.locationjob.district.province.name = :province AND j.status.id = 1 ORDER BY j.createDate DESC")
    Page<Job> findByNameAndProvinceActivePaging(@Param("name") String name, @Param("province") String province, Pageable pageable);

    // FIND JOB ACTIVE PAGINATION LATEST
    @Query(value = "SELECT j FROM Job j WHERE j.status.id = 1 ORDER BY j.createDate DESC",
            countQuery = "SELECT count(j) FROM Job j WHERE j.status.id = 1 ORDER BY j.createDate DESC")
    Page<Job> findAllActivePaging(Pageable pageable);
    

    @Query(value = "SELECT j FROM Job j WHERE j.status.id = 1 AND j.locationjob.district.name = :district AND j.locationjob.district.province.name = :province ORDER BY j.createDate DESC")
    Page<Job> findByDistrictAndProvincePaging(String district, String province, Pageable pageable);

    // FIND JOB ACTIVE BY HR ID PAGINATION LATEST
    @Query("SELECT j FROM Job j WHERE j.status.id = 1 AND j.hr.id = :hrId ORDER BY j.createDate DESC")
    Page<Job> findByHrIdActivePaging(int hrId, Pageable pageable);

    // FIND JOB ACTIVE THAT CARE BY CANDIDATE ID PAGINATION
    @Query(value = "SELECT j FROM Job j WHERE j.status.id = 1 AND j.name LIKE %:name% AND j.id IN (SELECT c.jobCare.id FROM CareList c WHERE c.candidateCare.id = :candidateId)",
            countQuery = "SELECT count(j) FROM Job j WHERE j.status.id = 1 AND j.name LIKE %:name% AND j.id IN (SELECT c.jobCare.id FROM CareList c WHERE c.candidateCare.id = :candidateId)")
    Page<Job> findByNameAndCandidateCareActivePaging(String name, int candidateId, Pageable pageable);

    @Query(value = "SELECT j FROM Job j WHERE j.status.id = 1 AND j.name LIKE %:name% AND j.id IN (SELECT a.jobApp.id FROM ApplyList a WHERE a.candidate.id = :candidateId)",
            countQuery = "SELECT count(j) FROM Job j WHERE j.status.id = 1 AND j.name LIKE %:name% AND j.id IN (SELECT a.jobApp.id FROM ApplyList a WHERE a.candidate.id = :candidateId)")
    Page<Job> findByNameAndCandidateApplyActivePaging(String name, int candidateId, Pageable pageable);

    // FIND BY COMPANY ID ACTIVE
    @Query("SELECT j FROM Job j WHERE j.status.id = 1 AND j.hr.company.id = :companyId ORDER BY j.createDate DESC")
    Page<Job> findByCompanyIdActivePaging(int companyId, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.status.id = 1 AND j.hr.user.id = :userId ORDER BY j.createDate DESC")
    Page<Job> findByUserIdActivePaging(long userId, Pageable pageable);
    
    @Query("SELECT j FROM Job j WHERE j.status.id = 4 AND j.hr.user.id = :userId ORDER BY j.createDate DESC")
    Page<Job> findByUserIdDisablePaging(long userId, Pageable pageable);

    //FIND ALL BY USER NAME 
    @Query("SELECT j FROM Job j WHERE j.hr.user.id = :userId ORDER BY j.createDate DESC")
    Page<Job> findAllByUserIdPaging(long userId, Pageable pageable);
    
    // FIND BY USERNAME ACTIVE
    @Query("SELECT j FROM Job j WHERE j.status.id = 1 AND j.hr.user.username = :username ORDER BY j.createDate DESC")
    Page<Job> findByUsernameActivePaging(String username, Pageable pageable);
    // GET JOBS ACTIVE
    @Query("SELECT j FROM  Job j where j.status.id = 1  Order by j.createDate DESC")
    List<Job> getJobsActive();

    //Statistics
    @Query(nativeQuery = true, value = "SELECT count(u.id) FROM job u where  u.create_Date <= CURDATE() and u.create_Date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)")
    Long statisticsNewJob();

    // Thong ke theo thoi gian
    @Query("SELECT count(u.id) FROM Job u where  u.createDate BETWEEN :from and  :to")
    Long getCountJobByDate(@Param("from") LocalDate from, @Param("to") LocalDate to);

    // Thong ke theo status
    @Query("SELECT s.name, count(u.id) FROM Job u, Status s WHERE u.status.id = s.id GROUP BY s.name")
    List<Object[]> statisticsByStatus();

    // Get Job by Company_id
    @Query("SELECT j FROM  Job j  WHERE j.hr.id =:id  Order by j.createDate ASC")
    List<Job> getJobByCompanyId(@Param("id") int id);

    // Get Job by User_id
    @Query("SELECT j FROM  Job j  WHERE j.hr = (select hr from HR hr where hr.user.id =:id)  Order by j.createDate ASC")
    List<Job> getJobByUserId(@Param("id") long id);

    // Get Job by HR_id
    @Query("SELECT j FROM  Job j  WHERE j.hr = (select hr from HR hr where hr.user = ( select u from User u where u.username  =:username))  Order by j.createDate ASC")
    List<Job> getJobByUserName(@Param("username") String username);


    //Thong ke theo job_position
    @Query("SELECT jb.name, count(j.id) FROM Job j, JobPosition jb WHERE j.jobposition.id = jb.id GROUP BY jb.name")
    List<Object[]> statisticsByJobPosition();

    //Thong ke theo job_position
    @Query("SELECT m.name, count(j.id) FROM Job j, Major m WHERE j.major = m.id GROUP BY m.name")
    List<Object[]> statisticsByMajor();


}
