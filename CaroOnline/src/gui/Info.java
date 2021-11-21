package gui;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Info{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Info();
	}
	ImageIcon backgroundinf;
	JPanel pinf;
	ImageIcon infavatar;
	JTextField displayname_inf = new JTextField();
	JFrame window = new JFrame();
	public Info() {
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
		window.setTitle("Cờ Caro");
		window.setVisible(true);
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundinf=img;
	}
	public void setEventbchange_pass(JButton bchange_pass) {
		bchange_pass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bchange_pass đã được nhấn!!!");
			}		
		});
	}
	public void setEventbchange_displayname(JButton bchange_displayname) {
		bchange_displayname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bchange_displayname đã được nhấn!!!");
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
}