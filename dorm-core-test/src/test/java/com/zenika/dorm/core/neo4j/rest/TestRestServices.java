package com.zenika.dorm.core.neo4j.rest;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.DormOrigin;
import com.zenika.dorm.core.model.graph.proposal1.Dependency;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependency;
import com.zenika.dorm.core.model.graph.proposal1.impl.DefaultDependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.impl.Usage;
import com.zenika.dorm.core.model.impl.DefaultDormMetadata;
import com.zenika.dorm.core.model.impl.DefaultDormOrigin;
import org.junit.Test;

import java.io.IOException;

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
        DependencyNodeComposite composite = new DefaultDependencyNodeComposite(dependency);
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
        DormOrigin origin = new DefaultDormOrigin("maven2");
        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
        Dependency dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
        DependencyNodeComposite composite = new DefaultDependencyNodeComposite(dependency);
        try {
            test.push(dependency, "maven:1.0.0:dorm");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
