package atm;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void deleteByUser(long id);
    //使用Mybatis

}
