package com.zenika.dorm.core.service.get;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.core.service.DormServiceProcess;

import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public interface DormServiceGetResult extends DormServiceProcess {

    public boolean isResultSet();

    public boolean hasResult();

    public DependencyNode getUniqueNode();

    public void addNode(DependencyNode node);

    public List<DependencyNode> getNodes();

    public void setNodes(List<DependencyNode> nodes);
}
