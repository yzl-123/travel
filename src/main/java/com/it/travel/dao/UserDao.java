package com.it.travel.dao;

import com.it.travel.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.jws.soap.SOAPBinding;

//对用户表的操作
public class UserDao extends BaseDao {
    /**
     通过一条用户名找一个用户对象
     * @param username 用户名
     * @return
     */
    public User findUserByName(String username){
        //查询一条记录queryForObject
        try {
            return template.queryForObject("SELECT * FROM tab_user WHERE username=?",new BeanPropertyRowMapper<>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addUser(User user){
        return template.update("INSERT INTO tab_user VALUES(null,?,?,?,?,?,?,?)",
                user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),
                user.getSex(),user.getTelephone(),user.getEmail());
    }
}
