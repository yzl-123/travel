package com.it.travel.service;

import com.it.travel.dao.RouteDao;
import com.it.travel.entity.*;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.Map;

/*
线路的业务层
 */
public class RouteService {
    private RouteDao routeDao = new RouteDao();

    /**
     按cid,查询收藏的人数最多
     */
    public List<Route> findRoutes(int cid){
        return routeDao.findRoutes(cid);
    }

    /**
     * 分页
     * @param cid
     * @param current
     * @param rname
     * @return
     */
    public PageBean getPageBean(int cid ,int current,String rname){
        //1.创建PageBean对象
        PageBean<Route> pageBean = new PageBean<>();

        //2.由用户提交的2个属性:current,size
        pageBean.setCurrent(current);
        pageBean.setSize(3);

        //3.从数据库查询抖抖傲2个属性:data,count
        List<Route> data = routeDao.getRoutesByPage(cid,current,pageBean.getSize(),rname);
        int count = routeDao.getCountByCid(cid,rname);

        pageBean.setData(data);
        pageBean.setCount(count);
        //4,其中4个属性是通过计算得来的
        return  pageBean;
    }

    /**
      将DAO层查询的4张表的数据重新封装成一个Route对象
     */
    public Route findRouteByRid(int rid){
        //1.调用DAO,得到三张表的map数据
        Map<String,Object> map = routeDao.findRouteByRid(rid);

        //2.复制map中的数据,到分类对象,商家对象,线路对象
        Route route= new Route();
        Category category = new Category();
        Seller seller = new Seller();

        try {
            BeanUtils.populate(route,map); //复制了线路
            BeanUtils.populate(category,map); //复制了分类
            BeanUtils.populate(seller,map); //复制了商家
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3.将封装好数据的分类和商家数值成线路的属性
        route.setCategory(category);
        route.setSeller(seller);

        //4.电泳DAO,得到线路图表多条数据
        List<RouteImg> imgs = routeDao.findRouteImgsByRid(rid);

        //5.封装到Route中
        route.setRouteImgList(imgs);

        return route;
    }
    /**
     查询收藏排行榜1页数据
     @param current
     @return
     */
    public PageBean<Route> getPageBeanByFavoriteRank(int current) {
        PageBean<Route> pageBean = new PageBean<>();
        //2个属性由用户提交
        pageBean.setCurrent(current);
        pageBean.setSize(8);
        //2个属性从数据库中得到
        List<Route> routes = routeDao.getRoutesFavoriteRankByPage(current, pageBean.getSize());
        int count = routeDao.getCountByFavoriteRank();
        pageBean.setData(routes);
        pageBean.setCount(count);
        return pageBean;
    }
    /**
     查询收藏排行榜1页数据
     @param current 当前页
     @param condition 查询条件
     @return
     */
    public PageBean<Route> getPageBeanByFavoriteRank(int current,Map<String,String> condition) {
        PageBean<Route> pageBean = new PageBean<>();
        //2个属性由用户提交
        pageBean.setCurrent(current);
        pageBean.setSize(8);
        //2个属性从数据库中得到
        List<Route> routes = routeDao.getRoutesFavoriteRankByPage(current, pageBean.getSize(), condition);
        int count = routeDao.getCountByFavoriteRank(condition);
        pageBean.setData(routes);
        pageBean.setCount(count);
        return pageBean;
    }
}
