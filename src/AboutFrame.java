

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutFrame extends JFrame 
{
	public AboutFrame()
	{
		super("About Snack");
		this.setSize(200, 80);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		JPanel content = new JPanel();
			JLabel label = new JLabel("Written by Ashley Gwinnell");
			content.add(label);			
			JLabel label2 = new JLabel("Copyright 2009.");
			content.add(label2);
		this.add(content);
		this.setVisible(true);
	}
	
	//public void paint(Graphics g)
	//{
	//	g.drawString("Written by Ashley Gwinnell", 10, 10);
	//}
}