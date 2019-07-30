package com.it.travel.dao;

import com.it.travel.entity.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/*
分类
 */
public class CategoryDao extends BaseDao {
    /**
     查询所有的分类
     */
    public List<Category> findAllCategory(){
        return  template.query("SELECT * FROM tab_category ORDER BY cid=5",new BeanPropertyRowMapper<>(Category.class));
    }
 }
