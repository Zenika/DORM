package com.zenika.dorm.core.test.graph.visitor.filter;

import com.zenika.dorm.core.graph.visitor.filter.LoggerDependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.filter.WithResourceDependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.impl.DependenciesCollector;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.test.graph.AbstractDependencyGraphUnitTest;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Ignore
public class WithResourceDependencyVisitorFilterUnitTest extends AbstractDependencyGraphUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(WithResourceDependencyVisitorFilter.class);

    @Test
    public void collectDependenciesWithFileOnly() {

        DependencyNode notLinearGraph = fixtures.getCyclicGraphWithNoResourceAndNotLinear();

        DependenciesCollector collector = new DependenciesCollector();
        collector.addFilter(new LoggerDependencyVisitorFilter());
        collector.addFilter(new WithResourceDependencyVisitorFilter());
        notLinearGraph.accept(collector);

        LOG.debug("Collected dependencies : " + collector.getDependencies());
    }

    /**
     * Not linear graph : (A, B, C are 3 nodes)
     * -- A with resource
     * ----- B without resource
     * -------- C with resource
     *
     * Result of the collector must be : A and C.
     */
    @Test
    public void collectDependenciesWithFileOnlyFromNotLinearGraph() {

        DependencyNode notLinearGraph = fixtures.getCyclicGraphWithNoResourceAndNotLinear();

        DependenciesCollector collector = new DependenciesCollector();
        collector.addFilter(new LoggerDependencyVisitorFilter());
        collector.addFilter(new WithResourceDependencyVisitorFilter());
        notLinearGraph.accept(collector);

        LOG.debug("Collected dependencies : " + collector.getDependencies());
    }
}
