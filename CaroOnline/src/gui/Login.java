package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.*;

import dto.Requests;
import dto.Responses;
public class Login{
	public static void main(String[] args) {
		new Login();
	}

	Socket sk;
	DataInputStream dis;
	DataOutputStream dos;
	
	JPanel content = null;
	JFrame window = new JFrame();
	
	ImageIcon backgroundlo;
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	
	public void createConnection() {
		try {
			// sửa host để chạy qua lan
			sk = new Socket("localhost", 14972);
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			// hiển thị thông báo lỗi, thoát hoặc thử lại, thử lại thì gọi lại hàm này
		}
	}
	
	public Login() {
		createConnection();
		backgroundlo = null;
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		window.setSize(1180,740);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		//thiet lap icon
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
		//thiet lap BG
		this.setBackground(new ImageIcon("././resources/images/Login_BG.png"));
		content = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			if(backgroundlo!=null)
			{
				g1.drawImage(backgroundlo.getImage(),0,0,getWidth(),getHeight(),null);
			}}
			};
		content.setLayout(null);
	    window.setContentPane(content);
	    //
		JLabel t1 = new JLabel("Tên đăng nhập");
		t1.setBounds(415, 240, 200, 30);
		t1.setFont(new Font("Arial", Font.PLAIN, 15));
		window.add(t1);
		//
		username.setBounds(550, 240, 200, 30);
		window.add(username);
		//
		JLabel t2 = new JLabel("Mật khẩu");
		t2.setBounds(415, 300, 200, 30);
		t2.setFont(new Font("Arial", Font.PLAIN, 15));
		window.add(t2);
		//
		password.setBounds(550, 300, 200, 30);
		window.add(password);
		//
		JButton blogin = new JButton(new ImageIcon("././resources/images/blogin.png"));
		blogin.setBounds(520, 380, 140, 60);
		setEventblogin(blogin, this.sk);
		window.add(blogin);
		//
		JButton bmisspass = new JButton(new ImageIcon("././resources/images/bmisspass.png"));
		bmisspass.setBounds(415, 520, 150, 40);
		setEventbmisspass(bmisspass);
		window.add(bmisspass);
		//
		JButton bsignup = new JButton(new ImageIcon("././resources/images/bsignup.png"));
		bsignup.setBounds(607, 520, 150, 40);
		setEventbsignup(bsignup);
		window.add(bsignup);
		
		window.setTitle("Cờ Caro");
		window.setVisible(true);

	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundlo=img;
	}
	//Event button
	public void setEventblogin(JButton blogin, Socket sk) {
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dos.writeUTF(Requests.Login);
					dos.writeUTF(username.getText());
					dos.writeUTF(new String(password.getPassword()));
					String s = dis.readUTF();
					if (s.equals(Responses.LoginFail)) {
						PopUpMessage.infoBox("Sai tên đăng nhập hoặc mật khẩu!", "Lỗi");
						return;
					}
					if (s.equals(Responses.LoginSuccess)) {
						new Menu(sk, window);
						window.setVisible(false);
						return;
					}
					if (s.equals(Responses.AccountIsInUse)) {
						PopUpMessage.infoBox("Tài khoản đang được sử dụng ở nơi khác", "Lỗi");
						return;
					}
				}
				catch(Exception e1) {
					e1.printStackTrace();
					// hiển thị thông báo lỗi, thoát hoặc thử lại
				}
			}	
		});
	}
	public void setEventbmisspass(JButton bmisspass) {
		bmisspass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bmisspass đã được nhấn!!!");
			}		
		});
	}
	public void setEventbsignup(JButton bsignup) {
		bsignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bsignup đã được nhấn!!!");
			}
		});
	}
	
}