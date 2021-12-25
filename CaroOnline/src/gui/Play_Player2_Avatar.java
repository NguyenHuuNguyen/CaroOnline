package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class Play_Player2_Avatar extends JFrame {
	ImageIcon pl2;
	public JPanel setPayer2(ImageIcon ava2, String displayName, boolean isTurn) {
		pl2 = new ImageIcon("././resources/images/player2.png");
		JPanel p2 = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
				super.paintComponent(g1);
				g1.drawImage(ava2.getImage(),150,0,100,100,null);
				g1.drawImage(pl2.getImage(),0,0,getWidth(),getHeight(),null);
				g1.setFont(new Font("arial",Font.BOLD,20));
				int l = displayName.length();
				int x = (145 - 11*l)/2 + 3 + 1*l/3;
				g1.drawString(displayName, x, 24);
				if (isTurn) g1.drawString("X", 70, 56);
			}
		};
		p2.setBackground(new Color(217, 246, 252));
		return p2;
	}
}