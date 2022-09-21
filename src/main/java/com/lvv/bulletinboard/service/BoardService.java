package com.lvv.bulletinboard.service;

import com.lvv.bulletinboard.model.Board;
import com.lvv.bulletinboard.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Vitalii Lypovetskyi
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }
    public Optional<Board> get(int id) {
        return repository.findById(id);
    }

}
