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


public class Play extends JFrame implements ActionListener{

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
	public Play(){
		background = null;
		ta.setEditable(false);
		img = new BufferedImage(n*s,n*s , BufferedImage.TYPE_3BYTE_BGR);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1180,740);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		//thiet lap icon
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("./././resources/images/favicon.png"));
		//thiet lap background
		this.setBackground(new ImageIcon("./././resources/images/Play_BG.png"));
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
		JButton bsend = new JButton("send");
		bsend.addActionListener(this);
		bsend.setBounds(1090, 615, 60, 30);
		this.add(bsend);
		//button luachon
		JButton bop = new JButton("send");
		bop.addActionListener(this);
		bop.setBounds(1090, 615, 60, 30);
		this.add(bop);
		
		this.setTitle("Cờ Caro");
		this.setVisible(true);
	}
	public void setBackground(ImageIcon img)
	{
		this.background=img;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
