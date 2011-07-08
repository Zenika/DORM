package com.zenika.dorm.ivy;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.event.EventManager;
import org.apache.ivy.core.module.descriptor.Artifact;
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor;
import org.apache.ivy.core.module.descriptor.MDArtifact;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Antoine Rouaze <lukasz.piliszczuk AT zenika.com>
 */
public class PublishTest {

    private File base = new File("tmp/tests").getAbsoluteFile();

    private FileRepository fileRepository;
    private PublishEngine publishEngine;
    private FileSystemResolver fileSystemResolver;

    private IvyPattern ivyPattern;
    private IvyPattern artifactPattern;

    private IvySettings ivySettings;

    private DefaultModuleDescriptor moduleDescriptor;
    private ModuleRevisionId moduleRevisionId;
    private ModuleId moduleId;

    private File file;

    public void init() {

        Helper.deleteDirectory(base);
//        try {
//            URL url = ClassLoader.getSystemResource("com/zenika/dorm/resources/ivy.xml");
//            File file = new File(url.toURI());
        ivySettings = new IvySettings();
//            ivySettings.load(file);
//            System.out.println(ivySettings.getVariable("info"));
//            System.out.println("Get variable");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (ParseException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
    }

    public void configureRepository() {
        file = new File(base, "repo").getAbsoluteFile();
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
        ivyPattern.setPattern(file.getAbsolutePath() + "[module]/[revision]/[artifact].[ext]");

        artifactPattern = new IvyPattern();
        artifactPattern.setPattern(file.getAbsolutePath() + "/[module]/[revision]/[artifact].[ext]");

        fileSystemResolver.setChecksums("md5");
        fileSystemResolver.addConfiguredIvy(ivyPattern);
        fileSystemResolver.addConfiguredArtifact(artifactPattern);


    }

    public void configureModule() {
        moduleId = new ModuleId("com.zenika", "foo");
        moduleRevisionId = new ModuleRevisionId(moduleId, "1.0.0");

        moduleDescriptor = DefaultModuleDescriptor.newDefaultInstance(moduleRevisionId);

        Artifact artifactA = new MDArtifact(moduleDescriptor, "A", "jar", "jar");
        Artifact artifactB = new MDArtifact(moduleDescriptor, "B", "jar", "jar");

        moduleDescriptor.addArtifact("default", artifactA);
        moduleDescriptor.addArtifact("default", artifactB);

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
//            publishOptions.set
            publishOptions.setSrcIvyPattern(base.getAbsoluteFile() + "/topublish/[artifact].[ext]");
            publishEngine.publish(moduleDescriptor, searchPattern, fileSystemResolver, publishOptions);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resolve() {
        try {
            URL url = ClassLoader.getSystemResource("com/zenika/dorm/resources/ivy.xml");
            File file = new File(url.toURI());
            Ivy ivy = Ivy.newInstance(ivySettings);
            ivy.resolve(file);
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void workflow1() {
        init();
        configureModule();
        configureRepository();
        configureResolver();
        configurePublisher();
        //resolve();
        publish();
    }

    public static void main(String[] args) {
        PublishTest repositoryPublishTest = new PublishTest();
        repositoryPublishTest.workflow1();
    }


}
