package com.it.travel.web.servlet;

import com.it.travel.entity.PageBean;
import com.it.travel.entity.Route;
import com.it.travel.entity.User;
import com.it.travel.service.RouteService;
import com.it.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/route")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteService();
    private FavoriteServiceImpl favoriteService = new FavoriteServiceImpl();
    //查询收藏人数最多的6个旅游的线路
    protected void findRoutesByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置内容类型为JSON
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //从浏览器得到cid
        int cid = Integer.parseInt(request.getParameter("cid"));

        //2.调用业务得到6条线路
        List<Route> routes = routeService.findRoutes(cid);

        //3.转成JSON对象
        String value = mapper.writeValueAsString(routes);
        //4.打印到浏览器
        out.print(value);
    }
    /*
    查询一页的数据
     */
    protected void findRouteListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //1.会话域得到cid
     int cid = Integer.parseInt(request.getParameter("cid"));
     //2.得到第二页
        int current = Integer.parseInt(request.getParameter("current"));
        String rname = request.getParameter("rname");
        //3.调用业务层得到PageBean
        PageBean pageBean = routeService.getPageBean(cid,current,rname);
        //4.转成JSON打印到浏览器
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        out.print(mapper.writeValueAsString(pageBean));
    }
    protected void findRouteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.得到提交的rid参数值
        int rid = Integer.parseInt(request.getParameter("rid"));
        //2.调用业务层得到线路对象
        Route route = routeService.findRouteByRid(rid);
        //3.转成JSON打印到浏览器
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(route));
    }
    protected void isFavoriteByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.如果当前用户没有登录直接返回false,如果返回的是false,则表示按钮可用
        if(user==null){
            out.print(false); //收藏按钮可用
        }
        else{
            //2.如果当前用户登录了,获取当前旅游线路rid和登录用户uid判断是否已收藏
            int rid = Integer.parseInt(request.getParameter("rid"));
            int uid = user.getUid();

            //如果收藏就返回true
            boolean flag = favoriteService.isFavoriteByRidAndUserId(rid,uid);
            //3.如果已经收藏返回true,否侧返回false
            out.print(flag);
        }
    }
    //添加收藏
    protected void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //1.从会话中得到用户对象,如果用户对象为空,则输出false,请求结束
        HttpSession session = request.getSession();
        //2.从会话中得到User对象
        User user = (User) session.getAttribute("user");
        if(user == null){
            out.print(false);

        }else{
            //3.通过提交的参数得到线路的rid
            int rid = Integer.parseInt(request.getParameter("rid"));
            //4.传递rid和User对象,调用添加收藏对象的方法
            favoriteService.addFavorite(rid,user);
            //5.调用业务层的方法:通过rid得到当前线路对象.
            Route route = routeService.findRouteByRid(rid);
            //输出线路的对象的count值到浏览器
            out.print(route.getCount());//最新的数量
        }
    }
    //查询收藏排行榜
    private void findRoutesFavoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到用户提交的当前页号
        int current = Integer.parseInt(request.getParameter("current"));

        //封装查询的条件
        Map<String, String> condition = new HashMap<>();
        condition.put("rname", request.getParameter("rname"));
        condition.put("startPrice", request.getParameter("startPrice"));
        condition.put("endPrice", request.getParameter("endPrice"));

        //调用业务层得到PageBean
        PageBean<Route> pageBean = routeService.getPageBeanByFavoriteRank(current, condition);

        //转成JSON打印
        String value = mapper.writeValueAsString(pageBean);

        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(value);
    }
}
