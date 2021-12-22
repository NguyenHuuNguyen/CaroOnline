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

import bll.BLL;
import dto.Account;
import dto.Requests;
import dto.Responses;
import dto.Room;


public class User_Host implements Runnable{

	public static void main(String[] args) {
		User_Host a = new User_Host(new Socket(), null, null,null); 
		Thread t = new Thread(a);
		t.start();
	}
	
	boolean isrun = true;
	static JFrame window = new JFrame();
	static JPanel board = null;
	
	static int n = 15;
    static int s = 30;
	ImageIcon background;
	JPanel p ;
	JTextField tf = new JTextField();
	static JTextArea ta = new JTextArea();
	ImageIcon ava1;
	static ImageIcon ava2;
	static JPanel panel2;
	static JButton bsend;
	static JButton bop;
	static JButton bexit;
	static JButton bdrawProposal;
	static JButton bff;
	
	Socket skToMainServer;
	DataInputStream disToMainServer;
	DataOutputStream dosToMainServer;
	
	static boolean isPlayer2Joined;
	static int XYtype = 1;
	static boolean isTurn = true;
	static int[][] boardXY = new int[n][n];
	static String player2DispayName = "";
	static Account userAccount;
	public static Vector<Client> clients = new Vector<>();
	Room currentRoom = null;
	ServerSocket HostSocket = null;
	
