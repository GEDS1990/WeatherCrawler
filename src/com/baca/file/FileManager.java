package com.baca.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileManager {

	public static String readTxtFile(String filePath) {
		String lineTxt = null;
		String result = null;
		
		try {

			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 
				StringBuilder builder = new StringBuilder();
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				
				while ((lineTxt = bufferedReader.readLine()) != null) {
					builder.append(lineTxt);
				}
				read.close();
				result = builder.toString();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return result;

	}
	
	public static void saveToFile(){
		
	}
}