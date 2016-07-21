package cn.didadu.sdn.repository;

import cn.didadu.sdn.entity.Movie;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjing on 16-7-17.
 */
@Repository
public interface MovieRepository extends GraphRepository<Movie> {
}
