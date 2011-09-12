package com.zenika.dorm.core.test.unit;

import com.zenika.dorm.core.test.AbstractDormTest;
import org.mockito.MockitoAnnotations;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public abstract class AbstractUnitTest extends AbstractDormTest {

    @Override
    public void before() {

        super.before();

        // required by mockito
        MockitoAnnotations.initMocks(this);
    }
}