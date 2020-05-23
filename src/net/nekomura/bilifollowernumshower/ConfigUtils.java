package net.nekomura.bilifollowernumshower;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;

public class ConfigUtils {
	public static String uid = new String();
	public static int refreshFreq;
	
	public static void get() throws IOException {
		File config = new File("config\\config.conf");
		String content = FileUtils.readFileToString(config, "utf-8");
		JSONObject configJSON = new JSONObject(content);
		uid = configJSON.getString("uid");
		refreshFreq = configJSON.getInt("refreshFreq");
	}
	
	public static void set() throws IOException{
	    JLabel uidLabel = new JLabel("UID:", SwingConstants.RIGHT);
	    JLabel refreshFreqLabel = new JLabel("更新頻率(次/毫秒):", SwingConstants.RIGHT);
	    
	    JTextField uidField = new JTextField(uid, 35);
	    JTextField refreshFreqField = new JTextField(String.valueOf(refreshFreq), 35);
	    
		Container cp = new Container();
		cp.setLayout(new GridBagLayout());
		cp.setBackground(UIManager.getColor("control"));
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
	    c.gridy = GridBagConstraints.RELATIVE;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.insets = new Insets(2, 2, 2, 2);
	    c.anchor = GridBagConstraints.EAST;
	    
	    cp.add(uidLabel, c);
	    cp.add(refreshFreqLabel, c);

	    c.gridx = 1;
	    c.gridy = 0;
	    c.weightx = 1.0;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.anchor = GridBagConstraints.CENTER;

	    cp.add(uidField, c);
	    c.gridx = 1;
	    c.gridy = GridBagConstraints.RELATIVE;
	    cp.add(refreshFreqField, c);
	    c.weightx = 0.0;
	    c.fill = GridBagConstraints.NONE;
	    
	    int result = JOptionPane.showOptionDialog(null, cp,"設定",JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
	    if (result == 0) {
	    	if (uidField.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "UID不得為空。", "錯誤", JOptionPane.WARNING_MESSAGE);
	    		set();
	    		return;
	    	}else if (!(NumberUtils.isCreatable(uidField.getText()))) {
	    		JOptionPane.showMessageDialog(null, "UID只得輸入數字。", "錯誤", JOptionPane.WARNING_MESSAGE);
	    		set();
	    		return;
	    	}else if (!uidField.getText().matches("[0-9]+")) { 
	    		JOptionPane.showMessageDialog(null, "UID只有正整數。", "錯誤", JOptionPane.WARNING_MESSAGE);
	    		set();
	    		return;
	    	}else if (refreshFreqField.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "更新頻率不得為空。", "錯誤", JOptionPane.WARNING_MESSAGE);
	    		set();
	    		return;
	    	}else if (!(NumberUtils.isCreatable(refreshFreqField.getText()))) {
	    		JOptionPane.showMessageDialog(null, "更新頻率只得輸入數字。", "錯誤", JOptionPane.WARNING_MESSAGE);
	    		set();
	    		return;
	    	}else if (!refreshFreqField.getText().matches("[0-9]+") || Integer.valueOf(refreshFreqField.getText()) <= 0) { 
	    		JOptionPane.showMessageDialog(null, "更新頻率只得輸入正整數。", "錯誤", JOptionPane.WARNING_MESSAGE);
	    		set();
	    		return;
	    	}
	    	
	    	uid = uidField.getText();
	    	refreshFreq = Integer.valueOf( refreshFreqField.getText());
	    	
	    	write();
	    }
	}

	public static void write() {
		try {
			File configFile = new File("config\\config.conf");
			configFile.createNewFile();
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("config\\config.conf"),"UTF-8");
			JSONObject configJSON = new JSONObject();
			configJSON.put("uid", uid);
			configJSON.put("refreshFreq", refreshFreq);
			out.write(configJSON.toString());
			out.close();
		}catch (IOException exc) {
			
		}
	}
}
