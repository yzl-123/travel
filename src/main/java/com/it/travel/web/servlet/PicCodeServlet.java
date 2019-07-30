package com.it.travel.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 创建一个验证码图片
 */
@WebServlet("/code")
public class PicCodeServlet extends HttpServlet {
    private Random ran = new Random();      //随机类

    //1) 写一个方法随机获取颜色
    private Color getColor() {
        //红，绿，蓝取值0-255，随机生成 0-255之间数
        int r = ran.nextInt(256);
        int g = ran.nextInt(256);
        int b = ran.nextInt(256);
        return new Color(r, g, b);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //2) 创建缓存图片：指定宽
        int width = 90, height = 30;
        //参数3：图片的类型，使用RGB类型
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //3) 获取画笔对象
        Graphics graphics = image.getGraphics();
        //4) 设置画笔颜色，并且填充矩形区域
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        //创建一个StringBuider对象
        StringBuilder sb = new StringBuilder();
        //5) 从字符数组中随机得到字符
        char[] arr = {'A', 'B', 'C', 'D', 'N', 'E', 'W', 'b', 'o', 'y', '1', '2', '3', '4', '5', '6'};
        for (int i = 0; i < 4; i++) {
            //随机从0到长度之间去取索引
            int index = ran.nextInt(arr.length);
            //得到1个字符
            char c = arr[index];
            sb.append(c);
            //6) 设置字体，大小为18，设置字的颜色随机
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD + Font.ITALIC, 18));
            graphics.setColor(getColor());
            //7) 将每个字符画到图片，位置：5+(i*20), 20
            //将字符转成字符串
            graphics.drawString(String.valueOf(c), 5 + (i * 20), 20);
        }
        System.out.println("生成的验证码:" + sb);
        //得到验证码，将生成的字符串放到会话域中
        request.getSession().setAttribute("vcode", sb.toString());
        //8) 画干扰线8条线，线的位置是随机的，x范围在width之中，y的范围在height之中。
        for (int i = 0; i < 8; i++) {
            graphics.setColor(getColor());
            int x1 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int x2 = ran.nextInt(width);
            int y2 = ran.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }
        //9) 将缓存的图片输出到响应输出流中
        //参数1：要输出的缓存图片，参数2：格式，参数3：输出到哪个流中
        ImageIO.write(image, "jpeg", response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
