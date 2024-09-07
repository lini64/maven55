package atm.ui;
import atm.UserDao;
import atm.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//登录界面继承BaseFrame
public class LoginJFrame extends BaseFrame {
    UserDao userDao = new UserDao();
    public LoginJFrame() {
        this.setTitle("欢迎使用ATM机");
        // 设置窗体可关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置组件
        this.init();
        // 界面可视化
        this.setVisible(true);
    }

    // 初始化界面
    private void init() {
        final LoginJFrame iframe = this;

        this.setSize(410, 310);
        this.setLocationRelativeTo(null);
        // 创建面板对象，并定义为空布局
        JPanel jp = new JPanel(null);
        jp.setBackground(Color.cyan);
        // 添加标签
        JLabel jl1 = new JLabel("账号：");
        JLabel jl2 = new JLabel("密码：");
        // 设置标签字体
        jl1.setFont(FONT_20);
        jl2.setFont(FONT_20);
        // 设置标签在面板中的位置
        jl1.setBounds(52, 27, 70, 70);
        jl2.setBounds(52, 100, 70, 70);
        // 添加文本框
        final JTextField jtf = new JTextField(20);
        // 添加密码文本框
        final JPasswordField jpf = new JPasswordField(20);
        // 设置文本框位置
        jtf.setBounds(132, 45, 200, 40);
        jpf.setBounds(132, 118, 200, 40);
        // 添加按钮
        JButton jb = new JButton("登录");
        // 设置按钮文字大小
        jb.setFont(FONT_20);
        // 设置按钮位置及大小
        jb.setBounds(178, 188, 154, 50);
        // 设置面板背景颜色
        //jp.setBackground(Color.YELLOW);



        // 内部类进行事件处理
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取输入的账号和密码
                String username = jtf.getText();
                String password = new String(jpf.getPassword());
                User user = userDao.findByUsername(username);
                if(user==null) {
                    JOptionPane.showMessageDialog(null, "账号不存在");
                    return;
                }
                if(password.equals(user.getPassword())) {
                    JOptionPane.showMessageDialog(null, "登录成功");
                    WorkFrame.loginUser = user;
                    iframe.dispose(); //销毁当前窗口
                    new WorkFrame();
                }else {
                    JOptionPane.showMessageDialog(null, "密码不正确");
                }
            }
        });
        // 添加组件
        jp.add(jl1);
        jp.add(jtf);
        jp.add(jl2);
        jp.add(jpf);
        jp.add(jb);
        // 窗体添加面板
        getContentPane().add(jp);

        JButton jb_1 = new JButton("\u53BB\u6CE8\u518C");

        jb_1.setFont(new Font("楷体", Font.PLAIN, 20));
        jb_1.setBounds(50, 187, 100, 50);
        jp.add(jb_1);


        jb_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();
                iframe.dispose();
            }
        });
    }


}
