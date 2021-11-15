package gui;

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


public class Play extends JFrame{

	public static void main(String[] args) {
		new Play(); 
	}
	static final int n = 16;
    static final int s = 30;
	ImageIcon background;
	BufferedImage img;
	JPanel p ;
	Graphics g;
	JTextField tf = new JTextField();
	JTextArea ta = new JTextArea();
	ImageIcon ava1;
	ImageIcon ava2;
	public Play(){
		background = null;
		ta.setEditable(false);
		img = new BufferedImage(n*s,n*s , BufferedImage.TYPE_3BYTE_BGR);
		g = img.getGraphics();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1180,740);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		//thiet lap icon
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("././resources/images/favicon.png"));
		//thiet lap background
		this.setBackground(new ImageIcon("././resources/images/Play_BG.png"));
		p = new JPanel() {
			@Override
			protected  void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			if(background!=null)
			{
				g1.drawImage(background.getImage(),0,0,getWidth(),getHeight(),null);
				g1.drawImage(img,175,112,null);
			}}
			};
		p.setLayout(null);
	    this.setContentPane(p);
	    //scoll
		JScrollPane scroll = new JScrollPane(ta);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setBounds(850, 250, 300, 350);
	    this.add(scroll);
	    //textfield
	    tf.setBounds(850, 615, 230, 30);
		this.add(tf);
		//button "send"
		JButton bsend = new JButton(new ImageIcon("././resources/images/bsend.png"));
		bsend.setBounds(1090, 615, 60, 30);
		setEventbsend(bsend);
		this.add(bsend);
		//button luachon
		JButton bop = new JButton(new ImageIcon("././resources/images/bop.png"));
		bop.setBounds(1102, 217, 45, 25);
		setEventbop(bop);
		this.add(bop);
		//button ve trang chu
		JButton bexit = new JButton(new ImageIcon("././resources/images/bexit.png"));
		bexit.setBounds(1087, 10, 60, 30);
		setEventbexit(bexit);
		this.add(bexit);	
		//button xin hoa
		JButton bdrawProposal = new JButton(new ImageIcon("././resources/images/bdrawproposal.png"));
		bdrawProposal.setBounds(850, 95, 140, 75);
		setEventbdrawProposal(bdrawProposal);
		this.add(bdrawProposal);
		//button dau hang
		JButton bff = new JButton(new ImageIcon("././resources/images/bff.png"));
		bff.setBounds(1008, 95, 140, 75);
		setEventbff(bff);
		this.add(bff);
		//player1(tam)
		ava1 = new ImageIcon("././resources/images/favicon.png");
		Play_Player1 plr1 = new Play_Player1();
		JPanel panel1 = plr1.setPayer1(ava1);
		panel1.setLayout(null);
		panel1.setBounds(20,595, 250, 100);
		this.add(panel1);
		
		//player2(tam)
		ava2 = new ImageIcon("././resources/images/favicon.png");
		Play_Player2 plr2 = new Play_Player2();
		JPanel panel2 = plr2.setPayer2(ava2);
		panel2.setLayout(null);
		panel2.setBounds(560,10, 250, 100);
		this.add(panel2);
		
		this.setTitle("Cờ Caro");
		this.setVisible(true);
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