package com.hchenpan.exception;

/**
 * Project : WarehouseManagement
 * ClassName : com.hchenpan.exception.CustomException
 * Description :
 *
 * @author Huangcp
 * @version 1.0
 * @date 2019/3/1 08:21 下午
 **/
public class CustomException extends Exception {

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}