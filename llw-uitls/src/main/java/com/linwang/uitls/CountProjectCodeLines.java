package com.linwang.uitls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 统计源码总共有多少行
 * 接收参数为 目录
 *
 */
public class CountProjectCodeLines {
	public static int count;

	public static void listAllFiles(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (null != files) {
				for (File fs : files) {
					listAllFiles(fs);
				}
			}
		} else {// 遇到单个文件，则开始计算行数
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				try {
					while (null != br.readLine())
						count++;
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
