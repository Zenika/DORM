package com.zenika.dorm.ivy;

import org.apache.ivy.core.cache.DefaultRepositoryCacheManager;
import org.apache.ivy.core.cache.DefaultResolutionCacheManager;
import org.apache.ivy.core.event.EventManager;
import org.apache.ivy.core.module.descriptor.*;
import org.apache.ivy.core.module.id.ModuleId;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.resolve.ResolveEngine;
import org.apache.ivy.core.retrieve.RetrieveEngine;
import org.apache.ivy.core.retrieve.RetrieveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.core.sort.SortEngine;

import java.io.File;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class RepoResolutionTest {

    private File base = new File("tests");
    private File fileA;
    private File fileB;

    private DefaultArtifact artifactA;
    private DefaultArtifact artifactB;

    private DefaultDependencyDescriptor dependencyDescriptorA;
    private DefaultDependencyDescriptor dependencyDescriptorB;
    private DefaultDependencyArtifactDescriptor dependencyArtifactDescriptorA;

    private DefaultModuleDescriptor mdrA;
    private DefaultModuleDescriptor mdrB;
    private DefaultModuleDescriptor moduleDescriptor;


    private ModuleRevisionId moduleRevisionId;
    private ModuleId moduleId;

    public void before() throws Exception {
        Helper.deleteDirectory(base);

        // create local repo
        File repo = new File(base, "repo");
        repo.mkdirs();
        System.out.println("repo directory is : " + repo.getAbsolutePath());


        File repA = new File(repo, "org.zenika/foo/1.0.0");
        repA.mkdirs();
        File repB = new File(repo, "org.zenika/bar/1.0.0");
        repB.mkdirs();

        fileA = new File(repA, "foo-1.0.0.jar");
        fileA.createNewFile();

        fileB = new File(repB, "bar-1.0.0.jar");
        fileB.createNewFile();
    }

    public void after() throws Exception {

    }

    /**
     * What should be done here :
     * - Create locale repo
     * - Transform A and B jars as ivy artifacts (resolve ?)
     * - Publish these ivy artifacts to the repository
     */
    public void workflow1() throws Exception {

        // create project's module
        createProjectModule();

        // add dependencies
        addDependenciesModules();

        //
        retreiveDependencies();

        // resolve xml
    }

    private void addDependenciesModules() {

        ModuleRevisionId mridFoo = ModuleRevisionId.newInstance("org.zenika", "foo", "1.0.0");
        DefaultModuleDescriptor mdFoo = DefaultModuleDescriptor.newDefaultInstance(mridFoo);
        DefaultDependencyDescriptor ddA = new DefaultDependencyDescriptor(mdFoo, mridFoo, false, false,
                true);

        moduleDescriptor.addDependency(ddA);
    }

    private void createProjectModule() {

        moduleId = new ModuleId("com.zenika", "foomodule");
        moduleRevisionId = new ModuleRevisionId(moduleId, "1.0.0");
        moduleDescriptor = DefaultModuleDescriptor.newDefaultInstance(moduleRevisionId);

//        artifactA = new DefaultArtifact(moduleRevisionId, new Date(), "foo1", "jar", "jar");
//        artifactB = new DefaultArtifact(moduleRevisionId, new Date(), "foo2", "jar", "jar");


//        moduleDescriptor.addArtifact(ModuleDescriptor.DEFAULT_CONFIGURATION, artifactA);
//        moduleDescriptor.addArtifact(ModuleDescriptor.DEFAULT_CONFIGURATION, artifactB);


    }

    private void retreiveDependencies() throws Exception {

        File ivyFile = new File(base, "module/ivy.xml");

        try {
            moduleDescriptor.toIvyFile(ivyFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        IvySettings settings = new IvySettings();
        ((DefaultRepositoryCacheManager) settings.getDefaultRepositoryCacheManager())
                .setBasedir(new File("/CACHE"));
//                .setArtifactPattern(settings.substitute("TEZT/[module]/[originalname].[ext]"));

        RetrieveEngine retrieveEngine = new RetrieveEngine(settings, new EventManager());
        RetrieveOptions retrieveOptions = new RetrieveOptions();
        retrieveOptions.setDestArtifactPattern(base.getAbsolutePath() +
                "/retrieve/[module]/[conf]/[artifact]-[revision].[ext]");

        File cacheBaseDir = new File(base, "cache");

        DefaultResolutionCacheManager resolutionCacheManager = new DefaultResolutionCacheManager(cacheBaseDir);
        settings.setResolutionCacheManager(resolutionCacheManager);

        ResolveEngine resolveEngine = new ResolveEngine(settings, new EventManager(),
                new SortEngine(settings));

        resolveEngine.resolve(ivyFile);

        retrieveEngine.retrieve(moduleDescriptor.getModuleRevisionId(), retrieveOptions);
    }

    private void describeModule() {
        System.out.println("Publications in module :");
        for (Artifact artifact : moduleDescriptor.getArtifacts(ModuleDescriptor.DEFAULT_CONFIGURATION)) {
            System.out.println(artifact.getName() + " ; " + artifact.getType() + " ; " + artifact.getExt());
        }

        System.out.println("Dependencies in module :");
        for (DependencyDescriptor descriptor : moduleDescriptor.getDependencies()) {
            System.out.println("MODULE DEP = " + descriptor.getDependencyId().getName() + " ; " +
                    descriptor
                            .getDependencyId().getName());

            for (DependencyArtifactDescriptor artifactDescriptor : descriptor.getAllDependencyArtifacts()) {
                System.out.println("ARTIFACT DEP = " + artifactDescriptor.getName() + " ; " +
                        artifactDescriptor.getType() + " ; " + artifactDescriptor.getExt());
            }
        }
    }

    public static void main(String[] args) throws Exception {

        RepoResolutionTest r = new RepoResolutionTest();
        r.before();
        r.workflow1();
        r.after();
    }
}
