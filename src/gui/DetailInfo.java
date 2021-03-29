package gui;

import store.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class DetailInfo extends JFrame {

   private JPanel contentPane;
   private JButton buy_btn;
   private JLabel icon_label;
   private JLabel code_label;
   private JLabel name_label;
   private JLabel price_label;
   private JLabel recoSize_label;
   private JLabel chooseSize_label;
   
   //변경되는 항목들
   private JLabel codeValue_label;
   private JLabel nameValue_label;
   private JLabel priceValue_label;
   private JLabel recoSizeValue_label;
   private JComboBox size_cbBox; //사이즈를 고를 수 있는 콤보박스
   String[] sizes; //콤보박스 데이터

   public DetailInfo(User cli, Shoe shoe) {
      JFrame frame = this;
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 655, 385);
      contentPane = new JPanel();
      contentPane.setBackground(Color.WHITE);
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      //이미지 
      ImageIcon shoeImage = new ImageIcon("assets/"+shoe.getCode()+".jpg"); 
      icon_label = new JLabel(shoeImage);
      icon_label.setBounds(60, 30, 255, 165);
      contentPane.add(icon_label);
      
      //구매버튼
      buy_btn = new JButton("구매");
      buy_btn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               shoe.setSize(Integer.parseInt((String) size_cbBox.getSelectedItem()));
               int n = JOptionPane.showConfirmDialog(
                      frame,
                      "정말 구매하시겠습니까?",
                      "구매 확인",
                      JOptionPane.YES_NO_OPTION);
               if(n == JOptionPane.YES_OPTION) {
                  ((Client)cli).buyShoes(shoe);
                  frame.dispose();
               }
               
            } catch (IOException e1) {
               JOptionPane.showMessageDialog(frame,
                      "구매가 정상적으로 처리되지 않았습니다. 재시도 해주세요.",
                      "구매 오류",
                      JOptionPane.ERROR_MESSAGE);
            }
         }
      });
      buy_btn.setBounds(445, 285, 90, 25);
      contentPane.add(buy_btn);
       
      
      //고유 코드 
      code_label = new JLabel("고유 코드");
      code_label.setBounds(350, 60, 70, 15);
      contentPane.add(code_label);
      
      //신발 이름
      name_label = new JLabel("이름");
      name_label.setBounds(350, 105, 50, 15);
      contentPane.add(name_label);
      
      //신발 가격
      price_label = new JLabel("가격");
      price_label.setBounds(350, 150, 50, 15);
      contentPane.add(price_label);
      
      //사이즈 추천
      recoSize_label = new JLabel("사이즈 추천");
      recoSize_label.setBounds(55, 210, 90, 15);
      contentPane.add(recoSize_label);
      
      //사이즈 선택
      chooseSize_label = new JLabel("사이즈 선택");
      chooseSize_label.setBounds(60, 290, 85, 15);
      contentPane.add(chooseSize_label);
      
      //----------변경되는 항목들----------
      
      //신발 사이즈 220 ~ 300
      sizes = new String[17];
      for(int i = 0; i < sizes.length; i++) { //220부터 300까지 
         sizes[i] = ""+(220 + 5*i);
      }
      size_cbBox = new JComboBox();
      size_cbBox.setModel(new DefaultComboBoxModel(sizes));
      size_cbBox.setBounds(155, 285, 175, 25);
      contentPane.add(size_cbBox);
      
      //고유 코드 
      codeValue_label = new JLabel(shoe.getCode());
      codeValue_label.setBounds(445, 60, 90, 15);
      contentPane.add(codeValue_label);
      
      //신발 이름 
      nameValue_label = new JLabel(shoe.getShoeName());
      nameValue_label.setBounds(445, 105, 90, 15);
      contentPane.add(nameValue_label);
      
      //신발 가격
      priceValue_label = new JLabel(""+shoe.getPrice());
      priceValue_label.setBounds(445, 150, 50, 15);
      contentPane.add(priceValue_label);
      
      //신발 사이즈 추천
      recoSizeValue_label = new JLabel(""+shoe.recommendSize((Client)cli));
      recoSizeValue_label.setBounds(145, 210, 50, 15);
      contentPane.add(recoSizeValue_label);
      
   }
}