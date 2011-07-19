package com.zenika.dorm.maven.test.unit;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractUnitTest {

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
}
