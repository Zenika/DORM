package com.zenika.dorm.core.test.graph.proposal1;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.visitor.impl.ConsoleVisitor;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import org.junit.Test;

/**
 * Tests for graph model proposal 1
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class GraphProposal1Test {

    @Test
    public void test() {
        DefaultDormOrigin originA = new DefaultDormOrigin("foo");
        DormMetadata a = new DefaultDormMetadata("1.0", originA);

        DefaultDormOrigin originB = new DefaultDormOrigin("bar");
        DormMetadata b = new DefaultDormMetadata("1.0", originB);

        DefaultDormOrigin originC = new DefaultDormOrigin("toto");
        DormMetadata c = new DefaultDormMetadata("1.0", originC);

        Dependency d1 = new DefaultDependency(a);
        Dependency d2 = new DefaultDependency(b);

        DependencyNode n1 = new DefaultDependencyNodeLeaf(d1);
        DependencyNodeComposite n2 = new DefaultDependencyNodeComposite(d2);
        n2.addChildren(n1);

        ConsoleVisitor visitor = new ConsoleVisitor();
        n2.accept(visitor);

//        Dependency dA = new Dependency(a, new Usage("Usage1"));
//        Dependency dB = new Dependency(b, new Usage("Usage1"));
//        Dependency dC = new Dependency(c, new Usage("Usage1"));
//
//        DependencyNode nodeA = new DefaultDependencyNode(dA);
//        DependencyNode nodeB = new DefaultDependencyNode(dB);
//        DependencyNode nodeC = new DefaultDependencyNode(dC);
//
//        nodeA.getChildrens().add(nodeB);
//        nodeB.getChildrens().add(nodeC);
//        nodeC.getChildrens().add(nodeA);
//        nodeC.getChildrens().add(nodeA);
//
//        ConsoleVisitor visitor = new ConsoleVisitor();
//        nodeA.accept(visitor);
//
//        Set<DormMetadata> dormMetadatas = new HashSet<DormMetadata>();
//        CollectArtifactsVisitor collectArtifactsVisitor = new CollectArtifactsVisitor(dormMetadatas,
//                new Usage("Usage1"));
//        nodeA.accept(collectArtifactsVisitor);
//
//        System.out.println();
//        System.out.println("Artifacts for usage 1");
//        for (DormMetadata dormMetadata : dormMetadatas) {
//            System.out.println(dormMetadata.getFullQualifier());
//        }
    }


}
