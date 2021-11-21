package gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
public class Menu_AvatarFrame extends JFrame{
	ImageIcon a;
	public JPanel setAvatar(ImageIcon ava) {
		a = new ImageIcon("././resources/images/Avatar.png");
		JPanel p3 = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			g1.drawImage(ava.getImage(),2,2,148,148,null);
			g1.drawImage(a.getImage(),0,0,getWidth(),getHeight(),null);
				
			}
		};
		p3.setBackground(Color.WHITE);
		return p3;
	}
}