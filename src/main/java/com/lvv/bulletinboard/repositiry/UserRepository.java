package com.lvv.bulletinboard.repositiry;

import com.lvv.bulletinboard.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Vitalii Lypovetskyi
 */
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}
