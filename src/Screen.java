 import javax.swing.*;
 import java.io.*; 
 import java.io.InputStream;
 import java.awt.*;
 import java.awt.event.*;
 import java.awt.geom.AffineTransform;
 import java.awt.geom.Rectangle2D;
 import java.awt.image.*;

 public class Screen extends JPanel
{
	private static final int width = 240*2;
	private static final int height = 160*2;
	
	
	private BufferedImage myImage;
	public Graphics2D myBuffer;
	private Timer t;
	private ImageIcon bg;
	private ImageIcon bg1, bg2, bg3, bg4, bg5, bg6, bg7, bg8, bg9, bg10, bg11, bg12, bg13, bg14, bg15, bg16, bg17, bg18;
	private ImageIcon textbox, textboxBig;
	
	private boolean oneleft;
	private boolean twoleft;
	
	
	private CharacterWrapper charone, chartwo;
	
	private String screenText;
	private boolean screenDir;
	private Font FEdialogue;
	private Color black, grey, red;
	private String text1, text2, text3;
	private boolean single;
	private boolean extended;
	
	private String onechar, twochar;
	
	private int eye1, eye2, mouth1, mouth2;
	private boolean flip1,flip2;
	
	FontMetrics fm;
	Rectangle2D bounds;
	
	public Screen()
	{
		screenDir = true;
		screenText = "This is an example conversation, and you don't need to press enter!";
		try {
			String fName = "Font/GBA FE Dialogue.ttf";
			//InputStream is = Screen.class.getResourceAsStream(fName);
			//Font FEdialogue = Font.createFont(Font.TRUETYPE_FONT, is);
			InputStream myStream = new BufferedInputStream(new FileInputStream(fName));
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            FEdialogue = ttfBase.deriveFont(Font.PLAIN, 25);   
		}
	    catch(IOException e) {
	    	FEdialogue = new Font("Comic Sans MS", Font.PLAIN, 25);
	    	System.out.println(e);
	    }
		catch(FontFormatException e) {
			FEdialogue = new Font("Comic Sans MS", Font.PLAIN, 25);
			System.out.println(e);
	    }
		black = new Color(40, 40, 40);
		grey = new Color(184, 184, 184);
		red = Color.red;
		
		myImage =  new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    myBuffer = myImage.createGraphics();
	    myBuffer.setFont(FEdialogue);
	    myBuffer.setColor(black);
		
	    eye1 = 1;
	    eye2 = 1;
	    mouth1 = 1;
	    mouth2 = 1;
	    
	    onechar = "Eirika";
	    twochar = "Ephraim";
	    
	    bg = new ImageIcon("Backgrounds/Town.png");
	    
	    textbox = new ImageIcon("Assets/textbox.png");
	    textboxBig = new ImageIcon("Assets/textbox_big.png");
	    
	    setCharone("Eirika");
	    setChartwo("Ephraim");
	    //charone = new Character("Assets/Eirika.png");
	    //chartwo = new Character("Assets/Ephraim.png");
	    
	    oneleft = true;
	    twoleft = false;
		
		t = new Timer(5, new Listener());
		setFocusable(true);
	    t.start();
	}
	
	public void paintComponent(Graphics g)
	{
	    g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}
	
	public BufferedImage getImage()
	{
		return myImage;
	}
	
	public void setBackground(String bgname)
	{
		bg = new ImageIcon("Backgrounds/"+bgname+".png");
	}
	public void setCharone(String chara)
	{
		charone = new CharacterWrapper();
		onechar = chara;
   	 	charone.gatherCharacterData(onechar);
	}
	public void setChartwo(String chara)
    {
		chartwo = new CharacterWrapper();
		twochar = chara;
   	 	chartwo.gatherCharacterData(twochar);
    }
	public void setScreenText(String temp)
	{
		screenText = temp;
	}
	public void setScreenDir(boolean temp)
	{
		screenDir = temp;
	}
	public void seteye1(int temp)
	{
		eye1 = temp;
	}
	public void seteye2(int temp)
	{
		eye2 = temp;
	}
	public void setmouth1(int temp)
	{
		mouth1 = temp;
	}
	public void setmouth2(int temp)
	{
		mouth2 = temp;
	}

