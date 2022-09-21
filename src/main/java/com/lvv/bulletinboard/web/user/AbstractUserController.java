package com.lvv.bulletinboard.web.user;

import com.lvv.bulletinboard.model.User;
import com.lvv.bulletinboard.repository.UserRepository;
import com.lvv.bulletinboard.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author Vitalii Lypovetskyi
 */
@Slf4j
public abstract class AbstractUserController {

    protected final UserRepository repository;

    protected UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    protected AbstractUserController(UserRepository repository, UniqueMailValidator emailValidator) {
        this.repository = repository;
        this.emailValidator = emailValidator;
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}
