import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.io.BufferedReader;

public class CharacterWrapper 
{

	private static final int wholeWidth = 128;
	private static final int wholeHeight = 112;

	private static final int mWidth = 32;
	private static final int mHeight = 16;

	private static final int eWidth = 32;
	private static final int eHeight = 16;

	int righteyecalc;
	int rightmouthcalc;
	
	CharacterInterface character;

	public CharacterWrapper()
	{
		//int charalength = filename.length();
		//charaname = filename.substring(7, charalength-4);
	}

	public void gatherCharacterData(String name)
	{
		///////////////////////////////////////////
		//COORDINATE CALCULATIONS

		String style = "FE";

		String outstr = "";
		try {
			BufferedReader fr = new BufferedReader(new FileReader(new File("Characters/"+name+"/data.txt")));
			//myBuffer.setColor(Color.RED);
			//myBuffer.drawString(String.valueOf(fr.readLine()), 10, 25);
			String str;
			while ((str = fr.readLine()) != null) {
				outstr += str + "\n";
                //builder.append(str).append("\n");
            }
			fr.close();
			//outstr = Files.readString(Path.of("Characters/"+name+"/data.txt"));
		} catch (IOException e) {
			//myBuffer.drawString(String.valueOf(e), 10, 25);
		}
		/*try {scanner = FE8CM.getCharacterScanner(name);}
		catch (FileNotFoundException e){
			myBuffer.drawString("ERROR.", 10, 25);
			myBuffer.drawString("POSITION DATA COULD NOT BE RETRIVED.", 10, 50);
			myBuffer.drawString("GO TWEET ME AT @RASAMBOWL", 10, 125);
			myBuffer.drawString("I'LL TRY TO FIX IT FOR YOU.", 10, 150);
		}*/
		//System.out.println(String.valueOf(outstr));
		Pattern theWhale = Pattern.compile("\\w+:(\\w|\\.|-)+");
		Matcher theWhaleLocation = theWhale.matcher(outstr);
		List<MatchResult> weNeedToKillTheWhale = new ArrayList<MatchResult>();
		if (theWhaleLocation.find(0)) {
			weNeedToKillTheWhale.add(theWhaleLocation.toMatchResult());
			while (theWhaleLocation.find())
			{
				weNeedToKillTheWhale.add(theWhaleLocation.toMatchResult());
			}
		}
		HashMap<String, String> kpv = new HashMap<String, String>();
		for (MatchResult whale : weNeedToKillTheWhale) {
			String[] whaleData = whale.group().split(":");
			String whaleName = whaleData[0];
			String whaleValue = whaleData[1];
			switch (whaleName) {
			case "style":
				style = whaleValue;
				break;
			/*case "eyex":
				eyex = Integer.valueOf(whaleValue)*2;
				break;
			case "eyey":
				eyey = Integer.valueOf(whaleValue)*2;
				break;
			case "mouthx":
				mouthx = Integer.valueOf(whaleValue)*2;
				break;
			case "mouthy":
				mouthy = Integer.valueOf(whaleValue)*2;
				break;
			case "loop":
				loop = Integer.valueOf(whaleValue)*2;
				break;*/

			default:
				kpv.put(whaleName, whaleValue);
				break;
			}
		}
		
		switch (style) {
		case "FE":
			character = new FECharacter(name, kpv.getOrDefault("eyex", "0"), kpv.getOrDefault("eyey", "0"), kpv.getOrDefault("mouthx", "0"), kpv.getOrDefault("mouthy", "0"));
			break;
		case "TeaLeaves":
			character = new TeaLeafCharacter(name, kpv.getOrDefault("ofsxl", kpv.getOrDefault("ofsx", "0")), kpv.getOrDefault("ofsxr", kpv.getOrDefault("ofsx", "0")), kpv.getOrDefault("ofsy", "0"), kpv.getOrDefault("shouldflip", "false"), kpv.getOrDefault("scale", "1"));
			break;

		default:
			character = new FECharacter(name, 0, 0, 0, 0);
			break;
		}
		
		
		///////////////////////////////////////////

		
	}

	public void drawCharacter(int mouth, int eye, boolean manualFlip, boolean leftornot, Graphics2D myBuffer) {
		if (character!=null) character.drawCharacter(mouth, eye, manualFlip, leftornot, myBuffer);
	}

	public String getFirstKeyword() {
		return character.getFirstKeyword();
	}

	public String getSecondKeyword() {
		return character.getSecondKeyword();
	}

	public int getFirstKeywordCount() {
		return character.getFirstKeywordCount();
	}

	public int getSecondKeywordCount() {
		return character.getSecondKeywordCount();
	}
}
