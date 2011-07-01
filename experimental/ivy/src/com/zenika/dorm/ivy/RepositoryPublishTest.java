package com.zenika.dorm.ivy;

import org.apache.ivy.core.event.EventManager;
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.module.id.ModuleId;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.publish.PublishEngine;
import org.apache.ivy.core.publish.PublishOptions;
import org.apache.ivy.core.settings.IvyPattern;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.parser.xml.XmlModuleDescriptorWriter;
import org.apache.ivy.plugins.repository.file.FileRepository;
import org.apache.ivy.plugins.resolver.FileSystemResolver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class RepositoryPublishTest {

    private File base = new File("tmp/tests").getAbsoluteFile();

    private FileRepository fileRepository;
    private PublishEngine publishEngine;
    private FileSystemResolver fileSystemResolver;

    private IvyPattern ivyPattern;
    private IvyPattern artifactPattern;

    private IvySettings ivySettings;

    private ModuleDescriptor moduleDescriptor;
    private ModuleRevisionId moduleRevisionId;
    private ModuleId moduleId;

    public void init() {
        ivySettings = new IvySettings();
    }

    public void configureRepository() {
        File file = new File(base, "repo").getAbsoluteFile();
        file.mkdirs();

        fileRepository = new FileRepository(file);
    }

    public void configurePublisher() {
        publishEngine = new PublishEngine(ivySettings, new EventManager());
    }

    public void configureResolver() {

        fileSystemResolver = new FileSystemResolver();
        fileSystemResolver.setRepository(fileRepository);

        fileSystemResolver.setName("fs resolver test");
        fileSystemResolver.setSettings(ivySettings);


        ivyPattern = new IvyPattern();
        ivyPattern.setPattern(base.getAbsolutePath() + "/[module]/[revision]/[artifact].[ext]");

        artifactPattern = new IvyPattern();
        artifactPattern.setPattern(base.getAbsolutePath() + "/[module]/[revision]/[artifact].[ext]");

        fileSystemResolver.addConfiguredIvy(ivyPattern);
        fileSystemResolver.addConfiguredArtifact(artifactPattern);


    }

    public void configureModule() {
        moduleId = new ModuleId("com.zenika", "foo");
        moduleRevisionId = new ModuleRevisionId(moduleId, "1.0.0");

        moduleDescriptor = DefaultModuleDescriptor.newDefaultInstance(moduleRevisionId);

        try {
            // generate ivy.xml
            XmlModuleDescriptorWriter.write(moduleDescriptor, new File(base, "module/ivy.xml"));
            System.out.println("ivy.xml created at : " + new File(base, "module/ivy.xml").getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publish() {

        try {
            new File(base, "topublish").mkdir();
            new File(base, "topublish/A.jar").createNewFile();
            new File(base, "topublish/B.jar").createNewFile();

            Collection searchPattern = new ArrayList();
            searchPattern.add(base.getAbsoluteFile() + "/topublish/[artifact].[ext]");

            PublishOptions publishOptions = new PublishOptions();
            publishOptions.setSrcIvyPattern(base.getAbsoluteFile() + "/topublish/[artifact].[ext]");
            publishEngine.publish(moduleDescriptor, searchPattern, fileSystemResolver, publishOptions);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void workflow1() {
        init();
        configureModule();
        configureRepository();
        configureResolver();
        configurePublisher();

        publish();
    }

    public static void main(String[] args) {
        RepositoryPublishTest repositoryPublishTest = new RepositoryPublishTest();
        repositoryPublishTest.workflow1();
    }


}
