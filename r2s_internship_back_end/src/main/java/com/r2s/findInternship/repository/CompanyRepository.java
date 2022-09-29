package com.r2s.findInternship.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.r2s.findInternship.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	List<Company> findByNameContaining(String name);

	Optional<Company> findByTax(String tax);

	@Transactional
	@Modifying
	@Query(value = "insert into company_location (location_id, company_id) VALUES (?1, ?2)", nativeQuery = true)
	void insertIntoFruitsTable(int locationid, int companyid);
	
	
	//get companies active
	@Query("SELECT c FROM  Company c where c.status.id = 1   Order by c.name ASC ")
	List<Company> getCompaniesActive();

	// statistics
	// Thong ke Company vua dang ky
	@Query(nativeQuery = true, value = "SELECT count(u.id) FROM company u where  u.date <= CURDATE() and u.date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)")
	Long statisticsNewCompany();

	// Thong ke theo thoi gian
	@Query("SELECT count(u.id) FROM Company u where  u.date BETWEEN :from and  :to")
	Long getCountCompanyByDate(@Param("from") LocalDate from, @Param("to") LocalDate to);

	// Thong ke theo status
	@Query("SELECT s.name, count(u.id) FROM Company u, Status s WHERE u.status = s.id GROUP BY s.name ")
	List<Object[]> statisticsByStatus();
	
	// Lay Company theo job id
	@Query("SELECT c FROM  Company c  WHERE c.id = (select  hr.company.id from HR hr where hr.id =(select j.hr.id from Job j where id =:id)) Order by c.name ASC" )
	Company getCompanyByJobId(@Param("id") int id);
}
