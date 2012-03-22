

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Snack extends JFrame// implements KeyListener
{
	public static void main (String[] args)
	{
		try 
		{
			Snack s = new Snack();
			s.setVisible(true);
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Failed to load background image.", "Snack Calculator Error: ", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	public final JTextField txtNumber = new JTextField(" ", 15);
	public final JTextField txtTotal  = new JTextField(" 0", 15);
	
	public final Color c = new Color(0,0,0,0);
	public final Color colorAlphaBlack = new Color(0,0,0,125);
	
	public JMenuItem menuEditUndo;
	public JMenuItem menuEditRedo;
	
	/** This is used for undo's and redoes. */
	public double previousInputNumber = 0.0;
	public int currentTotalStackPointer = 0;
	public ArrayList<Double> totals = new ArrayList<Double>();
	
	public enum Action { ADD, SUBTRACT, MUTIPLY, DIVIDE, SQUAREROOT, PERCENT };
	public Action previousAction = null;;
	
	public Snack() throws IOException
	{
		this.setTitle("Snack Calculator");
		this.setSize(450, 588);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(this.c);
		//this.addKeyListener(this);
			
		
		ImageBackgroundPanel contentPanel = new ImageBackgroundPanel(this);
		//contentPanel.setLayout(new GridLayout(0,1));
		contentPanel.setLayout(null);
		
		contentPanel.setBackground(this.c);
		//contentPanel.setBackground(null);
		
		
		
		
		//JButton button = new JButton("+");
		//button.set
		
		JMenuBar menubar = new JMenuBar();
			JMenu menuSnack = new JMenu("Snack");
				/*JMenuItem menuSnackGame = new JMenuItem("Game");
					menuSnackGame.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(Snack.this, "http://www.ashleygwinnell.co.uk/games/Damon_The_Dinosaur_(Arcade)", "Message: ", JOptionPane.INFORMATION_MESSAGE);
						}
					});
				menuSnack.add(menuSnackGame);*/
				JMenuItem menuSnackAbout = new JMenuItem("About");
					menuSnackAbout.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							AboutFrame frame = new AboutFrame();
							//frame.open();
						}
					});
				menuSnack.add(menuSnackAbout);
				JMenuItem menuSnackQuit  = new JMenuItem("Quit");
					menuSnackQuit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					});
				menuSnack.add(menuSnackQuit);
			menubar.add(menuSnack);
			
			JMenu menuEdit = new JMenu("Edit");
				menuEditUndo = new JMenuItem("Undo");
					menuEditUndo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							undo();
						}
					});
					menuEditUndo.setEnabled(false);
				menuEdit.add(menuEditUndo);
				
				menuEditRedo = new JMenuItem("Redo");
					menuEditRedo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							redo();
						}
					});
					menuEditRedo.setEnabled(false);
				menuEdit.add(menuEditRedo);
				
				JMenuItem menuEditClear = new JMenuItem("Clear Edit History");
					menuEditClear.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Snack.this.currentTotalStackPointer = 0;
							Snack.this.totals.clear();
							Snack.this.totals.add(Double.parseDouble(Snack.this.txtTotal.getText().trim()));
							Snack.this.checkEditHistoryPointer();
						}
					});
				menuEdit.add(menuEditClear);
				
			menubar.add(menuEdit);
			
			JMenu menuConvert = new JMenu("Convert");
			
				JMenuItem menuConvertBinary = new JMenuItem("To Binary");
				menuConvertBinary.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int total = (int) Double.parseDouble(Snack.this.txtTotal.getText().trim());
							JOptionPane.showMessageDialog(null, "Number: " + total + "\nBinary: " + Snack.this.toBinary(total), "Convert To Binary: ", JOptionPane.PLAIN_MESSAGE);
						}
					});
				menuConvert.add(menuConvertBinary);
				
				JMenuItem menuConvertHex = new JMenuItem("To Hex");
				menuConvertHex.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int total = (int) Double.parseDouble(Snack.this.txtTotal.getText().trim());
							JOptionPane.showMessageDialog(null, "Number: " + total + "\nHex: " + Snack.this.toHex(total), "Convert To Hex: ", JOptionPane.PLAIN_MESSAGE);
						}
					});
				menuConvert.add(menuConvertHex);
				
				JMenuItem menuConvertOctal = new JMenuItem("To Octal");
				menuConvertOctal.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int total = (int) Double.parseDouble(Snack.this.txtTotal.getText().trim());
							JOptionPane.showMessageDialog(null, "Number: " + total + "\nOctal: " + Snack.this.toOctal(total), "Convert To Octal: ", JOptionPane.PLAIN_MESSAGE);
						}
					});
				menuConvert.add(menuConvertOctal);
			
			menubar.add(menuConvert);
		this.setJMenuBar(menubar);
		
