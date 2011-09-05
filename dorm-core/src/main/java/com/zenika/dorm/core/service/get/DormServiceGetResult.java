package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.model.DependencyNode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class DormServiceGetResult {

    private List<DependencyNode> nodes = new ArrayList<DependencyNode>();

    public void addNode(DependencyNode node) {
        nodes.add(node);
    }

    public DependencyNode getUniqueNode() {

        if (isManyResult()) {
            throw new CoreException("Result is not unique, use the getNodes() method");
        }

        return nodes.get(0);
    }

    public List<DependencyNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<DependencyNode> nodes) {
        this.nodes = nodes;
    }

    public boolean isManyResult() {
        return nodes.size() > 1;
    }

    public boolean hasResult() {
        return !nodes.isEmpty();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nodes", nodes)
                .appendSuper(super.toString())
                .toString();
    }
}