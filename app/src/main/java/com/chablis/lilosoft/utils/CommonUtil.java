package com.chablis.lilosoft.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.os.Environment;

public class CommonUtil {

	public static void saveLog(String time, String strs){
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); 
		if(sdCardExist){
			try {
				String path = Environment.getExternalStorageDirectory().toString();
				File file = new File(path+"/newegov/EGOVLog.txt");
				if(!file.exists()){
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
//				String info = time+","+strs[1]+","+strs[2]+"\n";
				String info = time+","+strs+"\n";
				bw.write(info);
				bw.flush();
				
				fw.close();
				bw.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
