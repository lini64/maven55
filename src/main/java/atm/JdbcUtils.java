package atm;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
public class JdbcUtils {
    /* 创建连接池对象 */
    private static ComboPooledDataSource ds = null;
    public static Connection getConnection() throws SQLException {
        if (ds == null) {
            try {
                ds = new ComboPooledDataSource();
                /* 如何获取JDBC连接 */
                ds.setDriverClass("com.mysql.jdbc.Driver");
                ds.setJdbcUrl("jdbc:mysql://localhost:3306/atm?useUnicode=true&characterEncoding=utf-8&useSSL=false");
                ds.setUser("root");
                ds.setPassword("123456");
                /* 设置连接池工作参数 */
                ds.setMaxPoolSize(40);  // 最大连接数
                ds.setMinPoolSize(2);   // 最小连接数
                ds.setInitialPoolSize(5); // 初始连接数
                /* 经过连接池代理的连接，内部方法有所变化 */
                // Connection conn = ds.getConnection();

                /* C3P0处理过的连接，close经过重写，不是真的关闭，而是归还给ds */
                // conn.close();

            } catch (Exception e) {
                System.err.println("初始化连接池失败：" + e.getMessage());
                e.printStackTrace();
                throw new ExceptionInInitializerError("无法初始化数据库连接池: " + e);
            }
        }

        return ds.getConnection();
    }

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            System.out.println("数据库连接成功: " + connection);
            // 在这里可以执行一些数据库操作
            connection.close();
        } catch (SQLException e) {
            System.err.println("获取连接失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
