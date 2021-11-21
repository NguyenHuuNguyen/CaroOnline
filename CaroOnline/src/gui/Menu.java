package gui;

import java.awt.Color;
import java.awt.Font;
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
	
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	
	ImageIcon backgroundmn;
	JPanel pmn;
	ImageIcon ava;
	JTextField search_mn = new JTextField();
	JFrame window = new JFrame();
	
	public Menu(Socket sk, JFrame jf) {
		if (sk != null)
			try {
				skToMainServer = sk;
				dis = new DataInputStream(sk.getInputStream());
				dos = new DataOutputStream(sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		backgroundmn = null;
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		window.setSize(1180,740);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		//thiet lap icon
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
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
		window.setContentPane(pmn);
	    //Avatar
	    Menu_AvatarFrame mnava = new Menu_AvatarFrame();
	    ava = new ImageIcon("././resources/images/favicon.png");
	    JPanel panel3 = mnava.setAvatar(ava);
	    panel3.setLayout(null);
		panel3.setBounds(50,20, 150, 150);
		window.add(panel3);
		//nut chinh sua thong tin
		JButton beditinfo = new JButton(new ImageIcon("././resources/images/beditinfo.png"));
		beditinfo.setBounds(30, 600, 190, 40);
		window.add(beditinfo);
		//nut dang xuat
		JButton blogout = new JButton(new ImageIcon("././resources/images/blogout.png"));
		blogout.setBounds(50, 652, 150, 35);
		setEventblogout(blogout);
		window.add(blogout);
		//tao phong
		JButton bcreate_room = new JButton(new ImageIcon("././resources/images/bcreate_room.png"));
		bcreate_room.setBounds(300, 25, 250, 70);
		setEventbcreate_room(bcreate_room);
		window.add(bcreate_room);
		//checkbox...
		JCheckBox chb1 = new JCheckBox("Caro 5");
		chb1.setBounds(300, 128, 150, 50);
		chb1.setBackground(new Color(217, 246, 252));
		chb1.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb1);
		//
		JCheckBox chb2 = new JCheckBox("Caro 3");
		chb2.setBounds(300, 168, 150, 50);
		chb2.setBackground(new Color(217, 246, 252));
		chb2.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb2);
		//
		JCheckBox chb3 = new JCheckBox("Phòng trống");
		chb3.setBounds(500, 128, 150, 50);
		chb3.setBackground(new Color(217, 246, 252));
		chb3.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb3);
		//
		JCheckBox chb4 = new JCheckBox("Phòng đầy");
		chb4.setBounds(500, 168, 150, 50);
		chb4.setBackground(new Color(217, 246, 252));
		chb4.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb4);
		//
		JCheckBox chb5 = new JCheckBox("Có mật khẩu");
		chb5.setBounds(700, 128, 150, 50);
		chb5.setBackground(new Color(217, 246, 252));
		chb5.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb5);
		//
		JCheckBox chb6 = new JCheckBox("Không mật khẩu");
		chb6.setBounds(700, 168, 200, 50);
		chb6.setBackground(new Color(217, 246, 252));
		chb6.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb6);
		//
		search_mn.setBounds(300, 233, 225, 30);
		window.add(search_mn);
		//nut tim kiem
		JButton bsearch = new JButton(new ImageIcon("././resources/images/bsearch.png"));
		bsearch.setBounds(555, 233, 100, 30);
		setEventbsearch(bsearch);
		window.add(bsearch);
		//
		window.setTitle("Cờ Caro");
		window.setVisible(true);
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundmn=img;
	}
	public void setEventblogout(JButton blogout) {
		blogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("blogout đã được nhấn!!!");
			}		
		});
	}
	public void setEventbsearch(JButton bsearch) {
		bsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bsearch đã được nhấn!!!");
			}		
		});
	}
	public void setEventbcreate_room(JButton bcreate_room) {
		bcreate_room.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bcreate_room đã được nhấn!!!");
			}		
		});
	}
}