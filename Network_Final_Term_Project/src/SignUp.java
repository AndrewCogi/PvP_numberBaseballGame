import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField txtSignup;
	private JTextField txtName;
	private JTextField txtId;
	private JTextField txtPw;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtPwCheck;
	private JPasswordField passwordField;
	private JPasswordField erdpasswordField_1;
	private boolean idCheck = false;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public SignUp(PrintWriter out, Scanner in) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 420);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtSignup = new JTextField();
		txtSignup.setEditable(false);
		txtSignup.setBackground(new Color(211, 211, 211));
		txtSignup.setHorizontalAlignment(SwingConstants.CENTER);
		txtSignup.setFont(new Font("Microsoft Tai Le", Font.BOLD, 21));
		txtSignup.setText("Sign Up");
		txtSignup.setColumns(10);

		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
		txtName.setHorizontalAlignment(SwingConstants.TRAILING);
		txtName.setText("name:");
		txtName.setColumns(10);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
		txtId.setHorizontalAlignment(SwingConstants.TRAILING);
		txtId.setText("id:");
		txtId.setColumns(10);

		txtPw = new JTextField();
		txtPw.setEditable(false);
		txtPw.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
		txtPw.setHorizontalAlignment(SwingConstants.TRAILING);
		txtPw.setText("pw:");
		txtPw.setColumns(10);

		textField = new JTextField();
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);

		JButton btnIdCheck = new JButton("id check");
		btnIdCheck.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));

		txtPwCheck = new JTextField();
		txtPwCheck.setEditable(false);
		txtPwCheck.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
		txtPwCheck.setHorizontalAlignment(SwingConstants.TRAILING);
		txtPwCheck.setText("check:");
		txtPwCheck.setColumns(10);

		JButton btnFinish = new JButton("Finish");
		btnFinish.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));

		passwordField = new JPasswordField();

		erdpasswordField_1 = new JPasswordField();

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addGap(106).addGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(txtId, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtPwCheck, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
										.addComponent(txtPw, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 59,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
										.addComponent(erdpasswordField_1, GroupLayout.DEFAULT_SIZE, 192,
												Short.MAX_VALUE)
										.addGroup(Alignment.TRAILING,
												gl_contentPane.createSequentialGroup()
														.addComponent(btnFinish, GroupLayout.PREFERRED_SIZE, 85,
																GroupLayout.PREFERRED_SIZE)
														.addGap(27).addComponent(btnCancel).addGap(28)))))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnIdCheck).addGap(63))
				.addGroup(Alignment.LEADING,
						gl_contentPane.createSequentialGroup().addGap(120)
								.addComponent(txtSignup, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(119, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(31)
				.addComponent(
						txtSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(26)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(btnIdCheck))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPw, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPwCheck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(erdpasswordField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(26).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnCancel)
						.addComponent(btnFinish))
				.addContainerGap(83, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
		// gui��

		// idCheck�Ҷ�
		btnIdCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = textField_1.getText();
				if (id.equals("")) {
					JOptionPane.showMessageDialog(null, "id�� �Է��Ͻÿ�");
				} else {
					System.out.println("id������");
					JOptionPane.showMessageDialog(null, "id ��밡���մϴ�");
					idCheck = true;
				} // else close

			}
		});

		// ȸ�����Խõ��Ҷ�-finish��ư ������
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				String id = textField_1.getText();
				String pw = passwordField.getText();
				String pwCheck = erdpasswordField_1.getText();
				String regExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,20}$";
				String regExp2 = "^(?=.*[0-9])(?=.*[a-z]).{4,10}$";
				// (?=.*[0-9]): 0~9���������ּ��Ѱ�
				// (?=.*[a-z]): �����ҹ��� �ּ� �Ѱ�
				// (?=.*[A-Z]): �����빮�� �ּ� �Ѱ�
				// (?=.*\\W): Ư������ �ּ� �Ѱ�
				// (?=\\S+$): ���� ��� ����
				// .{8,20}: 8~20�ڸ� ���ڿ�

				// name�� �ƹ��͵� �Է����� �ʾ��� �� �˾�â����
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "�̸��� �Է��ϼ���");
				}
				// id�� �ƹ��͵� �Է����� �ʾ��� �� �˾�â����
				else if (id.equals("")) {
					JOptionPane.showMessageDialog(null, "id�� �Է��Ͻÿ�");
				}
				// id�� ���Ǿȸ����� �˾�â����
				else if (id.matches(regExp2) == false) {
					JOptionPane.showMessageDialog(null, "id�� ���� �ҹ���,���� �ּ� 1�� ������ \n4-10 �ڸ����� �մϴ�.");
				}
				// pw�� �ƹ��͵� �Է����� �ʾ��� �� �˾�â����
				else if (pw.equals("")) {
					JOptionPane.showMessageDialog(null, "pw�� �Է��Ͻÿ�");
				}
				// pw�� ���Ǿȸ����� �˾�â����
				else if (pw.matches(regExp) == false) {
					JOptionPane.showMessageDialog(null, "pw�� ���� �ҹ���,�빮��,����,Ư����ȣ�� �ּ� 1�� ������ \n8-20 �ڸ����� �մϴ�.");
				}
				// pwCheck�� �ƹ��͵� �Է����� �ʾ��� �� �˾�â����
				else if (pwCheck.equals("")) {
					JOptionPane.showMessageDialog(null, "pw�� �ѹ��� �Է��Ͻÿ�");
				}
				// pw�� pwCheck�̰����� Ȯ��
				else if (!pw.equalsIgnoreCase(pwCheck)) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�");
				}
				// idcheck�ߴ�������Ȯ��
				else if (idCheck == false) {
					JOptionPane.showMessageDialog(null, "ID Check�� �Ͻʽÿ�");
				}

				// pw hash�Լ������ؼ������ϱ�
				// ��������̸�����-ȸ�����Լ���
				else {

					System.out.println("AAAAAAAAAAAAAAAA");

					out.println("signUp&success&name&" + name);
					out.println("signUp&success&id&" + id);
					out.println("signUp&success&pw&" + pw);

					// �������˾�â����
					System.out.println("ȸ�����ԿϷ�");
					// �α���ȭ�������̵��ϱ�
					JOptionPane.showMessageDialog(null, "ȸ������ ����!\n ������ �� �α����� �����մϴ�.\n [Ȯ��]�� �����ø� ����˴ϴ�.");
					System.exit(0);
					SignUp.this.setVisible(false);

					try {
						System.out.println("ID: " + id);
						WaitWindowView a = new WaitWindowView(id, out, in);
						a.run();
					} catch (Exception e1) {

					}

				}

				// �ʱ�ȭ�ϱ�
				textField.setText("");
				textField_1.setText("");
				passwordField.setText("");
				erdpasswordField_1.setText("");

			}
		});

		// ȸ����������Ҷ�-Cancel��ư ������
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp.this.setVisible(false);
				GamePlayer.frame.setVisible(true);
				// �ٽ÷α���â���� ���ư���
			}
		});
	}
}