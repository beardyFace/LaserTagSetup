
import gnu.io.CommPortIdentifier;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GameScreen extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 800;
	
//	private final GamePanel gamePanel;
	
	private SerialController serial;
	private JTextArea text_area = new JTextArea();
	
	private int player_id = 0;
	
	private JTextField team_field = new JTextField("", 5);
	private JTextField player_field = new JTextField(""+player_id, 5);
//	private JTextField gun_field = new JTextField("", 5);
	private JTextField health_field = new JTextField("", 5);
	private JTextField armour_field = new JTextField("", 5);
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Guns
	private final String[] guns = createGunMap();
	private static final HashMap<String, String> gun_map = new HashMap<String, String>(); 
	
	private static String[] createGunMap() {
		String[] guns = {"Multi", "Normal", "Heal", "APR", "PAPR"};
		gun_map.put(guns[0], "A" );
		gun_map.put(guns[1], "B" );
		gun_map.put(guns[2], "C" );
		gun_map.put(guns[3], "E" );
		gun_map.put(guns[4], "F" );
		return guns;
	}
	
	private JComboBox<String> gun_list = new JComboBox<String>(guns);
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JComboBox<String> com_list = new JComboBox<String>();
	
	public GameScreen()
	{
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			com_list.addItem(currPortId.getName());
		}
		
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		JPanel main_panel = new JPanel();
		main_panel.setSize(WIDTH, HEIGHT);
		main_panel.setLayout(new BorderLayout());
		
//		this.gamePanel = new GamePanel();
//		main_panel.add(gamePanel, BorderLayout.SOUTH);
		
		JPanel top_panel = new JPanel();
		top_panel.setSize(WIDTH, 200);
		
		JPanel button_panel = new JPanel();
		button_panel.setSize(WIDTH/2, 200);
		makeButton(button_panel, "Send", this);
		makeButton(button_panel, "Connect", this);
		makeButton(button_panel, "Close", this);
		makeButton(button_panel, "Next", this);
//		String velocity_str = box.getText();
		
		top_panel.add(button_panel, BorderLayout.WEST);
		
		team_field.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				insertInt(team_field);
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}
		});
		
		player_field.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				insertInt(player_field);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}
		});
		
//		gun_field.getDocument().addDocumentListener(new DocumentListener() {
//			
//			@Override
//			public void removeUpdate(DocumentEvent arg0) {
//				
//			}
//			
//			@Override
//			public void insertUpdate(DocumentEvent arg0) {
//				insertSingleChar(gun_field);
//			}
//			
//			@Override
//			public void changedUpdate(DocumentEvent arg0) {
//				
//			}
//		});
		
		health_field.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				insertInt(health_field);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}
		});
		
		armour_field.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				insertInt(armour_field);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}
		});
		
		JPanel text_panel = textPanels();
		top_panel.add(text_panel, BorderLayout.EAST);
		
		main_panel.add(top_panel, BorderLayout.NORTH);
		
		text_area.setSize(WIDTH, HEIGHT);
		JScrollPane sp = new JScrollPane(text_area); 
		JScrollBar vert = sp.getVerticalScrollBar();
		vert.setValue(vert.getMaximum());
		main_panel.add(sp, BorderLayout.CENTER);
		
		this.add(main_panel);
		
		this.pack();
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	

	private void insertSingleChar(final JTextField j_field) {
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		    	String a = setSingleChar(j_field.getText());
		    	a = a.toUpperCase();
		    	j_field.setText(a);
		    }
		});
	}
	
	private String setSingleChar(String text){
//		if(text.isEmpty())
//			return "";
		if(text.length() > 1)
			return text.substring(1, 2);
		return text;
	}
	
	private void insertInt(final JTextField j_field) {
		SwingUtilities.invokeLater(new Runnable()
		{
		    public void run()
		    {
		    	int a = setInt(j_field.getText());
		    	j_field.setText(""+a);
		    }
		});
	}
	
	protected int setInt(String text) {
		
		try{
			int value = Integer.parseInt(text);
			return value;
		}
		catch(Exception e){
			return 0;
		}
		
	}

	public JPanel textPanels(){
//		text_panel.add(health_field);
//		text_panel.add(armour_field);
//		text_panel.add(team_field);
//		text_panel.add(player_field);
		
		JPanel panel = new JPanel();
		panel.setSize(WIDTH/2, 200);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
//		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Team:"), c);
		c.gridx = 2;
		c.gridy = 0;
		panel.add(team_field, c);
		
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel("Player"), c);
		c.gridx = 2;
		c.gridy = 1;
		panel.add(player_field, c);
		
		c.gridx = 3;
		c.gridy = 0;
		panel.add(new JLabel("Armour:"), c);
		c.gridx = 5;
		c.gridy = 0;
		panel.add(armour_field, c);
		
		c.gridx = 3;
		c.gridy = 1;
		panel.add(new JLabel("Health:"), c);
		c.gridx = 5;
		c.gridy = 1;
		panel.add(health_field, c);
		
		c.gridx = 7;
		c.gridy = 0;
		panel.add(new JLabel("Gun:"), c);
		c.gridx = 9;
		c.gridy = 0;
		panel.add(gun_list, c);
		
		c.gridx = 11;
		c.gridy = 0;
		panel.add(new JLabel("COM:"), c);
		c.gridx = 13;
		c.gridy = 0;
		panel.add(com_list, c);
		
//		com_list
		return panel;
	}
	
	public void render()
	{
//		gamePanel.render();
//		gamePanel.repaint();
	}
	
	public static Button makeButton(JPanel panel, String name, ActionListener actionListener)
	{
		Button button = new Button(name);
		button.addActionListener(actionListener);
		panel.add(button);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent button) {
		if(button.getActionCommand() == "Send")
			sendString();
		else if(button.getActionCommand() == "Connect")
			connectSerial();
		else if(button.getActionCommand() == "Close")
			closeSerial();
		else if(button.getActionCommand() == "Next")
			nextPlayer();
	}
	
	private void nextPlayer(){
		player_id++;
		player_field.setText(""+player_id);
		health_field.setText("100");
		armour_field.setText("0");
	}
	
	private void closeSerial() {
		if(this.serial != null)
			this.serial.close();
		this.serial = null;
	}

	private void connectSerial() {
		int index = com_list.getSelectedIndex();
		this.serial = new SerialController(text_area, com_list.getItemAt(index));
		boolean connected = this.serial.initialize();
		if(!connected){
			this.serial = null;
			return;
		}
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendString(){
		if(this.serial != null){
			int health_text = Integer.parseInt(health_field.getText());
			byte[] health = convertIntToByte(health_text);
			int armour_text = Integer.parseInt(armour_field.getText());
			byte[] armour = convertIntToByte(armour_text);
			
			byte team = (byte)team_field.getText().charAt(0);
			byte player = (byte)player_field.getText().charAt(0);
			
			int index = gun_list.getSelectedIndex();
			String key = gun_list.getItemAt(index);
			String gun_id = gun_map.get(key);
			byte gun = (byte)gun_id.charAt(0);
			
			byte[] data = {team, player, gun, health[0], health[1], armour[0], armour[1]};
			serial.serialWrite(data);
		}
	}
	
	private byte[] convertIntToByte(int value){
		byte upper = (byte) (value >> 8); //Get the upper 8 bits
		byte lower = (byte) (value & 0xFF); //Get the lower 8bits
		byte[] bytes = {upper, lower};
		return bytes;
		
	}
	
	public static void main(String[] args) throws Exception {
		GameScreen screen = new GameScreen();
	}
}