//		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//		splitPane.setLastDividerLocation(150);
//		splitPane.setDividerLocation(150);
		
		JPanel leftPanel = new JPanel();
		//leftPanel.setBackground(Color.white);
		leftPanel.setBounds(10,390,135,135);
		leftPanel.setLayout(null);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon ico = new ImageIcon("images/damon.jpg");
		
		
		//System.out.println(ico.getIconWidth());
		JLabel icoLabel = new JLabel(ico);
		icoLabel.setBounds(1, 1, 133, 133);
		
		leftPanel.add(icoLabel);
		contentPanel.add(leftPanel);
		
		JPanel leftPanel2 = new JPanel();
		leftPanel2.setBounds(10, 248, 135, 135);
		leftPanel2.setLayout(null);
		leftPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon ico2 = new ImageIcon("images/damon2.jpg");
		JLabel ico2Label = new JLabel(ico2);
		ico2Label.setBounds(1, 1, 133, 133);
		leftPanel2.add(ico2Label);
		contentPanel.add(leftPanel2);
		
		JPanel leftPanel3 = new JPanel();
		leftPanel3.setBounds(10, 106, 135, 135);
		leftPanel3.setLayout(null);
		leftPanel3.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon ico3 = new ImageIcon("images/damon3.jpg");
		JLabel ico3Label = new JLabel(ico3);
		ico3Label.setBounds(1, 1, 133, 133);
		leftPanel3.add(ico3Label);
		contentPanel.add(leftPanel3);
		
		
		//splitPane.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setBounds(145, 5, 304, 539);
		rightPanel.setBackground(null);
		//rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		
		JPanel boxesPanel = new JPanel();
		boxesPanel.setLayout(null);
		boxesPanel.setBackground(this.colorAlphaBlack);
		boxesPanel.setForeground(Color.white);
		boxesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		boxesPanel.setBounds(10, 10, 426, 88);
		
		int buttonSize = 32;
		int buttonSpacing = 5;
		
			Font f = new Font("Arial", Font.PLAIN, 18);
			
			JLabel totalLabel = new JLabel("Total:");
			totalLabel.setFont(f);
			totalLabel.setForeground(Color.white);
			totalLabel.setBounds(7 + 10, 5, 95, buttonSize);
			boxesPanel.add(totalLabel);
			
			JLabel totalLabelB = new JLabel("Total:");
			totalLabelB.setFont(f);
			totalLabelB.setForeground(Color.black);
			totalLabelB.setBounds(9 + 10, 7, 97, buttonSize);
			boxesPanel.add(totalLabelB);
			
			txtTotal.setFont(f);
			txtTotal.setEditable(false);
			txtTotal.setBackground(Color.darkGray);
			txtTotal.setForeground(Color.white);
			txtTotal.setBorder(BorderFactory.createLineBorder(Color.black));
			txtTotal.setBounds( 85, 5, 320, buttonSize);
			txtTotal.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
			txtTotal.setMargin(new Insets(10, 10, 10, 10));
			totals.add(0.0);
			boxesPanel.add(txtTotal);
			
			JLabel numberLabel = new JLabel("Input:");
			numberLabel.setFont(f);
			numberLabel.setForeground(Color.white);
			numberLabel.setBounds(7 + 10, 50, 95, buttonSize);
			boxesPanel.add(numberLabel);
			

			JLabel numberLabelB = new JLabel("Input:");
			numberLabelB.setFont(f);
			numberLabelB.setForeground(Color.black);
			numberLabelB.setBounds(9 + 10, 52, 97, buttonSize);
			boxesPanel.add(numberLabelB);
		
		
			
			txtNumber.setFont(f);
			txtNumber.setEditable(false);
			txtNumber.setBackground(Color.darkGray);
			txtNumber.setForeground(Color.white);
			txtNumber.setBorder(BorderFactory.createLineBorder(Color.black));
			txtNumber.setBounds( 85, 50, 320, buttonSize);
			txtNumber.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
			//runningNumber.set
			boxesPanel.add(txtNumber);
			
			
				contentPanel.add(boxesPanel);
			//contentPanel.add(boxesPanel);
			
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(null);
			buttonPanel.setBounds(10, 101, 281, 419);
			buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			buttonPanel.setBackground(this.colorAlphaBlack);
			buttonSize = 64;
			
				Color myblue = new Color(60, 60, 255, 255);
			
				JButton addButton = new JButton("+");
				addButton.setMargin(new Insets(1,1,1,1));
				addButton.setFont(f);
				addButton.setBackground(Color.darkGray);
				addButton.setForeground(Color.white);
				addButton.setBorder(BorderFactory.createLineBorder(myblue));
				
				addButton.setBounds(buttonSpacing, 5, buttonSize, buttonSize);
				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						add();
					}
				});
				buttonPanel.add(addButton);
				
				JButton subtractButton = new JButton("-");
				subtractButton.setMargin(new Insets(1,1,1,1));
				subtractButton.setFont(f);
				subtractButton.setBackground(Color.darkGray);
				subtractButton.setForeground(Color.white);
				subtractButton.setBorder(BorderFactory.createLineBorder(myblue));
				
				subtractButton.setBounds(buttonSize + 2*buttonSpacing, 5, buttonSize, buttonSize);
				subtractButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						subtract();
					}
				});
				buttonPanel.add(subtractButton);
				
				JButton multiplyButton = new JButton("x");
				multiplyButton.setMargin(new Insets(1,1,1,1));
				multiplyButton.setFont(f);
				multiplyButton.setBackground(Color.darkGray);
				multiplyButton.setForeground(Color.white);
				multiplyButton.setBorder(BorderFactory.createLineBorder(myblue));
				multiplyButton.setBounds(2*buttonSize + 3*buttonSpacing, 5, buttonSize, buttonSize);
				multiplyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						multiply();
					}
				});
				buttonPanel.add(multiplyButton);
				
				JButton divideButton = new JButton("/");
				divideButton.setMargin(new Insets(1,1,1,1));
				divideButton.setFont(f);
				divideButton.setBackground(Color.darkGray);
				divideButton.setForeground(Color.white);
				divideButton.setBorder(BorderFactory.createLineBorder(myblue));
				divideButton.setBounds(3*buttonSize + 4*buttonSpacing, 5, buttonSize, buttonSize);
				divideButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						divide();
					}
				});
				buttonPanel.add(divideButton);
			
				
				// row 2
				JButton sevenButton = new JButton("7");
				sevenButton.setMargin(new Insets(1,1,1,1));
				sevenButton.setFont(f);
				sevenButton.setBackground(Color.darkGray);
				sevenButton.setForeground(Color.white);
				sevenButton.setBorder(BorderFactory.createLineBorder(Color.black));
				sevenButton.setBounds(buttonSpacing, 2*buttonSpacing + buttonSize, buttonSize, buttonSize);
				sevenButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "7");
					}
				});
				buttonPanel.add(sevenButton);
				
				JButton eightButton = new JButton("8");
				eightButton.setMargin(new Insets(1,1,1,1));
				eightButton.setFont(f);
				eightButton.setBackground(Color.darkGray);
				eightButton.setForeground(Color.white);
				eightButton.setBorder(BorderFactory.createLineBorder(Color.black));
				
				eightButton.setBounds(buttonSpacing*2 + buttonSize, 2*buttonSpacing + buttonSize, buttonSize, buttonSize);
				eightButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "8");
					}
				});
				buttonPanel.add(eightButton);
				
				JButton nineButton = new JButton("9");
				nineButton.setMargin(new Insets(1,1,1,1));
				nineButton.setFont(f);
				nineButton.setBackground(Color.darkGray);
				nineButton.setForeground(Color.white);
				nineButton.setBorder(BorderFactory.createLineBorder(Color.black));
				
				nineButton.setBounds(buttonSpacing*3 + buttonSize*2, 2*buttonSpacing + buttonSize, buttonSize, buttonSize);
				nineButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "9");
					}
				});
				buttonPanel.add(nineButton);
				
				JButton rootButton = new JButton("Sqrt");
				rootButton.setMargin(new Insets(1,1,1,1));
				rootButton.setFont(f);
				rootButton.setBackground(Color.darkGray);
				rootButton.setForeground(Color.white);
				rootButton.setBorder(BorderFactory.createLineBorder(myblue));
				rootButton.setBounds(buttonSpacing*4 + buttonSize*3, 2*buttonSpacing + buttonSize, buttonSize, buttonSize);
				rootButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						squareroot();
					}
				});
				buttonPanel.add(rootButton);
				
				
				// row 3
				JButton fourButton = new JButton("4");
				fourButton.setMargin(new Insets(1,1,1,1));
				fourButton.setFont(f);

				fourButton.setBackground(Color.darkGray);
				fourButton.setForeground(Color.white);
				fourButton.setBorder(BorderFactory.createLineBorder(Color.black));
				fourButton.setBounds(buttonSpacing, 3*buttonSpacing + 2*buttonSize, buttonSize, buttonSize);
				fourButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "4");
					}
				});
				buttonPanel.add(fourButton);
				
				JButton fiveButton = new JButton("5");
				fiveButton.setMargin(new Insets(1,1,1,1));
				fiveButton.setFont(f);
				fiveButton.setBackground(Color.darkGray);
				fiveButton.setForeground(Color.white);
				fiveButton.setBorder(BorderFactory.createLineBorder(Color.black));
				fiveButton.setBounds(buttonSpacing*2 + buttonSize, 3*buttonSpacing + 2*buttonSize, buttonSize, buttonSize);
				fiveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "5");
					}
				});
				buttonPanel.add(fiveButton);
				
				JButton sixButton = new JButton("6");
				sixButton.setMargin(new Insets(1,1,1,1));
				sixButton.setFont(f);
				sixButton.setBackground(Color.darkGray);
				sixButton.setForeground(Color.white);
				sixButton.setBorder(BorderFactory.createLineBorder(Color.black));
				sixButton.setBounds(buttonSpacing*3 + buttonSize*2, 3*buttonSpacing + 2*buttonSize, buttonSize, buttonSize);
				sixButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "6");
					}
				});
				buttonPanel.add(sixButton);
				
				JButton percentButton = new JButton("%");
				percentButton.setMargin(new Insets(1,1,1,1));
				percentButton.setFont(f);
				percentButton.setBackground(Color.darkGray);
				percentButton.setForeground(Color.white);
				percentButton.setBorder(BorderFactory.createLineBorder(myblue));
				percentButton.setBounds(buttonSpacing*4 + buttonSize*3, 3*buttonSpacing + 2*buttonSize, buttonSize, buttonSize);
				percentButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						percentage();
					}
				});
				buttonPanel.add(percentButton);
				
				// row 4
				JButton oneButton = new JButton("1");
				oneButton.setMargin(new Insets(1,1,1,1));
				oneButton.setFont(f);
				oneButton.setBackground(Color.darkGray);
				oneButton.setForeground(Color.white);
				oneButton.setBorder(BorderFactory.createLineBorder(Color.black));
				oneButton.setBounds(buttonSpacing, 4*buttonSpacing + 3*buttonSize, buttonSize, buttonSize);
				oneButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "1");
					}
				});
				buttonPanel.add(oneButton);
				
				JButton twoButton = new JButton("2");
				twoButton.setMargin(new Insets(1,1,1,1));
				twoButton.setFont(f);
				twoButton.setBackground(Color.darkGray);
				twoButton.setForeground(Color.white);
				twoButton.setBorder(BorderFactory.createLineBorder(Color.black));twoButton.setBounds(buttonSpacing*2 + buttonSize, 4*buttonSpacing + 3*buttonSize, buttonSize, buttonSize);
				twoButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "2");
					}
				});
				buttonPanel.add(twoButton);
				
				JButton threeButton = new JButton("3");
				threeButton.setMargin(new Insets(1,1,1,1));
				threeButton.setFont(f);
				threeButton.setBackground(Color.darkGray);
				threeButton.setForeground(Color.white);
				threeButton.setBorder(BorderFactory.createLineBorder(Color.black));
				threeButton.setBounds(buttonSpacing*3 + buttonSize*2, 4*buttonSpacing + 3*buttonSize, buttonSize, buttonSize);
				threeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						txtNumber.setText(txtNumber.getText() + "3");
					}
				});
				buttonPanel.add(threeButton);
				
				JButton clearButton = new JButton("C");
				clearButton.setMargin(new Insets(1,1,1,1));
				clearButton.setFont(f);
				Color darkred = new Color(255, 30, 30, 255);
				clearButton.setBackground(Color.darkGray);
				clearButton.setForeground(Color.white);
				clearButton.setBorder(BorderFactory.createLineBorder(darkred));
				clearButton.setBounds(buttonSpacing*4 + buttonSize*3, 4*buttonSpacing + 3*buttonSize, buttonSize, buttonSize *2 + buttonSpacing);
				clearButton.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e)
					{
						clear();
					}
				});
				buttonPanel.add(clearButton);
				
				// row 5
				JButton zeroButton = new JButton("0");
				zeroButton.setMargin(new Insets(1,1,1,1));
				zeroButton.setFont(f);
				zeroButton.setBackground(Color.darkGray);
				zeroButton.setForeground(Color.white);
				zeroButton.setBorder(BorderFactory.createLineBorder(Color.black));
				zeroButton.setBounds(buttonSpacing, 5*buttonSpacing + 4*buttonSize, buttonSize*2 + buttonSpacing, buttonSize);
				zeroButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						String currentText = txtNumber.getText();
						
						if (currentText.contains(".") || 
								currentText.contains("1") || 
								currentText.contains("2") || 
								currentText.contains("3") || 
								currentText.contains("4") || 
								currentText.contains("5") || 
								currentText.contains("6") || 
								currentText.contains("7") || 
								currentText.contains("8") || 
								currentText.contains("9") ||
								currentText.trim().equals("")
								)
						{
							txtNumber.setText(txtNumber.getText() + "0");
						}
						else
						{
							return;
						}
					}
				});
				buttonPanel.add(zeroButton);
				
				JButton pointButton = new JButton(".");
				pointButton.setMargin(new Insets(1,1,1,1));
				pointButton.setFont(f);
				pointButton.setBackground(Color.darkGray);
				pointButton.setForeground(Color.white);
				pointButton.setBorder(BorderFactory.createLineBorder(Color.black));
				pointButton.setBounds(buttonSpacing*3 + buttonSize*2, 5*buttonSpacing + 4*buttonSize, buttonSize, buttonSize);
				pointButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						if (txtNumber.getText().contains("."))
						{
							return;
						}
						else
						{
							txtNumber.setText(txtNumber.getText() + ".");
						}
					}
				});
				buttonPanel.add(pointButton);
				
				// rot 6
				JButton equalsButton = new JButton("=");
				equalsButton.setMargin(new Insets(1,1,1,1));
				equalsButton.setFont(f);
				equalsButton.setBackground(Color.darkGray);
				equalsButton.setForeground(Color.white);
				equalsButton.setBorder(BorderFactory.createLineBorder(myblue));
				equalsButton.setBounds(buttonSpacing, 6*buttonSpacing + 5*buttonSize, buttonSize*4 + 3*buttonSpacing, buttonSize);
				equalsButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						Snack.this.equals();
					}

				});
				buttonPanel.add(equalsButton);
			
		rightPanel.add(buttonPanel);
		
		
		contentPanel.add(rightPanel);
		
	//	contentPanel.add(txtNumber);
	//	contentPanel.add(txtTotal);
		
		
		
		
		
		
		this.add(contentPanel);
		
		
	
	}
	
	//fsf@Override
	/*protected void processKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		super.processKeyEvent(e);
		System.out.println(e.getKeyCode() + " : " + KeyEvent.VK_0);
		if (e.getKeyCode() == KeyEvent.VK_0)
		{
			txtNumber.setText(txtNumber.getText() + "0");
		}
	}*/

	/*@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("pressed");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("released");
	}

	//@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("typed");
	}*/
	
	public String toBinary(int total) 
	{
		return Integer.toBinaryString(total);
	}
	
	public String toHex(int total) 
	{
		return Integer.toHexString(total);
	}
	
	public String toOctal(int total) 
	{
		return Integer.toOctalString(total);
	}

	public void preAction()
	{
		//if (this.txtNumber.getText().trim().equals(""))
		//{
		//	this.txtNumber.setText(" 0.0");
		//}
		
		try
		{
			this.previousInputNumber = Double.parseDouble(this.txtNumber.getText().trim());
		}
		catch (NumberFormatException e)
		{
			this.previousInputNumber = 0.0;
		}
		
	}
	
	public void undo()
	{
		this.currentTotalStackPointer--;
		this.txtTotal.setText(" " + this.totals.get(currentTotalStackPointer));
		this.checkEditHistoryPointer();
	}
	public void redo()
	{
		this.currentTotalStackPointer++;
		this.txtTotal.setText(" " + this.totals.get(currentTotalStackPointer));
		this.checkEditHistoryPointer();
	}
	
	
	
	public void postAction()
	{
		this.currentTotalStackPointer++;
		this.totals.add(currentTotalStackPointer, Double.parseDouble(this.txtTotal.getText()));
		this.checkEditHistoryPointer();
	}
	
	public void checkEditHistoryPointer()
	{
		if (currentTotalStackPointer >= 1 )
		{
			menuEditUndo.setEnabled(true);
		}
		else
		{
			menuEditUndo.setEnabled(false);
		}
	}
	
	public void add()
	{
		try
		{
			if (txtNumber.getText().trim().length() == 0)
			{
				throw new EmptyStringException();
			}
			if (txtNumber.getText().trim().equals("."))
			{
				throw new SolelyDecimalException();
			}
			
			this.preAction();
			this.previousAction = Action.ADD;
			double totalNumber = Double.parseDouble(txtTotal.getText());
			double inputNumber = Double.parseDouble(txtNumber.getText());
			double newNumber = totalNumber + inputNumber;
			
			txtTotal.setText(" " + newNumber);
			txtNumber.setText(" ");
			this.postAction();
		}
		catch (EmptyStringException e)
		{
			JOptionPane.showMessageDialog(null, "You must input a number to add to the total!", "Add: ", JOptionPane.ERROR_MESSAGE);
		}
		catch (SolelyDecimalException e)
		{
			JOptionPane.showMessageDialog(null, "Inputted number cannot be solely a decimal point.", "Add: ", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void subtract()
	{
		
		try
		{
			//System.out.println("Num: " + txtNumber.getText().trim());
			if (txtNumber.getText().trim().length() == 0)
			{
				throw new EmptyStringException();
			}
			if (txtNumber.getText().trim().equals("."))
			{
				throw new SolelyDecimalException();
			}
			
			this.preAction();
			this.previousAction = Action.SUBTRACT;
			double totalNumber = Double.parseDouble(txtTotal.getText().trim());
			double inputNumber = Double.parseDouble(txtNumber.getText().trim());
			
			double newNumber = totalNumber - inputNumber;
			txtTotal.setText(" " + newNumber);
			txtNumber.setText(" ");
			this.postAction();
		}
		catch (NumberFormatException e)
		{	
			e.printStackTrace();
		} 
		catch (EmptyStringException e) 
		{
			JOptionPane.showMessageDialog(null, "You must input a number to subtract from the total!", "Subtract: ", JOptionPane.ERROR_MESSAGE);
		}
		catch (SolelyDecimalException e)
		{
			JOptionPane.showMessageDialog(null, "Inputted number cannot be solely a decimal point.", "Subtract: ", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	
	public void multiply()
	{
		try
		{
			if (txtNumber.getText().trim().length() == 0)
			{
				throw new EmptyStringException();
			}
			if (txtNumber.getText().trim().equals("."))
			{
				throw new SolelyDecimalException();
			}
			
			this.preAction();
			this.previousAction = Action.MUTIPLY;
			
			
			double totalNumber = Double.parseDouble(txtTotal.getText());
			double inputNumber = Double.parseDouble(txtNumber.getText());
			double newNumber = totalNumber * inputNumber;
			txtTotal.setText(" " + newNumber);
			txtNumber.setText(" ");
			this.postAction();
		}
		catch (EmptyStringException e)
		{
			JOptionPane.showMessageDialog(null, "You must input a number to multiply the total by!", "Multiply: ", JOptionPane.ERROR_MESSAGE);	
		}
		catch (SolelyDecimalException e)
		{
			JOptionPane.showMessageDialog(null, "Inputted number cannot be solely a decimal point.", "Multiply: ", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void divide()
	{
		try
		{
			this.preAction();
			this.previousAction = Action.DIVIDE;
			double totalNumber = Double.parseDouble(txtTotal.getText());
			double inputNumber = Double.parseDouble(txtNumber.getText());
			double newNumber = totalNumber / inputNumber;
			//System.out.println(inputNumber + " : " + newNumber);
			if (inputNumber == 0.0)
			{
				throw new DivideByZeroException();
			}
			if (txtNumber.getText().trim().equals("."))
			{
				throw new SolelyDecimalException();
			}
			txtTotal.setText(" " + newNumber);
			txtNumber.setText(" ");
			this.postAction();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "You must input a number to find to divide by first!", "Divide: ", JOptionPane.ERROR_MESSAGE);
			
		} 
		catch (DivideByZeroException e) 
		{
			JOptionPane.showMessageDialog(null, "You cannot divide by zero!", "Divide: ", JOptionPane.ERROR_MESSAGE);
		}
		catch (SolelyDecimalException e)
		{
			JOptionPane.showMessageDialog(null, "Inputted number cannot be solely a decimal point.", "Divide: ", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void squareroot()
	{
	
		
		//double totalNumber = Double.parseDouble(txtTotal.getText());
		try
		{
			this.preAction();
//			if (this.txtNumber.getText().trim().equals(""))
//			{
//				}
			if (txtNumber.getText().trim().equals("."))
			{
				throw new SolelyDecimalException();
			}
			this.previousAction = Action.SQUAREROOT;
			double inputNumber = Double.parseDouble(txtNumber.getText());
			
			double newNumber = Math.sqrt(inputNumber);
			txtTotal.setText(" " + newNumber);
			txtNumber.setText(" ");
			this.postAction();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "You must input a number to find to square root of first!", "Square Root: ", JOptionPane.ERROR_MESSAGE);
			
		}
		catch (SolelyDecimalException e)
		{
			JOptionPane.showMessageDialog(null, "Inputted number cannot be solely a decimal point.", "Square Root: ", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void percentage()
	{
		try
		{
			this.preAction();
			this.previousAction = Action.PERCENT;
			
			double total = Double.parseDouble(this.txtTotal.getText().trim());
			double input = Double.parseDouble(this.txtNumber.getText().trim());
			
			if (total == 0.0)
			{
				throw new DivideByZeroException();
			}
			if (txtNumber.getText().trim().equals("."))
			{
				throw new SolelyDecimalException();
			}
			
			double percent = (input / total) * 100.0;
			this.txtTotal.setText(" " + percent);
			this.postAction();
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "You must input a number find the percentage of the total for first!", "Percentage: ", JOptionPane.ERROR_MESSAGE);
			
		} 
		catch (DivideByZeroException e) 
		{
			JOptionPane.showMessageDialog(null, "Neither the inputted number or the running total can be zero!", "Percentage: ", JOptionPane.ERROR_MESSAGE);
		}
		catch (SolelyDecimalException e)
		{
			JOptionPane.showMessageDialog(null, "Inputted number cannot be solely a decimal point.", "Percentage: ", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void equals()
	{
		if (this.previousAction == null) {
			return;
		}
		if (this.previousAction.equals(Action.ADD))
		{
			this.txtNumber.setText(" " + this.previousInputNumber);
			this.add();
		}
		else if (this.previousAction.equals(Action.SUBTRACT))
		{
			this.txtNumber.setText(" " + this.previousInputNumber);
			this.subtract();
		}
		else if (this.previousAction.equals(Action.MUTIPLY))
		{
			this.txtNumber.setText(" " + this.previousInputNumber);
			this.multiply();
		}
		else if (this.previousAction.equals(Action.DIVIDE))
		{
			this.txtNumber.setText(" " + this.previousInputNumber);
			this.divide();
		}
		
	}
	public void clear()
	{
		if (this.txtNumber.getText().trim().equals(""))
		{
			this.txtTotal.setText(" 0");
		}
		else
		{
			this.txtNumber.setText(" ");
		}
		this.postAction();
	}
	
	
	
}
