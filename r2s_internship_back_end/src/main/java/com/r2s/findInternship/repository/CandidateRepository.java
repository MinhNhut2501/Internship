package com.r2s.findInternship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    // lấy các candidate theo job
    @Query(value = "SELECT c FROM Candidate c WHERE c.id IN (SELECT a.candidate.id FROM ApplyList a WHERE a.jobApp.id = :jobId)",
            countQuery = "SELECT COUNT(c) FROM Candidate c WHERE c.id IN (SELECT a.candidate.id FROM ApplyList a WHERE a.jobApp.id = :jobId)")
    Page<Candidate> findByJobId(@Param("jobId") int jobId, Pageable pageable);

    @Query("SELECT c FROM Candidate c WHERE c.major.id = :id")
    Page<Candidate> findByMajorId(@Param("id") int id, Pageable pageable);
    @Query("SELECT c FROM Candidate c WHERE c.user.username = :username")
    Optional<Candidate> findByUserName(@Param("username") String username);

    @Query("SELECT c FROM Candidate c WHERE c.user.id = :id")
    Optional<Candidate> findByUserId(@Param("id") long id);

    @Query("SELECT c FROM Candidate c WHERE c.major.id = :id")
    List<Candidate> findByMajorName(@Param("id") int id);

    @Query("SELECT m.name, COUNT(c.id) FROM Candidate c, Major m WHERE c.major = m.id GROUP BY m.name")
    List<Object[]> statisticsByMajor();

}
