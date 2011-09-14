//package com.zenika.dorm.core.test.graph.visitor.filter;
//
//import com.zenika.dorm.core.graph.visitor.filter.LoggerDependencyVisitorFilter;
//import com.zenika.dorm.core.graph.visitor.filter.WithResourceDependencyVisitorFilter;
//import com.zenika.dorm.core.graph.visitor.impl.DependenciesCollector;
//import com.zenika.dorm.core.model.Dependency;
//import com.zenika.dorm.core.model.DependencyNode;
//import com.zenika.dorm.core.test.graph.AbstractDependencyGraphUnitTest;
//import org.fest.assertions.Assertions;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.LinkedHashSet;
//import java.util.Set;
//
///**
// * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
// */
//public class WithResourceDependencyVisitorFilterUnitTest extends AbstractDependencyGraphUnitTest {
//
//    private static final Logger LOG = LoggerFactory.getLogger(WithResourceDependencyVisitorFilter.class);
//
//    @Test
//    public void collectDependenciesWithFileOnly() {
//
//        DependencyNode notLinearGraph = fixtures.getCyclicGraphWithNoResource();
//
//        DependenciesCollector collector = new DependenciesCollector();
//        collector.addFilter(new LoggerDependencyVisitorFilter());
//        collector.addFilter(new WithResourceDependencyVisitorFilter());
//        notLinearGraph.accept(collector);
//
//        Set<Dependency> dependencies = new LinkedHashSet<Dependency>();
//        dependencies.add(fixtures.getDependencyWithResource());
//        dependencies.add(fixtures.getDependencyWithResource2());
//        dependencies.add(fixtures.getDependencyWithResource3());
//        dependencies.add(fixtures.getDependencyWithResource4());
//
//        LOG.debug("Collected dependencies : " + collector.getDependencies());
//        Assertions.assertThat(collector.getDependencies()).isEqualTo(dependencies);
//    }
//
//    /**
//     * Not linear graph is like : (A, B, C are 3 nodes)
//     * -- A with resource
//     * ----- B without resource
//     * -------- C with resource
//     *
//     * Result of the collector must be : A and C.
//     */
//    @Test
//    public void collectDependenciesWithFileOnlyFromNotLinearGraph() {
//
//        DependencyNode notLinearGraph = fixtures.getCyclicGraphWithNoResourceAndNotLinear();
//
//        DependenciesCollector collector = new DependenciesCollector();
//        collector.addFilter(new LoggerDependencyVisitorFilter());
//        collector.addFilter(new WithResourceDependencyVisitorFilter());
//        notLinearGraph.accept(collector);
//
//        Set<Dependency> dependencies = new LinkedHashSet<Dependency>();
//        dependencies.add(fixtures.getDependencyWithResource());
//        dependencies.add(fixtures.getDependencyWithResource2());
//        dependencies.add(fixtures.getDependencyWithResource3());
//        dependencies.add(fixtures.getDependencyWithResource4());
//        dependencies.add(fixtures.getDependencyWithResource5());
//
//        LOG.debug("Collected dependencies : " + collector.getDependencies());
//        Assertions.assertThat(collector.getDependencies()).isEqualTo(dependencies);
//    }
//}
