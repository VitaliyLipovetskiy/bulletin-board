package com.lvv.bulletinboard.web.ad;

import com.lvv.bulletinboard.model.Ad;
import com.lvv.bulletinboard.service.AdService;
import com.lvv.bulletinboard.web.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final AdService service;

    public AdController(AdService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ad> getAll() {
        int userId = SecurityUtil.authId();
        log.info("getAll ads for user {}", userId);
        return service.findAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public Ad get(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("get ad {} for user {}", id, userId);
        return checkNotFoundWithId(service.get(id, userId), id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        int userId = SecurityUtil.authId();
        log.info("delete ad {} for user {}", id, userId);
        checkNotFoundWithId(service.delete(id, userId), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ad> create(@RequestBody Ad ad) {
        int userId = SecurityUtil.authId();
        log.info("create {} for user {}", ad, userId);
        checkNew(ad);
        Assert.notNull(ad, "ad must not be null");
        Ad created = service.save(ad, userId);
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
        checkNotFoundWithId(service.save(ad, userId), ad.id());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        int userId = SecurityUtil.authId();
        Ad ad = checkNotFoundWithId(service.get(id, userId), id);
        ad.setEnabled(enabled);
    }

    @GetMapping("/filter")
    public List<Ad> findAll(@RequestParam Map<String,String> param) {
        Map<String, String> filter = new HashMap<>();
        List.of("name","description","contact","email")
                .forEach(s -> {
                    if (param.get(s) != null && !param.get(s).isEmpty()) {
                        filter.put(s, param.get(s).trim());
                    }
                });
        String enable = param.get("enabled");
        if (enable != null && !enable.isEmpty()
                && (enable.equalsIgnoreCase("true") || enable.equalsIgnoreCase("false"))) {
            filter.put("enabled", enable.trim());
        }

        int pageSize = Integer.parseInt(param.getOrDefault("pageSize", "3"));
        int pageNumber = Integer.parseInt(param.getOrDefault("pageNumber", "0"));
        String order = param.getOrDefault("order", "name").toLowerCase();

        return service.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(order)), filter).toList();
    }

}
