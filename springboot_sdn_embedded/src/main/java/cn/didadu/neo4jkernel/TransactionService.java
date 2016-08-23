package cn.didadu.neo4jkernel;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjing on 16-8-23.
 */

@Service
public class TransactionService {
    @Autowired
    private GraphDatabaseService graphDatabaseService;

    @Transactional
    public void threadA(){
        //1
        Node john = graphDatabaseService.getNodeById(143l);
        System.out.println(john.getProperty("age"));

        //4
        john = graphDatabaseService.getNodeById(143l);
        System.out.println(john.getProperty("age"));

        //6
        john = graphDatabaseService.getNodeById(143l);
        System.out.println(john.getProperty("age"));
    }

    @Transactional
    public void threadB(){
        //2
        Node john = graphDatabaseService.getNodeById(143l);
        System.out.println(john.getProperty("age"));

        //3
        john.setProperty("age", 35);
        System.out.println(john.getProperty("age"));

        //5、threadB方法结束，提交事物
    }

    public void readLock(){
        try(Transaction tx= graphDatabaseService.beginTx()){
            //1
            Node john = graphDatabaseService.getNodeById(143l);
            System.out.println(john.getProperty("age"));
            //读锁，其他线程对于该节点的写会等待读锁释放
            tx.acquireReadLock(john);

            //4
            john = graphDatabaseService.getNodeById(143l);
            System.out.println(john.getProperty("age"));
            tx.success();
        }
    }

    @Transactional
    public void writeWaitReadLock(){
        //2
        Node john = graphDatabaseService.getNodeById(143l);
        System.out.println(john.getProperty("age"));

        //3
        john.setProperty("age", 38);
        System.out.println(john.getProperty("age"));
    }


}
