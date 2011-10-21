package com.zenika.dorm.maven.test.processor.extension;


import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.maven.processor.extension.MavenProcessor;
import com.zenika.dorm.maven.test.fixtures.MavenFileFixtures;
import com.zenika.dorm.maven.test.unit.MavenUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the maven processor
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessorUnitTest extends MavenUnitTest {

    private MavenFileFixtures fileFixtures;

    @InjectMocks
    private MavenProcessor processor = new MavenProcessor();

    @Override
    public void before() {
        super.before();
        fileFixtures = new MavenFileFixtures();
    }

    @Test
    public void pushJar() {
        File file = fileFixtures.getJar();
        String uri = httpPathFixtures.getJar();
        processor.push(uri, file);
        // TODO
    }

    @Test
    public void pushJarSha1Hash() {
        File file = fileFixtures.getJarSha1();
        String uri = httpPathFixtures.getJarSha1Hash();
        processor.push(uri, file);
    }

    @Test
    public void pushJarMd5Hash() {
        File file = fileFixtures.getJarMd5();
        String uri = httpPathFixtures.getJarMd5Hash();
        processor.push(uri, file);
    }

    @Test
    public void pushPom() {
        File file = fileFixtures.getPom();
        String uri = httpPathFixtures.getPom();
        processor.push(uri, file);
    }

    @Test
    public void pushPomSha1Hash() {
        File file = fileFixtures.getPomSha1();
        String uri = httpPathFixtures.getPomSha1Hash();
        processor.push(uri, file);
    }

    @Test
    public void pushPomMd5Hash() {
        File file = fileFixtures.getPomMd5();
        String uri = httpPathFixtures.getPomMd5Hash();
        processor.push(uri, file);
    }

    @Test
    public void pushMavenMetadataFile() {

    }
}
