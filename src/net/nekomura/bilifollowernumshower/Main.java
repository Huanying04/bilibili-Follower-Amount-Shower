package net.nekomura.bilifollowernumshower;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	static int mouseAtX;
	static int mouseAtY;
	static SystemTray tray = SystemTray.getSystemTray();
	static TrayIcon trayIcon = null;
	public static JLabel head = new JLabel();
	public static JLabel num = new JLabel();
	public static JLabel name = new JLabel();
	
	public static void main(String[] args) throws IOException, AWTException {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension sc = kit.getScreenSize();
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(290,185);
		mainFrame.setType(Type.UTILITY);
		mainFrame.setTitle("bilibili粉絲數顯示器");
		mainFrame.setLocation(39*sc.width/45-290,1*sc.height/9);
		mainFrame.setUndecorated(true);
		mainFrame.setAlwaysOnTop(true);
		mainFrame.setBackground(new Color(0,0,0,0));
		
		trayIcon = new TrayIcon(Image.getIcon().getImage(), "bilibili粉絲數顯示器", new PopupMenu());
		trayIcon.setImageAutoSize(true);
		tray.add(trayIcon);
		
		mainFrame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) 
            {                 
				mouseAtX = e.getPoint().x;
            	mouseAtY= e.getPoint().y;
            }
		});

		mainFrame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				mainFrame.setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));
			}
		});
		
		ConfigUtils.get();
		
		JButton settingButton = new JButton(Image.getSettingButtonImage());
		settingButton.setContentAreaFilled(false);
		settingButton.setBorderPainted(false);
		settingButton.setBounds(240, 29, 12, 12);
		settingButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	try {
		    		ConfigUtils.set();
		    	}catch(IOException err) {
		    		
		    	}
		    }
		});
		
		
		JButton exitButton = new JButton(Image.getExitButtonImage());
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.setBounds(255, 29, 12, 12);
		exitButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0);
		    }
		});
		
		JLabel nametagStripe = new JLabel(Image.getStripeImage());
		nametagStripe.setBounds(52, 19, 139, 39);
		
		num.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
		num.setForeground(new Color(0, 0, 0));
		num.setHorizontalAlignment(JLabel.CENTER);
		num.setBounds(31, 27, 257, 154);
		num.setText(NumberFormat.getInstance().format(APIUtils.getFollower(ConfigUtils.uid)));
		
		name.setFont(new Font("Microsoft JhengHei", name.getFont().getStyle(), 20));
		name.setForeground(new Color(0, 0, 0));
		name.setHorizontalAlignment(JLabel.LEFT);
		name.setBounds(79, 25, 123, 27);
		name.setText(APIUtils.getUserName(ConfigUtils.uid));

		head = new JLabel(new ImageIcon(APIUtils.getUserHead(ConfigUtils.uid).getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH)));
		head.setBounds(7, 7, 65, 65);
		
		JLabel headBorderLabel = new JLabel(Image.getHeadBorderImage());
		headBorderLabel.setBounds(0, 0, 76, 76);
		
		JLabel bgLabel = new JLabel(Image.getBgImage());
		bgLabel.setBounds(31, 27, 257, 154);
		
		JPanel p = new JPanel();
		p.setLayout(null);
		p.add(headBorderLabel);
		p.add(head);
		p.add(num);
		p.add(name);
		p.add(nametagStripe);
		p.add(settingButton);
		p.add(exitButton);
		p.add(bgLabel);
		p.setOpaque(false);
		mainFrame.getContentPane().add(p);
		mainFrame.setVisible(true);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					num.setText(NumberFormat.getInstance().format(APIUtils.getFollower(ConfigUtils.uid)));
					name.setText(APIUtils.getUserName(ConfigUtils.uid));
					head.setIcon(new ImageIcon(APIUtils.getUserHead(ConfigUtils.uid).getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH)));
				}catch(Throwable err) {
					
				}
				
 			}
		}, 0L, (long)ConfigUtils.refreshFreq);
	}
}