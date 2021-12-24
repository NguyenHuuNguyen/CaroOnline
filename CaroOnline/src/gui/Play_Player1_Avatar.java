package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class Play_Player1_Avatar extends JFrame {
	ImageIcon pl1;
	public JPanel setPayer1(ImageIcon ava1, String displayName) {
		pl1 = new ImageIcon("././resources/images/player1.png");
		JPanel p1 = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				g1.drawImage(ava1.getImage(),0,0,100,100,null);
				g1.drawImage(pl1.getImage(),0,0,getWidth(),getHeight(),null);
				g1.setFont(new Font("arial",Font.BOLD,20));
				int l = displayName.length();
				int x = (145 - 11*l)/2 + 100 + 1*l/3;
				g1.drawString(displayName, x, 56);
			}
		};
		p1.setBackground(new Color(217, 246, 252));
		return p1;
	}
}