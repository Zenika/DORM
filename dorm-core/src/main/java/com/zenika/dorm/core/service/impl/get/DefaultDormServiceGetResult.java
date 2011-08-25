package com.zenika.dorm.core.service.impl.get;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.get.DormServiceGetResult;
import com.zenika.dorm.core.service.impl.DefaultDormServiceProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DefaultDormServiceGetResult extends DefaultDormServiceProcess implements DormServiceGetResult {

    private List<DependencyNode> nodes = new ArrayList<DependencyNode>();

    public DefaultDormServiceGetResult(String processName) {
        super(processName);
    }

    public DefaultDormServiceGetResult(String processName, DependencyNode node) {
        this(processName);
        nodes.add(node);
    }

    public DefaultDormServiceGetResult(String processName, List<DependencyNode> nodes) {
        this(processName);

        if (null != nodes) {
            this.nodes = nodes;
        }
    }

    @Override
    public void addNode(DependencyNode node) {
        nodes.add(node);
    }

    @Override
    public DependencyNode getUniqueNode() {

        if (isResultSet()) {
            throw new CoreException("Result is not unique, use the getResultSet() method");
        }

        return nodes.get(0);
    }

    @Override
    public List<DependencyNode> getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(List<DependencyNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean isResultSet() {
        return nodes.size() > 1;
    }

    @Override
    public boolean hasResult() {
        return !nodes.isEmpty();
    }
}
