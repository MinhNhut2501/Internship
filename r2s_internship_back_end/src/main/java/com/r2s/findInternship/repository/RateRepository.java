package com.r2s.findInternship.repository;

import com.r2s.findInternship.entity.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
    // lấy toàn bộ các đánh giá còn hiệu lực theo công ty
    @Query("SELECT r FROM Rate r WHERE r.company.id = :companyId AND r.status.id = 1")
    Page<Rate> findAllByCompanyIdPagingActive(int companyId, Pageable pageable);

    @Query("SELECT r FROM Rate r WHERE r.status.id = 1")
    List<Rate> findAllActive();

    @Query("SELECT r FROM Rate r WHERE r.user.username = ?1 AND r.company.id = ?2")
    Optional<Rate> findByUserAndCompany(String username, int companyId);

    @Query("SELECT r FROM Rate r WHERE r.user.username = ?1 AND r.company.id = ?2 AND r.status.id = 1")
    Optional<Rate> findByUserAndCompanyActive(String username, int companyId);
    
    // Find rate by companyId pagination
    @Query("SELECT n FROM Rate n WHERE n.company.id = companyId")
    Page<Rate> findByCompanyIdPagination(int companyId, Pageable pageable);
    
    
}
