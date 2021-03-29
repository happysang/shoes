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
import store.Client;
import store.Shoe;
import store.Store;

public class SearchDialog extends JDialog implements ActionListener, ListSelectionListener {
   
   JTable table;
   JTextField edits[] = new JTextField[5];
   DefaultTableModel tableModel;
   Client cli;
   ArrayList<Shoe> searchList;
   ArrayList<Shoe> shoeList=Store.shoeMgr.mList;
   
   public SearchDialog(){
      setTitle("Search shoes");
      JPanel topPanel = new JPanel(new BorderLayout());
      //검색창 부분
      JPanel westTopPanel = new JPanel(new GridLayout(2,5,5,5));
      westTopPanel.add(new JLabel("고유코드"));
      westTopPanel.add(new JLabel("브랜드"));
      westTopPanel.add(new JLabel("종류"));
      westTopPanel.add(new JLabel("이름"));
      westTopPanel.add(new JLabel("가격"));
      for (int i = 0; i < 5; i++) {
         edits[i] = new JTextField("", 10);
         westTopPanel.add(edits[i]);
      }
      topPanel.add(westTopPanel,BorderLayout.CENTER);
      
      //버튼 부분
      JPanel eastTopPanel = new JPanel(new GridLayout(2, 1, 5, 5));
      
      JButton btn1 = new JButton("상세보기");
      btn1.setActionCommand("상세보기");
      btn1.addActionListener(this);
      btn1.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 18));
      btn1.setSize(new Dimension());
      JButton btn2 = new JButton("검색");
      btn2.setActionCommand("검색");
      btn2.addActionListener(this);
      btn2.setFont(new Font("나눔스퀘어라운드 Regular", Font.BOLD, 18));
      btn2.setSize(new Dimension());
      eastTopPanel.add(btn1);
      eastTopPanel.add(btn2);
      topPanel.add(eastTopPanel,BorderLayout.EAST);
      
      //테이블 부분
      final String[] columnNames = {"고유코드","브랜드","종류","이름","가격"};
      tableModel = new DefaultTableModel(columnNames,0);
      for(Shoe s: Store.shoeMgr.getList()) {
         tableModel.addRow(s.getTexts());
      }
      table = new JTable(tableModel);
      table.setOpaque(true);
      table.setBackground(Color.white);
      JScrollPane scrollPane = new JScrollPane(table);
      scrollPane.getViewport().setBackground(Color.white);
      ListSelectionModel rowSM = table.getSelectionModel();
      rowSM.addListSelectionListener(this);
      add(topPanel,BorderLayout.NORTH);
      add(scrollPane,BorderLayout.CENTER);
      
      this.setBounds(250, 150, 950, 600);
      this.setVisible(true);
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
      if(e.getActionCommand().equals("상세보기")) {
         Shoe searchshoe = shoeList.get(selectedIndex);
         new DetailInfo(Store.myUser, searchshoe);
      }
      if(e.getActionCommand().equals("검색")) {
         search();
      }
      
   }
   //신발 정보 입력해서 검색
   void search() {
      
      String add[] = new String[5];
      
      for(int i = 0; i < 5 ; i++) {
         add[i] = edits[i].getText();
      }
      ArrayList<String> kwds = new ArrayList<>();
      for(int i = 0; i < 5 ; i++) {
         kwds.add(add[i]);
      }
      searchList = Store.shoeMgr.search(kwds);
      shoeList= searchList;
      tableModel.getDataVector().removeAllElements();
      if (searchList.size() == 0) {
         JOptionPane.showMessageDialog(null, "검색 결과 없음");
         for(Shoe s: Store.shoeMgr.getList()) {
            tableModel.addRow(s.getTexts());
         }
         return;
      }
      for(Shoe s: searchList)
         tableModel.addRow(s.getTexts());
            
   }
}