package net.nekomura.bilifollowernumshower;

import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.JSONObject;

public class APIUtils {
	public static int getFollower(String uid) throws IOException {
		JSONObject stat = JSONGetter.readJsonFromUrl("https://api.bilibili.com/x/relation/stat?vmid=" + uid);
		int followerNum;
		if(stat.has("code") && stat.getInt("code") == 0 && stat.has("data")) {
			followerNum = stat.getJSONObject("data").getInt("follower");
		}else {
			JOptionPane.showMessageDialog(null, "API錯誤。\n請確認輸入的uid是否正確。\n\n錯誤碼: " + -stat.getInt("code") + "\n錯誤訊息: " + stat.getString("message"), "錯誤", JOptionPane.WARNING_MESSAGE);
			ConfigUtils.set();
			followerNum = -1;
		}
		return followerNum;
	}
	
	public static String getUserName(String uid) throws IOException {
		JSONObject info = JSONGetter.readJsonFromUrl("https://api.bilibili.com/x/space/acc/info?mid=" + uid);
		String name = new String();
		if(info.has("code") && info.getInt("code") == 0 && info.has("data")) {
			name = info.getJSONObject("data").getString("name");
		}else {
			JOptionPane.showMessageDialog(null, "API錯誤。\n請確認輸入的uid是否正確。\n\n錯誤碼: " + -info.getInt("code") + "\n錯誤訊息: " + info.getString("message"), "錯誤", JOptionPane.WARNING_MESSAGE);
			ConfigUtils.set();
			name = "???";
		}
		return name;
	}
	
	public static Image getUserHead(String uid) throws IOException {
		JSONObject info = JSONGetter.readJsonFromUrl("https://api.bilibili.com/x/space/acc/info?mid=" + uid);
		String headURL = new String();
		if(info.has("code") && info.getInt("code") == 0 && info.has("data")) {
			headURL = info.getJSONObject("data").getString("face");
			return ImageUtils.getFromURL(headURL);
		}else {
			JOptionPane.showMessageDialog(null, "API錯誤。\n請確認輸入的uid是否正確。\n\n錯誤碼: " + -info.getInt("code") + "\n錯誤訊息: " + info.getString("message"), "錯誤", JOptionPane.WARNING_MESSAGE);
			ConfigUtils.set();
			headURL = "";
			return new ImageIcon("res\\misc\\NoAdvatar.png").getImage();
		}
	}
}
