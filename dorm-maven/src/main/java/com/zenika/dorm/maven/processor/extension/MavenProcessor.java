package com.zenika.dorm.maven.processor.extension;

import com.google.inject.Inject;
import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
import com.zenika.dorm.core.model.ws.DormWebServiceResult;
import com.zenika.dorm.core.model.ws.builder.DormWebServiceResultBuilder;
import com.zenika.dorm.core.processor.ProcessorExtension;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.helper.MavenFormatHelper;
import com.zenika.dorm.maven.model.impl.MavenMetadataExtension;
import com.zenika.dorm.maven.service.MavenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The maven processor needs to create an abstract dependency node which will be the parent of the
 * following maven nodes : maven pom node, maven jar node, maven sha1 node, etc...
 * The only difference between theses nodes is the file and his type : pom.xml, jar, etc...
 * <p/>
 * See : https://docs.google.com/drawings/d/1N1epmWY3dUy7th-VwrSNk1HXf6srEi0RoUoETQbe8qM/edit?hl=fr
 * <p/>
 * snapshot upload -
 *
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenProcessor extends ProcessorExtension {

    private static final Logger LOG = LoggerFactory.getLogger(MavenProcessor.class);

    @Inject
    private MavenService mavenService;

    /**
     * Process:
     * 1. Try to get maven artifact corresponding to the url identifier
     * 2. If yes, override file only
     * 3. If no, add file
     * 4. In both case check if maven deploy is complete. It means :
     * - presence of
     * -- jar
     * -- jar.md5
     * -- jar.sha1
     * -- pom
     * -- pom.md5
     * -- pom.sha1
     * -- maven-metadata.xml
     *
     * @param request
     * @return
     */
    @Override
    public DormWebServiceResult push(DormWebServiceRequest request) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven webservice get request : " + request);
        }

        if (!request.hasFile()) {
            throw new MavenException("File is required");
        }

        String url = MavenFormatHelper.formatUrl(request.getProperty("path"), request.getFilename());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Maven url : " + url);
        }

        MavenMetadataExtension mavenMetadata = mavenService.getMetadataByUrl(url);

        if (null == mavenMetadata) {
            mavenService.storeMavenFile(url, request.getFile());
            return new DormWebServiceResultBuilder(MavenMetadataExtension.EXTENSION_NAME)
                    .succeeded()
                    .build();
        }

        return new DormWebServiceResultBuilder(MavenMetadataExtension.EXTENSION_NAME)
                .failed()
                .build();


//        MavenMetadataExtension mavenMetadata = new MavenMetadataExtension(url);
//
//
//        DormMetadata metadata = DefaultDormMetadata.create(null, mavenMetadata);
//
//        // try to get from database
//        DormServiceGetMetadataValues values = new DormServiceGetMetadataValues(metadata);
//        DormServiceGetResult getMetadataResult = service.getMetadata(values);
//
//        if (getMetadataResult.hasResult()) {
//
//            Dependency dependencyFromDatabase = getMetadataResult.getUniqueNode().getDependency();
//            MavenMetadataExtension mavenMetadataFromDatabase = (MavenMetadataExtension) dependencyFromDatabase
//                    .getMetadata().getExtension();
//
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Maven metadata from dorm database : " + mavenMetadataFromDatabase);
//            }
//
//            if (mavenMetadataFromDatabase.isComplete()) {
//
//                // override previous file only
//                DormResource resource = DefaultDormResource.create(request.getFile());
//                DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
//                        .metadata(dependencyFromDatabase.getMetadata())
//                        .override(true);
//
//                service.storeResource(resource, config);
//                return new DormWebServiceResultBuilder(MavenMetadataExtension.EXTENSION_NAME)
//                        .succeeded()
//                        .build();
//            } else {
//                LOG.error("Maven metadata in dorm database is not complete : " + mavenMetadataFromDatabase);
//                return new DormWebServiceResultBuilder(MavenMetadataExtension.EXTENSION_NAME)
//                        .failed()
//                        .build();
//            }
//        }
//
//        // no result
//        else {
//            LOG.trace("Store maven file as it comes");
//            DormResource resource = DefaultDormResource.create(request.getFile());
//            DormServiceStoreResourceConfig config = new DormServiceStoreResourceConfig()
//                    .resourcePath(url, MavenMetadataExtension.EXTENSION_NAME);
//            service.storeResource(resource, config);
//
//            if (mavenDeployIsComplete()) {
//                convertMavenToDorm();
//            }
//
//            return new DormWebServiceResultBuilder(MavenMetadataExtension.EXTENSION_NAME)
//                    .succeeded()
//                    .build();
//        }
    }

    private boolean mavenDeployIsComplete() {

        return false;
    }

    private void convertMavenToDorm() {

    }

    @Override
    public DormWebServiceResult get(DormWebServiceRequest request) {
        return null;
    }

