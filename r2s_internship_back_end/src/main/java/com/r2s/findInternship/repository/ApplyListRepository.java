package com.r2s.findInternship.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.ApplyList;

@Repository
public interface ApplyListRepository extends JpaRepository<ApplyList, Integer>{
	// lấy các applylist theo candidate id và sắp xếp theo mới nhất
	@Query("SELECT a FROM  ApplyList a WHERE a.candidate.id = :id Order by a.createDate DESC")
	List<ApplyList> getApplyListByCandidateId(@Param("id") int id);

	// lấy các applylist thoe username và sắp xếp theo mới nhất
	@Query("SELECT a FROM ApplyList a WHERE a.candidate.user.username = :username ORDER BY a.createDate DESC")
	Page<ApplyList> findByUsername(@Param("username") String username, Pageable pageable);
	
	//get applies by jobId
	@Query("SELECT a FROM  ApplyList a WHERE a.jobApp.id = :id Order by a.createDate DESC")
	List<ApplyList> getApplyListByJobId(@Param("id") int id);
	
	//get num of apply by jobid
	@Query("SELECT COUNT(*) FROM  ApplyList a WHERE a.jobApp.id = :id ")
	int getNumOfApplyByJobId(@Param("id") int id);
	
}

