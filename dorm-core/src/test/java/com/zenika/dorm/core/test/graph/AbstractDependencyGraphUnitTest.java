package com.zenika.dorm.core.test.graph;

import com.zenika.dorm.core.test.helper.DormGraphFixtures;
import org.junit.Before;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractDependencyGraphUnitTest {

    protected DormGraphFixtures fixtures;

    @Before
    public void before() {
        fixtures = new DormGraphFixtures();
    }
}
