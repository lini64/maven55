package atm.ui;

import atm.User;
import atm.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class WorkFrame extends BaseFrame {
    UserDao userDao = new UserDao();
    //当前登录用户
    public static User loginUser = null;
    //跟踪主窗口

    static List<Window> windows = new ArrayList<>();
    public WorkFrame() {
        final JFrame iframe = this;
        windows.add(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, -39);

        getContentPane().setLayout(null);

        JButton btn1 = new JButton("存钱");
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CunqianFrame();
            }
        });
        btn1.setBounds(110, 102, 135, 50);
        getContentPane().add(btn1);

        JButton btn2 = new JButton("取钱");
        btn2.setBounds(310, 102, 135, 50);
        getContentPane().add(btn2);

        JButton btn3 = new JButton("查询余额");

        btn3.setBounds(110, 182, 135, 50);
        getContentPane().add(btn3);

        JButton btn4 = new JButton("转账");

        btn4.setBounds(310, 182, 135, 50);
        getContentPane().add(btn4);

        JButton btn5 = new JButton("修改密码");
        btn5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RepwdFrame();
            }
        });
        btn5.setBounds(110, 266, 135, 50);
        getContentPane().add(btn5);

        JButton btn6 = new JButton("交易记录");
        btn6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new RecordsFrame();
            }
        });
        btn6.setBounds(310, 266, 135, 50);
        getContentPane().add(btn6);

        JButton btn7 = new JButton("缴费充值");
        btn7.setBounds(110, 350, 135, 50);
        getContentPane().add(btn7);

        JButton btn8 = new JButton("退出系统");
        btn8.setBounds(310, 350, 135, 50);
        getContentPane().add(btn8);

        btn1.setFont(FONT_25);
        btn2.setFont(FONT_25);
        btn3.setFont(FONT_25);
        btn4.setFont(FONT_25);
        btn5.setFont(FONT_25);
        btn6.setFont(FONT_25);
        btn7.setFont(FONT_25);
        btn8.setFont(FONT_25);

        JLabel lblNewLabel = new JLabel("您好，欢迎进入系统");
        lblNewLabel.setForeground(Color.white);
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel.setBounds(44, 36, 170, 33);
        getContentPane().add(lblNewLabel);

        JLabel label3 = new JLabel("账号");
        label3.setForeground(Color.cyan);
        label3.setFont(new Font("宋体", Font.BOLD, 16));
        label3.setBounds(220, 36, 305, 33);
        getContentPane().add(label3);
        //新增功能
        JLabel label4 = new JLabel("<html><u>销户</u></html>");
        label4.setForeground(Color.RED);
        label4.setFont(new Font("宋体", Font.BOLD, 16));
        label4.setBounds(360, 36,50,33);
        label4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new XiaohuFrame();
            }
        });
        getContentPane().add(label4);
        //显示当前登录用户名
        label3.setText(String.format("%s[%s]", loginUser.getUsername(),loginUser.getRealname()));

        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setTitle("欢迎使用ATM机");
        this.setVisible(true);


        ImageIcon img = new ImageIcon(this.getClass().getResource("/images/180226.png"));
        JLabel background = new JLabel(img);
        this.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        BackgroundPanel bgp=new BackgroundPanel((new ImageIcon(this.getClass().getResource("/images/180226.png"))).getImage());
        bgp.setBounds(0,0,600,500);
        getContentPane().add(bgp);




        //查询余额
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User user = userDao.findByUserId(loginUser.getId());
                JOptionPane.showMessageDialog(null, "当前余额："+user.getBalance());
            }
        });

        //取钱
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				String str=JOptionPane.showInputDialog(WorkFrame.this,"请输入要取出的金额","取钱",JOptionPane.PLAIN_MESSAGE);
				System.out.println(str);
				Double money = Double.parseDouble(str);
				int row = userDao.decreaseBalance(loginUser.getId(), money);
				if(row==1) {
					JOptionPane.showMessageDialog(null, "成功取出："+str);
				}else {
					JOptionPane.showMessageDialog(null, "取出失败，请联系管理员");
				}
                new QuqianFrame();
            }
        });

        //转账
        btn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new TransferFrame();
            }
        });

        btn7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new JiaofeiFrame();
            }
        });

        btn8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                int result = JOptionPane.showConfirmDialog(iframe,"确定要退出?", "系统提示",
                        JOptionPane.YES_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if(result == JOptionPane.YES_OPTION){
                    WorkFrame.loginUser = null;
                    iframe.dispose();
                    new LoginJFrame();
                }
            }
        });
    }

    public static void main(String[] args) {
        new WorkFrame();
    }
}
