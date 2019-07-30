package com.it.travel.dao;

import com.it.travel.utils.DataSourceUtils;
import org.springframework.jdbc.core.JdbcTemplate;


public class BaseDao {
   //实例化模板对象,子类或同一个可以使用
    protected JdbcTemplate template = new JdbcTemplate(DataSourceUtils.getDataSource());
}
