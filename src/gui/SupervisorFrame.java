package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mgr.Manager;
import store.*;

public class SupervisorFrame extends JFrame implements ActionListener{
	
	public JPanel contentPane;

	JButton button[] = new JButton[2];
	
	public SupervisorFrame() {
		setVisible(true);
		setTitle("Supervisor Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 813, 480);
		contentPane = new JPanel(new BorderLayout(100, 125));
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(4, 4, 4, 4));
		setContentPane(contentPane);
		//제목 부분
		JLabel topLabel = new JLabel("관리자 메뉴");
		topLabel.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 35));
		topLabel.setOpaque(true);
		topLabel.setBackground(new Color(240, 248, 255));
		topLabel.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(topLabel, "North");
		//버튼 부분
		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 100, 10));
		centerPanel.setBackground(new Color(240, 248, 255));
		button[0] = new JButton("신발 데이터 관리");
		button[1] = new JButton("사용자 데이터 관리");
		button[0].setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 20));
		button[1].setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 20));
		
		button[0].setBackground(Color.LIGHT_GRAY);
		button[0].setActionCommand("신발 관리");
		button[0].setForeground(Color.WHITE);
		button[0].setPreferredSize(new Dimension(50, 50));
		button[0].addActionListener(this);
		centerPanel.add(button[0]);
		
		button[1].setBackground(Color.LIGHT_GRAY);
		button[1].setActionCommand("유저 관리");
		button[1].setForeground(Color.WHITE);
		button[1].setPreferredSize(new Dimension(50, 50));
		button[1].addActionListener(this);
		centerPanel.add(button[1]);
		
		centerPanel.add(button[1]);
		contentPane.add(centerPanel, "Center");
		
		JLabel empty1 = new JLabel("");
		JLabel empty2 = new JLabel("");
		JLabel empty3 = new JLabel("");
		contentPane.add(empty1, "South");
		contentPane.add(empty2, "East");
		contentPane.add(empty3, "West");
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().contentEquals("신발 관리")) {
			SupervisorShoe sDialog = new SupervisorShoe();
		}
		if(e.getActionCommand().contentEquals("유저 관리")) {
			SupervisorUser uDialog = new SupervisorUser();
		}
	}
}
