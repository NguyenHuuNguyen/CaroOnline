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

import dto.Requests;
import dto.Responses;
public class Create_Room {

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
		new Create_Room(null,null,null);
		
	}
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	String username;
	JFrame jf_mn;
	
	JFrame window = new JFrame();
	JPanel pcr;
	ImageIcon backgroundcr;
	JTextField cr_roomname = new JTextField("");
	JPasswordField cr_roompass = new JPasswordField("");
	JRadioButton rd_cr5 = new JRadioButton("Caro 5", true);
	JRadioButton rd_cr3 = new JRadioButton("Caro 3", false);
	JRadioButton rd_yes = new JRadioButton("Có", true);
	JRadioButton rd_no = new JRadioButton("Không", false);	
	public Create_Room(Socket _sk, String _username, JFrame jf) { // sửa ở đây
		if (_sk != null)
			try {
				skToMainServer = _sk;
				jf_mn = jf;
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
			if(backgroundcr!=null)
			{
				g1.drawImage(backgroundcr.getImage(),0,0,getWidth(),getHeight(),null);
			}}
			};
		pcr.setLayout(null);
		window.setContentPane(pcr);
		//
		JLabel lbcr1 = new JLabel("Tên phòng: ");
		lbcr1.setFont(new Font("Arial", Font.PLAIN, 16));
		lbcr1.setBounds(50,28, 100, 30);
		window.add(lbcr1);
		//
		cr_roomname.setBounds(170,33,255,24);
		window.add(cr_roomname);
		//
		JLabel lbcr2 = new JLabel("Mật khẩu: ");
		lbcr2.setBounds(50,63, 100, 30);
		lbcr2.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(lbcr2);
		//
		cr_roompass.setBounds(170,65,255,24);
		window.add(cr_roompass);
		//
		JLabel lbcr3 = new JLabel("Chế độ: ");
		lbcr3.setBounds(50,98, 100, 30);
		lbcr3.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(lbcr3);
		//
		ButtonGroup bg_mod = new ButtonGroup();
		bg_mod.add(rd_cr3);
		bg_mod.add(rd_cr5);
		rd_cr3.setBackground(new Color(217, 246, 252));
		rd_cr3.setFont(new Font("Arial", Font.PLAIN, 16));
		rd_cr3.setBounds(355,105,85,20);
		window.add(rd_cr3);
		rd_cr5.setBounds(170,105,85,20);
		rd_cr5.setBackground(new Color(217, 246, 252));
		rd_cr5.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(rd_cr5);
		//
		JLabel lbcr4 = new JLabel("Khán giả: ");
		lbcr4.setBounds(50,130, 100, 30);
		lbcr4.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(lbcr4);
		//
		ButtonGroup bg_spectators = new ButtonGroup();
		bg_spectators.add(rd_yes);
		bg_spectators.add(rd_no);
		rd_yes.setBackground(new Color(217, 246, 252));
		rd_yes.setFont(new Font("Arial", Font.PLAIN, 16));
		rd_yes.setBounds(170,137,85,20);
		window.add(rd_yes);
		rd_no.setBounds(355,137,85,20);
		rd_no.setBackground(new Color(217, 246, 252));
		rd_no.setFont(new Font("Arial", Font.PLAIN, 16));
		window.add(rd_no);
		//
		JButton bcreateroom = new JButton(new ImageIcon("././resources/images/bcreateroom.png"));
		bcreateroom.setBounds(195, 170, 100, 30);
		setEventbcreateroom(bcreateroom);
		window.add(bcreateroom);
		//
		window.setContentPane(pcr);
		window.setTitle("Cờ Caro");
		window.setVisible(true);
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundcr=img;
	}
	public void setEventbcreateroom(JButton bcreateroom) {
		bcreateroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("bcreateroom đã được nhấn!!!");
				try {
					dos.writeUTF(Requests.CreateRoom);
					dos.writeUTF(username);
					dos.writeUTF(cr_roomname.getText());
					dos.writeUTF(new String(cr_roompass.getPassword()));
					if (rd_cr5.isSelected()) dos.writeUTF("Caro 5");
					else dos.writeUTF("Caro 3");
					if (rd_yes.isSelected()) dos.writeUTF("Có");
					else dos.writeUTF("Không");
					String s = dis.readUTF();
					if (s.equals(Responses.RoomCreate_Success)) {
						System.out.println("Tao phong thanh cong");
						dos.writeUTF(Requests.GetRoom);
						dos.writeUTF(cr_roomname.getText());
						int ID = dis.readInt();
						//new User_Host(skToMainServer, jf_mn,ID,username );
						// gọi đến menu
						// window.dispose
						jf_mn.setVisible(false);
						window.dispose();
						return;
					}
					if (s.equals(Responses.RoomCreate_Fail)) {
						PopUpMessage.infoBox("Tên Phòng trống!!!", "Lỗi");
						return;
					}
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}		
		});
	}
}