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
public class Info{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Info(null, null, null, null);
	}
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	Account useraccount;
	String username;
	JFrame jf_mn;
	Menu menu;
	Info info;
	
	ImageIcon backgroundinf;
	JPanel pinf;
	ImageIcon infavatar;
	JTextField displayname_inf = new JTextField();
	JFrame window = new JFrame();
	public Info(Socket _sk, JFrame _jf, Menu _menu, Account _account) {
		if (_sk != null)
			try {
				useraccount = new Account(0, null, null, false, null, 0, 0);
				skToMainServer = _sk;
				jf_mn = _jf;
				menu = _menu;
				useraccount = _account;
				username = useraccount.getUsername();
				dis = new DataInputStream(_sk.getInputStream());
				dos = new DataOutputStream(_sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		info = this;
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
		JButton bchange_ava = new JButton(new ImageIcon("././resources/images/bchange_ava.png"));
		bchange_ava.setBounds(300, 230, 150, 30);
		setEventbchange_ava(bchange_ava);
		window.add(bchange_ava);
		//id label
		JLabel lbinfo1 = new JLabel("ID:");
		lbinfo1.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo1.setBounds(270,150, 150, 30);
		window.add(lbinfo1);
		//ten dang nhap
		JLabel lbinfo2 = new JLabel("Tên Đăng Nhập:");
		lbinfo2.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo2.setBounds(102,286, 150, 30);
		window.add(lbinfo2);
		//bietdanh
		JLabel lbinfo3 = new JLabel("Biệt Danh:");
		lbinfo3.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo3.setBounds(102,330, 150, 30);
		window.add(lbinfo3);
		//
		displayname_inf.setBounds(275,324, 210, 30);
		window.add(displayname_inf);
		//
		
		//
		JButton bchange_displayname = new JButton(new ImageIcon("././resources/images/bchange.png"));
		bchange_displayname.setBounds(385, 365, 100, 30);
		setEventbchange_displayname(bchange_displayname);
		window.add(bchange_displayname);
		//password
		JLabel lbinfo4 = new JLabel("Mật Khẩu:");
		lbinfo4.setFont(new Font("Arial", Font.PLAIN, 20));
		lbinfo4.setBounds(102,420, 150, 30);
		window.add(lbinfo4);
		JButton bchange_pass = new JButton(new ImageIcon("././resources/images/bchange.png"));
		bchange_pass.setBounds(385, 452, 100, 30);
		setEventbchange_pass(bchange_pass);
		window.add(bchange_pass);
		//
		JButton bexit_b = new JButton(new ImageIcon("././resources/images/bexit_b.png"));
		bexit_b.setBounds(245, 543, 100, 30);
		setEventCancel_Info(bexit_b);
		window.add(bexit_b);
		//
		setInfoUser(useraccount);
		//
		window.setTitle("Cờ Caro");
		window.setVisible(true);
	}
	private void setEventCancel_Info(JButton bexit_b) {
		bexit_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account c = menu.menuGetAccount(username);
				menu.setMenuInfoUser(c);
				menu.window.repaint();
				window.dispose();
				return;
			}		
		});
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundinf=img;
	}
	public void setEventbchange_pass(JButton bchange_pass) {
		bchange_pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("bchange_pass đã được nhấn!!!");
				new ChangePass(skToMainServer,window, info, useraccount.getUsername());
			}		
		});
	}
	public void setEventbchange_displayname(JButton bchange_displayname) {
		bchange_displayname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bchange_displayname đã được nhấn!!!");
				String displayname = displayname_inf.getText();
				try {
					dos.writeUTF(Requests.ChangeDisplayName);
					dos.writeUTF(displayname);
					dos.write(useraccount.getId_user());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				useraccount = menuGetAccount(username);
				setInfoUser(useraccount);
			}		
		});
	}
	public void setEventbchange_ava(JButton bchange_ava) {
		bchange_ava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bchange_ava đã được nhấn!!!");
			}		
		});
	}
	public void setInfoUser(Account room) {
		
		JLabel lb_userid = new JLabel(room.getId_user()+"");
		lb_userid.setFont(new Font("Arial", Font.PLAIN, 20));
		lb_userid.setBounds(320,150, 150, 30);
		window.add(lb_userid);
		//
		JLabel lb_username = new JLabel(room.getUsername());
		System.out.println(room.getUsername());
		lb_username.setFont(new Font("Arial", Font.PLAIN, 20));
		lb_username.setBounds(275,286, 150, 30);
		window.add(lb_username);
		//
		displayname_inf.setText(room.getDisplayName());
		displayname_inf.setFont(new Font("Arial", Font.PLAIN, 20));
		//
		JPasswordField lb_pass = new JPasswordField(room.getPassword());
		lb_pass.setFont(new Font("Arial", Font.PLAIN, 20));
		lb_pass.setBounds(275,415, 210, 30);
		lb_pass.setEnabled(false);
		window.add(lb_pass);
		
	}
	public Account menuGetAccount(String _username) {
		useraccount.setUsername(_username);
		try {
			dos.writeUTF(Requests.GetAccountbyUsername);
			dos.writeUTF(_username);
			useraccount.setId_user(dis.read());
			useraccount.setDisplayName(dis.readUTF());
			useraccount.setPassword(dis.readUTF());
			useraccount.setBattleLost(dis.read());
			useraccount.setBattleWon(dis.read());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return useraccount;
	}
}