import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class FECharacter implements CharacterInterface {
	private static final int wholeWidth = 128;
	private static final int wholeHeight = 112;

	private static final int mWidth = 32;
	private static final int mHeight = 16;

	private static final int eWidth = 32;
	private static final int eHeight = 16;
	protected int eyex;
	protected int eyey;
	protected int mouthx;
	protected int mouthy;
	
	protected ImageIcon charaIcon;
	protected String name;

	public FECharacter(String name, String eyex, String eyey, String mouthx, String mouthy) {
		this(name, Integer.parseInt(eyex), Integer.parseInt(eyey), Integer.parseInt(mouthx), Integer.parseInt(mouthy));
	}
	public FECharacter(String name, int eyex, int eyey, int mouthx, int mouthy) {
		super();
		this.eyex = eyex*2;
		this.eyey = eyey*2;
		this.mouthx = mouthx*2;
		this.mouthy = mouthy*2;
		this.name = name;
		charaIcon = new ImageIcon("Characters/" + name + "/img.png");
	}

	@Override
	public void drawCharacter(int eye, int mouth, boolean manualFlip, boolean leftornot, Graphics2D myBuffer) {
		BufferedImage charaimg = new BufferedImage(
				charaIcon.getIconWidth(),
				charaIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = charaimg.createGraphics();
		
		// paint the Icon to the BufferedImage.
		charaIcon.paintIcon(null, g, 0,0);
		g.dispose();
		BufferedImage mainimg = charaimg.getSubimage(0, 0, charaimg.getWidth()-eWidth, charaimg.getHeight()-(eHeight*2));
		boolean didAFlip = manualFlip ^ leftornot;
		if (didAFlip) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-charaimg.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			charaimg = op.filter(charaimg, null);
			
			AffineTransform tx2 = AffineTransform.getScaleInstance(-1, 1);
			tx2.translate(-mainimg.getWidth(null), 0);
			AffineTransformOp op2 = new AffineTransformOp(tx2, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			mainimg = op2.filter(mainimg, null);
		}
		int eyerectx = 0;
		int eyerectx2 = 0;
		int eyerecty = 0;
		int eyerecty2 = 0;
		int mouthrectx = 0;
		int mouthrectx2 = 0;
		int mouthrecty = 0;
		int mouthrecty2 = 0;
		int eyeFlipOffset = 0;
		int mouthFlipOffset = 0;
		if (didAFlip) {
			switch(eye)
			{
			case 0:
				break;
			case 1:
				eyerectx = 0;
				eyerecty = 48;
				eyerectx2 = 32;
				eyerecty2 = 64;
				break;
			case 2:
				eyerectx = 0;
				eyerecty = 64;
				eyerectx2 = 32;
				eyerecty2 = 80;
				break;
			}
			switch(mouth)
			{
			case 0:
				break;
			case 1:
				mouthrectx = 96;
				mouthrecty = 80;
				mouthrectx2 = 128;
				mouthrecty2 = 96;
				break;
			case 2:
				mouthrectx = 96;
				mouthrecty = 96;
				mouthrectx2 = 128;
				mouthrecty2 = 112;
				break;
			case 3:
				mouthrectx = 64;
				mouthrecty = 80;
				mouthrectx2 = 96;
				mouthrecty2 = 96;
				break;
			case 4:
				mouthrectx = 64;
				mouthrecty = 96;
				mouthrectx2 = 96;
				mouthrecty2 = 112;
				break;
			case 5:
				mouthrectx = 32;
				mouthrecty = 80;
				mouthrectx2 = 64;
				mouthrecty2 = 96;
				break;
			case 6:
				mouthrectx = 32;
				mouthrecty = 96;
				mouthrectx2 = 64;
				mouthrecty2 = 112;
				break;
			case 7:
				mouthrectx = 0;
				mouthrecty = 80;
				mouthrectx2 = 32;
				mouthrecty2 = 96;
				break;
			}
		} else {
			switch(eye)
			{
			case 0:
				break;
			case 1:
				eyerectx = 96;
				eyerecty = 48;
				eyerectx2 = 128;
				eyerecty2 = 64;
				break;
			case 2:
				eyerectx = 96;
				eyerecty = 64;
				eyerectx2 = 128;
				eyerecty2 = 80;
				break;
			}
			switch(mouth)
			{
			case 0:
				break;
			case 1:
				mouthrectx = 0;
				mouthrecty = 80;
				mouthrectx2 = 32;
				mouthrecty2 = 96;
				break;
			case 2:
				mouthrectx = 0;
				mouthrecty = 96;
				mouthrectx2 = 32;
				mouthrecty2 = 112;
				break;
			case 3:
				mouthrectx = 32;
				mouthrecty = 80;
				mouthrectx2 = 64;
				mouthrecty2 = 96;
				break;
			case 4:
				mouthrectx = 32;
				mouthrecty = 96;
				mouthrectx2 = 64;
				mouthrecty2 = 112;
				break;
			case 5:
				mouthrectx = 64;
				mouthrecty = 80;
				mouthrectx2 = 96;
				mouthrecty2 = 96;
				break;
			case 6:
				mouthrectx = 64;
				mouthrecty = 96;
				mouthrectx2 = 96;
				mouthrecty2 = 112;
				break;
			case 7:
				mouthrectx = 96;
				mouthrecty = 80;
				mouthrectx2 = 128;
				mouthrecty2 = 96;
				break;
			}
		}
		
		
		if(leftornot == true)
		{ 
			if (manualFlip) {
				eyeFlipOffset = -32;
				mouthFlipOffset = -64;
			}
			myBuffer.drawImage(mainimg, -64 + eWidth*2, 160, mainimg.getWidth()*2, mainimg.getHeight()*2, null);
			//could just negate the width and adjust all the x positions for everything accordingly but to be honest that is way too much work that i dont want to do right now
			if (eye!=0) {
				myBuffer.drawImage(charaimg, eyex+eyeFlipOffset, eyey, (eWidth*2)+eyex+eyeFlipOffset, (eHeight*2)+eyey, eyerectx, eyerecty, eyerectx2, eyerecty2, null);
			}
			if (mouth!=0) {
				myBuffer.drawImage(charaimg, mouthx+mouthFlipOffset, mouthy, (mWidth*2)+mouthx+mouthFlipOffset, (mHeight*2)+mouthy, mouthrectx, mouthrecty, mouthrectx2, mouthrecty2, null);
			}
			/*switch(eye)
			{
			case 0:
				break;
			case 1:
				//myBuffer.drawImage(charaimg, 80, 208, (eWidth*2)+80, (eHeight*2)+208, 0, 48, 32, 64, null);
				myBuffer.drawImage(charaimg, eyex, eyey, (eWidth*2)+eyex, (eHeight*2)+eyey, 0, 48, 32, 64, null);
				break;
			case 2:
				//myBuffer.drawImage(charaimg, 80, 208, (eWidth*2)+80, (eHeight*2)+208, 0, 64, 32, 80, null);
				myBuffer.drawImage(charaimg, eyex, eyey, (eWidth*2)+eyex, (eHeight*2)+eyey, 0, 64, 32, 80, null);
				break;
			}
			switch(mouth)
			{
			case 0:
				break;
			case 1:
				//myBuffer.drawImage(charaimg, 96, 240, (mWidth*2)+96, (mHeight*2)+240, 96, 80, 128, 96, null);
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 96, 80, 128, 96, null);
				break;
			case 2:
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 96, 96, 128, 112, null);
				break;
			case 3:
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 64, 80, 96, 96, null);
				break;
			case 4:
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 64, 96, 96, 112, null);
				break;
			case 5:
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 32, 80, 64, 96, null);
				break;
			case 6:
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 32, 96, 64, 112, null);
				break;
			case 7:
				myBuffer.drawImage(charaimg, mouthx, mouthy, (mWidth*2)+mouthx, (mHeight*2)+mouthy, 0, 80, 32, 96, null);
				break;
			}*/
		}
		else if(leftornot == false)
		{
			if (manualFlip) {
				eyeFlipOffset = 32;
				mouthFlipOffset = 64;
			}
			myBuffer.drawImage(mainimg, 288, 160, mainimg.getWidth()*2, mainimg.getHeight()*2, null);

			int teyex = 480 - (eyex + 64);
			int tmouthx = 480 - (mouthx + 64);
			if (eye!=0) {
				myBuffer.drawImage(charaimg, teyex+eyeFlipOffset, eyey, (eWidth*2)+teyex+eyeFlipOffset, (eHeight*2)+eyey, eyerectx, eyerecty, eyerectx2, eyerecty2, null);
			}
			if (mouth!=0) {
				myBuffer.drawImage(charaimg, tmouthx+mouthFlipOffset, mouthy, (mWidth*2)+tmouthx+mouthFlipOffset, (mHeight*2)+mouthy, mouthrectx, mouthrecty, mouthrectx2, mouthrecty2, null);
			}
			/*switch(eye)
			{
			case 0:
				break;
			case 1:
				//myBuffer.drawImage(charaimg, 336, 208, (eWidth*2)+336, (eHeight*2)+208, 96, 48, 128, 64, null);
				myBuffer.drawImage(charaimg, teyex, eyey, (eWidth*2)+teyex, (eHeight*2)+eyey, 96, 48, 128, 64, null);
				break;
			case 2:
				myBuffer.drawImage(charaimg, teyex, eyey, (eWidth*2)+teyex, (eHeight*2)+eyey, 96, 64, 128, 80, null);
				break;
			}
			switch(mouth)
			{
			case 0:
				break;
			case 1:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 0, 80, 32, 96, null);
				break;
			case 2:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 0, 96, 32, 112, null);
				break;
			case 3:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 32, 80, 64, 96, null);
				break;
			case 4:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 32, 96, 64, 112, null);
				break;
			case 5:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 64, 80, 96, 96, null);
				break;
			case 6:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 64, 96, 96, 112, null);
				break;
			case 7:
				myBuffer.drawImage(charaimg, tmouthx, mouthy, (mWidth*2)+tmouthx, (mHeight*2)+mouthy, 96, 80, 128, 96, null);
				break;
			}*/
		}
	}
	@Override
	public String getFirstKeyword() {
		return "Eyes";
	}
	@Override
	public String getSecondKeyword() {
		return "Mouth";
	}
	@Override
	public int getFirstKeywordCount() {
		return 3;
	}
	@Override
	public int getSecondKeywordCount() {
		return 8;
	}

}
