package com.zenika.dorm.core.graph.visitor.filter;

import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Collect dependencies which contains resource only
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class WithResourceDependencyVisitorFilter implements DependencyVisitorFilter {

    private static final Logger LOG = LoggerFactory.getLogger(WithResourceDependencyVisitorFilter.class);

    @Override
    public void init() {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Init filter to collect dependency with resource only");
        }
    }

    @Override
    public void doFilterOnEntrance(DependencyNode node, DependencyVisitorFilterChain filterChain) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Dependency with resource only filter on entrance for node : " + node);
        }

        if (node.getDependency().hasResource()) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Collect dependency with resource from node : " + node);
            }

            filterChain.process(node);
        } else {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Ignore dependency without resource from node : " + node);
            }

            filterChain.ignoreAndGoToNext();
        }


    }

    @Override
    public void doFilterOnExit(DependencyNode node, DependencyVisitorFilterChain filterChain) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Dependency with resource only filter on exit for node : " + node);
        }

        filterChain.process(node);
    }

    @Override
    public void destroy() {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Destroy filter to collect dependency with resource only");
        }
    }
}
