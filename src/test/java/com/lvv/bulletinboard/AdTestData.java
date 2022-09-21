package com.lvv.bulletinboard;

import com.lvv.bulletinboard.model.Ad;
import com.lvv.bulletinboard.web.MatcherFactory;


/**
 * @author Vitalii Lypovetskyi
 */
public class AdTestData {
    public static final MatcherFactory.Matcher<Ad> AD_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Ad.class, "user");
    public static final int AD_ID_1 = 1;
    public static final int AD_ID_2 = 2;
    public static final int AD_ID_3 = 3;
    public static final int AD_ID_4 = 4;
    public static final int NOT_FOUND = 100;

    public static final Ad ad1 = new Ad(AD_ID_1, "name1", "Description1", "Contact1", "Image1");
    public static final Ad ad2 = new Ad(AD_ID_2, "name2", "Description2", "Contact2", "Image2");
    public static final Ad ad3 = new Ad(AD_ID_3, "name3", "Description3", "Contact3", "Image3");
    public static final Ad ad4 = new Ad(AD_ID_4, "name4", "Description4", "Contact4", "Image4");

    public static Ad getNewAd() {
        return new Ad(null, "New", "newDescription", "newContact", "newImage");
    }

    public static Ad getUpdatedAd() {
        return new Ad(AD_ID_2, ad2.getName(), "Обновленный завтрак", "200", null);
    }
}
