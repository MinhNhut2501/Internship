package com.r2s.findInternship.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.r2s.findInternship.dto.PaginationDTO;
import com.r2s.findInternship.dto.UniversityCreateDTO;
import com.r2s.findInternship.dto.UniversityDTO;
import com.r2s.findInternship.dto.show.ResponeMessage;
import com.r2s.findInternship.entity.University;

public interface UniversityService {

    UniversityDTO saveFirst(UniversityCreateDTO dto);

    long count();

    boolean existsById(Integer id);

    UniversityDTO findById(Integer id);

    List<University> findAll(Sort sort);

    List<UniversityDTO> findAll(int no);

    List<UniversityDTO> findByNameContaining(String name);

    List<UniversityDTO> findByShortNameContaining(String shortName);

    List<UniversityDTO> findAll();

    PaginationDTO findAllPaging(int no, int limit);

    PaginationDTO findByNameContainingPaging(String name, int no, int limit);

    PaginationDTO findByShortNameContainingPaging(String shortName, int no, int limit);

    UniversityDTO save(UniversityCreateDTO dto);

    University getById(Integer id);

    UniversityDTO update(UniversityCreateDTO dto, int id);

    UniversityCreateDTO readJson(String value, String partner, MultipartFile fileUser, MultipartFile fileAvatar);

    List<UniversityDTO> getAllSort(String field);

    ResponeMessage deleteById(int id);

    void recover(int id);

    List<UniversityDTO> statisticsNewUniversity();

    Long getCount(String from, String to);

    List<Object[]> statisticsByStatus();

    PaginationDTO findActiveJobByProvincePagination(int provinceId, int no, int limit);


}
