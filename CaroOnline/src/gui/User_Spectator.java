package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

import dto.Account;
import dto.Requests;
import dto.Responses;
import dto.Room;


public class User_Spectator implements Runnable{

	public static void main(String[] args) {
		User_Spectator a = new User_Spectator(new Socket(), null, null, null); 
		Thread t = new Thread(a);
		t.start();
	}
	
	boolean isrun = true;
	JFrame window = new JFrame();
	JFrame menu = null;
	JPanel board = null;
	
	static int n = 15;
    static int s = 30;
	ImageIcon background;
	JPanel p ;
	JPanel panel1;
	JPanel panel2;
	JTextField tf = new JTextField();
	JTextArea ta = new JTextArea();
	ImageIcon ava1;
	ImageIcon ava2;
	JButton bsend;
	JButton bop;
	JButton bexit;
	JButton bdrawProposal;
	JButton bff;
	String hostDisplayName;
	String p2DisplayName;
	int host_id_ava;
	int p2_id_ava;
	
	Socket skToMainServer;
	DataInputStream disToMainServer;
	DataOutputStream dosToMainServer;
	
	Socket skToHost;
	DataInputStream disToHost;
	DataOutputStream dosToHost;
	
	Account userAccount;
	Room currentRoom = null;
	int[][] boardXY = new int[n][n];
	
