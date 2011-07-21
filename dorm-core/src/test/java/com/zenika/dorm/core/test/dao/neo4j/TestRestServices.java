package com.zenika.dorm.core.test.dao.neo4j;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class TestRestServices {


//    @Test
//    public void testPush() throws Exception {
//        DormDao test = new DormDaoNeo4j();
//        DormOrigin origin = new DefaultDormOrigin("maven");
//        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
//        Dependency dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
//        test.push(dependency);
//    }
//
//    @Test
//    public void testPushWithParentNoBatch() {
//        long time = System.currentTimeMillis();
//        DormDaoNeo4j test = new DormDaoNeo4j();
//        DormOrigin origin;
//        DormMetadata metadata;
//        Dependency dependency = null;
//        List<Dependency> dependencies = new ArrayList<Dependency>();
//        for (int i = 2; i < 100; i++) {
//            origin = new DefaultDormOrigin("maven" + i);
//            metadata = new DefaultDormMetadata("1.0.0", origin);
//            dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
//            dependencies.add(dependency);
//        }
//        for (int i = 0; i < dependencies.size(); i++) {
//            if (i == 0) {
//                test.pushNoBatch(dependencies.get(0));
//            } else {
//                test.pushWithParentNoBatch(dependencies.get(i), dependencies.get(i - 1));
//            }
//        }
//        System.out.println("Times to standard push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void testPushWithParent() {
//        long time = System.currentTimeMillis();
//        DormDao test = new DormDaoNeo4j();
//        DormOrigin origin;
//        DormMetadata metadata;
//        Dependency dependency = null;
//        List<Dependency> dependencies = new ArrayList<Dependency>();
//        for (int i = 200; i < 300; i++) {
//            origin = new DefaultDormOrigin("maven" + i);
//            metadata = new DefaultDormMetadata("1.0.0", origin);
//            dependency = new DefaultDependency(metadata, new Usage("DEFAULT"));
//            dependencies.add(dependency);
//        }
//        for (int i = 0; i < dependencies.size(); i++) {
//            if (i == 0) {
//                test.push(dependencies.get(0));
//            } else {
//                test.pushWithParent(dependencies.get(i), dependencies.get(i - 1));
//            }
//        }
//        System.out.println("Times to push : " + (System.currentTimeMillis() - time));
//    }
//
//    @Test
//    public void testGetDependencies() throws Exception {
//        DormDao test = new DormDaoNeo4j();
//        DormOrigin origin = new DefaultDormOrigin("maven2");
//        DormMetadata metadata = new DefaultDormMetadata("1.0.0", origin);
//        Usage usage = new Usage("DEFAULT");
//        DependencyNode dependencyNode = test.getByMetadata(metadata, usage);
//        System.out.println(dependencyNode.getDependency().getMetadata().getFullQualifier());
//        for (DependencyNode node : dependencyNode.getChildren()) {
//            System.out.println(node.getDependency().getMetadata().getFullQualifier());
//        }
//    }
}
