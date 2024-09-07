package atm.ui;

import atm.RecordsDao;
import atm.UserDao;
import atm.Records;
import atm.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

//取钱界面
public class QuqianFrame extends BaseFrame {

    UserDao userDao = new UserDao();
    RecordsDao reDao = new RecordsDao();

    public QuqianFrame() {
        // 设置标题
        this.setTitle("取钱");
        // 设置组件
        this.init();
        // 界面可视化
        this.setVisible(true);
    }

    JLabel label2 = new JLabel("余额");

    // 初始化界面
    private void init() {
        final JFrame iframe = this;

        this.setSize(410, 310);
        this.setLocationRelativeTo(null);
        // 创建面板对象，并定义为空布局
        JPanel jp = new JPanel(null);
        // 添加标签
        JLabel jl1 = new JLabel("金额：");
        // 设置标签字体
        jl1.setFont(FONT_20);
        // 设置标签在面板中的位置
        jl1.setBounds(51, 80, 70, 70);
        // 添加文本框
        final JTextField jtf = new JTextField(20);
        // 设置文本框位置
        jtf.setBounds(131, 98, 200, 40);
        // 添加按钮
        JButton jb = new JButton("确认取出");
        // 设置按钮文字大小
        jb.setFont(FONT_20);
        // 设置按钮位置及大小
        jb.setBounds(178, 188, 154, 50);
        // 设置面板背景颜色
        //jp.setBackground(Color.YELLOW);




        // 添加组件
        jp.add(jl1);
        jp.add(jtf);
        jp.add(jb);
        // 窗体添加面板
        getContentPane().add(jp);


        label2.setFont(new Font("宋体", Font.PLAIN, 14));
        label2.setBounds(51, 42, 286, 28);
        jp.add(label2);

        //重新加载余额信息
        reloadLabel();

        // 内部类进行事件处理
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Double value = Double.parseDouble(jtf.getText());

                User user = userDao.findByUserId(WorkFrame.loginUser.getId());
                if(user.getBalance()<value) {
                    JOptionPane.showMessageDialog(iframe, "余额不足，无法取出");
                    return;
                }

                int row = userDao.decreaseBalance(WorkFrame.loginUser.getId(), value);
                if(row==1) {
                    Records r = new Records();
                    r.setCreateTime(new Date());
                    r.setUserId(WorkFrame.loginUser.getId());
                    r.setAmount(-value);
                    r.setRemark("取款");
                    reDao.save(r);
                    JOptionPane.showMessageDialog(iframe, "成功取出："+value);
                }else {
                    JOptionPane.showMessageDialog(iframe, "取出失败，请联系管理员");
                }

                reloadLabel();
            }
        });

    }


    private void reloadLabel() {
        User user = userDao.findByUserId(WorkFrame.loginUser.getId());
        String info = String.format("账号:%s , 余额:%s", user.getUsername(), user.getBalance());
        label2.setText(info);
    }


}
