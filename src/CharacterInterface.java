import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public interface CharacterInterface {

	public void drawCharacter(int keyword1, int keyword2, boolean manualFlip, boolean isLeft, Graphics2D myBuffer);

	public String getFirstKeyword();

	public String getSecondKeyword();

	public int getFirstKeywordCount();

	public int getSecondKeywordCount();
}
