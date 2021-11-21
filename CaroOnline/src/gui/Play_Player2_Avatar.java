package gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class Play_Player2_Avatar extends JFrame {
	ImageIcon pl2;
	public JPanel setPayer2(ImageIcon ava2) {
		pl2 = new ImageIcon("././resources/images/player2.png");
		JPanel p2 = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			g1.drawImage(ava2.getImage(),150,0,100,100,null);
			g1.drawImage(pl2.getImage(),0,0,getWidth(),getHeight(),null);
			}
		};
		p2.setBackground(new Color(217, 246, 252));
		return p2;
	}
}