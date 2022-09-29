package com.r2s.findInternship.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.HRApplyList;

@Repository
public interface HRApplyListRepository extends JpaRepository<HRApplyList, Integer> {
    @Query("SELECT h FROM HRApplyList h WHERE h.status.id = 1")
    Page<HRApplyList> findAllActive(Pageable pageable);
}
