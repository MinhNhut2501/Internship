package com.r2s.findInternship.repository;

import com.r2s.findInternship.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomJobRepository {
    /* tạo một custom repository để thực hiện filter theo các tiêu chí:
    - tên,
    - tỉnh(thành),
    - vị trí làm việc,
    - loại công việc,
    - chuyên ngành
    - cách sắp xếp
    Trong đó vị trí làm việc, loại công việc và chuyên ngành gồm nhiều giá trị
    => Query: (jobPosition = 'Backend' OR jobPosition = 'Frontend')
    */
    Page<Job> filterByKeywords(String name, String province,
                               List<String> jobPositionList, List<String> jobTypeList, List<String> majorList,
                               String order, Pageable pageable);
}
