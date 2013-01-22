package com.designedbymark;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable grammarTable;
	private JTextField importPathTF;
	private JTextField txtInputString;
	
	public ArrayList rulesArr = new ArrayList();


	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Human Language Technologies Assignment 2012/13 - Mark Bonnici\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomPanel = new JPanel();
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		txtInputString = new JTextField();
		txtInputString.setText("Input String");
		bottomPanel.add(txtInputString, BorderLayout.NORTH);
		txtInputString.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		bottomPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnRecognize = new JButton("Recognize");
		btnRecognize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!rulesArr.isEmpty()){
					String sentence = txtInputString.getText();
					InputSentence is = new InputSentence();
					ArrayList<String> inArr = is.divideSentence(sentence);
					is.initTable(inArr);
					is.insertFirstLine(inArr, rulesArr);
					is.continueFillTable(inArr, rulesArr);
//					boolean found = is.recognize(inArr);
					if(is.recognize(inArr)){
						JOptionPane.showMessageDialog(null, "This String is in L(G)");
					}else {
						JOptionPane.showMessageDialog(null, "Not A Member");
					}
					is.print2dArr(inArr);
				}else {
					JOptionPane.showMessageDialog(MainFrame.this, "Rules were not defined beforehand. Please browse for the Grammar File", "Rule Not Defined!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnRecognize);
		
		JButton btnParse = new JButton("Parse");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!rulesArr.isEmpty()){
					String sentence = txtInputString.getText();
					InputSentence is = new InputSentence();
					ArrayList<String> inArr = is.divideSentence(sentence);
					is.initTable(inArr);
					is.insertFirstLine(inArr, rulesArr);
					is.continueFillTable(inArr, rulesArr);
//					boolean found = is.recognize(inArr);
//					if(is.recognize(inArr)){
//						JOptionPane.showMessageDialog(null, "This String is in L(G)");
//					}else {
//						JOptionPane.showMessageDialog(null, "Not A Member");
//					}
					is.print2dArr(inArr);
					
					ParseFrame pf = new ParseFrame(is.recognize(inArr), inArr, is.getTable());
					pf.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(MainFrame.this, "Rules were not defined beforehand. Please browse for the Grammar File", "Rule Not Defined!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnParse);
		
		JPanel centrePanel = new JPanel();
		contentPane.add(centrePanel, BorderLayout.CENTER);
		centrePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		centrePanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGrammarFile = new JLabel("Grammar File:");
		panel.add(lblGrammarFile, BorderLayout.WEST);
		
		importPathTF = new JTextField();
		panel.add(importPathTF, BorderLayout.CENTER);
		importPathTF.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(MainFrame.this);
				File selImpFile = fc.getSelectedFile();
				importPathTF.setText(selImpFile.toString());
				
				rulesArr.clear();
				
				InputGrammar i = new InputGrammar();
				try {
					rulesArr.addAll(i.readFile(selImpFile));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
				fillGrammarTable(rulesArr);
			}
		});
		panel.add(btnBrowse, BorderLayout.EAST);
		
		JScrollPane scrollPane = new JScrollPane();
		centrePanel.add(scrollPane, BorderLayout.CENTER);
		
		grammarTable = new JTable();
		grammarTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(grammarTable);
	}

	public void fillGrammarTable(ArrayList grammar){
		DefaultTableModel tableModel = new DefaultTableModel();

		String[] columnNames = { "Production Rule - Left Hand Side", "Production Rule - Right Hand Side" };
		tableModel.setColumnIdentifiers(columnNames);

		for(int i = 0; i < grammar.size(); i++){
			ArrayList value = (ArrayList) grammar.get(i);
			String front = null;
			String back = "";
			for(int c = 0; c < value.size(); c++){
				if(c == 0){
					front = value.get(c).toString();
				}else{
					back += value.get(c).toString() + " ";
				}
			}
			
			String[] row = {front, back};
			tableModel.addRow(row);
		}
		
		grammarTable.setModel(tableModel);
	}
}
