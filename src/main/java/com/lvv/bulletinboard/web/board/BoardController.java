package com.lvv.bulletinboard.web.board;

import com.lvv.bulletinboard.model.Board;
import com.lvv.bulletinboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vitalii Lypovetskyi
 */
@RestController
@RequestMapping(value = BoardController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "board")
public class BoardController {
    static final String REST_URL = "/api/board";

    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Board> get(@PathVariable int id) {
        log.info("get board {}", id);
        return ResponseEntity.of(service.get(id));
    }


}
