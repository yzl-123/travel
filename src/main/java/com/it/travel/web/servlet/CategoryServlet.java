package com.it.travel.web.servlet;

import com.it.travel.entity.Category;
import com.it.travel.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet {

  private CategoryService categoryService = new CategoryService();

  //查询所有的分类
    private  void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应类型为JSON
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        //调用业务层得到JSON分类
        String category = categoryService.findAllCategory();
        //打印给浏览器
        out.print(category);
    }
}
