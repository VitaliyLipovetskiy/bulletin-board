package com.lvv.bulletinboard.util.exception;

/**
 * @author Vitalii Lypovetskyi
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
        System.out.println("NotFoundException " + message);
    }
}
