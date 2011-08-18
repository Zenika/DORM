package com.zenika.dorm.core.test.graph.visitor;

import com.zenika.dorm.core.graph.visitor.filter.LoggerDependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesCollector;
import com.zenika.dorm.core.model.Dependency;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.test.graph.AbstractDependencyGraphUnitTest;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependenciesCollectorUnitTest extends AbstractDependencyGraphUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(DependenciesCollectorUnitTest.class);

    @Test
    public void dependenciesCollectorInSimpleGraph() {

        DependencyNode graph = fixtures.getSimpleGraph();

        DependenciesCollector collector = new DependenciesCollector();
        collector.addFilter(new LoggerDependencyVisitorFilter());
        graph.accept(collector);

        Set<Dependency> dependencies = new LinkedHashSet<Dependency>();
        dependencies.add(fixtures.getDependencyWithFile());
        dependencies.add(fixtures.getDependencyWithFile2());
        dependencies.add(fixtures.getDependencyWithFile3());

        LOG.debug("Collected dependencies : " + collector.getDependencies());
        Assertions.assertThat(collector.getDependencies()).isEqualTo(dependencies);
    }

    @Test
    public void dependenciesCollectorInGraphWithCyclicDependencies() {

        DependencyNode graph = fixtures.getCyclicGraph();

        DependenciesCollector collector = new DependenciesCollector();
        collector.addFilter(new LoggerDependencyVisitorFilter());
        graph.accept(collector);

        Set<Dependency> dependencies = new LinkedHashSet<Dependency>();
        dependencies.add(fixtures.getDependencyWithFile());
        dependencies.add(fixtures.getDependencyWithFile2());
        dependencies.add(fixtures.getDependencyWithFile3());

        LOG.debug("Collected dependencies : " + collector.getDependencies());
        Assertions.assertThat(collector.getDependencies()).isEqualTo(dependencies);
    }
}