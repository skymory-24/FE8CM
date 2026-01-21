import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class TeaLeafCharacter implements CharacterInterface {

	protected int ofsxl;
	protected int ofsxr;
	protected int ofsy;
	protected boolean shouldflip;
	protected double scale;
	
	protected ArrayList<ImageIcon> images;

	public TeaLeafCharacter(String name, String ofsxl, String ofsxr, String ofsy, String shouldflip, String scale) {
		this(name, Integer.parseInt(ofsxl), Integer.parseInt(ofsxr), Integer.parseInt(ofsy), Boolean.parseBoolean(shouldflip), Double.parseDouble(scale));
	}
	
	public TeaLeafCharacter(String name, int ofsxl, int ofsxr, int ofsy, boolean shouldflip, double scale) {
		super();
		this.ofsxl = ofsxl;
		this.ofsxr = ofsxr;
		this.ofsy = ofsy;
		this.shouldflip = shouldflip;
		this.scale = scale;
		this.images = new ArrayList<ImageIcon>();
		String[] directoriesBgs = new File("Characters/" + name + "/Sprites").list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return !new File(current, name).isDirectory();
			}
		});
		for (int i = 0; i < directoriesBgs.length; i++) {
			ImageIcon charaIcon = new ImageIcon("Characters/" + name + "/Sprites/"+directoriesBgs[i]);
			images.add(charaIcon);
		}
	}


	@Override
	public void drawCharacter(int keyword1, int keyword2, boolean manualFlip, boolean leftornot, Graphics2D myBuffer) {
		ImageIcon charaIcon = images.get(keyword1);
		BufferedImage charaimg = new BufferedImage(
				charaIcon.getIconWidth(),
				charaIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = charaimg.createGraphics();
		
		// paint the Icon to the BufferedImage.
		charaIcon.paintIcon(null, g, 0,0);
		g.dispose();
		int eWidth = (int) Math.round(charaIcon.getIconWidth());
		int eHeight = (int) Math.round(charaIcon.getIconHeight());
		//FE8CM.print(eWidth, eHeight);
		if (manualFlip) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-charaimg.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			charaimg = op.filter(charaimg, null);
		}
		if (leftornot)
		{
			if (shouldflip) {
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-charaimg.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				charaimg = op.filter(charaimg, null);
			}
			myBuffer.drawImage(charaimg, ofsxl, ofsy, (int)(eWidth*scale)+ofsxl, (int)(eHeight*scale)+ofsy, 0, 0, (int)(eWidth), (int)(eHeight), null);
		} else {
			myBuffer.drawImage(charaimg, ofsxr, ofsy, (int)(eWidth*scale)+ofsxr, (int)(eHeight*scale)+ofsy, 0, 0, (int)(eWidth), (int)(eHeight), null);
		}
	}

	@Override
	public String getFirstKeyword() {
		return "Sprite";
	}

	@Override
	public String getSecondKeyword() {
		return null;
	}
	@Override
	public int getFirstKeywordCount() {
		return images.size();
	}
	@Override
	public int getSecondKeywordCount() {
		return 0;
	}

}
