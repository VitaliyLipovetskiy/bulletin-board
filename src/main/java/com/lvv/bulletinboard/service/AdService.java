package com.lvv.bulletinboard.service;

import com.lvv.bulletinboard.model.Ad;
import com.lvv.bulletinboard.repository.AdRepository;
import com.lvv.bulletinboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public AdService(AdRepository adRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    public Ad get(int id, int userId) {
        return adRepository.findById(id)
                .filter(ad -> ad.getUser().id() == userId)
                .orElse(null);
    }

    public List<Ad> findAllByUserId(int userId) {
        return adRepository.getAll(userId);
    }

    public Page<Ad> findAll(PageRequest page, Map<String, String> filter) {
        if (filter.isEmpty()) {
            return adRepository.findAll(page);
        }
        return adRepository.findAll(specification(filter), page);
    }

    public boolean delete(int id, int userId) {
        return adRepository.delete(id, userId) != 0;
    }

    @Transactional
    public Ad save(Ad ad, int userId) {
        if (!ad.isNew() && get(ad.id(), userId) == null) {
            return null;
        }
        ad.setUser(userRepository.getReferenceById(userId));
        return adRepository.save(ad);
    }

    private Specification<Ad> specification(Map<String, String> filter) {
        return new Specification<Ad>() {
            @Override
            public Predicate toPredicate(Root<Ad> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                List<Predicate> criteria = new ArrayList<>();
                String strFilter = "name";
                if (filter.get(strFilter) != null) {
                    criteria.add(cb.like(root.get(strFilter), "%" + filter.get(strFilter) + "%"));
                }
                strFilter = "description";
                if (filter.get(strFilter) != null) {
                    criteria.add(cb.like(root.get(strFilter), "%" + filter.get(strFilter) + "%"));
                }
                strFilter = "contact";
                if (filter.get(strFilter) != null) {
                    criteria.add(cb.like(root.get(strFilter), "%" + filter.get(strFilter) + "%"));
                }
                strFilter = "enabled";
                if (filter.get(strFilter) != null) {
                    criteria.add(cb.equal(root.get(strFilter).as(Boolean.class), Boolean.valueOf(filter.get(strFilter))));
                }
                strFilter = "email";
                if (filter.get(strFilter) != null) {
                    criteria.add(cb.like(root.get("user").get(strFilter), "%" + filter.get(strFilter) + "%"));
                }
                return cb.and(criteria.toArray(new Predicate[0]));
            }
        };
    }

}
