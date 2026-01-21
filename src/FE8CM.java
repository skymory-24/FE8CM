import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
public class FE8CM
{
	private static final int width = 240*2;
	private static final int height = 600;
	public static void main(String[] args)
	{ 
		JFrame frame = new JFrame("GBA Fire Emblem Conversation Maker (VEI edition)");
		frame.setResizable(false);
		frame.setSize(width, height);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MainPanel());
		frame.setVisible(true);
	}
	
	
	public static Scanner getCharacterScanner(String name) throws FileNotFoundException {
		return new Scanner(new File("Characters/"+name+"/data.txt"));
	}
	
	public static void print(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			builder.append(objects[i]);
			if (i<objects.length-1) {
				builder.append(", ");
			}
		}
		System.out.println(String.valueOf(builder));
	}
}	