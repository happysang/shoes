package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import store.Client;
import store.Shoe;
import store.Client.MyShoe;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import mgr.Manager;
import javax.swing.JScrollBar;

public class MyPage implements ActionListener {
	static Client client;
	private JFrame frame;
	private JLabel labelId;
	private String labelLength;
	private String labelWidth;
	public static Manager<Shoe> orderMgr = new Manager<>();
	private JTextField textLength;
	private JTextField textWidth;
	static float newLength;
	static char newWidth;
	static String newReview;
	private JPanel panelMyPage;
	JTextArea content;
	public ArrayList<MyShoe> labelPurchaseList2 = new ArrayList<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public MyPage(Client client) throws IOException {
		MyPage.client = client;
		client.roadOrder();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("My Page"); // 상단바 제목
		frame.setBounds(400, 200, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		myInfo();
		update();
		callSearch();
		printList();

		printList();
	}

	private void myInfo() {
		panelMyPage = new JPanel();
	      panelMyPage.setBounds(0, 0, 685, 465);
	      panelMyPage.setLayout(null);

	      JLabel info = new JLabel("<나의 정보>");
	      info.setBounds(70, 15, 550, 30);
	      info.setHorizontalAlignment(SwingConstants.CENTER);
	      info.setOpaque(true);
	      info.setBackground(new Color(240, 248, 255));
	      panelMyPage.add(info);

	      if (newLength == 0)
	         labelLength = Float.toString(client.myLength);
	      else {
	         labelLength = Float.toString(newLength);
	         MyPage.client.myLength = newLength;
	      }
	      if (newWidth == 0)
	         labelWidth = Character.toString(client.myWidth);
	      else {
	         labelWidth = Character.toString(newWidth);
	         MyPage.client.myWidth = newWidth;
	      }
	      panelMyPage.setBackground(Color.white);
	      frame.getContentPane().add(panelMyPage);
	      
	      JPanel subpanel = new JPanel();
	      subpanel.setBounds(70, 45, 550, 31);
	      subpanel.setBackground(new Color(240, 248, 255));
	      subpanel.setLayout(null);
	      
	      JLabel id = new JLabel("아이디:");
	      id.setBounds(100, 0, 42, 30);
	      subpanel.add(id);

	      labelId = new JLabel(client.id);
	      labelId.setBounds(150, 0, 42, 30);
	      subpanel.add(labelId);

	      JLabel length = new JLabel("발 길이: ");
	      length.setBounds(220,0, 85, 30);
	      subpanel.add(length);
	      
	      JLabel label_length = new JLabel(labelLength);
	      label_length.setBounds(288, 8, 50, 15);
	      subpanel.add(label_length);

	      JLabel width = new JLabel("발 볼: ");
	      width.setBounds(360, 0, 75, 30);
	      subpanel.add(width);
	      panelMyPage.add(subpanel);
	      
	      JLabel label_width = new JLabel(labelWidth);
	      label_width.setBounds(400, 8, 50, 15);
	      subpanel.add(label_width);

	}

	private void update() {
		JButton btnUpdate = new JButton("발 사이즈 수정하기");
		btnUpdate.setBounds(106, 370, 173, 71);
		panelMyPage.add(btnUpdate);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setVisible(false);
		panel.setBounds(0, 0, 686, 500);
		frame.getContentPane().add(panel);

		JLabel top = new JLabel("<나의 발 사이즈 수정>");
		top.setBounds(0, 0, 686, 100);
		top.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 40));
		top.setHorizontalAlignment(JLabel.CENTER);
		top.setOpaque(true);
		top.setBackground(new Color(240, 248, 255));
		panel.add(top, BorderLayout.NORTH);
		JPanel panelUpdate = new JPanel();
		panelUpdate.setBackground(new Color(240, 248, 255));
		panelUpdate.setBounds(0, 50, 686, 500);
		panelUpdate.setLayout(null);

		JLabel newlength = new JLabel("새로 입력할 발 길이 :");
		newlength.setBounds(98, 80, 205, 30);
		panelUpdate.add(newlength);
		textLength = new JTextField();
		textLength.setBounds(99, 112, 205, 56);
		panelUpdate.add(textLength);
		textLength.setColumns(10);

		JLabel newwidth = new JLabel("새로 입력할 발 볼 :");
		newwidth.setBounds(98, 223, 205, 30);
		panelUpdate.add(newwidth);
		textWidth = new JTextField();
		textWidth.setColumns(10);
		textWidth.setBounds(99, 253, 205, 56);
		panelUpdate.add(textWidth);

		JButton btnUpdateClear = new JButton("수정완료");
		btnUpdateClear.setBounds(453, 203, 162, 39);
		panelUpdate.add(btnUpdateClear);

