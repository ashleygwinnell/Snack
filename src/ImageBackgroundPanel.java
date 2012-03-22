

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageBackgroundPanel extends JPanel
{
	public BufferedImage backgroundImage;
	public JFrame frame;
	
	public ImageBackgroundPanel(JFrame frame) throws IOException
	{
		this.backgroundImage = ImageIO.read(new File("images/background.jpg"));
		
		this.frame = frame;
		
	}
	
	
	public void paint(Graphics g) 
	{
		
		g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
		super.paint(g);
		
		//this.repaint();
	}
}
