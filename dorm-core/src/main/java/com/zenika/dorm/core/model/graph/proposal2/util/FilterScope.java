package com.zenika.dorm.core.model.graph.proposal2.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/8/11
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterScope {
    Set<Scope> scopes;

    public FilterScope(Set<Scope> scopes) {
        this.scopes = scopes;
    }

    public FilterScope(Scope... scopes){
        this.scopes = new HashSet<Scope>();
        for (Scope scope : scopes){
            this.scopes.add(scope);
        }
    }

    public void addScope(Scope scope){
        scopes.add(scope);
    }

    public void removeScope(Scope scope){
        scopes.remove(scope);
    }

    public boolean containtScope(Scope scope){
        return scopes.contains(scope);
    }
}
