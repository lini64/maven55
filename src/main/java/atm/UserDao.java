package atm;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.Connection;
public class UserDao {

    //用于执行SQL语句
    QueryRunner runner = new QueryRunner();
    //查询结果转换器，只要数据库字段和实体属性字母相同，它可以忽略符号和大小写的差异
    BeanHandler<User> bh = new BeanHandler<User>(User.class, new BasicRowProcessor(new GenerousBeanProcessor()));


    public int save(User user) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "insert into user(id,username,password,realname,balance) values(0,?,?,?,?)";
            return runner.update(conn, sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getRealname(),
                    user.getBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public User findByUsername(String username) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from user where username=?";
            return runner.query(conn, sql, bh, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByUserId(long userId) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from user where id=?";
            return runner.query(conn, sql, bh, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int increaseBalance(long userid, double value) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update user set balance=balance+? where id=?";
            return runner.update(conn, sql, value, userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int decreaseBalance(long userid, double value) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update user set balance=balance-? where id=?";
            return runner.update(conn, sql, value, userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int update(User user) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update user set username=?,realname=?,password=?,balance=? where id=?";
            return runner.update(conn, sql,
                    user.getUsername(),
                    user.getRealname(),
                    user.getPassword(),
                    user.getBalance(),
                    user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}