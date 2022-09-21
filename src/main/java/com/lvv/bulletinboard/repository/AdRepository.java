package com.lvv.bulletinboard.repository;

import com.lvv.bulletinboard.model.Ad;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vitalii Lypovetskyi
 */
//@Transactional(readOnly = true)
public interface AdRepository extends PagingAndSortingRepository<Ad, Integer>, JpaSpecificationExecutor<Ad> {

    @Query("SELECT a FROM Ad a WHERE a.user.id=:userId")
    List<Ad> getAll(@Param("userId") int userId);

    @Query("SELECT a FROM Ad a WHERE a.user.id=:userId AND a.id=:id")
    Ad get(@Param("id") int id, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ad a WHERE a.user.id=:userId AND a.id=:id")
    int delete(@Param("id") int id, @Param("userId") int userId);
}
