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
// 存钱界面
public class CunqianFrame extends BaseFrame {
    private UserDao userDao = new UserDao();
    private RecordsDao reDao = new RecordsDao();
    private JLabel label2 = new JLabel("余额");

    public CunqianFrame() {
        // 设置标题
        this.setTitle("存钱");
        // 设置组件
        this.init();
        // 界面可视化
        this.setVisible(true);
    }
    // 初始化界面
    private void init() {
        final JFrame iframe = this;
        this.setSize(410, 310);
        this.setLocationRelativeTo(null);
        // 创建面板对象，并定义为空布局
        JPanel jp = new JPanel(null);

        // 添加标签
        JLabel jl1 = new JLabel("金额：");
        jl1.setFont(new Font("宋体", Font.PLAIN, 20));
        jl1.setBounds(51, 80, 70, 70);

        // 添加文本框
        final JTextField jtf = new JTextField(20);
        jtf.setBounds(131, 98, 200, 40);

        // 添加按钮
        final JButton jb = new JButton("确认存入");
        jb.setFont(new Font("宋体", Font.PLAIN, 20));
        jb.setBounds(178, 188, 154, 50);

        // 添加组件
        jp.add(jl1);
        jp.add(jtf);
        jp.add(jb);
        getContentPane().add(jp);

        // 添加余额标签
        label2.setFont(new Font("宋体", Font.PLAIN, 14));
        label2.setBounds(51, 42, 286, 28);
        jp.add(label2);

        // 重新加载余额信息
        reloadLabel();

        // 内部类进行事件处理
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Double value = Double.parseDouble(jtf.getText());
                    int row = userDao.increaseBalance(WorkFrame.loginUser.getId(), value);
                    if (row == 1) {
                        Records r = new Records();
                        r.setCreateTime(new Date());
                        r.setUserId(WorkFrame.loginUser.getId());
                        r.setAmount(value);
                        r.setRemark("存款");
                        reDao.save(r);
                        JOptionPane.showMessageDialog(iframe, "成功存入：" + value);
                    } else {
                        JOptionPane.showMessageDialog(iframe, "存入失败，请联系管理员");
                    }
                    reloadLabel();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(iframe, "请输入有效的金额", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private void reloadLabel() {
        User user = userDao.findByUserId(WorkFrame.loginUser.getId());
        String info = String.format("账号:%s , 余额:%s", user.getUsername(), user.getBalance());
        label2.setText(info);
    }
}
