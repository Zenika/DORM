package com.zenika.dorm.core.service.spi;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class ModuleServiceExperimentation {
//
//      private ServiceLoader<Dictionary> loader;
//
//    private URL[] urls = null;
//
//    public ModuleServiceExperimentation() {
//        try {
//            File test = new File("");
//            File[] files = test.listFiles();
//            urls = new URL[files.length];
//            for (int i = 0; i < files.length; i++){
//                urls[i] = files[i].toURI().toURL();
//            }
////            ClassLoader classLoader = new URLClassLoader(urls);
////            URL url = classLoader.getResource("ExtendedDictionary.jar");
////            System.out.println("Url: " + url);
//            ClassLoader classLoader = getClass().getClassLoader();
//            classLoader.loadClass("com.example.dictionary.ExtendedDictionary");
////            loader = ServiceLoader.load(Dictionary.class, classLoader);
//            loader = ServiceLoader.load(Dictionary.class);
//            System.out.println("Loader: " + loader);
//        } catch (MalformedURLException e) {
//            throw new CoreException("Error: ", e);
//        } catch (ClassNotFoundException e) {
//            throw new CoreException("Class not found: ", e);
//        }
//    }
//
//    public Dictionary getModuleResource(String extensionName){
//        for (Dictionary resource : loader){
//            if (resource.getType().equals(extensionName)){
//                return resource;
//            }
//        }
//        return null;
//    }
}
