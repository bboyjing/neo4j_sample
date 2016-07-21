package cn.didadu.sdn.repository;

import cn.didadu.sdn.entity.Seen;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjing on 16-7-18.
 */

@Repository
public interface SeenRepository extends GraphRepository<Seen> {
}
