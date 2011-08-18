package com.zenika.dorm.core.graph.visitor;

import com.zenika.dorm.core.model.DependencyNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DependencyVisitorFilterChain {

    private static final Logger LOG = LoggerFactory.getLogger(DependencyVisitorFilterChain.class);

    private LinkedList<DependencyVisitorFilter> filters = new LinkedList<DependencyVisitorFilter>();
    private ListIterator<DependencyVisitorFilter> iterator;

    private DependencyVisitor visitor;
    private Orientation orientation = Orientation.FORWARD;

    private boolean processContinue;

    public DependencyVisitorFilterChain(DependencyVisitor visitor) {
        this.visitor = visitor;
    }

    public void addFilter(DependencyVisitorFilter filter) {
        if (!filters.contains(filter)) {
            filters.add(filter);
        }

    }

    public void process(DependencyNode node) {

        // invalidate by default before each filter,
        // then the filter must validate explicitely by processing (= forwading) the node to the visitor
        processContinue = false;

        if (null == iterator) {

            if (LOG.isTraceEnabled()) {
                LOG.trace("Init the filter iterator of the filter chain");
            }

            iterator = filters.listIterator();
        }

        if (orientation.equals(Orientation.FORWARD)) {
            processForward(node);
        } else {
            processBackward(node);
        }

    }

    private void processForward(DependencyNode node) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Filter iterator of the filter chain is oriented to FORWARD");
        }

        if (iterator.hasNext()) {

            if (LOG.isTraceEnabled()) {
                LOG.trace("Move to the next filter of the filter chain");
            }

            iterator.next().doFilterOnEntrance(node, this);

        } else {

            if (LOG.isTraceEnabled()) {
                LOG.trace("No more next filter in the filter chain, call the visitor#visitEnter and reset the iterator");
            }

            processContinue = visitor.visitEnter(node);
            iterator = filters.listIterator();
        }
    }

    public void processBackward(DependencyNode node) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Filter iterator of the filter chain is oriented to BACKWARD");
        }

        if (iterator.hasPrevious()) {

            if (LOG.isTraceEnabled()) {
                LOG.trace("Move to the previous filter of the filter chain");
            }

            iterator.previous().doFilterOnExit(node, this);

        } else {

            if (LOG.isTraceEnabled()) {
                LOG.trace("No more previous filter in the filter chain, call the visitor#visitExit and reset the iterator");
            }

            processContinue = visitor.visitExit(node);
            resetIteratorToLastFilter();
        }
    }

    public boolean isProcessContinue() {
        return processContinue;
    }

    public void changeOrientationToBackward() {

        if (orientation.equals(Orientation.BACKWARD)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Filter chain orientation is already set to backward");
            }
            return;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Change filter chain orientation to backward and set filter iterator to the last element");
        }

        orientation = Orientation.BACKWARD;
        resetIteratorToLastFilter();
    }

    private void resetIteratorToLastFilter() {
        iterator = filters.listIterator(filters.size());
    }

    private static enum Orientation {
        FORWARD, BACKWARD
    }
}
