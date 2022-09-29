package com.r2s.findInternship.service;

import java.util.List;

import com.r2s.findInternship.dto.JobDTO;
import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.show.JobDTOShow;
import com.r2s.findInternship.dto.show.ResponeMessage;

public interface JobService {

    PaginationDTO findAllActiveJobPaging(int no, int limit);

    PaginationDTO searchActiveJobByNameAndProvincePaging(String name, String province, int no, int limit);

    PaginationDTO findAllActiveJobByHRIdPaging(int hrId, int no, int limit);

    PaginationDTO findAllJobPaging(int no, int limit);

    PaginationDTO findAllActiveJobByCompanyIdPaging(int companyId, int no, int limit);

    PaginationDTO findAllActiveJobByUserIdPaging(long userId, int no, int limit);

    PaginationDTO findAllActiveJobByUsernamePaging(String username, int no, int limit);
    
    PaginationDTO findAllJobByUserIdPaging(long userId, int no, int limit);
	
	PaginationDTO findDisableJobByUserIdPaging(long userId, int no, int limit);

    PaginationDTO findActiveJobByDistrictAndProvincePaging(String district, String province, int no, int limit);

    PaginationDTO searchActiveJobByNameAndCandidateCarePaging(String name, int candidateId, int no, int limit);

    PaginationDTO searchActiveJobByNameAndCandidateApplyPaging(String name, int candidateId, int no, int limit);

    PaginationDTO filterPaging(String name, String province, List<String> jobTypeList, List<String> jobPositionList, List<String> majorList, String order, int no, int limit);

    ResponeMessage deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    JobDTO findById(Integer id);

    JobDTOShow findByIdToShow(Integer id);

    List<JobDTO> findAllToShowClient();

    List<JobDTO> findAll();

    JobDTO save(JobDTO entity);

    JobDTO update(int id, JobDTO dto);

    JobDTO changeStatus(int id, JobDTO dto);

    Long getCount(String from, String to);

    List<Object[]> statisticsByStatus();

    Long statisticsNewJob();

    List<JobDTO> getJobByCompanyId(Integer id);

    List<JobDTO> getJobByUserId(long id);

    List<JobDTO> getJobByUserName(String username);

    List<Object[]> statisticsByMajor();

    List<Object[]> statisticsByJobPosition();

}
