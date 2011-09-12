package com.zenika.dorm.core.test;

import com.zenika.dorm.core.test.helper.DormFixtures;
import org.junit.Before;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class AbstractDormTest {

    protected DormFixtures fixtures;

    @Before
    public void before() {

        // reset fixtures before every test
        fixtures = new DormFixtures();
    }
}
