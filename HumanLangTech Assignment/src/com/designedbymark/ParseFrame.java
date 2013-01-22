package com.designedbymark;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSplitPane;

public class ParseFrame extends JFrame {

	private JPanel contentPane;
	private JTable parseJTable;
	private JLabel answerTxt;
	private JSplitPane splitPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private JTable sentencesJTable;

	/**
	 * Create the frame.
	 */
	public ParseFrame(Boolean found, ArrayList<String> inArr, String[][] parseTable) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BorderLayout(0, 0));
		
		answerTxt = new JLabel("text");
		topPanel.add(answerTxt, BorderLayout.SOUTH);
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);
		
		parseJTable = new JTable();
		scrollPane_1.setViewportView(parseJTable);
		
		scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		sentencesJTable = new JTable();
		scrollPane.setViewportView(sentencesJTable);
		
		splitPane.setDividerLocation(150);
		
		setFound(found);
		fillTable(parseTable, inArr);
		ArrayList<int[]> spos = getSentencesPos(inArr.size());
		ArrayList<String> sentences = getSentences(spos, inArr);
		fillSentencesTable(sentences);
	}
	
	public ArrayList<String> getSentences(ArrayList<int[]> positions, ArrayList<String> inArr){
		ArrayList<String> sentences = new ArrayList<String>();
		
		for(int i = 0; i < positions.size(); i++){
			int tempPos[] = positions.get(i);
			int row = tempPos[0];
			int col = tempPos[1];
			
			String sentence = ""; 
			for(int c = col; c < (inArr.size() - row) + col; c++){
				sentence += inArr.get(c) + " ";
			}
			
			if(!sentences.contains(sentence)){
				sentences.add(sentence);
			}
		}
		
		return sentences;
	}
	
	public void fillSentencesTable(ArrayList<String> sentences){
		DefaultTableModel tableModel = new DefaultTableModel();
		
		String[] columnNames = {"Sentences"};
		tableModel.setColumnIdentifiers(columnNames);
		
		for (int i = 0; i < sentences.size(); i++) {
			String[] row = {sentences.get(i)};
			tableModel.addRow(row);
		}
		
		sentencesJTable.setModel(tableModel);
	}
	
	public ArrayList<int[]> getSentencesPos(int size){
		TableModel tm = parseJTable.getModel();
		
		ArrayList<int[]> pos = new ArrayList<int[]>();
		
		for(int row = 0; row < size; row++){
			for(int col = 0; col < size; col++){
				String st = tm.getValueAt(row, col).toString();
				if(st.matches("(.*)[S](.*)")){
					int[] tempPos = new int[2];
					tempPos[0] = row;
					tempPos[1] = col;
					pos.add(tempPos);
				}
			}
		}
		
		return pos;
	}
	
	public void fillTable(String[][] tableArr, ArrayList<String> inArr){
		DefaultTableModel tableModel = new DefaultTableModel();

		int size = inArr.size();
		
		String[] columnNames = new String[size];
		for(int i = 0; i < size; i++){
			columnNames[i] = i+"";
		}
		tableModel.setColumnIdentifiers(columnNames);
		
		for(int i = size - 1; i >= 0; i--){
			String[] row = new String[size];
			
			for(int c = 0; c < size; c++){
				row[c] = tableArr[c][i];
			}
			
			tableModel.addRow(row);
		}
		
		tableModel.addRow(inArr.toArray());
		
		parseJTable.setModel(tableModel);
	}
	
	public void setFound(Boolean found){
		if(found){
			answerTxt.setText("This String is in L(G)");
		}else {
			answerTxt.setText("Not A Member");
		}
	}
}
