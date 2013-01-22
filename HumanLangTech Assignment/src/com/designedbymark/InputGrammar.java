package com.designedbymark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InputGrammar {

	public ArrayList readFile(File inFile) throws IOException{
		FileReader fr = new FileReader(inFile);
		BufferedReader in = new BufferedReader(fr);
		
		String line = null;
		ArrayList outArr = new ArrayList();
		
		while((line = in.readLine()) != null){
			outArr.add(parseLine(line));
		}
		
		in.close();
		
		System.out.println(outArr.toString());
		
		return outArr;
	}
	
	public ArrayList parseLine(String line){
		ArrayList parsedLine = new ArrayList();
		
		StringTokenizer st = new StringTokenizer(line);
		while(st.hasMoreElements()){
			parsedLine.add(st.nextElement());
		}
		
		parsedLine.remove("->");
//		parsedLine.add("\n");
		
//		System.out.println(parsedLine.toString());
		
		return parsedLine;
	}
}
