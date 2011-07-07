package com.zenika.dorm.core.test.graph.proposal1;

import com.zenika.dorm.core.model.graph.proposal1.*;
import com.zenika.dorm.core.modelnew.impl.DefaultDormOrigin;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for graph model proposal 1
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class GraphProposal1Test {

    @Test
    public void test() {
        DefaultDormOrigin originA = new DefaultDormOrigin("foo");
        Artifact A = new Artifact("1.0", originA);

        DefaultDormOrigin originB = new DefaultDormOrigin("bar");
        Artifact B = new Artifact("1.0", originB);

        DefaultDormOrigin originC = new DefaultDormOrigin("toto");
        Artifact C = new Artifact("1.0", originC);

        Dependency dA = new Dependency(A, new Usage("Usage1"));
        Dependency dB = new Dependency(B, new Usage("Usage1"));
        Dependency dC = new Dependency(C, new Usage("Usage1"));

        DependencyNode nodeA = new DefaultDependencyNode(dA);
        DependencyNode nodeB = new DefaultDependencyNode(dB);
        DependencyNode nodeC = new DefaultDependencyNode(dC);

        nodeA.getChildrens().add(nodeB);
        nodeB.getChildrens().add(nodeC);
        nodeC.getChildrens().add(nodeA);
        nodeC.getChildrens().add(nodeA);

        ConsoleVisitor visitor = new ConsoleVisitor();
        nodeA.accept(visitor);

        Set<Artifact> artifacts = new HashSet<Artifact>();
        CollectArtifactsVisitor collectArtifactsVisitor = new CollectArtifactsVisitor(artifacts,
                new Usage("Usage1"));
        nodeA.accept(collectArtifactsVisitor);

        System.out.println();
        System.out.println("Artifacts for usage 1");
        for (Artifact artifact : artifacts) {
            System.out.println(artifact.getFullQualifier());
        }
    }


}
