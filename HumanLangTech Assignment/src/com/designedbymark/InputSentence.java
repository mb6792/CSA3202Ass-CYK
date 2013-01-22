package com.designedbymark;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class InputSentence {
	public String[][] table;
	
	public ArrayList<String> divideSentence(String sentence){
		ArrayList<String> inArr = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(sentence);
		while (st.hasMoreElements()) {
			inArr.add(st.nextElement().toString());
		}
		
//		System.out.println(inArr.toString());
		
		return inArr;
	}
	
	public void initTable(ArrayList<String> inArr){
		table = new String[inArr.size()][inArr.size()];
		
		for(int i = 0; i<inArr.size(); i++){
			for(int j = 0; j<inArr.size(); j++){
				table[i][j] = "";
			}
		}
	}
	
	public void insertFirstLine(ArrayList<String> inArr, ArrayList rulesArr){
//		System.out.println(inArr);
//		System.out.println(rulesArr);
		
		for(int i = 0; i<inArr.size(); i++){
			for(int r = 0; r<rulesArr.size(); r++){
				ArrayList value = (ArrayList) rulesArr.get(r);
				
				if (inArr.get(i).equals(value.get(1).toString())) {
					table[i][0] = table[i][0] + "[" +value.get(0).toString() + "]";//space
				}
			}
		}
		
//		print2dArr(inArr);
	}
	
	public void continueFillTable(ArrayList<String> inArr, ArrayList rulesArr){
//		System.out.println(inArr);
//		System.out.println(rulesArr);

        for (int row = 1; row<inArr.size(); row++){
            for (int col = 0; col<(inArr.size() - row); col++){
            	for (int k = 0; k<row; k++){
                    for (int rules = 0; rules<rulesArr.size(); rules++){
                        ArrayList prodRules = (ArrayList) rulesArr.get(rules);

                        if (prodRules.size() > 2)
                        {
                            // check if either of the boxes is null - neither should be null
                            if ((!table[col + k + 1][row - k - 1].equals("")) && (!table[col][k].equals("")))
                            {
                                if ((table[col][k].indexOf((String) prodRules.get(1)) != -1) && (table[col + k + 1][row - k - 1].indexOf((String)prodRules.get(2)) != -1)){
                                    checkDuplicates(col, row, prodRules);
                                }
                            }
                        }
                    }
				}
            }
        }
	}
	
	public void checkDuplicates(int no1, int no2, ArrayList value){
		if(table[no1][no2].length() > 0){
			boolean flag = false;
			
			for(int i = 0; i<table[no1][no2].length(); i++){
				if(table[no1][no2].indexOf(value.get(0).toString()) != -1){
					flag = true;
					// if set to true it is already present in the list
				}
			}
			
			//if not already in list
			if(flag == false){
				table[no1][no2] = table[no1][no2] + "[" + value.get(0).toString() + "]";
			}
		}else {
			table[no1][no2] = "[" + value.get(0).toString() + "]";
		}
	}
	
	public Boolean recognize(ArrayList<String> inArr){
		Boolean found;
		if(table[0][inArr.size()-1].indexOf("S") == -1){
			found = false;
		}else{
			found = true;
		}
		return found;
	}
	
	public void print2dArr(ArrayList<String> inArr){
		for(int i = 0; i<inArr.size(); i++){
			for(int j = 0; j<inArr.size(); j++){
				System.out.print(table[j][i] + ",");
			}
			System.out.println();
		}
	}
	
	public String[][] getTable(){
		return table;
	}
}
