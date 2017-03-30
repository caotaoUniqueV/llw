package com.linwang.test;

public class DemoProvider {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active","production");
		System.setProperty("dubbo.spring.config","classpath:conf/*.xml");
		com.alibaba.dubbo.container.Main.main(new String[]{});
	} 
} 