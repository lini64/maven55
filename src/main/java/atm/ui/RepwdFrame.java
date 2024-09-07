package atm.ui;

import atm.UserDao;
import atm.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RepwdFrame extends BaseFrame {

    UserDao userDao = new UserDao();
    private JPasswordField tx3;

    public RepwdFrame() {

        this.setTitle("修改密码");
        // 设置组件
        this.init();
        // 界面可视化
        this.setVisible(true);
    }

    // 初始化界面
    private void init() {
        final JFrame iframe = this;

        this.setSize(485, 359);
        this.setLocationRelativeTo(null);
        // 创建面板对象，并定义为空布局
        JPanel jp = new JPanel(null);
        // 添加标签
        JLabel jl1 = new JLabel("原密码");
        JLabel jl2 = new JLabel("新密码");
        // 设置标签字体
        jl1.setFont(FONT_20);
        jl2.setFont(FONT_20);
        // 设置标签在面板中的位置
        jl1.setBounds(73, 22, 77, 70);
        jl2.setBounds(73, 95, 112, 70);
        // 添加文本框
        final JTextField tx1 = new JTextField(20);
        // 添加密码文本框
        final JPasswordField tx2 = new JPasswordField(20);
        // 设置文本框位置
        tx1.setBounds(195, 38, 200, 40);
        tx2.setBounds(195, 111, 200, 40);
        // 添加按钮
        JButton jb = new JButton("确认修改");
        // 设置按钮文字大小
        jb.setFont(FONT_20);
        // 设置按钮位置及大小
        jb.setBounds(241, 249, 154, 50);
        // 设置面板背景颜色
        //jp.setBackground(Color.YELLOW);




        // 添加组件
        jp.add(jl1);
        jp.add(tx1);
        jp.add(jl2);
        jp.add(tx2);
        jp.add(jb);
        // 窗体添加面板
        getContentPane().add(jp);

        JLabel jl2_1 = new JLabel("重复新密码");
        jl2_1.setFont(new Font("楷体", Font.PLAIN, 20));
        jl2_1.setBounds(73, 169, 112, 70);
        jp.add(jl2_1);

        tx3 = new JPasswordField(20);
        tx3.setBounds(195, 185, 200, 40);
        jp.add(tx3);


        // 按钮事件
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取输入的账号和密码
                String oldPwd = tx1.getText();
                String newPwd = new String(tx2.getPassword());
                String newPwd2 = new String(tx3.getPassword());
                if(!newPwd.equals(newPwd2)) {
                    JOptionPane.showMessageDialog(null, "两次密码不一致");
                    return;
                }
                User user = userDao.findByUserId(WorkFrame.loginUser.getId());
                if(user==null) {
                    JOptionPane.showMessageDialog(null, "账号不存在");
                    return;
                }
                if(!user.getPassword().equals(oldPwd)) {
                    JOptionPane.showMessageDialog(null, "原密码不正确");
                    return;
                }
                user.setPassword(newPwd);
                int row = userDao.update(user);
                if(row==1) {
                    JOptionPane.showMessageDialog(null, "密码修改成功");
                    iframe.dispose();
                }
            }
        });
    }

}

