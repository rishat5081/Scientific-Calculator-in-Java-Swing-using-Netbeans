package calculator;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Calculations extends JFrame {

//setting the interface of the calculator

//setting the horizontal margin and vertical margin
private static final int horizontal_margin = 10,
                        vertical_margin = 10,x_padding = 13, 
                        y_padding = 82,  width_btn = 58,
                        height_btn = 30,row_padding = 4,col_padding = 10;

//font style
//for for all the calclator interface
private static final Font font_for_whole_calculator = new Font("Times New Roman", Font.BOLD, 14),
font_of_menu = new Font("Arial", Font.BOLD, 16), font_of_calculating_values = new Font("Calbari", Font.PLAIN, 30);

//background color of the interface !!! 
private static final Insets no_Values = new Insets(0, 0, 0, 0);
private static final Color whole_Screen = new Color(29,191,115), background = new Color(93, 191, 197);

private final JLabel[] screen = new JLabel[3];
private final JButton[] btns_of_calculator = new JButton[54];
private final Main calc = new Main();
private String nTyppp = null, sysMode = null;


//main point of execution.
public static void main(String[] args) {
	new Calculations().openCalculator();
}

//constructor for the frame.
public Calculations() {
	setTitle("Calculator");
	getContentPane().setLayout(null);
	this.setResizable(false);
	this.setSize(710, 385);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	this.Component_of_screen();
	this.getContentPane().setBackground(whole_Screen);
	
}

private void Component_of_screen() {
    
	JMenuBar menuBar = new JMenuBar();
	JMenu[] menus = new JMenu[4];
	JMenuItem[] menuItems = null;
	JPanel scDip = new JPanel();
	JPanel hexPanel = new JPanel();
	ButtonGroup buttonGroup = null;
	
	JRadioButton[] radioButtons = new JRadioButton[4];
	String[] texts = {Main.A_text, Main.mc_text, Main.mr_text, Main.ms_text, Main.m_addition, Main.m_subtraction,
		Main.clear_screen, Main.logN_x_text, Main.bracketOpening, Main.bracketClosing, Main.B_text, Main.BSPC, 
                Main.clent_option, Main.clear_Screen, Main.negative_text, Main.SRT,
		Main.aveerage, Main.sin_x_text, Main.FCT, Main.SQR, Main.C_text, Main.seven, Main.eight, Main.nine, 
                Main.divide, Main.PRCT,
		Main.SUM, Main.cos_x_text, Main.CRT, Main.CUB, Main.D_text, Main.four, Main.five, Main.six, Main.multiply,
                Main.reciprocal_text,
		Main.LST, Main.tan_x_text, Main.INTX, Main.power_text, Main.E_text, Main.one, Main.two, Main.three, 
                Main.subtractionn, Main.equalSign,
		Main.clearr_screeen, Main.log_x_text, Main.modulus, Main.PWTX, Main.F_text, Main.zero, Main.point_text, Main.addition};
	
	this.setJMenuBar(menuBar);
	for (int i = 0; i < menus.length; i++) {
		menus[i] = new JMenu();
		menus[i].setFont(font_of_menu);
		
		switch (i) {
			case 0:
				menus[i].setText("Calculator Mode");
				menuItems = new JMenuItem[2];
				buttonGroup = new ButtonGroup();
				for (int j = 0; j < 2; j++) {
					menuItems[j] = new JRadioButtonMenuItem(j == 0 ? Main.bracketClosing : Main.advance);
					menuItems[j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Change_systemMode(e.getActionCommand());
						}
					});
					buttonGroup.add(menuItems[j]);
				}
				menuItems[0].setSelected(true);
				break;
			case 1:
				menus[i].setText("View");
				menuItems = new JMenuItem[1];
				menuItems[0] = new JCheckBoxMenuItem(Main.placement);
				menuItems[0].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						key_generate(e.getActionCommand());
					}
				});
				break;
			case 2:
				menus[i].setText("History");
				menuItems = new JMenuItem[1];
				menuItems[0] = new JMenuItem("Show History");
				menuItems[0].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String resultsOf="";
						for( int i=0;i<calc.results_of_num.size();i++)
						{
							resultsOf=resultsOf+calc.results_of_num.get(i)+"\n";
						}
						JOptionPane.showMessageDialog(null, resultsOf,"History",JOptionPane.INFORMATION_MESSAGE);

					}
				});
				break;
			case 3:
				menus[i].setText("Help");
				menuItems = new JMenuItem[1];
				menuItems[0] = new JMenuItem("About us");
				menuItems[0].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null,
							"Calculator ",
								"About", JOptionPane.INFORMATION_MESSAGE);
					}
				});
				break;
		}
		
		for (int j = 0; j < menuItems.length; j++) {
			menuItems[j].setFont(font_of_menu);
			menus[i].add(menuItems[j]);
		}
		menuBar.add(menus[i]);
	}
	
	scDip.setBackground(background);
	scDip.setBorder(new LineBorder(Color.GRAY, 1));
	scDip.setLayout(null);
	scDip.setLocation(x_padding, x_padding);
	scDip.setSize((width_btn + horizontal_margin) * col_padding - horizontal_margin,
		y_padding - scDip.getY() - vertical_margin);
	
	for (int i = 0; i < screen.length; i++) {
		screen[i] = new JLabel();
		switch (i) {
			case 0:
				screen[i].setBounds(horizontal_margin, vertical_margin + 2, 15, 15);
				screen[i].setFont(new Font(font_for_whole_calculator.getName(), Font.PLAIN, 13));
				break;
			case 1:
				screen[i].setLocation(screen[i-1].getX() * 2 + screen[i-1].getWidth(),
					screen[i-1].getY());
				screen[i].setSize(scDip.getWidth() - (screen[i].getX() + horizontal_margin),
						screen[i-1].getHeight());
				screen[i].setFont(screen[i-1].getFont());
				screen[i].setHorizontalAlignment(SwingConstants.RIGHT);
				break;
			case 2:
				screen[i].setBounds(screen[i-2].getX(), 27,
					scDip.getWidth() - screen[i-2].getX() * 2, 30);
				screen[i].setFont(font_of_calculating_values);
				screen[i].setHorizontalAlignment(SwingConstants.RIGHT);
				break;
		}
		
		scDip.add(screen[i]);
	}
	
	hexPanel.setBackground(whole_Screen);
	hexPanel.setBorder(new LineBorder(Color.GRAY, 1));
	hexPanel.setLayout(null);
	hexPanel.setLocation(15, 85);
	hexPanel.setSize(252, 49);
	
	buttonGroup = new ButtonGroup();
	for (int i = 0; i < radioButtons.length; i++) {
		radioButtons[i] = new JRadioButton(i == 0 ? Main.hexadeimal_text :
			i == 1 ? Main.decimal_text : i == 2 ? Main.oct_text : Main.bin_text);
		radioButtons[i].setBackground(whole_Screen);
		radioButtons[i].setFocusPainted(false);
		radioButtons[i].setFont(new Font(font_for_whole_calculator.getName(),
			font_for_whole_calculator.getStyle(), font_for_whole_calculator.getSize() - 2));
		radioButtons[i].setMargin(no_Values);
		radioButtons[i].setSize(hexPanel.getWidth() / 4 - 4, 20);
		radioButtons[i].setLocation(radioButtons[i].getWidth() * i + 9, 5);
		
		radioButtons[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Setting_modes_types(e.getActionCommand());
				key_generate(e.getActionCommand());
			}
		});
		
		if (i == 1) radioButtons[i].setSelected(true);
		buttonGroup.add(radioButtons[i]);
		hexPanel.add(radioButtons[i]);
	}
	
	for (int i = 0, x = 4, y = 0; i < btns_of_calculator.length; i++) {
		btns_of_calculator[i] = new JButton(texts[i]);
		btns_of_calculator[i].setFocusPainted(false);
		btns_of_calculator[i].setFont(font_for_whole_calculator);
		btns_of_calculator[i].setMargin(no_Values);
		btns_of_calculator[i].setSize(width_btn, height_btn);
		btns_of_calculator[i].setLocation(x_padding + (width_btn + horizontal_margin) * x,
			y_padding + (height_btn + vertical_margin) * y);
		if (i > 0 && i < 6) btns_of_calculator[i].setBackground(whole_Screen);
		
		if (texts[i] == Main.clear_Screen || texts[i] == Main.PRCT) {
			btns_of_calculator[i].setBackground(whole_Screen);
			btns_of_calculator[i].setEnabled(false);
		} else if (texts[i] == Main.equalSign) {
			btns_of_calculator[i].setSize(width_btn, height_btn * 2 + vertical_margin);
		} else if (texts[i] == Main.zero) {
			btns_of_calculator[i].setSize(width_btn * 2 + horizontal_margin, height_btn);
			x++;
		}
		
		btns_of_calculator[i].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				key_generate(e.getActionCommand());
			}
		});
		
		this.getContentPane().add(btns_of_calculator[i]);
		if (++x >= col_padding) {
			x = 0; y++;
		}
	}
	
	this.getContentPane().add(scDip);
	this.getContentPane().add(hexPanel);
}

 private void key_generate(String key) {
	calc.inputKey(key);
	if (key != Main.placement) {
		screen[0].setText(calc.hasvalueTakeen() ? "M" : "");
		if (key != Main.ms_text)
			screen[1].setText(calc.getSecScreenText());
		btns_of_calculator[6].setText(calc.getSetSize() + "");
	}
	screen[2].setText(calc.getpScText());
}

