package com.it.travel.service;

import com.it.travel.dao.UserDao;
import com.it.travel.entity.User;
import com.it.travel.exception.CustomerErrorMsgException;
import com.it.travel.utils.Md5Utils;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 用户业务层
 */
public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 判断用户名是否存在.如果存在就返回true
     * @param username 用户名
     * @return true/false
     */
    public boolean isUserExists(String username){
        //如果不为空,则返回true
        return userDao.findUserByName(username) !=null;
    }

    /**
     * 注册,注册前对加密进行md5加密
     * @param user
     * @return
     */
    public int register(User user){
        String password = user.getPassword();//得到原来的密码
        String md5 = Md5Utils.getMd5(password);//加密后
        user.setPassword(md5); //发回到实体对象中
        return  userDao.addUser(user); //调用DAO添加到数据中
    }
    /**
     * 用户登录的方法
     * @throws ServletException
     * @throws IOException
     */
    public User login(String username,String password) throws CustomerErrorMsgException {
        //1.判断用户名是否存在
        User user = userDao.findUserByName(username);
        if (user == null){
            throw new CustomerErrorMsgException("用户名错误");
        }
        //2.判断密码是否正确
        String md5 = Md5Utils.getMd5(password);
        if(!md5.equals(user.getPassword())){
            throw new CustomerErrorMsgException("密码错误");
        }
        //3.如果上面的没有出现异常,则表示登录成功,返回用户对象
        return user;
    }
}
