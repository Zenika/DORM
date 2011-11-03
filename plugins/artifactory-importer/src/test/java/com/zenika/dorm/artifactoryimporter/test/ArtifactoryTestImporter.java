package com.zenika.dorm.artifactoryimporter.test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zenika.dorm.artifactoryimporter.ArtifactoryImporter;
import com.zenika.dorm.core.guice.module.DormCoreModule;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.mockito.Mockito.*;
/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ArtifactoryTestImporter {

    private ArtifactoryImporter importer;

    private MavenProcessor mavenProcessor;

    @Before
    public void setUp(){
        mavenProcessor = mock(MavenProcessor.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }).when(mavenProcessor).push(any(DormWebServiceRequest.class));
        Injector injector = Guice.createInjector(new ArtifactoryTestModule(), new AbstractModule() {
            @Override
            protected void configure() {
                bind(MavenProcessor.class).toInstance(mavenProcessor);
            }
        });
        importer = injector.getInstance(ArtifactoryImporter.class);
    }

    @Test
    public void test(){
        importer.executeImport();
    }
}