// Set in Basic or Advanced 
private void Change_systemMode(String mode) {
	boolean b = mode == Main.advance;
	for (int i = 6; i < btns_of_calculator.length; i += 10)
		for (int j = i > 6 ? i : 7; j < i + 4; j++)	// Exclude XXX
			btns_of_calculator[j].setEnabled(b);
	sysMode = mode;
	key_generate(Main.clear_screen);
	key_generate(Main.clearr_screeen);
}



//Updates the click ability of the btns_of_calculator on the system of numbers. 
private void Setting_modes_types(String mode) {
	switch (mode) {
		case Main.hexadeimal_text:
			Decimal_Mode(true);
			HexaDecimal_Mode(true);
			break;
		case Main.decimal_text:
			Decimal_Mode(true);
			HexaDecimal_Mode(false);
			break;
		case Main.oct_text:
			Decimal_Mode(true);
			HexaDecimal_Mode(false);
			for (int i : new int[]{22, 23})
				btns_of_calculator[i].setEnabled(false);
			break;
		case Main.bin_text:
			Decimal_Mode(false);
			HexaDecimal_Mode(false);
			break;
	}
	btns_of_calculator[52].setEnabled(mode == Main.decimal_text);
	nTyppp = mode;
	calc.inputKey(mode);
}

// Enables all decimal number system 
private void Decimal_Mode(boolean b) {
	int[] arr = {21, 22, 23, 31, 32, 33, 42, 43};
	for (int i : arr) btns_of_calculator[i].setEnabled(b);
}

//Allows the hexadecimal btns_of_calculator
private void HexaDecimal_Mode(boolean b) {
	for (int i = 0; i < btns_of_calculator.length; i += 10)
		btns_of_calculator[i].setEnabled(b);
}


//Makes the JFrame avaliable
public void openCalculator() {
	Change_systemMode(Main.basic);
	Setting_modes_types(Main.decimal_text);
	this.setVisible(true);
}
}
