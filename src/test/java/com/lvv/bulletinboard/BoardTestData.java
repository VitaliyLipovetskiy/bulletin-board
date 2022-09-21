package com.lvv.bulletinboard;

import com.lvv.bulletinboard.model.Board;
import com.lvv.bulletinboard.web.MatcherFactory;

import static com.lvv.bulletinboard.AdTestData.*;
import static com.lvv.bulletinboard.UserTestData.admin;
import static com.lvv.bulletinboard.UserTestData.user;

/**
 * @author Vitalii Lypovetskyi
 */
public class BoardTestData {
    public static final MatcherFactory.Matcher<Board> BOARD_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Board.class);
    public static final int BOARD_ID_1 = 1;
    public static final int BOARD_ID_2 = 2;
    public static final int BOARD_ID_3 = 3;
    public static final int NOT_FOUND = 100;

    public static final Board board1 = new Board(BOARD_ID_1, admin, ad1, true, "DESCRIPTION1");
    public static final Board board2 = new Board(BOARD_ID_2, user, ad2, true, "DESCRIPTION2");
    public static final Board board3 = new Board(BOARD_ID_3, user, ad3, false, "DESCRIPTION3");

    public static Board getNewBoard() {
        return new Board(null, ad4, true, "description4");
    }

}
