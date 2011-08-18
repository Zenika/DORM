package com.zenika.dorm.core.graph.visitor.filter;

import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilterChain;
import com.zenika.dorm.core.model.DependencyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class CyclicDependencyVisitorFilter implements DependencyVisitorFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CyclicDependencyVisitorFilter.class);

    private Set<DependencyNode> visitedNodes = new HashSet<DependencyNode>();

    @Override
    public void init() {
    }

    @Override
    public void doFilterOnEntrance(DependencyNode node, DependencyVisitorFilterChain filterChain) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Cyclic dependency filter on entrance for node : " + node);
        }

        if (visitedNodes.contains(node)) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Cyclic dependency detected for node : " + node);
            }

            return;
        }

        visitedNodes.add(node);
        filterChain.process(node);
    }

    @Override
    public void doFilterOnExit(DependencyNode node, DependencyVisitorFilterChain filterChain) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Cyclic dependency filter on exit for node : " + node);
        }

        filterChain.process(node);
    }

    @Override
    public void destroy() {
    }
}
