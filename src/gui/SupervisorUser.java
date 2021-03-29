package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import mgr.Manager;
import store.Shoe;
import store.Store;
import store.User;

public class SupervisorUser extends JDialog implements ActionListener, ListSelectionListener{

	JTable table;
	DefaultTableModel data;
	JComboBox combo;
	JTextField edits[] = new JTextField[3];
	String width[] = {"전체","S","M","L"};
	
	public SupervisorUser() {
		setBounds(350, 150, 950, 600);
		setVisible(true);
		setTitle("User Management");
		JPanel topPanel = new JPanel(new BorderLayout());
		//검색창 부분
		JLabel label = new JLabel("사용자 데이터 관리");
		label.setOpaque(true);
		label.setBackground(new Color(240, 248, 255));
		label.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(label,BorderLayout.NORTH);
		JPanel westPanel = new JPanel();
		JPanel westTopPanel = new JPanel(new GridLayout(4,2,5,5));
		for (int i = 0; i < 3; i++) {
			edits[i] = new JTextField("", 10);
		}
		combo = new JComboBox(width);
		combo.setPreferredSize(new Dimension(10, 10));
		westTopPanel.add(new JLabel("유저 아이디"));
		westTopPanel.add(edits[0]);
		westTopPanel.add(new JLabel("비밀번호"));
		westTopPanel.add(edits[1]);
		westTopPanel.add(new JLabel("유저 발길이"));
		westTopPanel.add(edits[2]);
		westTopPanel.add(new JLabel("발볼"));
		westTopPanel.add(combo);
		topPanel.add(westTopPanel,BorderLayout.CENTER);
		topPanel.add(westPanel,BorderLayout.WEST);
		
		//버튼 부분
		JPanel eastTopPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		JButton Btn[] = new JButton[4];
		Btn[0] = new JButton("수정");
		Btn[0].setActionCommand("수정");
		Btn[1] = new JButton("추가");
		Btn[1].setActionCommand("추가");
		Btn[2] = new JButton("검색");
		Btn[2].setActionCommand("검색");
		Btn[3] = new JButton("삭제");
		Btn[3].setActionCommand("삭제");
		for(JButton btn: Btn) {
			btn.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 17));
			eastTopPanel.add(btn);
			btn.addActionListener(this);
		}
		topPanel.add(eastTopPanel,BorderLayout.EAST);
		
		//테이블 부분
		final String[] columnNames = {"유저 아이디","비밀번호","유저 발길이","발볼"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		for(User u: Store.userMgr.getList()) {
			tableModel.addRow(u.getTexts());
		}
		table = new JTable(tableModel);
		table.setOpaque(true);
		table.setBackground(Color.white);
		ListSelectionModel rowSM = table.getSelectionModel();
		rowSM.addListSelectionListener((ListSelectionListener) this);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.white);
		
		add(topPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
	}

	int selectedIndex = -1;
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		if (!lsm.isSelectionEmpty()) {
			selectedIndex = lsm.getMinSelectionIndex();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		data = (DefaultTableModel) (table.getModel());
		
		if(e.getActionCommand().equals("추가")) {
			addShoe();
		}
		if(e.getActionCommand().equals("삭제")) {
			if (selectedIndex == -1) {
				JOptionPane.showMessageDialog(null, "유저를 선택하세요.");
				return;
			}
			data.removeRow(selectedIndex);
			Store.userMgr.mList.remove(selectedIndex);
			selectedIndex = -1;
		}
		if(e.getActionCommand().equals("검색")) {
			search();
		}
		if(e.getActionCommand().equals("수정")) {
			update();
			
		}
	}
	//테이블에 유저 추가
	void addShoe() {
		String add[] = new String[4];
		for(int i = 0; i < 3 ; i++) {
			if (edits[i].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "정보를 입력해주세요.");
				return;
			}
			add[i] = edits[i].getText();
		}
		if(combo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "발볼 정보를 입력해주세요.");
			return;
		}
		add[3] = width[combo.getSelectedIndex()];
		User u = new User(add);
		Store.userMgr.mList.add(u);
		data.addRow(u.getTexts());	
		JOptionPane.showMessageDialog(null, "추가되었습니다.");
	}
	//유저 정보 입력하여 검색
	void search() {
		ArrayList<User> searchList;
		String add[] = new String[4];
		for(int i = 0; i < 3 ; i++) {
			add[i] = edits[i].getText();
		}
		if(combo.getSelectedIndex()==0)
			add[3] = "";
		else
			add[3] = width[combo.getSelectedIndex()];
		ArrayList<String> kwds = new ArrayList<>();
		for(int i = 0; i < 4 ; i++) {
			kwds.add(add[i]);
		}
		searchList = Store.userMgr.search(kwds);
		data.getDataVector().removeAllElements();
		if (searchList.size() == 0) {
			JOptionPane.showMessageDialog(null, "검색 결과 없음");
			for(User u: Store.userMgr.getList()) {
				data.addRow(u.getTexts());
			}
			return;
		}
		for(User u: searchList)
			data.addRow(u.getTexts());
	}
	//유저 정보 입력하여 수정하고 기존의 선택한 유저 정보 삭제
	void update() {
		String add[] = new String[4];
		for(int i = 0; i < 3; i++) {
			if (edits[i].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "정보를 입력해주세요.");
				return;
			}
			add[i] = edits[i].getText();
		}
		if(combo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "발볼 정보를 입력해주세요.");
			return;
		}
		add[3] = width[combo.getSelectedIndex()];
		
		User u = new User(add);
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "수정할 데이터를 선택하세요.");
			return;
		}
		data.removeRow(selectedIndex);
		Store.userMgr.mList.remove(selectedIndex);
		selectedIndex = -1;
		Store.userMgr.mList.add(u);
		data.addRow(u.getTexts());
		JOptionPane.showMessageDialog(null, "수정되었습니다.");
	}
}
