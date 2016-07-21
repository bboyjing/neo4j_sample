package cn.didadu.sdn.repository;

import cn.didadu.sdn.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjing on 16-7-17.
 */

@Repository
public interface UserRepository extends GraphRepository<User>{

    @Query("MATCH (user:USERS {name:{name}}) RETURN user")
    User getUserByName(@Param("name") String name);

}
