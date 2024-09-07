package atm.ui;

import atm.MybatisUtil;
import atm.User;
import atm.UserDao;
import atm.UserMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XiaohuFrame extends BaseFrame{
    private UserDao userDao = new UserDao();
    private UserMapper userMapper = MybatisUtil.getSqlSession().getMapper(UserMapper.class);
    User user = null;
    final JFrame iframe = this;
    public XiaohuFrame() {

        this.setTitle("销户");
        this.init();
        this.setVisible(true);
    }
    //初始化
    private void init() {
       // 设置窗口大小
        this.setSize(410, 310);
        this.setLocationRelativeTo(null);
        // 创建面板对象，并定义为空布局
        JPanel jp = new JPanel(null);

        // 添加按钮
        JButton jb1 = new JButton("确认");
        jb1.setFont(new Font("宋体", Font.PLAIN, 20));
        jb1.setBounds(160, 200, 80, 30);

        // 添加组件
        jp.add(jb1);

        getContentPane().add(jp);

        // 添加余额标签
        JLabel label2 = new JLabel("余额");
        label2.setFont(new Font("宋体", Font.PLAIN, 18));
        //加载余额信息
        user = userDao.findByUserId(WorkFrame.loginUser.getId());
        label2.setText("当前账号为"+user.getUsername()+", 剩余余额为"+user.getBalance());
        label2.setBounds(40, 20, 350, 40);
        jp.add(label2);

        JLabel label3 = new JLabel("提示");
        label3.setFont(new Font("宋体", Font.PLAIN, 16));
        label3.setBounds(51, 60, 286, 28);
        label3.setText("请输入密码以确认销户：");
        jp.add(label3);

        JPasswordField pwdText = new JPasswordField(20);
        pwdText.setBounds(131, 98, 200, 30);
        jp.add(pwdText);

        JLabel label4 = new JLabel("<html>提示：销户后，账号余额会取出<p>账号将无法使用，请谨慎操作</p></html>");
        label4.setForeground(Color.RED);
        label4.setFont(new Font("宋体", Font.PLAIN, 16));
        label4.setBounds(80, 140, 286, 50);
        jp.add(label4);

        //为点击事件添加监听器
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取输入的账号和密码
                String password = user.getPassword();
                if(!password.equals(pwdText.getText())) {
                    JOptionPane.showMessageDialog(null, "密码错误，请重新输入");
                    return;
                }
                //销户逻辑
                //清空余额删除用户
                //使用Mybatis
                userMapper.deleteByUser(user.getId());
                JOptionPane.showMessageDialog(null, "销户成功");
                //关闭所有窗口，开启LoginJFrame
                WorkFrame.windows.get(0).dispose();
                new LoginJFrame();


            }
        });

    }


}
