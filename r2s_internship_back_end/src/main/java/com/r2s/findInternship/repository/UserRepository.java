package com.r2s.findInternship.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.r2s.findInternship.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	@Query("select c.gender, count(c.username) from User c group by c.gender")
	List<Object[]> statisticsBySex();
	@Query("SELECT count(u.username) FROM User u where  u.createDate BETWEEN :from and  :to")
	Long getCountUsernameByYear(@Param("from") LocalDate from, @Param("to") LocalDate to);
	@Query("SELECT r.name, count(r.id) FROM User c, Role r WHERE c.role.id = r.id GROUP by r.name")
	List<Object[]> statisticsByRole();
	@Query("SELECT s.name, count(u.username) from User u , Status s where u.status = s.id group by s.name")
	List<Object[]> statisticsByStatus();
	@Query(nativeQuery = true, 	value="SELECT count(c.username) FROM user c where  c.create_Date <= CURDATE() and c.create_Date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)")
	Long statisticsNewUser();
	boolean existsByUsername(String username);

	@Query("SELECT a FROM User a WHERE a.username like %:username%")
	Page<User> getUserByUserNameAndPaging(@Param("username") String username, Pageable pageable);
}
