package com.zenika.dorm.core.neo4j.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: erouan
 * Date: 7/8/11
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBTest {

    public static final String SERVER_ROOT_URI = "http://localhost:7474/db/data";
    private static GraphDatabaseService graphDb;
    private Transaction transaction;
    private URI firstNode;
    private URI secondNode;

    @BeforeClass
    public static void setUpClass() {
//        graphDb = new EmbeddedGraphDatabase("/home/erouan/Documents/test");
    }

    @Before
    public void setUp() {
//        transaction = graphDb.beginTx();
        firstNode = createNode();
        secondNode = createNode();
    }

    @After
    public void tearDown() throws InterruptedException, IOException {
        String request = DBTest.SERVER_ROOT_URI + "/node";
        ObjectMapper mapper = new ObjectMapper();
        int i = 0;
        List<URI> uris = new ArrayList<URI>();
        boolean isEnd = true;
        while (isEnd) {
            WebResource resource = Client.create().resource(request + "/" + i + "/relationships/all");
            ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            String responseReturn = response.getEntity(String.class);
            System.out.println("GET to " + request + "/" + i + "/relationships/all" + ", status code " + response.getStatus());
//            System.out.println("Response : " + responseReturn);
            if (response.getStatus() != 404 && !(responseReturn.length() < 4)) {
                responseReturn = responseReturn.substring(2, (responseReturn.length() - 2));
                Map<String, Object> userData = mapper.readValue(responseReturn, Map.class);
                if (userData.get("self") != null || !userData.get("self").toString().isEmpty()) {
                    resource = Client.create().resource(userData.get("self").toString());
                    response = resource.delete(ClientResponse.class);
                    System.out.println("DELETE to " + userData.get("self") + ", status code " + response.getStatus());
                }
            }
            resource = Client.create().resource(request + "/" + i + "/properties");
            response = resource.delete(ClientResponse.class);
            System.out.println("DELETE to " + request + "/" + i + "/properties, status code " + response.getStatus());
            resource = Client.create().resource(request + "/" + i);
            response = resource.delete(ClientResponse.class);
            System.out.println("DELETE to " + request + "/" + i + ", status code " + response.getStatus());
            isEnd = response.getStatus() != 404;
//            i++;
//            isEnd = false;
            Thread.sleep(50);
        }
    }

//    @Test
//    public void testCreateGraphBasicExample() {
//        try {
//
//            Node firstNode = graphDb.createNode();
//            Node secondNode = graphDb.createNode();
//            Relationship relationship = firstNode.createRelationshipTo(secondNode, NodeRelationShip.DEPEND);
//
//            firstNode.setProperty("message", "Hello, ");
//            secondNode.setProperty("message", "world!");
//            relationship.setProperty("message", "brave Neo4j");
//
//            System.out.println(firstNode.getProperty("message"));
//            System.out.println(relationship.getProperty("message"));
//            System.out.println(secondNode.getProperty("message"));
//
//            transaction.success();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testRemoteRestConnection() {
        Client client = Client.create();
        WebResource resource = client.resource(SERVER_ROOT_URI);
        ClientResponse response = resource.get(ClientResponse.class);

        System.out.println(String.format("GET on " + SERVER_ROOT_URI + ", status code " + response.getStatus()));

    }

//    @Test
//    public void testCreateRemoteNode() {
//        createNode();
//    }

    @Test
    public void testAddingRemoteProperties() {
        addProperties(firstNode, "name", "Joe Strummer");
        addProperties(secondNode, "band", "The Clash");
    }

    @Test
    public void testAddingRemoteRelationShip() throws URISyntaxException {
        try {
            URI relationshipURI = addRelationship(firstNode, secondNode, "singer", "{ \"from\" : \"1976\", \"until\" : \"1986\" }");
            addMetadataToProperty(relationshipURI, "stars", "5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URI createNode() {
        String nodeEntryPointUri = SERVER_ROOT_URI + "/node";
        WebResource resource = Client.create().resource(nodeEntryPointUri);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity("{}").post(ClientResponse.class);

        System.out.println("POST to " + nodeEntryPointUri + ", status code " + response.getStatus() + ", location header " + response.getLocation().toString());
        return response.getLocation();
    }

    private void addProperties(URI node, String propertyName, String propertyValue) {
        String propertyUri = node.toString() + "/properties/" + propertyName;

        WebResource resource = Client.create().resource(propertyUri);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(toJsonStringLiteral(propertyValue)).put(ClientResponse.class);

        System.out.println("PUT to " + propertyUri + ", status code " + response.getStatus());
    }

    private String toJsonStringLiteral(String str) {
        return "\"" + str + "\"";
    }

    private String toJsonNameValuePairCollection(String name, String value) {
        return "{ \"" + name + "\" : \"" + value + "\" }";
    }

    private String generateJsonRelationship(URI endNode, String relationshipptType, String... jsonAttributes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ \"to\" : \"");
        stringBuilder.append(endNode.toString());
        stringBuilder.append("\", ");
        stringBuilder.append("\"type\" : \"");
        stringBuilder.append(relationshipptType);
        if (jsonAttributes == null || jsonAttributes.length < 1) {
            stringBuilder.append("\"");
        } else {
            stringBuilder.append("\", \"data\" : ");
            for (int i = 0; i < jsonAttributes.length; i++) {
                stringBuilder.append(jsonAttributes[i]);
                if (i < jsonAttributes.length - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    private void addMetadataToProperty(URI relationshipUri, String name, String value) throws URISyntaxException {
        URI propertyURI = new URI(relationshipUri.toString() + "/properties");
        WebResource resource = Client.create().resource(propertyURI);

        String entity = toJsonNameValuePairCollection(name, value);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(entity).put(ClientResponse.class);

        System.out.println("PUT " + entity + " to " + propertyURI + ", status code " + response.getStatus());
    }

    private URI addRelationship(URI startNode, URI endNode, String relationshipType, String jsonAttributes) throws URISyntaxException {
        URI fromUri = new URI(startNode.toString() + "/relationships");
        String relationshipJson = generateJsonRelationship(endNode, relationshipType, jsonAttributes);

        WebResource resource = Client.create().resource(fromUri);
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(relationshipJson).post(ClientResponse.class);

        final URI location = response.getLocation();
        System.out.println("POST to " + fromUri + ", status code " + response.getStatus() + ", location header " + location.toString());
        return location;
    }


}