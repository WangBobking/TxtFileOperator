package com.dfsebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Convertor {

	private static StringBuilder sb = new StringBuilder();
	
	public static void removeFrequence(String filePath, String fileName) {
		try {			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath + fileName)));
			String data = null;
			while((data = br.readLine())!= null) {
				data = data.substring(data.indexOf("、") + 1);
				data = insertSign(data);
				data = deepInsert(data);
				sb.append(data + "\r\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void saveFile(String filePath, String fileName) {
		File dir = new File(filePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		try { 
			File f = new File(filePath + fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileWriter fw = new FileWriter(f,false);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String insertSign(String str) {
		int stemEnd = str.indexOf("A");
		String stem = str.substring(0, stemEnd);
		stem += "@";
		stem += str.substring(stemEnd).replace("参考答案:", "#");
		return stem;
	}
	
	public static String deepInsert(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(str.split("@")[0] + "@");
		String ba = str.split("@")[1];
		String branch = ba.split("#")[0];
		String answer = ba.split("#")[1];
		String[] branches = branch.split("、");
		for (int i = 1; i < branches.length; i ++) {
			String br = branches[i];
			if (i == 1) 
				sb.append(br.subSequence(0, br.length() - 1));				
			else if (i < branches.length - 1)
				sb.append("○" + br.substring(0, br.length() - 1));
			else
				sb.append("○" + br.substring(0, br.length()));
		}
		sb.append("#");
		String[] answers = answer.split(",");
		for (int i = 0; i < answers.length; i ++) {
			if (i == 0) 
				sb.append(answers[0]);
			else
				sb.append("△" + answers[i]);
		}
		return sb.toString();
	}
}
