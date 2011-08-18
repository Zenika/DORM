package com.zenika.dorm.core.graph.visitor.filter;

import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilter;
import com.zenika.dorm.core.graph.visitor.DependencyVisitorFilterChain;
import com.zenika.dorm.core.model.DependencyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Graph visitor that prints dependencies in the console logs
 * Remark : The log level used is info, and it must be enabled if logback
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class LoggerDependencyVisitorFilter implements DependencyVisitorFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerDependencyVisitorFilter.class);

    private Integer counter = 0;

    @Override
    public void init() {
    }

    @Override
    public void doFilterOnEntrance(DependencyNode node, DependencyVisitorFilterChain filterChain) {
        print("Enter node : " + node.getDependency().getMetadata());
        print("Usage : " + node.getDependency().getUsage());
        counter++;

        filterChain.process(node);
    }

    @Override
    public void doFilterOnExit(DependencyNode node, DependencyVisitorFilterChain filterChain) {
        print("Exit node : " + node.getDependency().getMetadata());
        counter--;

        filterChain.process(node);
    }

    @Override
    public void destroy() {
    }

    private void print(String text) {

        if (!LOG.isInfoEnabled()) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < counter * 3; i++) {
            builder.append('-');
        }

        builder.append(" ").append(text);
        LOG.info(builder.toString());
    }
}
