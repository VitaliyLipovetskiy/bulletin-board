package com.lvv.bulletinboard.web.ad;

import com.lvv.bulletinboard.model.Ad;
import com.lvv.bulletinboard.repositiry.AdRepository;
import com.lvv.bulletinboard.web.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.lvv.bulletinboard.util.validation.ValidationUtil.*;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = AdController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "ads")
public class AdController {
    static final String REST_URL = "/api/ads";
    private final AdRepository adRepository;

    public AdController(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @GetMapping
    public List<Ad> getAll() {
        int userId = SecurityUtil.authId();
        log.info("getAll ads for user {}", userId);
        return adRepository.getAll(userId);
    }

    @GetMapping("/{id}")
    public Ad get(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("get ad {} for user {}", id, userId);
        return checkNotFoundWithId(adRepository.get(id, userId), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("delete ad {} for user {}", id, userId);
        checkNotFoundWithId(adRepository.delete(id, userId), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ad> create(@RequestBody Ad ad) {
        int userId = SecurityUtil.authId();
        log.info("create {} for user {}", ad, userId);
        checkNew(ad);
        Assert.notNull(ad, "ad must not be null");
        Ad created = adRepository.save(ad, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Ad ad, @PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("update ad {} for user {}", id, userId);
        assureIdConsistent(ad, id);
        Assert.notNull(ad, "ad must not be null");
        checkNotFoundWithId(adRepository.save(ad, userId), ad.id());
    }
}
