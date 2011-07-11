package com.zenika.dorm.core.model.graph.proposal1.visitor;

import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite;
import com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf;
import com.zenika.dorm.core.model.graph.proposal1.visitor.impl.DependencyVisitorCheckException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
@Aspect
public class DependencyVisitorCheckAspect {

    /**
     * pointcut on the method visitEnter used to enter to an composite node
     *
     * @param node    the composite node
     * @param visitor the current visitor
     */
    @Pointcut("execution(Boolean com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor.visitEnter" +
            "(com.zenika.dorm.core.model.graph.proposal1.DependencyNodeComposite)) && args(node) " +
            "&& target(visitor)")
    public void visitComposite(DependencyNodeComposite node, DependencyVisitor visitor) {
    }

    /**
     * pointcut on the method visit used to enter to an leaf node
     *
     * @param node    the leaf node
     * @param visitor the current visitor
     */
    @Pointcut("execution(Boolean com.zenika.dorm.core.model.graph.proposal1.visitor.DependencyVisitor.visit" +
            "(com.zenika.dorm.core.model.graph.proposal1.DependencyNodeLeaf)) && args(node) " +
            "&& target(visitor)")
    public void visitLeaf(DependencyNodeLeaf node, DependencyVisitor visitor) {
    }

    @Around("visitComposite(node, visitor)")
    public Object performChecksOnComposite(ProceedingJoinPoint joinPoint, DependencyNodeComposite node,
                                           DependencyVisitor visitor) throws Throwable {

        try {
            visitor.performChecks(node);
        } catch (DependencyVisitorCheckException e) {
            return false;
        }

        return joinPoint.proceed();
    }

    @Around("visitLeaf(node, visitor)")
    public Object performChecksOnLeaf(ProceedingJoinPoint joinPoint, DependencyNodeLeaf node,
                                      DependencyVisitor visitor) throws Throwable {

        try {
            visitor.performChecks(node);
        } catch (DependencyVisitorCheckException e) {
            return false;
        }

        return joinPoint.proceed();
    }
}
