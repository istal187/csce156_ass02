package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadCSV {

	private String fileName;
	private ArrayList<String> data = new ArrayList<>();
	private static int memberCount;

	public ReadCSV(String filename) {
		this.fileName = filename;
		this.data = loadData(this.fileName);
	}

	private static ArrayList<String> loadData(String fileName){
		ArrayList<String> data = new ArrayList<>();
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
			String firstLine = s.nextLine();
			String[] tokens = firstLine.split(",");
			memberCount = Integer.parseInt(tokens[0]);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				line = line.replace("\"", "");
				data.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public ArrayList<String> getData() {
		return data;
	}	
	
	public int getMemberCount() {
		return memberCount;
	}

}
