package com.zenika.dorm.core.neo4j.rest;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNode;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class TestRestServices {

    @Test
    public void testPush(){
        BasicRestServices test = new BasicRestServices();
        DormOrigin origin = new DefaultDormOrigin("maven");
        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
        Dependency dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
        try {
            test.push(dependency);
//            test.push(dependency, null);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testPushWithParent(){
        BasicRestServices test = new BasicRestServices();
        DormOrigin origin;
        DormMetadata metadata;
        Dependency dependency;
        try {
            List<Dependency> dependencies = new ArrayList<Dependency>();
            for (int i = 2; i < 100; i++){
                test = new BasicRestServices();
                origin = new DefaultDormOrigin("maven" + i);
                metadata = new DefaultDormMetadata("1.0.0", origin);
                dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
                dependencies.add(dependency);
            }
            for (int i = 0; i < dependencies.size(); i++){
                if (i == 0) {
                    test.push(dependencies.get(i));
                } else {
                    test.push(dependencies.get(i), dependencies.get(i - 1).getMetadata().getFullQualifier());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testGetDependencies() throws Exception {
        BasicRestServices test = new BasicRestServices();
        DormOrigin origin = new DefaultDormOrigin("maven2");
        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
        Dependency dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
        try {
            DependencyNode dependencyNode = test.getDependencies(dependency);
            System.out.println(dependencyNode.getDependency().getMetadata().getFullQualifier());
            for (DependencyNode node : dependencyNode.getChildren()){
                System.out.println(node.getDependency().getMetadata().getFullQualifier());
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
