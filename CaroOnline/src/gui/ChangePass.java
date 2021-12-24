package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
public class ChangePass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			Socket a = new Socket("localhost",14972);
//			new Create_Room(a);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new ChangePass(null,null,null,null);
		
	}
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	String username;
	JFrame jf_mn;
	Info info;
	
	JFrame window = new JFrame();
	JPanel pcr;
	ImageIcon backgroundcp;
	JPasswordField cp_pass = new JPasswordField("");
	JPasswordField cp_passnew1 = new JPasswordField("");
	JPasswordField cp_passnew2 = new JPasswordField("");
	public ChangePass(Socket _sk, JFrame jf,Info _info, String _username) { // sửa ở đây
		if (_sk != null)
			try {
				skToMainServer = _sk;
				jf_mn = jf;
				info = _info;
				username = _username;
				dis = new DataInputStream(_sk.getInputStream());
				dos = new DataOutputStream(_sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		window.setSize(500,250);
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
		JLabel lbcp1 = new JLabel("Mật khẩu cũ: ");
		lbcp1.setFont(new Font("Arial", Font.PLAIN, 16));
		lbcp1.setBounds(50,28, 100, 30);
		window.add(lbcp1);
		//
		cp_pass.setBounds(230,33,205,24);
		window.add(cp_pass);
		//
		JLabel lbcp2 = new JLabel("Mật khẩu mới: ");
		lbcp2.setBounds(50,63, 150, 30);
		lbcp2.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(lbcp2);
		//
		cp_passnew1.setBounds(230,65,205,24);
		window.add(cp_passnew1);
		//
		JLabel lbcp3 = new JLabel("Nhập lại mật khẩu: ");
		lbcp3.setBounds(50,98, 150, 30);
		lbcp3.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(lbcp3);
		//
		cp_passnew2.setBounds(230,97,205,24);
		window.add(cp_passnew2);
		//
		JButton bchangepass = new JButton(new ImageIcon("././resources/images/bchangepass.png"));
		bchangepass.setBounds(75, 145, 100, 30);
		setEventbchangepass(bchangepass);
		window.add(bchangepass);
		//
		JButton bexit_b = new JButton(new ImageIcon("././resources/images/bexit_b.png"));
		bexit_b.setBounds(325, 145, 100, 30);
		setEventCancel(bexit_b);
		window.add(bexit_b);
		//
		window.setContentPane(pcr);
		window.setTitle("Cờ Caro - Đổi Mật Khẩu");
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
	public void setBackground(ImageIcon img)
	{
		this.backgroundcp=img;
	}
	public void setEventbchangepass(JButton bchangepass) {
		bchangepass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("bcreateroom đã được nhấn!!!");
				if (new String(cp_passnew1.getPassword()).equals(new String(cp_passnew2.getPassword()))) {
					try {
						dos.writeUTF(Requests.ChangePass);
						dos.writeUTF(username);
						dos.writeUTF(new String(cp_pass.getPassword()));
						dos.writeUTF(new String(cp_passnew1.getPassword()));
						String tb = dis.readUTF();
						if (tb.equals(Responses.ChangePassSuccess)) {
							PopUpMessage.infoBox("Đổi mật khẩu thành công!!", "Thành công");
						}
						else if (tb.equals(Responses.ChangePassFail)) {
							PopUpMessage.infoBox("Nhập mật khẩu không khớp!!", "Lỗi");
						}
						Account a = info.menuGetAccount(username);
						info.setInfoUser(a);
						window.dispose();
						return;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					PopUpMessage.infoBox("Nhập mật khẩu không khớp!!", "Lỗi");
					window.dispose();
					return;
				}
			}		
		});
	}
}