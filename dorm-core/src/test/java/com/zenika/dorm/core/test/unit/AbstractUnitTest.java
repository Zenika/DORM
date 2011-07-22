package com.zenika.dorm.core.test.unit;

import com.zenika.dorm.core.test.helper.DormTestFixtures;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractUnitTest {

    protected DormTestFixtures fixtures;

    @Before
    public void before() {

        // required by mockito
        MockitoAnnotations.initMocks(this);

        // reset fixtures before every test
        fixtures = new DormTestFixtures();
    }
}