package atm.ui;
import atm.Records;
import atm.RecordsDao;
import atm.User;
import atm.UserDao;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TransferFrame extends BaseFrame{
    private UserDao userDao = new UserDao();
    RecordsDao reDao = new RecordsDao();

    private JTextField textField1;
    private JTextField textField2;
    public TransferFrame() {
        getContentPane().setLayout(null);

        JLabel label1 = new JLabel("\u76EE\u6807\u8D26\u53F7");
        label1.setBounds(47, 39, 105, 34);
        label1.setFont(FONT_20);

        getContentPane().add(label1);

        JLabel label2 = new JLabel("\u8F6C\u8D26\u91D1\u989D");
        label2.setBounds(47, 94, 105, 34);
        getContentPane().add(label2);
        label2.setFont(FONT_20);

        textField1 = new JTextField();
        textField1.setBounds(162, 39, 226, 36);
        getContentPane().add(textField1);
        textField1.setColumns(10);

        textField2 = new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(162, 96, 226, 36);
        getContentPane().add(textField2);

        JButton btn1 = new JButton("提交");

        btn1.setBounds(250, 169, 138, 42);
        getContentPane().add(btn1);
        btn1.setFont(FONT_20);

        this.setSize(479, 330);
        this.setLocationRelativeTo(null);
        this.setTitle("修改密码");
        this.setVisible(true);

        final JFrame iframe = this;

        //提交注册
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();

                if(username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号不能为空");
                    return;
                }

                double amount = 0;
                try {
                    amount = Double.parseDouble(textField2.getText());
                    if(amount<=0) {
                        JOptionPane.showMessageDialog(null, "金额需要大于零");
                        return;
                    }
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "金额有误");
                    return;
                }


                //当前用户
                User curr = userDao.findByUserId(WorkFrame.loginUser.getId());
                System.out.println(curr.getUsername());
                System.out.println(curr.getBalance());
                //目标用户
                User target = userDao.findByUsername(username);
                if(target==null) {
                    JOptionPane.showMessageDialog(null, "账号不存在，请修改后重试");
                    return;
                }


                if(curr.getBalance()<amount) {
                    JOptionPane.showMessageDialog(null, "余额不足，无法完成");
                    return;
                }


                if(userDao.increaseBalance(target.getId(), amount)==1) {
                    userDao.decreaseBalance(curr.getId(), amount);
                    //交易记录1
                    Records r = new Records();
                    r.setCreateTime(new Date());
                    r.setUserId(WorkFrame.loginUser.getId());
                    r.setAmount(-amount);
                    r.setRemark("给["+target.getRealname()+"]转账");
                    reDao.save(r);
                    //交易记录2
                    Records r2 = new Records();
                    r2.setCreateTime(new Date());
                    r2.setUserId(target.getId());
                    r2.setAmount(amount);
                    r2.setRemark("来自["+curr.getRealname()+"]的转账");
                    reDao.save(r2);

                    JOptionPane.showMessageDialog(null, "转账成功");
                    iframe.dispose();
                }else {
                    JOptionPane.showMessageDialog(null, "注册失败，请联系管理员");
                }

            }
        });

    }


}
