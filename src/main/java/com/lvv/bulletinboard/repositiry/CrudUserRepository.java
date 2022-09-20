package com.lvv.bulletinboard.repositiry;

import com.lvv.bulletinboard.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Vitalii Lypovetskyi
 */
public interface CrudUserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}
