package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import member.Account;
import store.*;

public class LoginFrame extends JFrame {
	private JPanel contentPane;
	private JTextField idField;
	private JPasswordField pwdField;

	private String id = "";
	private String pwd = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setTitle("login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 300, 380, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(71, 53, 28, 18);
		contentPane.add(idLabel);
		
		idField = new JTextField();
		idField.setBounds(160, 50, 150, 24);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(50, 89, 70, 18);
		contentPane.add(pwdLabel);
		
		pwdField = new JPasswordField();
		pwdField.setBounds(160, 86, 150, 24);
		contentPane.add(pwdField);
		pwdField.setColumns(10);
		
		JButton loginButton = new JButton("로그인");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = null;
				setIdPwd(idField,pwdField);
				Account account = new Account();
				user = account.loginPage(id,pwd);
				Store.myUser = user;
				if (Store.myUser==null) { // 로그인 실패
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인 후\n다시 로그인해주세요.");
				} else { // 로그인 성공
					JOptionPane.showMessageDialog(null, "로그인 성공");
					setVisible(false); // 기존의 로그인 화면 꺼주기
					try {
						
						if(Store.myUser.managerAuth==true) {
							
							SupervisorFrame superFrame = new SupervisorFrame();
							
						}else {
							MyPage mypage = new MyPage(new Client(user.id, user.pwd, user.myLength, user.myWidth, user.managerAuth));
						
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				//새로운 화면 구현
			}
		});
		loginButton.setBounds(200, 150, 85, 30);
		contentPane.add(loginButton);
		
		JButton joinButton = new JButton("회원가입");
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JoinFrame joinframe = new JoinFrame();
				joinframe.setVisible(true);
			}
		});
		joinButton.setBounds(85, 150, 85, 30);
		contentPane.add(joinButton);
	}

	private void setIdPwd(JTextField txtId, JTextField txtPw) {
		id = txtId.getText();
		pwd = txtPw.getText();
	}
}
