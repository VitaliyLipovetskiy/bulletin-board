package com.lvv.bulletinboard.repository;

import com.lvv.bulletinboard.model.User;

import java.util.Optional;

/**
 * @author Vitalii Lypovetskyi
 */
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}
