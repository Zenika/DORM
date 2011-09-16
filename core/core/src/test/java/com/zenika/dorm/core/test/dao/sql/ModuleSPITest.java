package com.zenika.dorm.core.test.dao.sql;

import com.zenika.dorm.core.util.ModuleService;
import com.zenika.dorm.core.ws.resource.Dictionary;
import org.junit.Test;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ModuleSPITest {

    private Logger LOG = LoggerFactory.getLogger(ModuleSPITest.class);

    @Test
    public void test(){
        ModuleService service = new ModuleService();
        Dictionary dictionary = service.getModuleResource("test");
        LOG.info("Test: " + dictionary.getString());
    }
}
