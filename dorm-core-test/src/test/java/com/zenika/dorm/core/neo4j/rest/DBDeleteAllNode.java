package com.zenika.dorm.core.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/11/11
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBDeleteAllNode {
//
//    @Test
//    public void deleteAllNode() throws IOException, InterruptedException {
//        String request = DBTest.SERVER_ROOT_URI + "/node";
//        ObjectMapper mapper = new ObjectMapper();
//        int i = 0;
//        List<URI> uris = new ArrayList<URI>();
//        boolean isEnd = true;
//        while (i < 200) {
//            WebResource resource = Client.create().resource(request + "/" + i + "/relationships/all");
//            ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//            String responseReturn = response.getEntity(String.class);
//            System.out.println("GET to " + request + "/" + i + "/relationships/all" + ", status code " + response.getStatus());
////            System.out.println("Response : " + responseReturn);
//            if (response.getStatus() != 404 && !(responseReturn.length() < 4)) {
//                responseReturn = responseReturn.substring(2, (responseReturn.length() - 2));
//                Map<String, Object> userData = mapper.readValue(responseReturn, Map.class);
//                if (userData.get("self") != null || !userData.get("self").toString().isEmpty()) {
//                    resource = Client.create().resource(userData.get("self").toString());
//                    response = resource.delete(ClientResponse.class);
//                    System.out.println("DELETE to " + userData.get("self") + ", status code " + response.getStatus());
//                }
//            }
//            resource = Client.create().resource(request + "/" + i + "/properties");
//            response = resource.delete(ClientResponse.class);
//            System.out.println("DELETE to " + request + "/" + i + "/properties, status code " + response.getStatus());
//            resource = Client.create().resource(request + "/" + i);
//            response = resource.delete(ClientResponse.class);
//            System.out.println("DELETE to " + request + "/" + i + ", status code " + response.getStatus());
//            isEnd = response.getStatus() != 404;
//            i++;
////            isEnd = false;
//            Thread.sleep(50);
//        }
//    }
}
