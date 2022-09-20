package com.lvv.bulletinboard.web;

import com.lvv.bulletinboard.model.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serial;

/**
 * @author Vitalii Lypovetskyi
 */
@Getter
@ToString(of = "user")
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

}
