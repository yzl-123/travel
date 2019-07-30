package com.it.travel.web.servlet;

import com.it.travel.entity.Favorite;
import com.it.travel.entity.PageBean;
import com.it.travel.entity.User;
import com.it.travel.service.IFavoriteService;
import com.it.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/favorite")
public class FavoriteServlet extends BaseServlet{

    private IFavoriteService favoriteService = new FavoriteServiceImpl();

    //查询某个用户1页的线路
    protected void findFavoriteByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //得到第几页
        int current = Integer.parseInt(request.getParameter("current"));

        //从会话中得到uid
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        //如果为空,表示会话过期
        if(user == null){
            out.print(false);
        }
        else{
            //调用业务类得到pageBean对象
            int uid = user.getUid();
            PageBean<Favorite> pageBean = favoriteService.getPageBean(uid,current);

            //转成JSON打印大浏览器
            String value = mapper.writeValueAsString(pageBean);
            out.print(value);
        }
    }
}
