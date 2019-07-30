package com.it.travel.service;

import com.it.travel.entity.Favorite;
import com.it.travel.entity.PageBean;
import com.it.travel.entity.User;

/**
 收藏的业务层接口
 */
public interface IFavoriteService {

    /*
    通过rid和uid判断线路是否收藏，如果收藏返回true
     */
    boolean isFavoriteByRidAndUserId(int rid, int uid);

    /**
     添加收藏
     */
    void addFavorite(int rid, User user);

    /**
     查询收藏的数据封装到PageBean对象
     */
    PageBean<Favorite> getPageBean(int uid, int current);

}
