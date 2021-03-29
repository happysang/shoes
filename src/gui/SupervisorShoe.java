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

public class SupervisorShoe extends JDialog implements ActionListener, ListSelectionListener{

	JTable table;
	DefaultTableModel data;
	JComboBox combo;
	JTextField edits[] = new JTextField[6];
	String width[] = {"전체","S","M","L"};
	
	
	public SupervisorShoe(){
		
		setBounds(350, 150, 950, 600);
		setVisible(true);
		setTitle("shoes Management");
		JPanel topPanel = new JPanel(new BorderLayout());
		//검색창 부분
		JLabel label = new JLabel("신발 데이터 관리");
		label.setOpaque(true);
		label.setBackground(new Color(240, 248, 255));
		label.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 40));
		label.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(label,BorderLayout.NORTH);
		JPanel westTopPanel = new JPanel(new GridLayout(2,7,5,5));
		westTopPanel.add(new JLabel("고유코드"));
		westTopPanel.add(new JLabel("브랜드"));
		westTopPanel.add(new JLabel("종류"));
		westTopPanel.add(new JLabel("이름"));
		westTopPanel.add(new JLabel("가격"));
		westTopPanel.add(new JLabel("실측길이"));
		westTopPanel.add(new JLabel("발볼"));
		combo = new JComboBox(width);
		combo.setPreferredSize(new Dimension(10, 10));
		
		for (int i = 0; i < 6; i++) {
			edits[i] = new JTextField("", 10);
			westTopPanel.add(edits[i]);
		}
		westTopPanel.add(combo);
		
		topPanel.add(westTopPanel,BorderLayout.CENTER);
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
		final String[] columnNames = {"고유코드","브랜드","종류","이름","가격","실측길이","발볼"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
		for(Shoe s: Store.shoeMgr.getList()) {
			tableModel.addRow(s.getAllTexts());
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
				JOptionPane.showMessageDialog(null, "데이터를 선택하세요.");
				return;
			}
			data.removeRow(selectedIndex);
			Store.shoeMgr.mList.remove(selectedIndex);
			JOptionPane.showMessageDialog(null, "삭제되었습니다.");
			selectedIndex = -1;
		}
		if(e.getActionCommand().equals("검색")) {
			search();
		}
		if(e.getActionCommand().equals("수정")) {
			update();
		}
	}
	//테이블에 신발 추가
	void addShoe() {
		String add[] = new String[7];
		for(int i = 0; i < 6 ; i++) {
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
		add[6] = width[combo.getSelectedIndex()];
		if(!add[4].matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "가격은 숫자만 입력해주세요.");
			return;
		}
		Shoe s = new Shoe(add);
		Store.shoeMgr.mList.add(s);
		data.addRow(s.getAllTexts());
		JOptionPane.showMessageDialog(null, "추가되었습니다.");
	}
	
	//신발 정보 입력해서 검색
	void search() {
		ArrayList<Shoe> searchList;
		String add[] = new String[7];
		for(int i = 0; i < 6 ; i++) {
			add[i] = edits[i].getText();
		}
		if(combo.getSelectedIndex()==0)
			add[6] = "";
		else
			add[6] = width[combo.getSelectedIndex()];
		ArrayList<String> kwds = new ArrayList<>();
		for(int i = 0; i < 7 ; i++) {
			kwds.add(add[i]);
		}
		searchList = Store.shoeMgr.search(kwds);
		data.getDataVector().removeAllElements();
		if (searchList.size() == 0) {
			JOptionPane.showMessageDialog(null, "검색 결과 없음");
			for(Shoe s: Store.shoeMgr.getList()) {
				data.addRow(s.getAllTexts());
			}
			return;
		}
		for(Shoe s: searchList)
			data.addRow(s.getAllTexts());
	}
	//신발 정보 수정하고 기존의 선택한 신발 정보 삭제
	void update() {
		String add[] = new String[7];
		for(int i = 0; i < 6 ; i++) {
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
		add[6] = width[combo.getSelectedIndex()];
		if(!add[4].matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null, "가격은 숫자만 입력해주세요.");
			return;
		}
		Shoe s = new Shoe(add);
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(null, "수정할 데이터를 선택하세요.");
			return;
		}
		data.removeRow(selectedIndex);
		Store.shoeMgr.mList.remove(selectedIndex);
		selectedIndex = -1;
		Store.shoeMgr.mList.add(s);
		data.addRow(s.getAllTexts());
		JOptionPane.showMessageDialog(null, "수정되었습니다.");
	}

}
