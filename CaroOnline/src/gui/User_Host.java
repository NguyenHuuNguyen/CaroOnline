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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;

import dto.Account;
import dto.Requests;
import dto.Room;


public class User_Host implements Runnable{

	public static void main(String[] args) {
		User_Host a = new User_Host(new Socket(), null, null, null); 
		Thread t = new Thread(a);
		t.start();
	}
	
	JFrame window = new JFrame();
	JPanel board = null;
	
	static int n = 15;
    static int s = 30;
	ImageIcon background;
	JPanel p ;
	JTextField tf = new JTextField();
	static JTextArea ta = new JTextArea();
	ImageIcon ava1;
	ImageIcon ava2;
	
	Socket skToMainServer;
	DataInputStream disToMainServer;
	DataOutputStream dosToMainServer;
	
	static boolean isOplayer;
	boolean isTurn = true;
	static int[][] boardXY = new int[n][n];
	
	Account userAccount;
	public static Vector<Client_player> clients = new Vector<>();
	Room currentRoom = null;
	ServerSocket HostSocket = null;
	
	public User_Host(Socket sk, JFrame jf, Room room, Account useraccount){
		if (sk != null)
			try {
//				skToMainServer = sk;
//				dis = new DataInputStream(sk.getInputStream());
//				dos = new DataOutputStream(sk.getOutputStream());
//				
//				currentRoom = room;
//				sendRoomToMainServer();
				HostSocket = new ServerSocket(16969);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		window.setTitle("Cờ Caro - chủ phòng");
		background = null;
		ta.setEditable(false);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		JButton bsend = new JButton(new ImageIcon("././resources/images/bsend.png"));
		bsend.setBounds(1090, 615, 60, 30);
		setEventbsend(bsend);
		window.add(bsend);
		//button luachon
		JButton bop = new JButton(new ImageIcon("././resources/images/bop.png"));
		bop.setBounds(1102, 217, 45, 25);
		setEventbop(bop);
		window.add(bop);
		//button ve trang chu
		JButton bexit = new JButton(new ImageIcon("././resources/images/bexit.png"));
		bexit.setBounds(1087, 10, 60, 30);
		setEventbexit(bexit);
		window.add(bexit);	
		//button xin hoa
		JButton bdrawProposal = new JButton(new ImageIcon("././resources/images/bdrawproposal.png"));
		bdrawProposal.setBounds(850, 95, 140, 75);
		setEventbdrawProposal(bdrawProposal);
		window.add(bdrawProposal);
		//button dau hang
		JButton bff = new JButton(new ImageIcon("././resources/images/bff.png"));
		bff.setBounds(1008, 95, 140, 75);
		setEventbff(bff);
		window.add(bff);
		//player1(tam)
		ava1 = new ImageIcon("././resources/images/favicon.png");
		Play_Player1_Avatar plr1 = new Play_Player1_Avatar();
		JPanel panel1 = plr1.setPayer1(ava1);
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		window.add(panel1);
		
		//player2(tam)
		ava2 = new ImageIcon("././resources/images/favicon.png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		JPanel panel2 = plr2.setPayer2(ava2);
		panel2.setLayout(null);
		panel2.setBounds(560,10, 250, 100);
		window.add(panel2);
		
		//board
		board = drawBoard();
		board.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mousePressedOnBoard( e.getY()/s, e.getX()/s);
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		  });
		window.add(board);
		
		window.setVisible(true);
	}
	//
	public void setBackground(ImageIcon img)
	{
		this.background=img;
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
	private void mousePressedOnBoard(int i, int j) {
		//
		// xu ly danh quan co o day
		//
		boardXY[i][j] = 1;
		draw();
		sendStringToAllClients(Requests.XYCoordinate);
		sendXYCoordinateToAllClients(i, j, boardXY[i][j]);
	}
	static public void sendXYCoordinateToAllClients(int i, int j, int type) {
		for (Client_player c : clients) {
			try {
				if (c.connectable) {
					c.dos.writeInt(i);
					c.dos.writeInt(j);
					c.dos.writeInt(type);
					c.dos.flush();
				}
			}
			catch(Exception e) {
				c.connectable = false;
			}
		}
	}
	static public void sendStringToAllClients(String s) {
		for (Client_player c : clients) {
			try {
				if (c.connectable) {
					c.dos.writeUTF(s);
					c.dos.flush();
				}
			}
			catch(Exception e) {
				c.connectable = false;
			}
		}
	}
	
	//set Event
	public void setEventbsend(JButton bsend) {
		bsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendStringToAllClients(Requests.ChatMessage);
				sendStringToAllClients(tf.getText());
				ta.append(tf.getText());
				ta.append("\n");
				tf.setText("");
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
	public void setEventbexit(JButton bexit) {
		bexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bexit đã được nhấn!!!");
			}
			
		});
	}
	public void setEventbdrawProposal(JButton bdrawProposal) {
		bdrawProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bdrawProposal đã được nhấn!!!");
			}
			
		});
	}
	public void setEventbff(JButton bff) {
		bff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bff đã được nhấn!!!");
			}
			
		});
	}
	private void sendRoomToMainServer() {
		try {
			dosToMainServer.writeUTF(Requests.RoomUpdate);
			dosToMainServer.writeInt(currentRoom.getRoomID());
			dosToMainServer.writeUTF(currentRoom.getRoomName());
			dosToMainServer.writeUTF(currentRoom.getPassword());
			dosToMainServer.writeUTF(currentRoom.getHostDisplayName());
			dosToMainServer.writeUTF(currentRoom.getHostIPAddress());
			dosToMainServer.writeInt(currentRoom.getHostPort());
			dosToMainServer.writeInt(currentRoom.getGameMode());
			dosToMainServer.writeInt(currentRoom.getCurrentPlayers());
			dosToMainServer.writeInt(currentRoom.getCurrentSpectators());
			dosToMainServer.writeBoolean(currentRoom.isAlowSpectator());
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("Waiting...");
			try {
				Socket sk = HostSocket.accept();
				System.out.println("client accepted");
				Client_player a = new Client_player(sk);
				clients.add(a);
				a.start();
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	}
}






class Client_player extends Thread{
	Socket sk;
	DataInputStream dis;
	DataOutputStream dos;
	boolean connectable = true;
	public Client_player(Socket SK) {
		try {
			sk = SK;
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());
			//
			// debug, lấy bàn cờ ngay khi vừa kết nối vào
			//
		}
		catch(Exception e) {
			System.out.println(e);
			connectable = false;
		}
	}
	public void run() {
		try {
			while(connectable) {
				String s = dis.readUTF();
				if (s.equals(Requests.XYCoordinate)) {
					int i = dis.readInt();
					int j = dis.readInt();
					int type;
					if (User_Host.isOplayer) type = 1;
					else type = 2;
					User_Host.boardXY[i][j] = type;
					
					User_Host.sendStringToAllClients(Requests.XYCoordinate);
					User_Host.sendXYCoordinateToAllClients(i, j, type);
					continue;
				}
				if (s.equals(Requests.ChatMessage)) {
					s = dis.readUTF();
					User_Host.ta.append(s);
					User_Host.ta.append("\n");
					
					User_Host.sendStringToAllClients(Requests.ChatMessage);
					User_Host.sendStringToAllClients(s);
					continue;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
			connectable = false;
		}
	}
}
