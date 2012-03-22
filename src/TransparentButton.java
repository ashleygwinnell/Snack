

import java.awt.Color;
import java.awt.Event;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class TransparentButton extends JButton
{
	public Color semiblack = new Color(0,0,0, 100);
	public TransparentButton(String str)
	{
		super(str);
		this.setBackground(semiblack);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setForeground(Color.white);
	}
	
	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		super.processMouseMotionEvent(e);
	//	this.setBackground(Color.white);
	//	this.setBackground(semiblack);
	//	this.setBorder(BorderFactory.createLineBorder(Color.black));
	//	this.setForeground(Color.white);
	}
	@Override
	protected void processMouseEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		
		super.processMouseEvent(e);
		this.setBackground(semiblack);
		
	}
	
}