	public User_Host(Socket sk, JFrame jf, Room _room, Account _account){
		if (sk != null)
			try {
				skToMainServer = sk;
				disToMainServer = new DataInputStream(sk.getInputStream());
				dosToMainServer = new DataOutputStream(sk.getOutputStream());
				userAccount = _account;
				currentRoom = _room;
				HostSocket = new ServerSocket(16969);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		window.setTitle("Cờ Caro - chủ phòng");
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
		//button xin hoa
		bdrawProposal = new JButton(new ImageIcon("././resources/images/bdrawproposal.png"));
		bdrawProposal.setBounds(850, 95, 140, 75);
		setEventbdrawProposal(bdrawProposal);
		window.add(bdrawProposal);
		//button dau hang
		bff = new JButton(new ImageIcon("././resources/images/bff.png"));
		bff.setBounds(1008, 95, 140, 75);
		setEventbff(bff);
		window.add(bff);
		//player1(tam)
		ava1 = new ImageIcon("././resources/images/favicon.png");
		Play_Player1_Avatar plr1 = new Play_Player1_Avatar();
		JPanel panel1 = plr1.setPayer1(ava1, userAccount.getDisplayName());
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		window.add(panel1);
		//player2(tam)
		ava2 = new ImageIcon("././resources/images/favicon.png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		panel2 = plr2.setPayer2(ava2, "");
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
	public static void setPlayer2Info(String displayname) {
		window.remove(panel2);
		ava2 = new ImageIcon("././resources/images/favicon.png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		panel2 = plr2.setPayer2(ava2, displayname);
		panel2.setLayout(null);
		panel2.setBounds(560,10, 250, 100);
		window.add(panel2);
		window.repaint();
	}
	
	static public JPanel drawBoard() {
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
	
	public static void draw() {
		board = drawBoard();
		window.repaint();
	}
	private void mousePressedOnBoard(int i, int j) {
		//
		// xu ly danh quan co o day
		//
		if (isPlayer2Joined == false) return;
		if (isTurn == false) return;
		if (boardXY[i][j] != 0) return;
		isTurn = false;
		boardXY[i][j] = XYtype;
		draw();
		sendStringToAllClients(Requests.XYCoordinate);
		sendXYCoordinateToAllClients(i, j, boardXY[i][j]);
		AnnounceIfFinish(i, j, boardXY[i][j]);
	}
	static public void AnnounceIfFinish(int i, int j, int value) {
		int k = BLL.Instance().isFinishCaro5(boardXY, i, j, value);
		if (k == 0) return;
		else if (k == -1) {
			sendStringToAllClients(Requests.FinishAnnounce);
			sendStringToAllClients(Requests.Draw_GameResult);
			PopUpMessage.infoBox("Hoà", "Kết quả");
		}
		else if (k == XYtype) {
			sendStringToAllClients(Requests.FinishAnnounce);
			//sửa lại thành displayname của host
			sendStringToAllClients("Host");
			PopUpMessage.infoBox("Host thắng", "Kết quả");
		}
		else{
			sendStringToAllClients(Requests.FinishAnnounce);
			//sửa lại thành displayname của người chơi
			sendStringToAllClients("Player2");
			PopUpMessage.infoBox("Player2 thắng", "Kết quả");
		}
		boardReset();
	}
	static void boardReset() {
		boardXY = new int[n][n];
		draw();
	}
	static public void sendXYCoordinateToAllClients(int i, int j, int type) {
		for (Client c : clients) {
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
		for (Client c : clients) {
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
	private void sendStringToPlayer2(String s) {
		for (Client c : clients) {
			if (c.isPlayer2 && c.connectable) {
				try {
					c.dos.writeUTF(s);
				}
				catch(Exception e) {
					c.connectable = false;
				}
			}
		}
	}
	
	//set Event
	public void setEventbsend(JButton bsend) {
		bsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tf.getText().trim().equals("")) return;
				sendStringToAllClients(Requests.ChatMessage);
				sendStringToAllClients(userAccount.getDisplayName() + ": " + tf.getText());
				ta.append(userAccount.getDisplayName() + ": " + tf.getText());
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
	public void setEventbexit(JButton bexit, JFrame jf) {
		bexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "Xác nhận thoát trận?");
				if (input == 0) {
					jf.setVisible(true);
					window.dispose();
					isrun = false;
					sendStringToAllClients(Requests.HostDisconnected);
					try {
						HostSocket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		});
	}
	public void setEventbdrawProposal(JButton bdrawProposal) {
		bdrawProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPlayer2Joined == false) return;
				sendStringToPlayer2(Requests.DrawProposal);
				User_Host.bdrawProposal.setEnabled(false);
			}
			
		});
	}
	public void setEventbff(JButton bff) {
		bff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPlayer2Joined == false) return;
				int input = JOptionPane.showConfirmDialog(null, "Xác nhận đầu hàng?");
				if (input == 0) {
					sendStringToAllClients(Requests.FinishAnnounce);
					//sửa lại thành displayname của người chơi
					sendStringToAllClients("Player2");
					PopUpMessage.infoBox("Player2 thắng", "Kết quả");
					boardReset();
				}
			}
			
		});
	}
	@Override
	public void run() {
		System.out.println("Waiting...");
		while(isrun) {
			try {
				Socket sk = HostSocket.accept();
				System.out.println("client accepted");
				Client a = new Client(sk);
				clients.add(a);
				a.start();
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	}
}

class Client extends Thread{
	Socket sk;
	DataInputStream dis;
	DataOutputStream dos;
	boolean connectable = true;
	boolean isPlayer2 = false;
	public Client(Socket SK) {
		try {
			sk = SK;
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());
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
					int type = 1;
					if (User_Host.XYtype == 1) type = 2;
					User_Host.boardXY[i][j] = type;
					User_Host.isTurn = true;
					User_Host.draw();
					User_Host.sendStringToAllClients(Requests.XYCoordinate);
					User_Host.sendXYCoordinateToAllClients(i, j, type);
					User_Host.AnnounceIfFinish(i, j, type);
				}
				else if (s.equals(Requests.ChatMessage)) {
					s = dis.readUTF();
					User_Host.ta.append(s);
					User_Host.ta.append("\n");
					
					User_Host.sendStringToAllClients(Requests.ChatMessage);
					User_Host.sendStringToAllClients(s);
				}
				else if (s.equals(Requests.Player2Joined)) {
					isPlayer2 = true;
					User_Host.isPlayer2Joined = true;
					User_Host.isTurn = true;
					User_Host.boardReset();
					String player2DisplayName = dis.readUTF();
					User_Host.setPlayer2Info(player2DisplayName);
					User_Host.player2DispayName = player2DisplayName;
					dos.writeUTF(User_Host.userAccount.getDisplayName());
					User_Host.sendStringToAllClients(Requests.SendInfos);
					User_Host.sendStringToAllClients(User_Host.userAccount.getDisplayName());
					User_Host.sendStringToAllClients(User_Host.player2DispayName);
					System.out.println(User_Host.userAccount.getDisplayName());
				}
				else if (s.equals(Requests.DrawProposalRefused)) {
					PopUpMessage.infoBox("Đối thủ từ chối lời đề nghị hoà của bạn", "Thông báo");
					User_Host.bdrawProposal.setEnabled(true);
				}
				else if (s.equals(Requests.DrawProposalAccepted)) {
					User_Host.sendStringToAllClients(Requests.FinishAnnounce);
					User_Host.sendStringToAllClients(Requests.Draw_GameResult);
					PopUpMessage.infoBox("Hoà", "Kết quả");
					User_Host.boardReset();
					User_Host.bdrawProposal.setEnabled(true);
				}
				else if (s.equals(Requests.DrawProposal)) {
					int input = JOptionPane.showConfirmDialog(null, "Đối thủ xin hoà, chấp nhận?");
					if (input == 0) {
						User_Host.sendStringToAllClients(Requests.FinishAnnounce);
						User_Host.sendStringToAllClients(Requests.Draw_GameResult);
						PopUpMessage.infoBox("Hoà", "Kết quả");
						User_Host.boardReset();
					}
					else {
						dos.writeUTF(Requests.DrawProposalRefused);
					}
				}
				else if (s.equals(Requests.Surrender)) {
					User_Host.sendStringToAllClients(Requests.FinishAnnounce);
					//sửa lại thành displayname của host
					User_Host.sendStringToAllClients("Host");
					PopUpMessage.infoBox("Host thắng", "Kết quả");
					User_Host.boardReset();
				}
				else if (s.equals(Requests.GetBoard)) {
					for(int i = 0; i < User_Host.n; i++) {
						for(int j = 0; j < User_Host.n; j++) {
							if (User_Host.boardXY[i][j] == 0) continue;
							dos.writeUTF(Requests.XYCoordinate);
							dos.writeInt(i);
							dos.writeInt(j);
							dos.writeInt(User_Host.boardXY[i][j]);
						}
					}
				}
				else if (s.equals(Requests.GetDisplayInfos)) {
					dos.writeUTF(Requests.SendInfos);
					dos.writeUTF(User_Host.userAccount.getDisplayName());
					dos.writeUTF(User_Host.player2DispayName);
				}
				else if (s.equals(Requests.Player2Disconnected)) {
					PopUpMessage.infoBox("Người chơi "+User_Host.player2DispayName+" đã đóng kết nối", "Thông báo");
					User_Host.boardReset();
					User_Host.isPlayer2Joined = false;
					connectable = false;
					User_Host.setPlayer2Info("");
					User_Host.sendStringToAllClients(Requests.Player2Disconnected);
					User_Host.sendStringToAllClients(User_Host.player2DispayName);
				}
			}
		}
		catch(Exception e) {
			System.out.println(sk.getRemoteSocketAddress().toString() + " disconnected");
			connectable = false;
			if (isPlayer2) {
				User_Host.isPlayer2Joined = false;
				//thong bao thua
			}
		}
	}
}
