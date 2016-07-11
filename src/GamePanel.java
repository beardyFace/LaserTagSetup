
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
	private BufferedImage backgroundImage;
	private BufferedImage coverImage;
	private BufferedImage front;
	private BufferedImage back;
	
	public static final int WIDTH = GameScreen.WIDTH;//Game.width;
	public static int HEIGHT = GameScreen.HEIGHT;
	
	private int playerCX, playerCY;
	
	public GamePanel()
	{
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		backgroundImage =  gc.createCompatibleImage(WIDTH, HEIGHT, Transparency.OPAQUE);
		coverImage =  gc.createCompatibleImage(WIDTH, HEIGHT, Transparency.OPAQUE);
		front = gc.createCompatibleImage(WIDTH, HEIGHT, Transparency.OPAQUE);
		back = gc.createCompatibleImage(WIDTH, HEIGHT, Transparency.OPAQUE);
		
		Graphics g = backgroundImage.getGraphics();
		
		BufferedImage bi = null;
		try{bi = ImageIO.read(GamePanel.class.getResource(""));}catch(Exception e){System.out.println("Ahhh "+e);}
		g.drawImage(bi, 0, 0, WIDTH, HEIGHT, null);
		
		Graphics gcc = coverImage.getGraphics();
		
		BufferedImage ci = null;
		try{ci = ImageIO.read(GamePanel.class.getResource(""));}catch(Exception e){System.out.println("Hmm "+e);}
		gcc.drawImage(ci, 0, 0, WIDTH, HEIGHT, null);
		
		setDoubleBuffered(false);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setIgnoreRepaint(true);
	}
	
	public synchronized void paintComponent(Graphics g) {
		 g.drawImage(coverImage, 0, 0, null);
	     g.drawImage(front, playerCX, playerCY, null);
	}
	
	public void render()
	{
		Graphics2D g = back.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
	               RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		
		g.drawImage(backgroundImage, 0, 0, null);
		
		flip();
		
		g.dispose();
	}
	
	private void flip() {
	      BufferedImage temp = back;
	      back = front;
	      front = temp;
	}
}
