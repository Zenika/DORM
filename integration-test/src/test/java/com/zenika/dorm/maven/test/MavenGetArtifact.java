package com.zenika.dorm.maven.test;

import com.zenika.dorm.maven.test.grinder.GrinderResultToCSV;
import com.zenika.dorm.maven.test.grinder.GrinderStatistics;
import com.zenika.dorm.maven.test.model.MavenConfiguration;
import com.zenika.dorm.maven.test.model.MavenGetResource;
import com.zenika.dorm.maven.test.model.MavenResource;
import com.zenika.dorm.maven.test.model.MavenSample;
import com.zenika.dorm.maven.test.result.MavenGetResult;
import com.zenika.dorm.maven.test.utils.MavenUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static org.fest.assertions.Assertions.*;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class MavenGetArtifact {

    private static final Logger LOG = LoggerFactory.getLogger(MavenGetArtifact.class);

    private static final String JSON_FILE_PATH = "maven_get_request.json";

    private static DefaultHttpClient client;
    private static MavenConfiguration configuration;
    private static List<MavenResource> resources;

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
    public void getTest() {
        for (Iterator<MavenResource> iterator = resources.iterator(); iterator.hasNext();) {
            MavenGetResource resource = (MavenGetResource) iterator.next();
            resource.setBaseUri(baseUri);
            executeGetRequest(resource.getPomUri(), resource.getExpectedResult());
            executeGetRequest(resource.getPomSha1Uri(), resource.getExpectedResult());
            executeGetRequest(resource.getPomMd5Uri(), resource.getExpectedResult());
            executeGetRequest(resource.getJarUri(), resource.getExpectedResult());
            executeGetRequest(resource.getJarSha1Uri(), resource.getExpectedResult());
            executeGetRequest(resource.getJarMd5Uri(), resource.getExpectedResult());
        }
    }

    @Test
    public void test(){
        GrinderStatistics statistics = new GrinderStatistics();
        statistics.setConnectTime(3423L);
        statistics.setErrors(98707L);
        statistics.setFirstByteTime(9870865L);
        statistics.setResponseErrors(856965L);
        statistics.setResponseLength(8765876L);
        statistics.setResponseStatus(987698875L);
        statistics.setTimedTests(9875965L);
        GrinderResultToCSV.write(statistics, "/home/erouan/test.csv");
    }

    private MavenGetResult executeGetRequest(String path, MavenGetResult expectedResult) {
        try {
                LOG.info("|---------Test Get URL------------|");
                LOG.info("|URL: " + path);

            HttpGet get = new HttpGet(path);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            MavenGetResult result = new MavenGetResult(entity.getContent(), response.getStatusLine().getStatusCode(),
                    entity.getContentType().getValue(), entity.getContentLength());

            LOG.info("|Result : " + result);
            LOG.info("|Result expected: " + expectedResult);

            try {
                assertThat(result).isEqualTo(expectedResult);
            } catch (AssertionError e) {
                LOG.error("|Assert fail", e);
            }

            LOG.info("|---------Test END----------------|");
            get.abort();
        } catch (IOException e) {
            LOG.error("Unable to send request", e);
        }
        return null;
    }

}