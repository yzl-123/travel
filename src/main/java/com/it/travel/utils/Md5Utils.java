package com.it.travel.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Utils {

	/**
	 * 对给定的字符使用md5进行加密，返回加密以后的字符串
	 * @param origin 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String getMd5(String origin) {
		// 1) 使用静态方法，创建MessageDigest对象
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 2) 将字符串使用utf-8进行编码，得到字节数组
			byte[] input = origin.getBytes("utf-8");
			// 3) 使用digest(input)对字节数组进行md5的哈希计算，得到加密以后的字节数组，长度是16个字节。
			byte[] num = md.digest(input);
			// 4) 使用类BigInteger(1, 加密后的字节数组)，将这个二进制数组转成无符号的大整数
			// 1 正数， -1表示负数
			BigInteger big = new BigInteger(1, num);
			// 5) 将这个大整数转成16进制字符串，参数为多少进制
			return big.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

    public static void main(String[] args) {
        System.out.println(Md5Utils.getMd5("123"));
        System.out.println(Md5Utils.getMd5("abc"));
    }
}
