package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

import dto.Account;
import dto.Requests;
import dto.Responses;
public class Signin{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Signin(null);
	}
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	
	ImageIcon backgroundinf;
	JPanel pinf;
	ImageIcon infavatar;
	JTextField username_inf = new JTextField();
	JTextField displayname_inf = new JTextField();
	JPasswordField setpass1 = new JPasswordField();
	JPasswordField setpass2 = new JPasswordField();
	JFrame window = new JFrame();
	public Signin(Socket _sk) {
		if (_sk != null)
			try {
				skToMainServer = _sk;
				dis = new DataInputStream(_sk.getInputStream());
				dos = new DataOutputStream(_sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		backgroundinf = null;
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		window.setSize(590,740);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		//thiet lap icon
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
		//thiet lap background
		this.setBackground(new ImageIcon("././resources/images/Info_BG.png"));
		pinf = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			if(backgroundinf!=null)
			{
				g1.drawImage(backgroundinf.getImage(),0,0,getWidth(),getHeight(),null);
			}}
			};
		pinf.setLayout(null);
		window.setContentPane(pinf);
		Menu_AvatarFrame infava = new Menu_AvatarFrame();
	    infavatar = new ImageIcon("././resources/images/favicon.png");
	    JPanel panel4 = infava.setAvatar(infavatar);
	    panel4.setLayout(null);
		panel4.setBounds(100,120, 150, 150);
		window.add(panel4);
		//button thay ava
		JButton bset_ava = new JButton(new ImageIcon("././resources/images/bchange_ava.png"));
		bset_ava.setBounds(300, 230, 150, 30);
		setEventbset_ava(bset_ava);
		window.add(bset_ava);
		//ten dang nhap
		JLabel lbinfo2 = new JLabel("Tên Đăng Nhập:");
		lbinfo2.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo2.setBounds(102,286, 150, 30);
		window.add(lbinfo2);
		//
		username_inf.setBounds(295,280, 210, 30);
		window.add(username_inf);
		//bietdanh
		JLabel lbinfo3 = new JLabel("Biệt Danh:");
		lbinfo3.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo3.setBounds(102,330, 150, 30);
		window.add(lbinfo3);
		//
		displayname_inf.setBounds(295,324, 210, 30);
		window.add(displayname_inf);
		//password
		JLabel lbinfo4 = new JLabel("Mật Khẩu:");
		lbinfo4.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo4.setBounds(102,374, 150, 30);
		window.add(lbinfo4);
		//
		setpass1.setBounds(295,368, 210, 30);
		window.add(setpass1);
		//
		JLabel lbinfo5 = new JLabel("Nhập lại mật khẩu:");
		lbinfo5.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo5.setBounds(102,418, 180, 30);
		window.add(lbinfo5);
		//
		setpass2.setBounds(295,411, 210, 30);
		window.add(setpass2);
		//
		JButton bsignin = new JButton(new ImageIcon("././resources/images/bsignin.png"));
		bsignin.setBounds(97, 543, 100, 30);
		setEventSignin(bsignin);
		window.add(bsignin);
		//
		JButton bexit_b = new JButton(new ImageIcon("././resources/images/bexit_b.png"));
		bexit_b.setBounds(252, 543, 100, 30);
		setEventCancel_Info(bexit_b);
		window.add(bexit_b);
		//
		//
		window.setTitle("Cờ Caro");
		window.setVisible(true);
	}
	private void setEventCancel_Info(JButton bexit_b) {
		bexit_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				return;
			}		
		});
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundinf=img;
	}
	public void setEventbset_ava(JButton bset_ava) {
		bset_ava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bset_ava đã được nhấn!!!");
			}		
		});
	}
	private void setEventSignin(JButton bsignin) {
		bsignin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (new String(setpass1.getPassword()).equals(new String(setpass2.getPassword()))) {
					try {
						dos.writeUTF(Requests.CreateNewUser);
						dos.writeUTF(username_inf.getText());
						dos.writeUTF(displayname_inf.getText());
						dos.writeUTF(new String(setpass1.getPassword()));
						String tb = dis.readUTF();
						if (tb.equals(Responses.UserCreate_Success)) {
							PopUpMessage.infoBox("Tạo tài khoản thành công, mời đăng nhập!!", "Thành công");
						}
						else if (tb.equals(Responses.UserCreate_Fail)){
							PopUpMessage.infoBox("Tên đăng nhập đã tồn tại!!", "Lỗi");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					PopUpMessage.infoBox("Nhập mật khẩu không khớp!!", "Lỗi");
					
				}
				window.dispose();
				return;
			}		
		});
	}
}