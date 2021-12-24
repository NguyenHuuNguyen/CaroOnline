package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dto.Account;
import dto.Requests;
import dto.Responses;
import dto.Room;

public class Menu {
	public static void main(String[] args) {
//		try {
//			JFrame b = new JFrame();
//			Socket g = new Socket("localhost",14972);
//			new Menu(g,b);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		new Menu(null, null,null);
	}
	
	Socket skToMainServer;
	DataInputStream dis;
	DataOutputStream dos;
	JFrame jf;
	String username;
	
	ImageIcon backgroundmn;
	JPanel pmn;
	ImageIcon ava;
	JTextField search_mn = new JTextField("");
	JFrame window = new JFrame();
	Vector vtData = new Vector();
	Vector vtHeader = new Vector();
	Menu m;
	JPanel add_tb = new JPanel();
	JTable tb_room = new JTable();
	Room room = new Room(0, "", "", "", "",0,"","");
	Account account = new Account(0,"","",false,"",0,0);
	JLabel lbmn_if1 = new JLabel();
	JLabel lbmn_if2 = new JLabel();
	JLabel lbmn_if3 = new JLabel();
	public Menu(Socket sk, JFrame _jf, String _username) {
		if (sk != null)
			try {
				skToMainServer = sk;
				username = _username;
				jf = _jf;
				dis = new DataInputStream(sk.getInputStream());
				dos = new DataOutputStream(sk.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		m = this;
		backgroundmn = null;
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		setEventbeditinfo(beditinfo);
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
//		JCheckBox chb1 = new JCheckBox("Caro 5");
//		chb1.setBounds(300, 128, 150, 50);
//		chb1.setBackground(new Color(217, 246, 252));
//		chb1.setFont(new Font("Arial", Font.PLAIN, 20));
//		window.add(chb1);
		//
//		JCheckBox chb2 = new JCheckBox("Caro 3");
//		chb2.setBounds(300, 168, 150, 50);
//		chb2.setBackground(new Color(217, 246, 252));
//		chb2.setFont(new Font("Arial", Font.PLAIN, 20));
//		window.add(chb2);
		//
		JCheckBox chb3 = new JCheckBox("Phòng trống");
		chb3.setBounds(300, 128, 150, 50);
		chb3.setBackground(new Color(217, 246, 252));
		chb3.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb3);
		//
		JCheckBox chb4 = new JCheckBox("Phòng đầy");
		chb4.setBounds(300, 168, 150, 50);
		chb4.setBackground(new Color(217, 246, 252));
		chb4.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb4);
		//
		JCheckBox chb5 = new JCheckBox("Có mật khẩu");
		chb5.setBounds(500, 128, 150, 50);
		chb5.setBackground(new Color(217, 246, 252));
		chb5.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(chb5);
		//
		JCheckBox chb6 = new JCheckBox("Không mật khẩu");
		chb6.setBounds(500, 168, 200, 50);
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
		//table
		vtHeader.add("ID");
		vtHeader.add("TÊN PHÒNG");
		vtHeader.add("MẬT KHẨU");
		//vtHeader.add("CHẾ ĐỘ");
		vtHeader.add("KHÁN GIẢ");
		vtHeader.add("THAM GIA");
		window.add(add_tb);
		setTableData();
		window.add(lbmn_if1);
		window.add(lbmn_if2);
		window.add(lbmn_if3);
		account = menuGetAccount(username);
		setMenuInfoUser(account);
		//System.out.println("Server is started");
		// khong cho chinh kich thuoc(ko thanh cong)
		
		//
		window.setTitle("Cờ Caro - "+username);
		window.setVisible(true);
	}
	public void setBackground(ImageIcon img)
	{
		this.backgroundmn=img;
	}
	public void setEventblogout(JButton blogout) {
		blogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("blogout đã được nhấn!!!");
				jf.setVisible(true);
				try {
					dos.writeUTF(Requests.Logout);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				window.dispose();
			}		
		});
	}
	public void setEventbsearch(JButton bsearch) {
		bsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTableData();
			}		
		});
	}
	public void setEventbcreate_room(JButton bcreate_room) {
		bcreate_room.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("bcreate_room đã được nhấn!!!");
				new Create_Room(skToMainServer,window, m, username);
			}		
		});
	}
	public void setEventbeditinfo(JButton beditinfo) {
		beditinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("beditinfo đã được nhấn!!!");
				account = menuGetAccount(username);
				new Info(skToMainServer,window, m, account);
			}		
		});
	}
	public void setMenuInfoUser(Account account) {
		window.remove(lbmn_if1);
		window.remove(lbmn_if2);
		window.remove(lbmn_if3);
		
		lbmn_if1 = new JLabel(account.getDisplayName());
		lbmn_if1.setFont(new Font("Arial", Font.PLAIN, 20));
		lbmn_if1.setBounds(50,210, 150, 30);
		window.add(lbmn_if1);
		//
		lbmn_if2 = new JLabel("Trận thắng: "+ account.getBattleWon());
		lbmn_if2.setFont(new Font("Arial", Font.PLAIN, 20));
		lbmn_if2.setBounds(50,250, 150, 30);
		window.add(lbmn_if2);
		//
		lbmn_if3 = new JLabel("Trận thua:    "+ account.getBattleLost());
		lbmn_if3.setFont(new Font("Arial", Font.PLAIN, 20));
		lbmn_if3.setBounds(50,290, 150, 30);
		window.add(lbmn_if3);
	}
	public void setTableData() {
		window.remove(add_tb);
		add_tb = new JPanel();
		tb_room = new JTable();
		vtData = new Vector();
		add_tb.setLayout(null);
		Vector element;
		try {
			dos.writeUTF(Requests.GetAllRoom);
			while (true) {
				String t = dis.readUTF();
				if (t.equals(Responses.Theend)) {
					break;
				}
				element = new Vector();
				element.add(dis.read());
				element.add(dis.readUTF());
				element.add(dis.readUTF());
				element.add(dis.readUTF());
				element.add(dis.readUTF());
				//element.add(dis.readUTF());
				vtData.add(element);
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		DefaultTableModel tableModel = new DefaultTableModel(vtData,vtHeader) {
		    @Override
		    public boolean isCellEditable(int row, int column ) {
		       //all cells false
		    	if (column == 4) {return true;}
		    	else return false;
		    }
		};
		tableModel.removeRow(0);
		tb_room.setModel(tableModel);
		tb_room.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//
		tb_room.getColumnModel().getColumn(0).setPreferredWidth(117);
		tb_room.getColumnModel().getColumn(1).setPreferredWidth(160);
		tb_room.getColumnModel().getColumn(2).setPreferredWidth(160);
		tb_room.getColumnModel().getColumn(3).setPreferredWidth(160);
		tb_room.getColumnModel().getColumn(4).setPreferredWidth(160);
		//tb_room.getColumnModel().getColumn(5).setPreferredWidth(140);
		//
		tb_room.getColumn("THAM GIA").setCellRenderer(new ButtonRenderer());
		tb_room.getColumn("THAM GIA").setCellEditor(new ButtonEditor(new JCheckBox(),m));
		
		JScrollPane sp = new JScrollPane(tb_room);  
		sp.setSize(820,382);
		add_tb.setBounds(300, 290, 820, 382);
		add_tb.setBackground(new Color(217, 246, 252));
	    add_tb.add(sp);
		window.add(add_tb);
	}
	// thêm hàm tạo phòng, vd: void createRoom(id room) { new ....}
	public void goToRoom_Host(int iD) {	
		//System.out.println(iD);
		room = menuGetRoom(iD);
		account = menuGetAccount(username);
		User_Host a = new User_Host(skToMainServer, window, room, account);
		Thread t = new Thread(a);
		t.start();
		window.setVisible(false);
	}
	public void goToRoom_Player(int ID) {
		System.out.println(ID);
		room = menuGetRoom(ID);
		if (room == null) {
			PopUpMessage.infoBox("Phòng không còn tồn tại", "Thông báo");
			setTableData();
			return;
		}
		System.out.println(room.getCurrentPlayers());
		if (room.getCurrentPlayers() < 2) {
			account = menuGetAccount(username);
			try {
				dos.writeUTF(Requests.AddCurrentPlayer);
				dos.write(room.getRoomID());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			User_Player a = new User_Player(skToMainServer, window, room, account);
			Thread t = new Thread(a);
			t.start();
			window.setVisible(false);
		}
		else if (room.getCurrentPlayers() >= 2) {
			int input = JOptionPane.showConfirmDialog(null, "Xác nhận tham gia với tư cách khán giả?");
			if (input == 0) {
				try {
					dos.writeUTF(Requests.AddCurrentSpectator);
					dos.write(room.getRoomID());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				User_Spectator a = new User_Spectator(skToMainServer, window, room, account);
				Thread t = new Thread(a);
				t.start();
				window.setVisible(false);
			}
		}
	}
	public Room menuGetRoom(int id) {
		room = new Room(0, "", "", "", "",0,"","");
		room.setRoomID(id);
		try {
			dos.writeUTF(Requests.GetRoomByID);
			dos.write(id);
			String ans = dis.readUTF();
			if (ans.equals(Requests.RoomDoNotExist)) return null;
			room.setRoomName(dis.readUTF());
			room.setPassword(dis.readUTF());
			room.setHostDisplayName(dis.readUTF());
			room.setHostIPAddress(dis.readUTF());
			room.setCurrentPlayers(dis.read());
			room.setCurrentSpectators(dis.read());
			room.setMode(dis.readUTF());
			room.setAlowSpectator_String(dis.readUTF());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return room;
	}
	public Account menuGetAccount(String _username) {
		account.setUsername(_username);
		try {
			dos.writeUTF(Requests.GetAccountbyUsername);
			dos.writeUTF(_username);
			account.setId_user(dis.read());
			account.setDisplayName(dis.readUTF());
			account.setPassword(dis.readUTF());
			account.setBattleLost(dis.read());
			account.setBattleWon(dis.read());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
}