package com.itmo.online.shop.util;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtility {
	private static final String SALT = "salt"; 
	
	@Bean 
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
//	@Bean
//	public static String randomPassword() {
//		String saltChars = "ABCEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//		StringBuilder salt = new StringBuilder();
//
//		while (salt.length() < 18) {
//			int index = (int) (new Random().nextFloat() * saltChars.length());
//			salt.append(saltChars.charAt(index));
//		}
//		return salt.toString();
//	}
}
