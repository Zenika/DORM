//package com.zenika.dorm.core.test.model;
//
//import com.zenika.dorm.core.model.ws.DormWebServiceRequest;
//import com.zenika.dorm.core.test.unit.AbstractUnitTest;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.io.File;
//
///**
// * Unit tests for the dorm request
// *
// * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
// */
//@Ignore
//public class DormRequestUnitTest extends AbstractUnitTest {
//
//    /**
//     * Test the construction by copy
//     */
//    @Test
//    public void createRequestFromExistingOneWithValidParams() {
//
//        String newVersion = "2.0";
//        File newFile = new File("tmp/file-new.jar");
//
//        // get the default request as model for tests
//        DormWebServiceRequest request = new DormWebServiceRequest.Builder(fixtures.getVersion())
//                .file(fixtures.getFile())
//                .filename(fixtures.getFilenameWithExtension())
//                .build();
//
//        /*
//            TEST BY EDITING THE VERSION
//         */
//
////        DormWebServiceRequest requestNewVersionModel = new DormWebServiceRequest.Builder(newVersion)
////                .file(fixtures.getFile())
////                .filename(fixtures.getFilenameWithExtension())
////                .build();
////
////        // create new request with the new version
////        DormWebServiceRequest requestNewVersion = new DormWebServiceRequest.Builder(request)
////                .version(newVersion)
////                .build();
////
////        Assertions.assertThat(requestNewVersion).isEqualTo(requestNewVersionModel);
////        Assertions.assertThat(requestNewVersion.getVersion()).isEqualTo(newVersion);
//
//
//        /*
//            TEST BY EDITING THE FILE
//        */
//
////        DormWebServiceRequest requestNewFileModel = new DormWebServiceRequest.Builder(fixtures.getVersion())
////                .file(newFile)
////                .filename(fixtures.getFilenameWithExtension())
////                .build();
////
////        // create new request with the new file
////        DormWebServiceRequest requestNewFile = new DormWebServiceRequest.Builder(request)
////                .file(newFile)
////                .build();
////
////        Assertions.assertThat(requestNewFile).isEqualTo(requestNewFileModel);
////        Assertions.assertThat(requestNewFile.getFile()).isEqualTo(newFile);
//
//
//        /*
//            TEST BY EDITING THE VERSION AND THE FILE
//        */
//
////        DormWebServiceRequest requestNewVersionAndFileModel = new DormWebServiceRequest.Builder(newVersion)
////                .file(newFile)
////                .filename(fixtures.getFilenameWithExtension())
////                .build();
////
////        // create new request with both the new version and file
////        DormWebServiceRequest requestNewVersionAndFile = new DormWebServiceRequest.Builder(request)
////                .version(newVersion)
////                .file(newFile)
////                .build();
////
////        Assertions.assertThat(requestNewVersionAndFile).isEqualTo(requestNewVersionAndFileModel);
////        Assertions.assertThat(requestNewVersionAndFile.getFile()).isEqualTo(newFile);
////        Assertions.assertThat(requestNewVersionAndFile.getVersion()).isEqualTo(newVersion);
//    }
//}
