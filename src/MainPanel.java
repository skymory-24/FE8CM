import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int screenwidth = 240*2;
	private static final int screenheight = 160*2;

	private Screen screen;
	private JPanel controlpanel;
	private JPanel textpanel;

	private JComboBox ch1select;
	private JComboBox ch2select;

	private JButton eye1button;
	private JButton eye2button;

	private JButton mouth1button;
	private JButton mouth2button;
	
	private JButton flip1button;
	private JButton flip2button;

	private JButton bgbutton;
	private JComboBox bgselect;
	private String[] bgtitles;
	private ArrayList<String> bgnames;

	private JButton expbutton;

	private JButton charswitch;
	private JTextField textbox;

	private String[] charnames;
	private ArrayList<String> charanames;

	//private String[] charnamesone;
	//private String[] charnamestwo;
	private boolean cs;
	private int bgint;
	private String tempCharone;
	private String tempChartwo;
	private String mainText;

	private int eye1;
	private int eye2;

	private int mouth1;
	private int mouth2;

	private boolean flipped1;
	private boolean flipped2;

	private Scanner startup;
	private String start;

	public MainPanel()
	{
		setLayout(new BorderLayout());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		screen = new Screen();
		screen.setPreferredSize(new Dimension(screenwidth, screenheight));
		add(screen, BorderLayout.NORTH);

		controlpanel = new JPanel();
		controlpanel.setPreferredSize(new Dimension(screenwidth, 100));
		controlpanel.setLayout(new GridLayout(5,2));
		add(controlpanel, BorderLayout.CENTER);

		textpanel = new JPanel();
		textpanel.setLayout(new GridLayout(2,1));
		add(textpanel, BorderLayout.SOUTH);

		//charnamesone = new String[]{"Eirika", "Ephraim", "Seth", "Gilliam", "Franz", "Moulder", "Vanessa", "Ross", "Neimi", "Colm", "Garcia", "Innes", "Lute", "Natasha", "Cormag", "Forde", "Kyle", "Amelia", "Artur", "Gerik", "Tethys", "Marisa", "Saleh", "Ewan", "L'Arachel", "Dozla", "Rennae", "Duessel", "Myrrh", "DragonMyrrh", "Knoll", "Joshua", "Syrene", "Tana", "Anna", "Caellach", "Glen","None"};
		//charnamestwo = new String[]{"Ephraim", "Eirika", "Seth", "Gilliam", "Franz", "Moulder", "Vanessa", "Ross", "Neimi", "Colm", "Garcia", "Innes", "Lute", "Natasha", "Cormag", "Forde", "Kyle", "Amelia", "Artur", "Gerik", "Tethys", "Marisa", "Saleh", "Ewan", "L'Arachel", "Dozla", "Rennae", "Duessel", "Myrrh", "DragonMyrrh", "Knoll", "Joshua", "Syrene", "Tana", "Anna", "Caellach", "Glen","None"};

		//charnames = new String[]{"None", "Amelia", "Anna", "Artur", "Caellach", "Colm", "Cormag", "Dozla", "Duessel", "Eirika", "Ephraim", "Ewan", "Forde", "Franz", "Garcia", "Gerik", "Gilliam", "Glen", "Innes", "Joshua", "Knoll", "Kyle", "L'Arachel", "Lute", "Lyon", "Marisa", "Moulder", "Myrrh", "MyrrhDragon", "Natasha", "Neimi", "Orson", "Rennae", "Riev", "Ross", "Saleh", "Selena", "Seth", "Syrene", "Tana", "Tethys", "Valter", "Vanessa", "Vigarde"};

		charanames = new ArrayList<String>();
		String[] directories = new File("Characters").list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		for (int i = 0; i < directories.length; i++) {
			//System.out.println(directories[i]);
			charanames.add(directories[i]);
		}

		charnames = charanames.toArray(new String[0]);
		ch1select = new JComboBox<String>(charnames);
		ch1select.addActionListener(new ch1Listener());
		((JLabel)ch1select.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		controlpanel.add(ch1select);

		ch2select = new JComboBox<String>(charnames);
		ch2select.addActionListener(new ch2Listener());
		((JLabel)ch2select.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		controlpanel.add(ch2select);

		eye1 = 0;
		eye1button = new JButton("Change Left Eye: " + (eye1+1));
		eye1button.addActionListener(new eye1Listener());
		controlpanel.add(eye1button);
		setEye1();

		eye2 = 0;
		eye2button = new JButton("Change Right Eye: " + (eye2+1));
		eye2button.addActionListener(new eye2Listener());
		controlpanel.add(eye2button);
		setEye2();

		mouth1 = 0;
		mouth1button = new JButton("Change Left Mouth: " + (mouth1+1));
		mouth1button.addActionListener(new mouth1Listener());
		controlpanel.add(mouth1button);
		setMouth1();

		mouth2 = 0;
		mouth2button = new JButton("Change Right Mouth: " + (mouth2+1));
		mouth2button.addActionListener(new mouth2Listener());
		controlpanel.add(mouth2button);
		setMouth2();
		
		flipped1 = false;
		flip1button = new JButton("Flip Left: " + (flipped1));
		flip1button.addActionListener(new flip1Listener());
		controlpanel.add(flip1button);

		flipped2 = false;
		flip2button = new JButton("Flip Right: " + (flipped2));
		flip2button.addActionListener(new flip2Listener());
		controlpanel.add(flip2button);


		bgnames = new ArrayList<String>();
		String[] directoriesBgs = new File("Backgrounds").list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return !new File(current, name).isDirectory();
			}
		});
		for (int i = 0; i < directoriesBgs.length; i++) {
			//System.out.println(directoriesBgs[i]);
			bgnames.add(directoriesBgs[i].substring(0, directoriesBgs[i].lastIndexOf('.')));
		}

		bgtitles = bgnames.toArray(new String[0]);
		//bgtitles = new String[]{"None", "Rock", "Caer Pelyn", "House", "Normal Village", "Village Clear", "Village Sunset", "Serafew Village", "Serafew Flashback", "Port", "Ship", "Fireplace", "Castle Interior", "Castle Night", "Grado Chamber", "Throne Normal", "Throne Flashback", "Castle Bright", "Castle Dark", "Gate", "Garden", "Garden Flashback", "Manse Back", "Manse Flashback", "Cell", "Plain 1", "Plain 1 Fog", "Plain 1 Sunset", "Plain 2", "Plain 2 Fog", "Plain 2 Sunset", "Plain 2 Night", "Grass Plains", "Grass Plains 2", "Stream", "Trees", "Forest", "Town", "Castle Back", "Interior Black", "Interior Brown", "Fort Sunset", "Passage", "Burning Castle", "Stone Chamber", "Stone Flashback", "Renais Chamber", "White Temple", "Desert", "Darkling Woods", "Volcano", "Black Temple Outside", "Black Temple Inside"};
		bgselect = new JComboBox<String>(bgtitles);
		((JLabel)bgselect.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		bgselect.addActionListener(new bgListener());
		bgselect.setSelectedIndex(1);
		controlpanel.add(bgselect);

		expbutton = new JButton("Export");
		expbutton.addActionListener(new expListener());
		controlpanel.add(expbutton);

		charswitch = new JButton("Left is Talking");
		cs = true;
		charswitch.addActionListener(new CharSwitchListener());
		//charswitch.setPreferredSize(new Dimension(15,160));
		textpanel.add(charswitch);

		mainText = "This is an example conversation, and you don't need to press enter!";
		textbox = new JTextField("" + mainText);
		textbox.setFont(new Font("default", Font. PLAIN, 12));
		textbox.setHorizontalAlignment(JTextField.CENTER);
		textbox.setPreferredSize(new Dimension(160,50));
		//textbox.addActionListener(new textboxListener());
		textbox.getDocument().addDocumentListener(new textboxListener());
		textpanel.add(textbox);

		tempCharone = "None";

		tempChartwo = "None";
		
		ch1select.setSelectedIndex(0);
		ch2select.setSelectedIndex(1);

		/*try {
			startup = new Scanner(new File("Data/startup.txt"));
			start = startup.nextLine();
		} catch (FileNotFoundException e) {
		}
		 if(start.equals(false))
		 {
			 JOptionPane.showMessageDialog(new JFrame(), "This is your first time using the Sacred Stones Conversation Maker! Have you read the README file included in the .zip? It contains the font necessary for proper use of this program. Please download that, thanks! -@rasambowl");
			 try{
				    PrintWriter writer = new PrintWriter("Data/startup.txt", "UTF-8");
				    writer.println("true");
				    writer.close();
				} catch (IOException e) {
				   // do something
				}
		 }*/

	}
	class CharSwitchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

			if(cs == true){
				charswitch.setText("Right is Talking");
				cs = false;}
			else if(cs == false){
				charswitch.setText("Left is Talking");
				cs = true;}

			screen.setScreenDir(cs);
		}
	}
	class bgListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			/*if(bgint < 18)
					bgint++;
			  else
					bgint = 1;

			  screen.setBackground(bgint);

			  bgbutton.setText("Change Background: " + bgint);*/

			screen.setBackground(bgselect.getSelectedItem().toString());
		}
	}
	class ch1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			screen.setCharone(ch1select.getSelectedItem().toString());
			if (eye1button!=null) {
				eye1button.setEnabled(screen.getFirstKeyword(true)!=null);
				mouth1button.setEnabled(screen.getSecondKeyword(true)!=null);
				setEye1();
				setMouth1();
			}
		}
	}
	class ch2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			screen.setChartwo(ch2select.getSelectedItem().toString());
			if (eye2button!=null) {
				eye2button.setEnabled(screen.getFirstKeyword(false)!=null);
				mouth2button.setEnabled(screen.getSecondKeyword(false)!=null);
				setEye2();
				setMouth2();
			}
		}
	}
	class textboxListener implements DocumentListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mainText = textbox.getText();
			screen.setScreenText(mainText);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			mainText = textbox.getText();
			screen.setScreenText(mainText);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			mainText = textbox.getText();
			screen.setScreenText(mainText);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			mainText = textbox.getText();
			screen.setScreenText(mainText);
		}
	}
	class eye1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			eye1++;
			if(eye1 >= screen.getFirstKeywordCount(true))
				eye1 = 0;

			setEye1();
		}
	}
	class eye2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			eye2++;
			if(eye2 >=  screen.getFirstKeywordCount(false))
				eye2 = 0;

			setEye2();
		}
	}
	class mouth1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mouth1++;
			if(mouth1 >=  screen.getSecondKeywordCount(true))
				mouth1 = 0;

			setMouth1();
		}
	}
	class mouth2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			mouth2++;
			if(mouth2 >= screen.getSecondKeywordCount(false))
				mouth2 = 0;

			setMouth2();
		}
	}
	class flip1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			flipped1 = !flipped1;

			setFlip1();
		}
	}
	class flip2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			flipped2 = !flipped2;

			setFlip2();
		}
	}
	class expListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{


			String suggesteddir = ".";
			String EXTENSION = ".png";
			JFileChooser fileChooser = new JFileChooser(suggesteddir);
			JFrame choose = new JFrame();
			choose.setTitle("Save To ...");
			int status = fileChooser.showSaveDialog(choose);
			if (status == JFileChooser.APPROVE_OPTION) 
			{

				try 
				{
					File selectedFile = fileChooser.getSelectedFile();
					String newfile = selectedFile.getCanonicalPath();
					if (!newfile.endsWith(EXTENSION)) {
						newfile=newfile + EXTENSION;
					}

					ImageIO.write(screen.getImage(), "png", new File(newfile)); //write img to file

				} catch (IOException ex) {
					ex.printStackTrace();

				}
			}
		}
	}
	public void setEye1() {
		screen.seteye1(eye1);
		String keyword = screen.getFirstKeyword(true);
		if (keyword!=null) eye1button.setText("Change Left "+keyword+": " + (eye1+1));
		else eye1button.setText("");
	}
	public void setEye2() {
		screen.seteye2(eye2);
		String keyword = screen.getFirstKeyword(false);
		if (keyword!=null) eye2button.setText("Change Right "+keyword+": " + (eye2+1));
		else eye2button.setText("");
	}
	public void setMouth1() {
		screen.setmouth1(mouth1);
		String keyword = screen.getSecondKeyword(true);
		if (keyword!=null) mouth1button.setText("Change Left "+keyword+": " + (mouth1+1));
		else mouth1button.setText("");
	}
	public void setMouth2() {
		screen.setmouth2(mouth2);
		String keyword = screen.getSecondKeyword(false);
		if (keyword!=null) mouth2button.setText("Change Right "+keyword+": " + (mouth2+1));
		else mouth2button.setText("");
	}
	public void setFlip1() {
		screen.setFlip1(flipped1);
		flip1button.setText("Flip Left: " + (flipped1));
	}
	public void setFlip2() {
		screen.setFlip2(flipped2);
		flip2button.setText("Flip Right: " + (flipped2));
	}
}