		panel.add(panelUpdate, BorderLayout.CENTER);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelMyPage.setVisible(false);
				panel.setVisible(true);
			}
		});

		btnUpdateClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newLength = Float.parseFloat(textLength.getText());
				newWidth = textWidth.getText().charAt(0);
				JOptionPane.showMessageDialog(null, "수정 완료", "정보 수정", JOptionPane.DEFAULT_OPTION);
				panel.setVisible(false);
				initialize();
			}
		});
		JButton btnBack = new JButton("마이페이지로 돌아가기");

		panelUpdate.add(btnBack);
		btnBack.setBounds(98, 15, 122, 20);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelMyPage.setVisible(true);
				panel.setVisible(false);
			}
		});
	}

	private void printList() {
	      panelMyPage.revalidate();

	      System.out.println("실행");
	      JPanel panelPurchaseL = new JPanel();
	      panelPurchaseL.setBounds(70, 75, 550, 270);
	      panelMyPage.add(panelPurchaseL);
	      panelPurchaseL.setLayout(null);

	      int a = 0;
	      JLabel listName = new JLabel("<나의 구매 리스트>");
	      listName.setBounds(15, 15, 120, 20);
	      listName.setFont(new Font("나눔스퀘어라운드 Regular", Font.PLAIN, 10));
	      panelPurchaseL.add(listName);
	      labelPurchaseList2 = client.myShoeList;
	      JLabel[] labelPurchaseList = new JLabel[client.myShoeList.size()];

	      JButton btnRe = new JButton("새로고침");
	      btnRe.setFont(new Font("나눔스퀘어라운드 Regular", Font.PLAIN, 10));
	      btnRe.setBounds(140, 15, 80, 20);
	      panelPurchaseL.add(btnRe);

	      btnRe.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            panelMyPage.setVisible(false);
	            try {
	               client.roadOrder();
	            } catch (IOException e1) {
	               // TODO Auto-generated catch block
	               e1.printStackTrace();
	            }
	            panelMyPage.setVisible(true);
	            initialize();
	         }
	      });
	      JPanel centerList = new JPanel();
	      centerList.setLayout(null);
	      centerList.setBounds(23, 45, 500, 160);
	      centerList.setBackground(Color.WHITE);
	      int i = 0;
	      for (MyShoe shoe : labelPurchaseList2) {
	         labelPurchaseList[i] = new JLabel("");
	         labelPurchaseList[i].setBounds(0, 0 + a, 450, 30);
	         centerList.add(labelPurchaseList[i]);
	         labelPurchaseList[i].setText(i + 1 + ". " + shoe.toString());
	         System.out.println();
	         a += 15;
	         i++;
	      }
	      if(a > 160) {
	         Dimension size = new Dimension();
	         size.setSize(500, a+15);
	         centerList.setPreferredSize(size);
	      }
	      JScrollPane scrollPane = new JScrollPane(centerList,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	      scrollPane.setBounds(23, 45, 500, 160);
	      scrollPane.getViewport().setBackground(Color.white);
	      panelPurchaseL.add(scrollPane);

	      JLabel Labelnum = new JLabel("<리뷰 숫자 입력               >");
	      Labelnum.setBounds(17, 220, 161, 27);
	      panelPurchaseL.add(Labelnum);

	      JButton btnMove = new JButton("이동");
	      btnMove.setFont(new Font("굴림", Font.PLAIN, 8));
	      btnMove.setHorizontalAlignment(SwingConstants.LEFT);
	      btnMove.setBounds(181, 220, 50, 23);
	      panelPurchaseL.add(btnMove);

	      JTextField textNum = new JTextField();
	      textNum.setBounds(110, 221, 41, 21);
	      panelPurchaseL.add(textNum);
	      textNum.setColumns(5);
	      panelPurchaseL.setBackground(new Color(240, 248, 255));

	      

	      btnMove.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            int i = 0;
	            i = Integer.parseInt(textNum.getText()) - 1;
	            panelMyPage.setVisible(false);
	            System.out.println(labelPurchaseList2.get(i));
	            reivew(labelPurchaseList2.get(i));
	            // JOptionPane.showMessageDialog(null, "수정 완료", "정보 수정",
	            // JOptionPane.DEFAULT_OPTION);
	         }
	      });


	}

	private void reivew(MyShoe labelPurchaseList2) {
		JPanel panelReview = new JPanel();
		panelReview.setVisible(true);
		panelReview.setBounds(0, 0, 700, 500);
		panelReview.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panelReview);
		panelReview.setLayout(null);

		JTextField textReview = new JTextField();
		textReview.setBounds(60, 139, 570, 169);
		panelReview.add(textReview);
		textReview.setColumns(30);

		JLabel lblNewLabel = new JLabel("<리뷰작성>");
		lblNewLabel.setBounds(310, 80, 95, 22);
		panelReview.add(lblNewLabel);

		JButton btnReview = new JButton("작성완료");
		btnReview.setBounds(300, 330, 91, 23);
		panelReview.add(btnReview);

		btnReview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				labelPurchaseList2.review = textReview.getText();
				panelReview.setVisible(false);
				panelMyPage.setVisible(true);
				System.out.println(labelPurchaseList2.toString());
				initialize();
			}
		});
	}

	private void callSearch() {
		JButton btnSearch = new JButton("신발목록 검색");
		btnSearch.setBounds(400, 370, 173, 71);
		panelMyPage.add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchDialog callsearch = new SearchDialog();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}