//    @Override
//    public DormServiceGetRequest buildGetRequest(DormWebServiceRequest request) {
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Build maven get request from webservice request : " + request);
//        }
//
//        MavenMetadataExtension mavenMetadata = null;// MavenMetadataBuilder(request).build();
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Maven metadata : " + mavenMetadata);
//        }
//
//        DormServiceGetRequest getRequest;
//
//        if (mavenMetadata.isMavenMetadata()) {
//            LOG.trace("Maven metadata is maven-metadata.xml file");
//            getRequest = new MavenServiceGetRequestBuilder(PROCESS_GET_METADATAXML_FILE, mavenMetadata)
//                    .withArtifactId()
//                    .withGroupId()
//                    .withVersion()
//                    .repositoryRequest(false)
//                    .build();
//        } else {
//            LOG.trace("Maven metadata is representation of an maven artifact");
//            getRequest = new MavenServiceGetRequestBuilder(PROCESS_GET_ARTIFACT, mavenMetadata)
//                    .withAll()
//                    .repositoryRequest(true)
//                    .build();
//        }
//
//        if (LOG.isInfoEnabled()) {
//            LOG.info("Maven get request : " + getRequest);
//        }
//
//        return getRequest;
//    }
//
//    @Override
//    public Dependency buildDependency(DormServiceGetResult result) {
//
//        if (!result.hasResult()) {
//            throw new DormProcessException("No result").type(DormProcessException.Type.NULL);
//        }
//
//        Dependency dependency;
//        if (StringUtils.equals(result.getProcessName(), PROCESS_GET_METADATAXML_FILE)) {
//            dependency = buildMavenMetadataFile(result);
//        } else if (StringUtils.equals(result.getProcessName(), PROCESS_GET_ARTIFACT)) {
//            dependency = result.getUniqueNode().getDependency();
//        } else {
//            throw new MavenException("Cannot find builder for maven process : " + result.getProcessName());
//        }
//
//        return dependency;
//    }
//
//    private Dependency buildMavenMetadataFile(DormServiceGetResult result) {
//
//        Collections.sort(result.getNodes(), new Comparator<DependencyNode>() {
//
//            @Override
//            public int compare(DependencyNode node1, DependencyNode node2) {
//
//                MavenMetadataExtension extension1 = MavenProcessorHelper.getMavenMetadata(node1);
//                MavenMetadataExtension extension2 = MavenProcessorHelper.getMavenMetadata(node2);
//
//                return new MavenSnapshotTimestampComparator().compare(extension1, extension2);
//            }
//        });
//
//        File folders = new File("tmp/maven-tmp");
//        folders.mkdirs();
//
//        File mavenMetadataFile = new File(folders, "maven-metadata.xml");
//        MavenMetadataFileWriter writer = new MavenMetadataFileWriter(mavenMetadataFile);
//
//        for (DependencyNode node : result.getNodes()) {
//            MavenMetadataExtension metadata = MavenProcessorHelper.getMavenMetadata(node);
//            writer.write(metadata);
//        }
//
//        DormResource resource = DefaultDormResource.create(MavenConstant.Special.MAVEN_METADATA_XML,
//                mavenMetadataFile);
//
//        return DefaultDependency.create(result.getNodes().get(0).getDependency().getMetadata(), resource);
//    }
//
//    @Override
//    public DormServicePutRequest buildPutRequest(DormWebServiceRequest request) {
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Maven webservice put request : " + request);
//        }
//
//        if (!request.hasFile()) {
//            throw new MavenException("File is required.");
//        }
//
//        DormResource resource = DefaultDormResource.create(request.getFilename(), request.getFile());
//
//        DormServicePutRequest putRequest = new DormServicePutRequest(PROCESS_PUT_ARTIFACT, resource);
//        putRequest.setDatabaseRequest(false);
//        putRequest.setRepositoryRequest(true);
//
//        DormServicePutValues values = new DormServicePutValues(MavenMetadataExtension.EXTENSION_NAME);
//        values.setResourcePath(request.getProperty("path"));
//        values.setResourceInternal(true);
//        putRequest.setValues(values);
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("Maven service put request to store artifact as this without any logic : " +
//                    putRequest);
//        }
//
//        return putRequest;
//
////        MavenMetadataExtension extension = new MavenMetadataBuilder(request).build();
////
////        String type = extension.getExtension();
////
////        Dependency dependency = new DependencyBuilderFromRequest(request, type, extension)
////                .build();
////
////        if (LOG.isDebugEnabled()) {
////            LOG.debug("Maven real dependency = " + dependency);
////        }
////
////        DependencyNode node = DefaultDependencyNode.create(dependency);
////
////        DefaultDormServicePutRequest putRequest = new DefaultDormServicePutRequest(PROCESS_PUT_ARTIFACT);
////        putRequest.setNode(node);
//    }
}