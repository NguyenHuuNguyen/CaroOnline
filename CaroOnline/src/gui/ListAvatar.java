package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;


import dto.Account;
import dto.Requests;
import dto.Responses;
public class ListAvatar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			Socket a = new Socket("localhost",14972);
//			new ListAvatar(a,0);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new ListAvatar(null,null,null);
		
	}
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	int ava_number;
	Signin signin;
	Info info;
	
	JFrame window = new JFrame();
	JPanel pcr;
	ImageIcon backgroundcp;
	JScrollPane sp = new JScrollPane();  
	JButton bava = new JButton();
	public ListAvatar(Socket _sk, Signin _signin, Info _info) { 
		if (_sk != null)
			try {
				skToMainServer = _sk;
				signin = _signin;
				info = _info;
				dis = new DataInputStream(_sk.getInputStream());
				dos = new DataOutputStream(_sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		window.setSize(500,450);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		//thiet lap icon
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
		//BG
		this.setBackground(new ImageIcon("././resources/images/Create_Room_BG.png"));
		pcr = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			if(backgroundcp!=null)
			{
				g1.drawImage(backgroundcp.getImage(),0,0,getWidth(),getHeight(),null);
			}}
			};
		pcr.setLayout(null);
		window.setContentPane(pcr);
		//
		JPanel a = new JPanel();
		
		a.setLayout(new GridLayout(3,10));
		sp.setBounds(0,0,490,440);
		try {
			dos.writeUTF(Requests.GetAvaNumber);
			ava_number = dis.read();
			for (int i = 1; i<= ava_number; i++) {
				bava = new JButton(i+"",new ImageIcon("././resources/avatar/"+i+".png"));
				bava.setSize(150,150);
				setEventClick(bava);
				a.add(bava);
			}
			sp = new JScrollPane(a);
			sp.setBounds(0,0,490,440);
			window.add(sp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		window.setTitle("Cờ Caro - List Ảnh Đại Diện");
		window.setVisible(true);
	}
	private void setEventCancel(JButton bexit_b) {
		bexit_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				return;
			}
		});
		
	}
	private void setEventClick(JButton bava) {
		bava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(signin == null)) {
					signin.id_ava = Integer.parseInt(bava.getText());
					signin.window.repaint();
					signin.setAvatarSignin(Integer.parseInt(bava.getText()));
				}else {
					info.id_ava = Integer.parseInt(bava.getText());
					info.window.repaint();
					info.getId_ava(Integer.parseInt(bava.getText()));
				}	
				window.dispose();
				return;
			}
		});
		
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundcp=img;
	}

}