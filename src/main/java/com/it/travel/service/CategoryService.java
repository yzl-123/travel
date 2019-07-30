package com.it.travel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.travel.dao.CategoryDao;
import com.it.travel.entity.Category;
import com.it.travel.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/*
分类业务层
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    /**
     * 查询所有的分类,返回JSON格式字符串
     */
    public String findAllCategory() {
        List<Category> categoryList = categoryDao.findAllCategory();
        String categories = null;
        try {
            categories = new ObjectMapper().writeValueAsString(categoryList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return categories;
       /* //1.从redis中回去分类数据
        //Jedis jedis = JedisUtils.getJedis();
        Jedis jedis = new Jedis("119.23.11.63");
        String categories = jedis.get("categories");
        //2.如果没有得到,从mysql中获取数据
        if(categories==null){
            List<Category> categoryList = categoryDao.findAllCategory();
            //3.将数据转成JSON字符串写入redis中
            try {
                categories=new ObjectMapper().writeValueAsString(categories);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        jedis.close();
        return categories;
    }*/
    }
}
