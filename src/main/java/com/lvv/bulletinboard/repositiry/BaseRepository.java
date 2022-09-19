package com.lvv.bulletinboard.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Vitalii Lypovetskyi
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {

}
