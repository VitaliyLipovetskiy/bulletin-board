package com.lvv.bulletinboard.web.ad;

import com.lvv.bulletinboard.AdTestData;
import com.lvv.bulletinboard.model.Ad;
import com.lvv.bulletinboard.service.AdService;
import com.lvv.bulletinboard.util.JsonUtil;
import com.lvv.bulletinboard.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.lvv.bulletinboard.AdTestData.*;
import static com.lvv.bulletinboard.AdTestData.NOT_FOUND;
import static com.lvv.bulletinboard.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vitalii Lypovetskyi
 */
class AdControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdController.REST_URL + '/';

    @Autowired
    private AdService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(AD_MATCHER.contentJson(ad2, ad3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + AD_ID_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(AD_MATCHER.contentJson(ad1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + AD_ID_1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + AD_ID_1))
                .andExpect(status().isNoContent());
        assertNull(service.get(AD_ID_1, USER_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Ad newAd = getNewAd();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newAd)))
                .andExpect(status().isCreated());

        Ad created = AD_MATCHER.readFromJson(action);
        int newId = created.id();
        newAd.setId(newId);
        AD_MATCHER.assertMatch(created, newAd);
        AD_MATCHER.assertMatch(service.get(newId, ADMIN_ID), newAd);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Ad updated = getUpdatedAd();
        perform(MockMvcRequestBuilders.put(REST_URL + AD_ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        AD_MATCHER.assertMatch(service.get(AD_ID_2, ADMIN_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Ad invalid = new Ad(AD_ID_1, "Name", "null", "6000", null);
        perform(MockMvcRequestBuilders.put(REST_URL + AD_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + AD_ID_2)
                .param("enabled", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertTrue(service.get(AD_ID_2, ADMIN_ID).getEnabled());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void enableNotFound() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + AdTestData.NOT_FOUND)
                .param("enabled", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getFilteredAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?email=gmail"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(AD_MATCHER.contentJson(ad2, ad3));
    }
}