package atm.ui;
import atm.RecordsDao;
import atm.UserDao;
import atm.Records;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
public class JiaofeiFrame extends BaseFrame {
    UserDao userDao = new UserDao();
    RecordsDao reDao = new RecordsDao();
    public JiaofeiFrame() {
        this.setTitle("缴费充值");
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
        JLabel jl1 = new JLabel("手机号：");

        // 设置标签字体
        jl1.setFont(FONT_20);

        // 设置标签在面板中的位置
        jl1.setBounds(90, 22, 90, 70);

        // 添加文本框
        final JTextField tx1 = new JTextField(20);

        // 设置文本框位置
        tx1.setBounds(195, 38, 200, 40);

        // 添加按钮
        JButton jb = new JButton("确认充值");
        // 设置按钮文字大小
        jb.setFont(FONT_20);
        // 设置按钮位置及大小
        jb.setBounds(241, 249, 154, 50);
        // 设置面板背景颜色





        // 添加组件
        jp.add(jl1);
        jp.add(tx1);

        jp.add(jb);
        // 窗体添加面板
        getContentPane().add(jp);

        // 按钮事件
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String phone = tx1.getText();
                double amount = 50;

                int row = userDao.decreaseBalance(WorkFrame.loginUser.getId(), amount);
                if(row==1) {
                    Records r = new Records();
                    r.setCreateTime(new Date());
                    r.setUserId(WorkFrame.loginUser.getId());
                    r.setAmount(-amount);
                    r.setRemark("手机号["+phone+"]充值50元话费成功~");
                    reDao.save(r);
                    JOptionPane.showMessageDialog(iframe, "给["+phone+"]充值50元话费成功~");
                    iframe.dispose();
                }else {
                    JOptionPane.showMessageDialog(iframe, "充值失败，请联系管理员");
                }


            }
        });
    }

    public static void main(String[] args) {
        new JiaofeiFrame();
    }

}
