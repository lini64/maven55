package atm.ui;

import atm.RecordsDao;
import atm.UserDao;
import atm.Records;
import atm.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//交易记录
public class RecordsFrame extends BaseFrame {

    UserDao userDao = new UserDao();
    RecordsDao reDao = new RecordsDao();

    public RecordsFrame() {
        // 设置标题
        this.setTitle("交易记录");
        // 设置组件
        this.init();
        // 界面可视化
        this.setVisible(true);
    }

    // 初始化界面
    private void init() {
        JFrame iframe = this;

        this.setSize(900, 400);
        this.setLocationRelativeTo(null);
        //取当前登录用户
        User curr = userDao.findByUserId(WorkFrame.loginUser.getId());


        List<Records> list = reDao.list(curr.getId());


        //定义表格列名数组
        String[] columnNames= {"交易金额","交易类型","交易时间"};
        //定义表格数据数组
        //String[][] tableValues={{"A1","B1",""},{"A2","B2",""},{"A3","B3",""},{"A4","B4",""},{"A5","B5",""}};
        String[][] tableValues=new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            Records ro = list.get(i);
            tableValues[i][0]=ro.getAmount()+"";
            tableValues[i][1]=ro.getRemark();
            tableValues[i][2]=ro.getCreateTime().toString();
        }


        //创建指定列名和数据的表格
        JTable table =new JTable(tableValues,columnNames);
        //创建显示表格的滚动面板
        JScrollPane scrollpane=new JScrollPane(table);
        //将滚动面板添加到边界布局的中间
        getContentPane().add(scrollpane,BorderLayout.CENTER);



    }


    public static void main(String[] args) {
        new RecordsFrame();
    }

}

