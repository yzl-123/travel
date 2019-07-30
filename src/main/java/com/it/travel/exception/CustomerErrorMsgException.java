package com.it.travel.exception;

/*
用户自定义异常没显示错误的信息,只是起到语义的作用
 */
public class CustomerErrorMsgException extends Exception{
    public CustomerErrorMsgException(String message){
        super(message);
    }
}