	public User_Spectator(Socket sk, JFrame jf, Room _room, Account _account){
		menu = jf;
		if (sk != null)
			try {	
				currentRoom = _room;
				userAccount = _account;
				skToHost = new Socket(currentRoom.getHostIPAddress(), currentRoom.getHostPort());
				disToHost = new DataInputStream(skToHost.getInputStream());
				dosToHost = new DataOutputStream(skToHost.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		window.setTitle("Cờ Caro - khán giả - " + userAccount.getDisplayName());
		background = null;
		ta.setEditable(false);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setResizable(false);
		window.setSize(1180,740);
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		//thiet lap icon
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
		//thiet lap background
		this.setBackground(new ImageIcon("././resources/images/Play_BG.png"));
		p = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			if(background!=null)
			{
				g1.drawImage(background.getImage(),0,0,getWidth(),getHeight(),null);
			}}
			};
		p.setLayout(null);
		window.setContentPane(p);
	    //scoll
		JScrollPane scroll = new JScrollPane(ta);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(850, 250, 300, 350);
	    window.add(scroll);
	    //textfield
	    tf.setBounds(850, 615, 230, 30);
	    window.add(tf);
		//button "send"
		bsend = new JButton(new ImageIcon("././resources/images/bsend.png"));
		bsend.setBounds(1090, 615, 60, 30);
		setEventbsend(bsend);
		window.add(bsend);
		//button luachon
		bop = new JButton(new ImageIcon("././resources/images/bop.png"));
		bop.setBounds(1102, 217, 45, 25);
		setEventbop(bop);
		window.add(bop);
		//button ve trang chu
		bexit = new JButton(new ImageIcon("././resources/images/bexit.png"));
		bexit.setBounds(1087, 10, 60, 30);
		setEventbexit(bexit, jf);
		window.add(bexit);	
//		//button xin hoa
//		bdrawProposal = new JButton(new ImageIcon("././resources/images/bdrawproposal.png"));
//		bdrawProposal.setBounds(850, 95, 140, 75);
//		    //bdrawProposal.setEnabled(false);
//		window.add(bdrawProposal);
//		//button dau hang
//		bff = new JButton(new ImageIcon("././resources/images/bff.png"));
//		bff.setBounds(1008, 95, 140, 75);
//		    //bff.setEnabled(false);
//		window.add(bff);
//		//player1(tam)
		ava1 = new ImageIcon("././resources/images/favicon.png");
		Play_Player1_Avatar plr1 = new Play_Player1_Avatar();
		panel1 = plr1.setPayer1(ava1, "", false);
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		window.add(panel1);
		
		//player2(tam)
		ava2 = new ImageIcon("././resources/images/favicon.png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		panel2 = plr2.setPayer2(ava2, "", false);
		panel2.setLayout(null);
		panel2.setBounds(560,10, 250, 100);
		window.add(panel2);
		
		//board
		board = drawBoard();
		window.add(board);
		
		window.setVisible(true);
	}
	//
	public void setBackground(ImageIcon img)
	{
		this.background=img;
	}
	public void setPlayer2Info(String displayname, int id_ava_p2) {
		window.remove(panel2);
		ava2 = new ImageIcon("././resources/avatar/"+id_ava_p2+".png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		panel2 = plr2.setPayer2(ava2, displayname, false);
		panel2.setLayout(null);
		panel2.setBounds(560,10, 250, 100);
		window.add(panel2);
		window.repaint();
	}
	public void setPlayer1Info(String displayname, int id_ava_host) {
		window.remove(panel1);
		ava1 = new ImageIcon("././resources/avatar/"+id_ava_host+".png");
		Play_Player1_Avatar plr1 = new Play_Player1_Avatar();
		JPanel panel1 = plr1.setPayer1(ava1, displayname, false);
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		window.add(panel1);
		window.repaint();
	}
	void setPlayer2Turn(boolean isTurn) {
		window.remove(panel2);
		ava2 = new ImageIcon("././resources/avatar/"+p2_id_ava+".png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		panel2 = plr2.setPayer2(ava2, p2DisplayName, isTurn);
		panel2.setLayout(null);
		panel2.setBounds(560,10, 250, 100);
		window.add(panel2);
		window.repaint();
	}
	void setPlayer1Turn(boolean isTurn) {
		window.remove(panel1);
		ava1 = new ImageIcon("././resources/avatar/"+host_id_ava+".png");
		Play_Player1_Avatar plr1 = new Play_Player1_Avatar();
		panel1 = plr1.setPayer1(ava1, hostDisplayName, isTurn);
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		window.add(panel1);
		window.repaint();
	}
	public JPanel drawBoard() {
		JPanel p1 = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(int i = 0; i<= n; i++)
				{
					g.drawLine(0, i*s, n*s, i*s);
					g.drawLine(i*s, 0, i*s, n*s);
				}
				g.setFont(new Font("arial",Font.BOLD,s));
				for(int i = 0; i < n; i++) {
					for(int j = 0; j < n; j++) {
						if (boardXY[i][j] == 0) continue;
						String st = "o";
						g.setColor(Color.BLUE);
						int x = j*s + s - s/2 - s/4 - s/11;
						int y = i*s + s - s/2 + s/4 + s/11;
						if (boardXY[i][j] == 2) {
							g.setColor(Color.RED);
							st = "x";
						}
						g.drawString(st, x, y);
					}
				}
			}
		};
		p1.setLayout(null);
		p1.setBackground(Color.WHITE);
		p1.setBounds(188, 125, n*s + 1, n*s + 1);
		return p1;
	}
	
	public void draw() {
		board = drawBoard();
		window.repaint();
	}
	void boardReset() {
		boardXY = new int[n][n];
		draw();
	}

	//set Event
	public void setEventbsend(JButton bsend) {
		bsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (tf.getText().trim().equals("")) return;
					dosToHost.writeUTF(Requests.ChatMessage);
					dosToHost.writeUTF(userAccount.getDisplayName() + ": " + tf.getText());
					tf.setText("");
				}
				catch(Exception e1) {
					System.out.println(e1.toString());
				}
			}
			
		});
	}
	public void setEventbop(JButton bop) {
		bop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bop đã được nhấn!!!");
			}
			
		});
	}
	public void setEventbexit(JButton bexit, JFrame jf) {
		bexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "Xác nhận thoát trận?");
				if (input == 0) {
					jf.setVisible(true);
					window.dispose();
					isrun = false;
					try {
						skToHost.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	@Override
	public void run() {
		try {
			dosToHost.writeUTF(Requests.GetDisplayInfos);
			while (isrun){
				String s = disToHost.readUTF();
				if (s.equals(Requests.XYCoordinate)) {
					int i = disToHost.readInt();
					int j = disToHost.readInt();
					int type = disToHost.readInt();
					boardXY[i][j] = type;
					draw();
					if (type == 1) {
						setPlayer1Turn(false);
						setPlayer2Turn(true);
					}
					else{
						//setPlayer1Turn(true);
						setPlayer2Turn(false);
						setPlayer1Turn(true);
					}
				}
				else if (s.equals(Requests.ChatMessage)) {
					s = disToHost.readUTF();
					ta.append(s);
					ta.append("\n");
				}
				else if (s.equals(Requests.FinishAnnounce)) {
					s = disToHost.readUTF();
					if (s.equals(Requests.Draw_GameResult)) {
						PopUpMessage.infoBox("Hoà", "Kết quả");
					}
					else {
						PopUpMessage.infoBox(s + " thắng", "Kết quả");
					}
					boardReset();
				}
				else if (s.equals(Requests.SendInfos)) {
					hostDisplayName = disToHost.readUTF();
					p2DisplayName = disToHost.readUTF();
					p2_id_ava = Integer.parseInt(disToHost.readUTF());
					host_id_ava = Integer.parseInt(disToHost.readUTF());
					setPlayer1Info(hostDisplayName, host_id_ava);
					setPlayer2Info(p2DisplayName, p2_id_ava);
					boardReset();
					dosToHost.writeUTF(Requests.GetBoard);
				}
				else if (s.equals(Requests.HostDisconnected)) {
					PopUpMessage.infoBox("Chủ phòng đã đóng kết nối", "Thông báo");
					menu.setVisible(true);
					window.dispose();
					isrun = false;
				}
				else if (s.equals(Requests.Player2Disconnected)) {
					String player2DisplayName = disToHost.readUTF();
					PopUpMessage.infoBox("Người chơi "+player2DisplayName+" đã đóng kết nối", "Thông báo");
					boardReset();
					setPlayer2Info("",0);
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
			menu.setVisible(true);
			window.dispose();
			isrun = false;
		}
	}
}