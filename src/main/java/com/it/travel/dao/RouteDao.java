package com.it.travel.dao;

import com.it.travel.entity.Route;
import com.it.travel.entity.RouteImg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 线路DAO
 */
public class RouteDao extends BaseDao {
    /**
      按cid,查询收藏人数最多的6条记录,按降序
     */
    public List<Route> findRoutes(int cid){
        return template.query("SELECT * FROM tab_route WHERE cid=? ORDER BY COUNT DESC LIMIT 0,6;",
                new BeanPropertyRowMapper<>(Route.class),cid);
    }
    /**
     * 查询某个分类1页的数据
     * @param cid
     * @return
     */
    public int getCountByCid(int cid){
        //参数1:SQL语句,参数2:返回类型,参数3,占位符
        return  template.queryForObject("select count(*) from tab_route where cid=?",int.class,cid);
    }

    /**
     * 通过分类id,查询1页的数据
     * @param cid 分类cid
     * @param current 当前是第几页
     * @param size 每页的大小
     * @param rname 模糊查询线路名字
     * @return
     */
    public List<Route> getRoutesByPage(int cid,int current,int size,String rname){
        int begin = (current - 1) * size;
        return template.query("select * from tab_route where rname like ? and cid=? limit ?,?",
                new BeanPropertyRowMapper<>(Route.class),
                "%" + rname + "%", cid, begin, size);
    }

  public int getCountByCid(int cid, String rname){
        //参数1:SQL语句,参数2:返回类型,参数3:占位符
      return  template.queryForObject("SELECT COUNT(*) FROM tab_route WHERE rname LIKE ? AND cid =?",
              int.class,"%"+ rname +"%",cid);
  }

    /**
     * 通过线路id,查询三张表信息
     * @param rid 线路的id
     * @return 包含三张的Map对象,一个map封装一条记录
     */
  public Map<String,Object> findRouteByRid(int rid){
        return template.queryForMap("SELECT * FROM tab_route r INNER JOIN tab_category c " +
                "ON r.cid = c.cid INNER JOIN tab_seller s ON r.sid = s.sid WHERE r.rid = ?",rid);
  }


    /**
     * 通过线路的id,查询把图片信息
     * @param rid 线路的id
     * @return
     */
  public List<RouteImg> findRouteImgsByRid(int rid){
      return  template.query("SELECT * FROM tab_route_img WHERE rid = ?",
              new BeanPropertyRowMapper<>(RouteImg.class),rid);
  }
    /**
     按收藏数量降序排序，查询1页的线路
     */
    public List<Route> getRoutesFavoriteRankByPage(int current, int size) {
        return template.query("select * from tab_route order by count desc limit ?,?",
                new BeanPropertyRowMapper<>(Route.class), (current - 1) * size, size);
    }

    /**
     线路的总记录数
     */
    public int getCountByFavoriteRank() {
        return template.queryForObject("select count(*) from tab_route", int.class);
    }
    /**
     按收藏数量降序排序，查询1页的线路
     @param condition {rname=双飞6天, startPrice=2000, endPrice=4000}
     */
    public List<Route> getRoutesFavoriteRankByPage(int current, int size, Map<String,String> condition) {
        //动态拼接SQL语句
        StringBuilder sql = new StringBuilder("select * from tab_route  where 1=1 ");
        //创建一个集合保存这些占位符的值
        ArrayList<Object> params = new ArrayList<>();

        //如果rname不为空且不为空串
        if (condition.get("rname")!=null && !"".equals(condition.get("rname"))) {
            sql.append(" and rname like ?");
            params.add("%" + condition.get("rname") + "%");  //替换占位符的值
        }
        //开始价格
        if (condition.get("startPrice")!=null && !"".equals(condition.get("startPrice"))) {
            sql.append(" and price >= ?");
            params.add(condition.get("startPrice"));
        }
        //结束价格
        if (condition.get("endPrice")!=null && !"".equals(condition.get("endPrice"))) {
            sql.append(" and price <=?");
            params.add(condition.get("endPrice"));
        }
        //拼接排序和分页
        sql.append(" order by count desc limit ?,?");
        params.add((current - 1) * size);
        params.add(size);

        System.out.println(sql);
        System.out.println(params);

        //最后一个参数可是Object的数组，但不能集合，将集合转成Object数组
        return template.query(sql.toString(),
                new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }
    /**
     线路的总记录数
     */
    public int getCountByFavoriteRank(Map<String,String> condition) {
        StringBuilder sql = new StringBuilder("select count(*) from  tab_route where 1=1 ");
        //创建一个集合保存这些占位符的值
        ArrayList<Object> params = new ArrayList<>();

        //如果rname不为空且不为空串
        if (condition.get("rname")!=null && !"".equals(condition.get("rname"))) {
            sql.append(" and rname like ?");
            params.add("%" + condition.get("rname") + "%");  //替换占位符的值
        }
        //开始价格
        if (condition.get("startPrice")!=null && !"".equals(condition.get("startPrice"))) {
            sql.append(" and price >= ?");
            params.add(condition.get("startPrice"));
        }
        //结束价格
        if (condition.get("endPrice")!=null && !"".equals(condition.get("endPrice"))) {
            sql.append(" and price <=?");
            params.add(condition.get("endPrice"));
        }
        return template.queryForObject(sql.toString(), int.class, params.toArray());
    }
}
