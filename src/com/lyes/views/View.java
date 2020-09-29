package com.lyes.views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.NumberFormatter;


import com.lyes.model.Board;


import come.lyes.controllers.Controller;

@SuppressWarnings("serial")
public class View extends JFrame{
	
	private Board board;
	private JSplitPane sp; 
	private JPanel sudokuPanel;
	private JPanel statusPanel;
	private JFormattedTextField[][] cells;
	private Controller controller;
	private JTextArea statusText;
	private int secondes;
	private String time = "00:00";
	private JLabel timeLabel;
	private JLabel statusTitleLabel;

	
	public View(Board board) throws IOException {
		super();
		this.board = board;
		this.sp = new JSplitPane(SwingConstants.VERTICAL); 
		this.cells = new JFormattedTextField[this.board.getBoard().length][this.board.getBoard().length];
		this.controller = new Controller();
		this.statusText = new JTextArea();
		this.statusText.setEditable(false);
		this.statusText.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 15));
		this.statusText.setForeground(Color.RED);
		this.statusText.setSize(100, 500);
		this.add(sp);
		this.sp.setDividerLocation(0.6);
		this.setTitle("Sudoku");
		this.setSize(800, 530);
		drawBoard();
		initStatus();
		this.setResizable(false);
		this.setIconImage(ImageIO.read(new File("images/icon.png")));
		this.setVisible(true);
	}
	
	
	private void drawBoard(){
		
		sudokuPanel = new JPanel(new GridLayout(this.board.getBoard().length,this.board.getBoard().length));
		int [][] board = this.board.getBoard();
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumIntegerDigits(1);
		NumberFormatter textFormatter = new NumberFormatter(numberFormat);
		textFormatter.setAllowsInvalid(false);
		
		for(int i = 0; i <  board.length; i++) {
			for(int j = 0; j <  board.length ; j++) {
				this.cells[i][j] = new JFormattedTextField(textFormatter);
				if(board[i][j] == 10) {				
					this.cells[i][j].setText(" ");
					this.cells[i][j].setColumns(2);
					this.cells[i][j].setHorizontalAlignment(JTextField.CENTER);
					this.cells[i][j].setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));
				}else {
					this.cells[i][j].setText( Integer.toString(board[i][j]));
					this.cells[i][j].setColumns(2);
					this.cells[i][j].setEditable(false);
					this.cells[i][j].setHorizontalAlignment(JTextField.CENTER);
					this.cells[i][j].setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));
				}
				
				  
				
				this.cells[i][j].getDocument().putProperty("cell", this.cells[i][j]);
				
				this.cells[i][j].getDocument().addDocumentListener(new DocumentListener() {
					
					 

					JOptionPane d = new JOptionPane();

					private void emptyTextField(DocumentEvent e) throws InvocationTargetException, InterruptedException {
						
						Runnable doRun = new Runnable() {

							@SuppressWarnings("static-access")
							@Override
							public void run() {
								// TODO Auto-generated method stub
								JFormattedTextField o = (JFormattedTextField) e.getDocument().getProperty("cell");
								System.out.println(o.getText());
								o.setText("");
								
							}
						};
						
						SwingUtilities.invokeLater(doRun);	
						
					}
					
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						
					}

					@SuppressWarnings("static-access")
					@Override
					public void insertUpdate(DocumentEvent e) {
						
						
						JFormattedTextField o = (JFormattedTextField) e.getDocument().getProperty("cell");
						
						if(o.getText().length() > 0) {

							
							try {
								if(!controller.handleChanges(e)) {
									
									try {
										emptyTextField(e);
										printMessage();
									} catch (InvocationTargetException | InterruptedException e1) {
										
										e1.printStackTrace();
									}
								}
							} catch (BadLocationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				sudokuPanel.add(cells[i][j]);
			}
		}
		
		this.sp.setLeftComponent(sudokuPanel);
		this.sp.getComponent(0).setSize(400, 450);
	}
	
	
	private void initStatus() {
		timeLabel = new JLabel("Temps écoulé : "+time);
		statusTitleLabel = new JLabel("Status de la partie");
		timeLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 15));
		statusTitleLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 20));
		timeLabel.setForeground(Color.GRAY);
		statusTitleLabel.setForeground(Color.BLUE);
		statusPanel = new JPanel(new BorderLayout());
		statusPanel.add(statusTitleLabel, BorderLayout.PAGE_START);
		statusPanel.add(statusText, BorderLayout.CENTER);
		statusPanel.add(timeLabel, BorderLayout.SOUTH);
		this.sp.setRightComponent(statusPanel);
	}
	
	private void printMessage() {
		this.statusText.append("This digit is not valid, it exist in that column \n");
	}
	
	
	public void updateTimer() {
		secondes++;
		int minutes = secondes/60;
		int modSecondes = secondes%60;
		time = (minutes < 10 ? "0"+minutes: minutes) +":"+ (modSecondes < 10 ? "0"+modSecondes: modSecondes);
		timeLabel.setText("Temps écoulé : "+time);
	}
}
