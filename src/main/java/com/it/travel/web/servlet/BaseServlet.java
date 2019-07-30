package com.it.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 没有访问地址,它作为其他Servlet的父类
 */
public class BaseServlet extends HttpServlet {

    protected ObjectMapper mapper = new ObjectMapper();

    //重写service方法
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1个方法的名字,和真实参数,就可以调用这个方法
        //得到提交的action的参数,决定调用哪个方法
        String action = request.getParameter("action");
        //判断action是哪个方法
        //1.得到类对象
        Class<? extends Servlet> clazz = this.getClass();
        //UserServlet.class
        try{
            //2.通过类对象得到方法对象:方法名,1个或多个参数的类型
            Method method = clazz.getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);
            //3.通过对象调用犯法:调用方法的独享,实参的值
            method.invoke(this,request,response);
            System.out.println("调用了: " + clazz+"类的方法:" + method.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
