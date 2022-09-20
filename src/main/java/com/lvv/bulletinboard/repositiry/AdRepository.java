package com.lvv.bulletinboard.repositiry;

import com.lvv.bulletinboard.model.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.lvv.bulletinboard.util.validation.ValidationUtil.checkNotFoundWithId;

/**
 * @author Vitalii Lypovetskyi
 */
@Repository
@Transactional(readOnly = true)
public class AdRepository {

    private final CrudAdRepository adRepository;
    private final CrudUserRepository userRepository;

    public AdRepository(CrudAdRepository adRepository, CrudUserRepository userRepository) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    public List<Ad> getAll(int userId) {
        return adRepository.getAll(userId);
    }

    public Ad get(int id, int userId) {
        Ad result = adRepository.findById(id)
                .filter(ad -> ad.getUser().id() == userId)
                .orElse(null);
        return result;
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
}
