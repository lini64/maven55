package atm;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;
public class RecordsDao {
    //用于执行SQL语句
    QueryRunner runner = new QueryRunner();
    //查询结果转换器，只要数据库字段和实体属性字母相同，它可以忽略符号和大小写的差异
    BeanHandler<Records> bh = new BeanHandler<Records>(Records.class, new BasicRowProcessor(new GenerousBeanProcessor()));
    BeanListHandler<Records> bh2 = new BeanListHandler<Records>(Records.class, new BasicRowProcessor(new GenerousBeanProcessor()));


    public int save(Records records) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "insert into records(id,user_id,amount,remark) values(0,?,?,?)";
            return runner.update(conn, sql,
                    records.getUserId(),
                    records.getAmount(),
                    records.getRemark()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Records> list(long userId){
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from records where user_id=?";
            return runner.query(conn, sql, bh2, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}