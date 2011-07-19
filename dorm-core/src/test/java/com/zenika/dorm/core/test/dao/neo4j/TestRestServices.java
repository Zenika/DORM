package com.zenika.dorm.core.test.dao.neo4j;

import com.zenika.dorm.core.dao.DormDao;
import com.zenika.dorm.core.dao.neo4j.DormDaoNeo4j;
import com.zenika.dorm.core.graph.Dependency;
import com.zenika.dorm.core.graph.DependencyNode;
import com.zenika.dorm.core.graph.impl.DefaultDependency;
import com.zenika.dorm.core.graph.impl.Usage;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
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
    public void testPush() {
        DormDao test = new DormDaoNeo4j();
        DormOrigin origin = new DefaultDormOrigin("maven");
        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
        Dependency dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
        test.push(dependency);
    }

    @Test
    public void testPushWithParent() {
        DormDao test = new DormDaoNeo4j();
        DormOrigin origin;
        DormMetadata metadata;
        Dependency dependency = null;
        List<Dependency> dependencies = new ArrayList<Dependency>();
        for (int i = 2; i < 10; i++) {
            origin = new DefaultDormOrigin("maven" + i);
            metadata = new DefaultDormMetadata("1.0.0", origin);
            dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
            dependencies.add(dependency);
        }
        for (int i = 0; i < dependencies.size(); i++) {
            if (i == 0) {
                test.push(dependencies.get(0));
            } else {
                test.pushWithParent(dependencies.get(i), dependencies.get(i - 1));
            }
        }
    }

    @Test
    public void testGetDependencies() throws Exception {
        DormDao test = new DormDaoNeo4j();
        DormOrigin origin = new DefaultDormOrigin("maven2");
        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
        Usage usage = new Usage("DEFAULT");
        DependencyNode dependencyNode = test.getByMetadata(metadata, usage);
        System.out.println(dependencyNode.getDependency().getMetadata().getFullQualifier());
        for (DependencyNode node : dependencyNode.getChildren()) {
            System.out.println(node.getDependency().getMetadata().getFullQualifier());
        }
    }
}