	public void setFlip1(boolean flipped1) {
		flip1 = flipped1;
	} 
	public void setFlip2(boolean flipped2) {
		flip2 = flipped2;
	} 
	public void drawTextBox(boolean left, String text, Graphics2D myBuffer)
	{
		
		single = true;
		extended = false;
		
		fm = myBuffer.getFontMetrics();
		bounds = fm.getStringBounds(text, myBuffer);
		//int length = (int)bounds.getWidth();
		text1 = text + " ";
		text2 = "";
		text3 = "";
		double text1bound = fm.getStringBounds(text1, myBuffer).getWidth();
		while ( (text1bound > 0 && !text1.endsWith(" ")) || text1bound > 330) {
			String chara = text1.substring(text1.length()-1, text1.length());
			text1 = text1.substring(0, text1.length()-1);
			text2 = chara + text2;
			text1bound = fm.getStringBounds(text1, myBuffer).getWidth();
		}
		double text2bound = fm.getStringBounds(text2, myBuffer).getWidth();
		while ( (text2bound > 0 && !text2.endsWith(" ")) || text2bound > 330) {
			String chara = text2.substring(text2.length()-1, text2.length());
			text2 = text2.substring(0, text2.length()-1);
			text3 = chara + text3;
			text2bound = fm.getStringBounds(text2, myBuffer).getWidth();
		}
		if (text2.length()>0) {
			single = false;
		}
		if (text3.length()>0) {
			extended = true;
		}
		BufferedImage textimg;
		if (extended) {
			myBuffer.setColor(grey);
			textimg = new BufferedImage(
			textboxBig.getIconWidth(),
			textboxBig.getIconHeight(),
			BufferedImage.TYPE_INT_ARGB);
			Graphics g = textimg.createGraphics();
			// paint the Icon to the BufferedImage.
			textboxBig.paintIcon(null, g, 0,0);
			g.dispose();
		} else {
			myBuffer.setColor(grey);
			textimg = new BufferedImage(
			textbox.getIconWidth(),
			textbox.getIconHeight(),
			BufferedImage.TYPE_INT_ARGB);
			Graphics g = textimg.createGraphics();
			// paint the Icon to the BufferedImage.
			textbox.paintIcon(null, g, 0,0);
			g.dispose();
		}
		
		
		
		if(left == true){
			if(single){
				myBuffer.drawImage(textimg, 16,48, 184*2, 55*2, null);
				myBuffer.drawString(text, 34, 92);
				myBuffer.setColor(black);
				//myBuffer.setColor(red);
				myBuffer.drawString(text, 32, 92);
			} else if (!extended) {
				myBuffer.drawImage(textimg, 16,48, 184*2, 55*2, null);
				myBuffer.drawString(text1, 34, 92);
				myBuffer.drawString(text2, 34, 124);
				myBuffer.setColor(black);
				//myBuffer.setColor(red);
				myBuffer.drawString(text1, 32, 92);
				myBuffer.drawString(text2, 32, 124);
			} else {
				myBuffer.drawImage(textimg, 16 , 48-12, 184*2, 70*2, null);
				myBuffer.drawString(text1, 34, 92-12);
				myBuffer.drawString(text2, 34, 124-12);
				myBuffer.drawString(text3, 34, 124+32-12);
				myBuffer.setColor(black);
				myBuffer.drawString(text1, 32, 92-12);
				myBuffer.drawString(text2, 32, 124-12);
				myBuffer.drawString(text3, 32, 124+32-12);
			}
		} else {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-textimg.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			textimg = op.filter(textimg, null);

			if(single){
				myBuffer.drawImage(textimg, 96,48, 184*2, 55*2, null);
				myBuffer.drawString(text, 116, 92);
				myBuffer.setColor(black);
				//myBuffer.setColor(red);
				myBuffer.drawString(text, 114, 92);
			} else if (!extended) {
				myBuffer.drawImage(textimg, 96,48, 184*2, 55*2, null);
				myBuffer.drawString(text1, 116, 92);
				myBuffer.drawString(text2, 116, 124);
				myBuffer.setColor(black);
				//myBuffer.setColor(red);
				myBuffer.drawString(text1, 114, 92);
				myBuffer.drawString(text2, 114, 124);
			} else {
				myBuffer.drawImage(textimg, 96 , 48-12, 184*2, 70*2, null);
				myBuffer.drawString(text1, 116, 92-12);
				myBuffer.drawString(text2, 116, 124-12);
				myBuffer.drawString(text3, 116, 124+32-12);
				myBuffer.setColor(black);
				myBuffer.drawString(text1, 114, 92-12);
				myBuffer.drawString(text2, 114, 124-12);
				myBuffer.drawString(text3, 114, 124+32-12);
			}
		}
	}
	
	private class Listener implements ActionListener
	   {
	      public void actionPerformed(ActionEvent e)
	      {
	    	 myBuffer.drawImage(bg.getImage(), 0, 0, width, height, null);

	    	 
	    	 if (charone != null) charone.drawCharacter(eye1, mouth1, flip1, oneleft, myBuffer);
	    	 if (chartwo != null) chartwo.drawCharacter(eye2, mouth2, flip2, twoleft, myBuffer);
	    	 
	    	 drawTextBox(screenDir, screenText, myBuffer);
	    	 
	         repaint();
	      }
	   }

	public String getFirstKeyword(boolean isLeft) {
		if (isLeft) return charone.getFirstKeyword();
		return chartwo.getFirstKeyword();
	} 
	public String getSecondKeyword(boolean isLeft) {
		if (isLeft) return charone.getSecondKeyword();
		return chartwo.getSecondKeyword();
	}

	public int getFirstKeywordCount(boolean isLeft) {
		if (isLeft) return charone.getFirstKeywordCount();
		return chartwo.getFirstKeywordCount();
	}
	public int getSecondKeywordCount(boolean isLeft) {
		if (isLeft) return charone.getSecondKeywordCount();
		return chartwo.getSecondKeywordCount();
	}
}