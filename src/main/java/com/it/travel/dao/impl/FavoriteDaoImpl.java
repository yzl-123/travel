package com.it.travel.dao.impl;

import com.it.travel.dao.BaseDao;
import com.it.travel.dao.IFavoriteDao;
import com.it.travel.entity.Favorite;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;
import java.util.Map;

/**
 收藏DAO的实现类
 */
public class FavoriteDaoImpl extends BaseDao implements IFavoriteDao {
    /**
     通过rid和uid查询指定的收藏是否存在
     */
    @Override
    public Favorite findFavoriteByRidAndUserId(int rid, int uid) {
        try {
            return template.queryForObject("select * from tab_favorite where rid=? and uid=?",
                    new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     向收藏表中添加1条记录
     */
    @Override
    public int addFavorite(Favorite favorite) {
        //rid, date, uid
        return template.update("insert into tab_favorite values (?, ?, ?)",
                favorite.getRoute().getRid(), favorite.getDate(), favorite.getUser().getUid());
    }

    /**
     更新线路表中收藏的数量
     */
    @Override
    public int updateRouteFavoriteNum(int rid) {
        return template.update("update tab_route set count = count + 1 where rid = ?",rid);
    }

    /**
     * 查询某个影忽的收藏的所有线路
     * @param uid 用户的id
     * @param current 第几页
     * @param size 每页大小
     * @return
     */
    @Override
    public List<Map<String, Object>> findFavoriteListByPage(int uid, int current, int size) {
        int begin = (current-1)*size;
        //queryForList方法,返回的就是List,其中每个元素是Map

        return template.queryForList("select * from tab_favorite f inner join tab_route r on f.rid = r.rid where f.uid = ? order by f.date desc limit ?,?",
                uid,begin,size);
    }

    /**
     * 查询这个用户的收藏线路条数
     * @param uid 用户的id
     * @return
     */
    @Override
    public int getCount(int uid) {
        return template.queryForObject("select count(*) from tab_favorite where uid = ?",int.class,uid);
    }
}
