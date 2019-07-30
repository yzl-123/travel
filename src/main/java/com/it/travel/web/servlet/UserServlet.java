package com.it.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.travel.entity.User;
import com.it.travel.exception.CustomerErrorMsgException;
import com.it.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 用户管理的控制器
 */
@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserService();

    /**
     退出
     @param request
     @param response
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //得到会话
        HttpSession session = request.getSession();
        //销毁会话
        session.invalidate();
        //重定向到login.html
        response.sendRedirect("login.html");
    }

    /**
     判断用户的登录状态
     @param request
     @param response
     */
    private void getLoginUserData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        //判断会话域中是否有用户的信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user !=null) {  //表示已经登录
            String value = mapper.writeValueAsString(user);
            out.print(value);
        }
        else {
            out.print(false);  //没有登录打印false
        }
    }

    /**
     登录的方法
     @param request
     @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        //1.判断验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute("vcode");
        if (check.equalsIgnoreCase(vcode)) {
            //2.判断用户名和密码是否正确
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                User login = userService.login(username, password);
                //没有异常表示登录成功
                //把用户信息保存到会话域中
                session.setAttribute("user",login);
                //3.如果正确，返回success
                out.print("success");
            } catch (CustomerErrorMsgException e) {
                //得到异常中错误信息
                out.print(e.getMessage());
            }
        }
        else {
            //不相等
            out.print("验证码错误");
        }
    }

    /**
     注册的操作
     @param request
     @param response
     @throws Exception
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        //1. 判断验证码
        //从会话域中取验证码
        HttpSession session = request.getSession();
        String vcode = (String) session.getAttribute("vcode");
        //得到用户提交的验证码
        String check = request.getParameter("check");
        if (check.equalsIgnoreCase(vcode)) {
            //2. 判断用户名是否存在
            String username = request.getParameter("username");
            if (userService.isUserExists(username)) {
                //存在
                out.print("用户名已存在");
            }
            else {
                //3. 注册写到数据库中
                User user = new User();
                Map<String, String[]> map = request.getParameterMap();//得到表单所有的数据
                try {
                    BeanUtils.populate(user, map);  //封装数据
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //如果大于就注册成功
                int row = userService.register(user);
                if (row > 0) {
                    out.print("success");
                }
                else {
                    throw new RuntimeException("注册失败");
                }
            }
        }
        else {
            //打印信息给浏览器
            out.print("验证码错误");
        }
    }

}
