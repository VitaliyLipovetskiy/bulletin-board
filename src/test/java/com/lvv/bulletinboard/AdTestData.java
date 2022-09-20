package com.lvv.bulletinboard;

import com.lvv.bulletinboard.model.Ad;
import com.lvv.bulletinboard.model.User;
import com.lvv.bulletinboard.util.JsonUtil;
import com.lvv.bulletinboard.web.MatcherFactory;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static com.lvv.bulletinboard.UserTestData.*;

/**
 * @author Vitalii Lypovetskyi
 */
public class AdTestData {

    public static final MatcherFactory.Matcher<Ad> AD_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Ad.class, "user");
    public static final int AD_ID_1 = 1;
    public static final int AD_ID_2 = 2;
    public static final int AD_ID_3 = 3;
    public static final int NOT_FOUND = 100;
//    public static final String USER_MAIL = "user@yandex.ru";
//    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final Ad ad1 = new Ad(AD_ID_1, "name1", "Description1", "Contact1", "Image1");
    public static final Ad ad2 = new Ad(AD_ID_2, "name2", "Description2", "Contact2", "Image2");
    public static final Ad ad3 = new Ad(AD_ID_3, "name3", "Description3", "Contact3", "Image3");

    public static Ad getNewAd() {
        return new Ad(null, "New", "newDescription", "newContact", "newImage");
    }

    public static Ad getUpdatedAd() {
        return new Ad(AD_ID_2, ad2.getName(), "Обновленный завтрак", "200", null);
    }
}
