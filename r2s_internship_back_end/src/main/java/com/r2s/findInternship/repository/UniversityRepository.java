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

import com.r2s.findInternship.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
    Page<University> findByShortNameContaining(String shortName, Pageable pageable);

    Page<University> findByNameContaining(String name, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT distinct u.* FROM university u, university_location u_l, location l, district d " +
                    "WHERE u_l.university_id = u.id " +
                    "and u_l.location_id=l.id " +
                    "and l.district_id = d.id " +
                    "and d.province_id = :provinceId",
            countQuery = "SELECT count(distinct u.*) FROM university u, university_location u_l, location l, district d " +
                    "WHERE u_l.university_id = u.id " +
                    "and u_l.location_id=l.id " +
                    "and l.district_id = d.id " +
                    "and d.province_id = :provinceId")
    Page<University> findByProvinceId(int provinceId, Pageable pageable);

    Optional<University> findByName(String name);

    Optional<University> findByShortName(String shortName);

    List<University> findByNameContaining(String name);

    List<University> findByShortNameContaining(String shortName);

    boolean existsByEmail(String email);

    Optional<University> findByEmail(String email);

    Optional<University> findByWebsite(String website);

    boolean existsByWebsite(String website);

    boolean existsByShortName(String website);

    boolean existsByName(String name);

    //statistics
    //Thong ke truong DH vua dang ky
    @Query(nativeQuery = true, value = "SELECT u.* FROM university u where  u.create_date <= CURDATE() and u.create_date >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)")
    List<University> statisticsNewUniversity();

    //Thong ke theo thoi gian
    @Query("SELECT count(u.id) FROM University u where  u.createDate BETWEEN :from and  :to")
    Long getCountUsernameByDate(@Param("from") LocalDate from, @Param("to") LocalDate to);

    //Thong ke theo status
    @Query("SELECT s.name, count(u.id) FROM University u, Status s WHERE u.status = s.id GROUP BY s.name")
    List<Object[]> statisticsByStatus();

    //Find by city id
    @Query(nativeQuery = true, value = "SELECT distinct u.*\r\n"
            + "FROM university u, university_location u_l, location l, district d\r\n"
            + "WHERE u_l.university_id = u.id \r\n"
            + "and u_l.location_id=l.id \r\n"
            + "and l.district_id = d.id \r\n"
            + "and d.province_id = :id")
    List<University> findActiveUniversityByProvince(@Param("id") int idCity);


}
