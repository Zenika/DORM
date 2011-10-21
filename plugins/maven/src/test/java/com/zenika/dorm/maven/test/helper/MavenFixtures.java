package com.zenika.dorm.maven.test.helper;

import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.impl.Usage;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.test.helper.ExtensionFixtures;
import com.zenika.dorm.maven.constant.MavenConstant;
import com.zenika.dorm.maven.model.MavenPluginMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenFixtures extends ExtensionFixtures {

    private static final Logger LOG = LoggerFactory.getLogger(MavenFixtures.class);

    /**
     * Maven metadata extension
     */
    private String mavenType = MavenConstant.Extension.JAR;
    private String origin = MavenPluginMetadata.MAVEN_PLUGIN;

    @Override
    public String getType() {
        return mavenType;
    }

    /**
     * Create a maven dependency
     * All maven dependency must have the usage internal because of top generic "entity" dependency
     *
     * @return
     */
    @Override
    public DormMetadata getDependencyWithResource() {
        Usage usage = Usage.createInternal(origin);
        DormMetadata dormMetadata = getMetadata();
        dormMetadata.setDerivedObject(getDerivedObject());
        dormMetadata.setUsage(usage);
        return dormMetadata;
    }

}
