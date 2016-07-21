package cn.didadu.neo4jkernel.evaluator;


import cn.didadu.neo4jkernel.generic.MyLabels;
import cn.didadu.neo4jkernel.generic.MyRelationshipTypes;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

/**
 * Created by zhangjing on 16-7-6.
 */
public class CustomNodeFilteringEvaluator implements Evaluator{

    private final Node userNode;

    public CustomNodeFilteringEvaluator(Node userNode) {
        this.userNode = userNode;
    }

    @Override
    public Evaluation evaluate(Path path) {
        //遍历路径中的最后一个节点，当前例子中是所有的USERS、MOVIE节点
        Node currentNode = path.endNode();
        //判断是否是MOVIES节点，如果不是，则丢弃并且继续遍历
        if(!currentNode.hasLabel(MyLabels.MOVIES)){
            return Evaluation.EXCLUDE_AND_CONTINUE;
        }
        //遍历指向当前节点的HAS_SEEN关系
        for(Relationship r : currentNode.getRelationships(Direction.INCOMING, MyRelationshipTypes.HAS_SEEN)){
            //获取HAS_SEEN关系的源头，即USERS节点，如果节点是给定的目标节点（John），则丢弃
            if(r.getStartNode().equals(userNode)){
                return Evaluation.EXCLUDE_AND_CONTINUE;
            }
        }
        return Evaluation.INCLUDE_AND_CONTINUE;
    }
}
