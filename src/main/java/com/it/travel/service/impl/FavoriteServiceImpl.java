package com.it.travel.service.impl;

import com.it.travel.dao.IFavoriteDao;
import com.it.travel.dao.impl.FavoriteDaoImpl;
import com.it.travel.entity.Favorite;
import com.it.travel.entity.PageBean;
import com.it.travel.entity.Route;
import com.it.travel.entity.User;
import com.it.travel.service.IFavoriteService;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteServiceImpl implements IFavoriteService {
    //面向接口编程
    private IFavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavoriteByRidAndUserId(int rid, int uid) {
        return favoriteDao.findFavoriteByRidAndUserId(rid,uid)!=null;
    }

    /**
     * 添加收藏
     * @param rid
     * @param user
     */
    @Override
    public void addFavorite(int rid, User user) {
        Favorite favorite = new Favorite();
        //创建线路对象
        Route route = new Route();
        route.setRid(rid);

        favorite.setRoute(route);  //封装了线路
        favorite.setUser(user); //封装了用户
        favorite.setDate(new Date(System.currentTimeMillis())); //封装现在的时间

        //在业务层调用了2次DML的操作:增删改操作,必须使用事务
        //调用DAO层两个方法
        favoriteDao.addFavorite(favorite);
        //更新线路的数量
        favoriteDao.updateRouteFavoriteNum(rid);
    }

    /**
     * 查询收藏的数据封装到PageBean
     * 需要将List<Map>重新封装List<Favorite>每个Favorite中都包含了一个Route对象
     * @param uid
     * @param current
     * @return
     */
    @Override
    public PageBean<Favorite> getPageBean(int uid, int current) {
        //实例化对象
        PageBean<Favorite> pageBean = new PageBean<>();

        pageBean.setSize(4);//每页显示4条
        pageBean.setCurrent(current); //当前第几页

        //调用DAO层得到两张表:收藏,线路的信息
        List<Map<String,Object>> list=favoriteDao.findFavoriteListByPage(uid,current,pageBean.getSize());

        //调用DAO层得到两行表:收藏,线路的信息
        List<Favorite> favorites = new ArrayList<>();
        //遍历每个元素,将每个Map封装成Favroite对象
        for (Map<String, Object> map : list) {
            //创建Favorite对象
            Favorite favorite = new Favorite();
            Route route = new Route();
            try {
                BeanUtils.populate(favorite,map);
                BeanUtils.populate(route,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //再把Route对象放到Favrorite中
            favorite.setRoute(route);
            //把每个元素在添加到favorites去
            favorites.add(favorite);
        }

        //封装data属性
        pageBean.setData(favorites);

        //调用DAO查询用户收藏的数量
        int count = favoriteDao.getCount(uid);
        pageBean.setCount(count);

        return pageBean;
    }
}
