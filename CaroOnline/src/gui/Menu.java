package gui;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.*;

public class Menu extends JFrame{
	public static void main(String[] args) {
		new Menu(null, null);
	}
	
	Socket sk;
	DataInputStream dis;
	DataOutputStream dos;
	
	ImageIcon backgroundmn;
	JPanel pmn;
	ImageIcon ava;
	
	public Menu(Socket sk, JFrame jf) {
		if (sk != null)
			try {
				dis = new DataInputStream(sk.getInputStream());
				dos = new DataOutputStream(sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		backgroundmn = null;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1180,740);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		//thiet lap icon
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
		//thiet lap background
		this.setBackground(new ImageIcon("././resources/images/Menu_BG.png"));
		pmn = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			if(backgroundmn!=null)
			{
				g1.drawImage(backgroundmn.getImage(),0,0,getWidth(),getHeight(),null);
			}}
			};
		pmn.setLayout(null);
	    this.setContentPane(pmn);
	    //Avatar
	    Menu_AvatarFrame mnava = new Menu_AvatarFrame();
	    ava = new ImageIcon("././resources/images/favicon.png");
	    JPanel panel3 = mnava.setAvatar(ava);
	    panel3.setLayout(null);
		panel3.setBounds(50,20, 150, 150);
		this.add(panel3);
		//nut chinh sua thong tin
		JButton beditinfo = new JButton(new ImageIcon("././resources/images/beditinfo.png"));
		beditinfo.setBounds(30, 650, 190, 40);
		//
		beditinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(true);
			}		
		});
		//
		this.add(beditinfo);
		
	    this.setTitle("Cờ Caro");
		this.setVisible(true);
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundmn=img;
	}
}