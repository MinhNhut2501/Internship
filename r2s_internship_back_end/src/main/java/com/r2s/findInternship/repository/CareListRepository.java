package com.r2s.findInternship.repository;

import com.r2s.findInternship.entity.CareList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareListRepository extends JpaRepository<CareList, Integer> {
    @Query("SELECT case WHEN count(c) > 0 THEN TRUE ELSE FALSE END FROM CareList c WHERE c.candidateCare.user.username = :username AND c.jobCare.id = :jobId")
    boolean existsByUsernameAndJobId(@Param("username") String username, @Param("jobId") int jobId);
    @Query("SELECT c FROM CareList c WHERE c.candidateCare.user.username = :username")
    List<CareList> findAllByUsername(@Param("username") String username);
    @Query("SELECT c FROM CareList c WHERE c.candidateCare.user.username = :username")
    Page<CareList> findAllByUsername(@Param("username") String username, Pageable pageable);

    @Query("SELECT c FROM CareList c WHERE c.candidateCare.id = :candidateId")
    Page<CareList> findAllByCandidateId(@Param("candidateId") int candidateId, Pageable pageable);

    @Query("SELECT c FROM CareList c WHERE c.candidateCare.user.username = :username AND c.jobCare.id = :jobId")
    Optional<CareList> findByUsernameAndJobId(@Param("username") String username, @Param("jobId") int jobId);
}
