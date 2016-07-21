package cn.didadu.service;

import cn.didadu.sdn.entity.Movie;
import cn.didadu.sdn.entity.Seen;
import cn.didadu.sdn.entity.User;
import cn.didadu.sdn.repository.MovieRepository;
import cn.didadu.sdn.repository.SeenRepository;
import cn.didadu.sdn.repository.UserRepository;
import com.google.common.collect.Lists;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjing on 16-7-17.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeenRepository seenRepository;

    @Autowired
    private GraphDatabaseService graphDatabaseService;

    @Transactional
    public void initData(){

        /**
         * 初始化用户
         */
        User user1 = new User("John Johnson");
        User user2 = new User("Kate Smith");
        User user3 = new User("Jack Jeffries");
        /**
         * 为用户John添加朋友关系
         */
        user1.setFriends(Lists.newArrayList(user2, user3));

        /**
         * 初始化电影
         */
        Movie movie1 = new Movie("Fargo");
        Movie movie2 = new Movie("Alien");
        Movie movie3 = new Movie("Heat");

        /**
         * 初始化HAS_SEEN关系
         */
        Seen hasSeen1 = new Seen(5, user1, movie1);
        Seen hasSeen2 = new Seen(3, user2, movie3);
        Seen hasSeen3 = new Seen(6, user2, movie2);
        Seen hasSeen4 = new Seen(4, user3, movie1);
        Seen hasSeen5 = new Seen(5, user3, movie2);

        /**
         * 如果不加@Transactional，下面每个save都会单独开启事物
         */
        userRepository.save(Lists.newArrayList(user1, user2, user3));
        movieRepository.save(Lists.newArrayList(movie1, movie2, movie3));
        seenRepository.save(Lists.newArrayList(hasSeen1, hasSeen2, hasSeen3, hasSeen4, hasSeen5));
    }

    @Transactional
    public User getUserByName(String name){
        return userRepository.getUserByName(name);
    }

    @Transactional
    public Node getUserById(Long id){
        return graphDatabaseService.getNodeById(id);
    }
}
