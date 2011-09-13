package com.zenika.dorm.maven.test;

import com.zenika.dorm.maven.test.model.MavenConfiguration;
import com.zenika.dorm.maven.test.model.MavenPutResource;
import com.zenika.dorm.maven.test.model.MavenResource;
import com.zenika.dorm.maven.test.model.MavenSample;
import com.zenika.dorm.maven.test.result.MavenPutResult;
import com.zenika.dorm.maven.test.utils.MavenUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static org.fest.assertions.Assertions.*;
/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenPutArtifact {

    private static final Logger LOG = LoggerFactory.getLogger(MavenPutArtifact.class);

    private static String JSON_FILE_PATH = "maven_put_request.json";

    private static MavenConfiguration configuration;
    private static List<MavenResource> resources;
    private static DefaultHttpClient client;

    private static String baseUri;

    @BeforeClass
    public static void beforeClass() {
        MavenSample sample = MavenUtils.getSample(JSON_FILE_PATH);
        configuration = sample.getConfiguration();
        resources = sample.getResources();
        client = new DefaultHttpClient();
        if (configuration.isProxyActive()) {
            HttpHost proxyHost = new HttpHost(configuration.getProxyHostname(), configuration.getProxyPort());
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
        }
        baseUri = MavenUtils.buildBaseUri(configuration);
    }

    @Test
    public void putTest() {
        for (Iterator<MavenResource> iterator = resources.iterator(); iterator.hasNext(); ) {
            MavenPutResource resource = (MavenPutResource) iterator.next();
            resource.setBaseUri(baseUri);
            executePutRequest(resource.getPomUri(), resource);
            executePutRequest(resource.getPomSha1Uri(), resource);
            executePutRequest(resource.getPomMd5Uri(), resource);
            executePutRequest(resource.getJarUri(), resource);
            executePutRequest(resource.getJarSha1Uri(), resource);
            executePutRequest(resource.getJarMd5Uri(), resource);
        }
    }

    @Test
    public void test(){
        MavenSample sample = MavenUtils.getSample(JSON_FILE_PATH);
        LOG.info("Sample: " + MavenUtils.buildBaseUri(sample.getConfiguration()));
    }

    private MavenPutResult executePutRequest(String path, MavenPutResource resource) {
        try {
            LOG.info("|---------Test Get URL------------|");
            LOG.info("|URL: " + path);

            LOG.info("|File path: " + resource.getFilePath());

            URL url = getClass().getResource(resource.getFilePath());
            File file = new File(url.toURI());
            HttpEntity entity = new FileEntity(file, "application/java");
            HttpPut put = new HttpPut(path);
            put.setEntity(entity);
            HttpResponse response = client.execute(put);
            MavenPutResult result = new MavenPutResult(response.getStatusLine().getStatusCode());
            MavenPutResult resultExpected = resource.getExpectedResult();
            
            LOG.info("|Result: " + result);
            LOG.info("|Result expected: " + resultExpected);
            
            try {
                assertThat(result).isEqualTo(resultExpected);
            } catch (AssertionError e){
                LOG.error("|Assert fail", e);
            }
            put.abort();
            return result;
        } catch (IOException e) {
            LOG.info("Unable to send request");
        } catch (URISyntaxException e) {
            LOG.info("URI syntax exception", e);
        }
        return null;
    }
}
