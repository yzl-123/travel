package com.it.travel.dao;

import com.it.travel.entity.Favorite;

import java.util.List;
import java.util.Map;

/**
 收藏表DAO
 */
public interface IFavoriteDao {

    /**
     通过rid和uid查询指定的收藏是否存在
     */
    Favorite findFavoriteByRidAndUserId(int rid, int uid);

    /**
     向收藏表中添加1条记录
     */
    int addFavorite(Favorite favorite);

    /**
     更新线路表中收藏的数量
     */
    int updateRouteFavoriteNum(int rid);

    /**
     查询某个用户收藏的所有线路
     @param uid 用户的id
     @param current 第几页
     @param size 每页大小
     */
    List<Map<String,Object>> findFavoriteListByPage(int uid, int current, int size);

    /**
     查询这个用户收藏的线路条数
     @param uid 用户的id
     @return
     */
    int getCount(int uid);

}
