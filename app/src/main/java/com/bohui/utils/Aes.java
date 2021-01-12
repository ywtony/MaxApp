package com.bohui.utils;

/**
 * @Title: Aes.java
 * @Package: com.dzy.util.security
 * @date: 2014�?�?�?涓嬪�?:06:50
 * @copyright: Copyright (c) 2014,dzyworld.com All Rights Reserved.
 */

import android.util.Base64;

import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Aes鍔犲�?
 * 
 * @ClassName: com.dzy.util.security.Aes
 * @author JoeHe
 * @date 2014�?�?�?涓嬪�?:06:50
 * @since 1.0
 * 
 */
public final class Aes {
	/**
	 * 秘钥算法
	 */
	private static String KEY_ALGORITHM = "AES";//
	/**
	 * 密码算法
	 */
	private static String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	/**
	 * 安全秘钥
	 */
	private static final byte[] SECURE_KEY = StringUtils
			.getBytesUtf8("Joehe@DZy#01$Aes");

	/**
	 *
	 * 鍔犲�?
	 *
	 * @author JoeHe
	 * @date 2014�?�?�?
	 * @since 1.0
	 *
	 * @param inputStr
	 * @return
	 * @throws Exception
	 *
	 */
	public static String encrypt(String inputStr) throws Exception {
		return encryptToString(StringUtils.getBytesUtf8(inputStr), SECURE_KEY);
	}
	/**
	 * 解密
	 *
	 * @author JoeHe
	 * @date 2014�?�?�?
	 * @since 1.0
	 *
	 * @param inputStr
	 * @return
	 * @throws Exception
	 *
	 */
	public static String decrypt(String inputStr) throws Exception {

		return decrypt(Base64.decode(inputStr,Base64.DEFAULT), SECURE_KEY);
	}

	/**
	 *
	 * 加密
	 *
	 * @author JoeHe
	 * @date 2014�?�?�?
	 * @since 1.0
	 *
	 * @param inputStr
	 * @param secureKey
	 * @return
	 * @throws Exception
	 *
	 */
	public static String encrypt(String inputStr, String secureKey)
			throws Exception {
		return encryptToString(StringUtils.getBytesUtf8(inputStr),
				StringUtils.getBytesUtf8(secureKey));
	}

	/**
	 * 
	 * 加密
	 * 
	 * @author JoeHe
	 * @date 2014�?�?�?
	 * @since 1.0
	 * 
	 * @param inputStr
	 * @param secureKey
	 * @return
	 * @throws Exception
	 * 
	 */
	public static String decrypt(String inputStr, String secureKey)
			throws Exception {
		return decrypt(Base64.decode(inputStr,Base64.DEFAULT),
				StringUtils.getBytesUtf8(secureKey));
	}

	private static String encryptToString(byte[] input, byte[] key)
			throws Exception {
		return String.valueOf(Base64.encode(encrypt(input, key),Base64.DEFAULT));
	}

	private static byte[] encrypt(byte[] input, byte[] key) throws Exception {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	private static String decrypt(byte[] input, byte[] key) throws Exception {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return StringUtils.newStringUtf8(decryptResult);
	}

	private static byte[] aes(byte[] input, byte[] key, int mode)
			throws Exception {

		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(mode, secretKey);
		return cipher.doFinal(input);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

	}

}
