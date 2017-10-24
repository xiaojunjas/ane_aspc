package com.ane56.controller;

import java.io.File;
import java.util.Calendar;

/**
 * @author 作者 E-mail:
 * @Date 创建时间：2017年10月24日 下午2:19:18
 * @version 类说明
 */
public class test {

	public static void main(String[] args) {
		String str1 = "hello";
		String str2 = "he" +new String("llo");
		System.out.println(str1 == str2);
		System.out.println(getval(2));
		
		 Calendar cal = Calendar.getInstance();  
	     cal.add(Calendar.DATE, -1);  
         System.out.println(cal.getTime());  
         
         File file=new File("F:\\图片");
         for(File temp:file.listFiles()){
             if(temp.isFile()){
                 System.out.println(temp.toString());
             }
             
         }
	}
	
	public static int getval(int i){
		int result = 0;
		switch (i) {
		case 1:
			result = result +i;
		case 2:
			result = result +i * 2;
		case 3:
			result = result +i * 3;
		}
		return result;
	}

}
