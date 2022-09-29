package com.r2s.findInternship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.r2s.findInternship.entity.DemandUni;
@Repository
public interface DemandUniRepository extends JpaRepository<DemandUni, Integer> {
	@Query("SELECT DISTINCT(d) FROM DemandUni d, Partner p, University u WHERE d.partner = p.id and p.university = u.id and u.id = :id")
	Page<DemandUni> findAllByUniversityId(@Param("id") int id, Pageable pageable);

	Page<DemandUni> findAllByNameContaining(String name, Pageable pageable);

	Page<DemandUni> findById(int id, Pageable pageable);
}
