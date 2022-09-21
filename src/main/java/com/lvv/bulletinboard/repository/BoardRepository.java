package com.lvv.bulletinboard.repository;

import com.lvv.bulletinboard.model.Board;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Vitalii Lypovetskyi
 */
@Transactional(readOnly = true)
public interface BoardRepository extends BaseRepository<Board> {
}
