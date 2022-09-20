package com.lvv.bulletinboard;

import com.lvv.bulletinboard.model.Role;
import com.lvv.bulletinboard.model.User;
import com.lvv.bulletinboard.util.JsonUtil;
import com.lvv.bulletinboard.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

/**
 * @author Vitalii Lypovetskyi
 */
public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);

    public static User getNewUser() {
        return new User(null, "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdatedUser() {
        return new User(USER_ID, USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
