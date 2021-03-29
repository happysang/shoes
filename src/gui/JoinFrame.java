package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import member.Account;
import store.Store;
import store.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class JoinFrame extends JFrame {

	private JPanel contentPane;
	private JTextField idField;
	private JTextField pwdField;
	private JTextField lengthField;
	private JComboBox<String> comboBox = new JComboBox<String>();
	private boolean id_check_ok = false; //아이디체크 t/f
	private String [] width = {"L", "M", "S"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JoinFrame frame = new JoinFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JoinFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 250, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(47, 33, 62, 18);
		contentPane.add(idLabel);
		
		JLabel pwdLabel = new JLabel("패스워드");
		pwdLabel.setBounds(47, 83, 62, 18);
		contentPane.add(pwdLabel);
		
		JLabel lengthLabel = new JLabel("발사이즈");
		lengthLabel.setBounds(47, 116, 62, 18);
		contentPane.add(lengthLabel);
		
		JLabel widthLabel = new JLabel("발볼");
		widthLabel.setBounds(47, 152, 41, 18);
		contentPane.add(widthLabel);
		
		idField = new JTextField();
		idField.setBounds(137, 30, 116, 24);
		contentPane.add(idField);
		idField.setColumns(10);
		
		pwdField = new JTextField();
		pwdField.setColumns(10);
		pwdField.setBounds(137, 77, 116, 24);
		contentPane.add(pwdField);
		
		lengthField = new JTextField();
		lengthField.setColumns(10);
		lengthField.setBounds(137, 113, 116, 24);
		contentPane.add(lengthField);
		
		JLabel lblNewLabel = new JLabel("5자 미만");
		lblNewLabel.setBounds(264, 34, 62, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("5자 이상");
		lblNewLabel_1.setBounds(264, 81, 62, 18);
		contentPane.add(lblNewLabel_1);
		
		
		comboBox.setModel(new DefaultComboBoxModel<String>(width));
		comboBox.setBounds(137, 149, 41, 24);
		contentPane.add(comboBox);
		
		joinChecker checker = new joinChecker(); //check 도와주는객체생성
		
		JButton checkButton = new JButton("중복확인");
		checkButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText();
				id_check_ok = checker.idCheck(id);
				System.out.println(id);
				if(id_check_ok) {
					JOptionPane.showMessageDialog(null, "사용 가능합니다.");
				}
			}
		});
		checkButton.setBounds(329, 29, 89, 27);
		contentPane.add(checkButton);
		
		JButton joinCompleteButton = new JButton("회원가입");
		joinCompleteButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!id_check_ok) {
					JOptionPane.showMessageDialog(null, "ID 중복체크 바랍니다");
					return;
				}
				boolean all_check = checker.allCheck();
				
				if(all_check) { //모두 통과 시
					
					Account account = new Account();
					try {
						Store.myUser=account.signUp(idField.getText(), pwdField.getText(), lengthField.getText(),(String)(comboBox.getSelectedItem()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					//등록하고
					
					//입력창 리셋하고
					JOptionPane.showMessageDialog(null, "회원가입 완료하였습니다.");
					setVisible(false);
				}
			}
			
		});
		joinCompleteButton.setBounds(129, 214, 105, 27);
		contentPane.add(joinCompleteButton);
		

	}
	
	class joinChecker{//회원가입 체크 도와주는 내부 클래스

		public boolean idCheck(String checkId) { //아이디체크하기
			
			if (checkId.contentEquals("")) { 
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
				return false;
			}
			if (checkId.length() >= 5) {
				JOptionPane.showMessageDialog(null, "5자 미만으로 입력하세요!");
				return false;
			}
			User checkUser = Store.userMgr.find(checkId);
			if (checkUser != null) {
				JOptionPane.showMessageDialog(null, "이미 가입된 아이디입니다.");
				return false;
			}
			
			return true;
		}

		public boolean allCheck() {
			if (pwdField.getText().contentEquals("") || lengthField.getText().contentEquals("")
					|| (comboBox.getSelectedItem()==null)) {
				JOptionPane.showMessageDialog(null, "빈곳이있음");
				return false;
			}
			if (pwdField.getText().length() < 5) {
				JOptionPane.showMessageDialog(null, "비밀번호 5자이상");
				return false;
			}
			
			return true;
			
		} 
		
	}
}
