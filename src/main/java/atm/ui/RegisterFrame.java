package atm.ui;
import atm.UserDao;
import atm.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends BaseFrame{
    private UserDao userDao = new UserDao();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    public RegisterFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel label1 = new JLabel("账号");
        label1.setBounds(95, 42, 54, 34);
        label1.setFont(FONT_20);

        getContentPane().add(label1);

        JLabel label2 = new JLabel("姓名");
        label2.setBounds(95, 97, 54, 35);
        getContentPane().add(label2);
        label2.setFont(FONT_20);

        JLabel label3 = new JLabel("密码");
        label3.setBounds(95, 156, 54, 33);
        getContentPane().add(label3);
        label3.setFont(FONT_20);

        textField = new JTextField();
        textField.setBounds(210, 42, 226, 36);
        getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(211, 99, 226, 36);
        getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(211, 156, 226, 36);
        getContentPane().add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(210, 211, 226, 36);
        getContentPane().add(textField_3);

        JLabel label3_1 = new JLabel("确认密码");
        label3_1.setFont(new Font("楷体", Font.PLAIN, 20));
        label3_1.setBounds(95, 214, 94, 33);
        getContentPane().add(label3_1);

        JButton btn1 = new JButton("提交注册");

        btn1.setBounds(293, 308, 138, 42);
        getContentPane().add(btn1);
        btn1.setFont(FONT_20);

        JButton btn_login = new JButton("去登录");

        btn_login.setFont(new Font("楷体", Font.PLAIN, 20));
        btn_login.setBounds(95, 308, 138, 42);
        getContentPane().add(btn_login);

        this.setSize(544, 411);
        this.setLocationRelativeTo(null);
        this.setTitle("新用户注册");
        this.setVisible(true);

        final JFrame iframe = this;

        //提交注册
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField.getText();
                String realname = textField_1.getText();
                String password = textField_2.getText();
                String password2 = textField_3.getText();
                if(username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号不能为空");
                    return;
                }
                if(realname.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "姓名不能为空");
                    return;
                }
                if(password.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "密码不能为空");
                    return;
                }
                if(!password.equals(password2)) {
                    JOptionPane.showMessageDialog(null, "两次密码不一致");
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setRealname(realname);
                user.setBalance(0.00);

                if(userDao.findByUsername(username)!=null) {
                    JOptionPane.showMessageDialog(null, "账号已存在，请修改后重试");
                    return;
                }

                if(userDao.save(user)==1) {
                    JOptionPane.showMessageDialog(null, "新账号注册成功");
                    new LoginJFrame();
                    iframe.dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "注册失败，请联系管理员");
                }

            }
        });

        btn_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginJFrame();
                iframe.dispose();
            }
        });

    }


    public static void main(String[] args) {
        RegisterFrame rf = new RegisterFrame();

    }
}
