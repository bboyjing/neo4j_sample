package cn.didadu.neo4jkernel;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjing on 16-7-21.
 */
@Service
public class LegacyIndexService {

    @Autowired
    private GraphDatabaseService graphDatabaseService;


    @Transactional
    public void createIndex(){
        String johnEmail = "john@example.org";
        String kateEmail = "kage@example.org";
        String jackEmail = "jack@example.org";

        //获取用户节点，并且设置email属性
        Node userJohn = graphDatabaseService.getNodeById(0l);
        userJohn.setProperty("email", johnEmail);
        Node userKate = graphDatabaseService.getNodeById(1l);
        userKate.setProperty("email", kateEmail);
        Node userJack = graphDatabaseService.getNodeById(2l);
        userJack.setProperty("email", jackEmail);

        //获取索引管理器
        IndexManager indexManager = graphDatabaseService.index();
        //查找名称为users的索引，若不存在则创建一个
        Index<Node> userIndex =indexManager.forNodes("users");
        //以email为值，为users索引添加具体的索引项目
        userIndex.add(userJohn, "email", johnEmail);
        userIndex.add(userKate, "email", kateEmail);
        userIndex.add(userJack, "email", jackEmail);
    }

    @Transactional
    public void getNodeByIndex(){
        //获取索引管理器
        IndexManager indexManager = graphDatabaseService.index();
        //查找名称为users的索引
        Index<Node> userIndex =indexManager.forNodes("users");
        //获取索引命中的结果集
        IndexHits<Node> indexHits = userIndex.get("email", "john@example.org");
        /**
         * 获取命中的节点，且要求命中节点只有一个，如果有多个则抛出NoSuchElementException("More than one element in...")
         * 若索引命中的结果集中不只一条是，秩序遍历indexHits即可
         * for(Node user : indexHits){
         *     System.out.println(user.getProperty("name"));
         * }
         */
        Node loggedOnUserNode = indexHits.getSingle();
        if(loggedOnUserNode != null){
            System.out.println(loggedOnUserNode.getProperty("name"));
        }
    }

    @Transactional
    public void updateIndex(){
        String johnEmail = "john@example.org";
        String updateJohnEmail = "john@new.example.org";

        //获取索引管理器
        IndexManager indexManager = graphDatabaseService.index();
        //查找名称为users的索引
        Index<Node> userIndex =indexManager.forNodes("users");
        //获取索引命中的结果集
        IndexHits<Node> indexHits = userIndex.get("email", johnEmail);
        Node loggedOnUserNode = indexHits.getSingle();
        if(loggedOnUserNode != null){
            //删除索引
            userIndex.remove(loggedOnUserNode, "email", johnEmail);
            //更新
            loggedOnUserNode.setProperty("email",updateJohnEmail);
            //新增索引
            userIndex.add(loggedOnUserNode, "email", updateJohnEmail);
        }
    }

}
