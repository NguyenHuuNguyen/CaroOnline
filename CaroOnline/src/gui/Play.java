package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Play{

	public static void main(String[] args) {
		new Play(); 
	}
	
	JFrame window = new JFrame();
	JPanel board = null;
	
	static int n = 15;
    static int s = 30;
	ImageIcon background;
	JPanel p ;
	JTextField tf = new JTextField();
	JTextArea ta = new JTextArea();
	ImageIcon ava1;
	ImageIcon ava2;
	
	public Play(){
		window.setTitle("Cờ Caro");
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
				//g1.drawImage(img,175,112,null);
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
		JPanel panel1 = plr1.setPayer1(ava1, "asdsd");
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		window.add(panel1);
		
		//player2(tam)
		ava2 = new ImageIcon("././resources/images/favicon.png");
		Play_Player2_Avatar plr2 = new Play_Player2_Avatar();
		JPanel panel2 = plr2.setPayer2(ava2, "asasd");
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
	
	//set Event
	public void setEventbsend(JButton bsend) {
		bsend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("bsend đã được nhấn!!!");
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
	
	
	public JPanel drawBoard() {
		JPanel p1 = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			for(int i = 0; i<= n; i++)
			{
				g1.drawLine(0, i*s, n*s, i*s);
				g1.drawLine(i*s, 0, i*s, n*s);
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
}