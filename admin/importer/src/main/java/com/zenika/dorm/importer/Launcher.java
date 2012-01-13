package com.zenika.dorm.importer;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.zenika.dorm.core.exception.CoreException;
import com.zenika.dorm.core.guice.module.DormCoreModule;
import com.zenika.dorm.core.guice.module.DormRepositoryConfigurationModule;
import com.zenika.dorm.core.repository.DormRepository;
import com.zenika.dorm.core.service.DormService;
import com.zenika.dorm.core.service.ImportDormService;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class Launcher {

    private static final String MAVEN_REPOSITORY_TYPE = "maven";
    private static final String NUXEO_REPOSITORY_TYPE = "nexus";
    private static final String ARCHIVA_REPOSITORY_TYPE = "archiva";
    private static final String ARTIFACTORY_REPOSITORY_TYPE = "artifactory";
    private static final String IVY_REPOSITORY_TYPE = "ivy";

    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    @Option(name = "-p", usage = "The repository path", required = true, aliases = {"--path"})
    private String repositoryPath;
    @Option(name = "-t", usage = "The type of repository", aliases = {"type"})
    private String repositoryType = MAVEN_REPOSITORY_TYPE;
    @Option(name = "-r", usage = "The DORM repository target", aliases = {"--repoName"})
    private String repositoryTargetName;

    public static void main(String[] args) {
        new Launcher().run(args);
    }

    private void run(String[] args) {
        CmdLineParser cmdLineParser = new CmdLineParser(this);
        try {
            cmdLineParser.parseArgument(args);
            Set<Module> modules = initModule();
            Class<? extends RepositoryImporter> importerClass;
            if (repositoryType.equals(MAVEN_REPOSITORY_TYPE)) {
                modules.add(new ImporterMavenModule());
                importerClass = MavenRepositoryImporter.class;
            } else if (repositoryType.equals(NUXEO_REPOSITORY_TYPE)) {
                modules.add(new ImporterMavenModule());
                importerClass = MavenRepositoryImporter.class;
            } else if (repositoryType.equals(ARCHIVA_REPOSITORY_TYPE)){
                modules.add(new ImporterMavenModule());
                importerClass = MavenRepositoryImporter.class;
            } else if (repositoryType.equals(ARTIFACTORY_REPOSITORY_TYPE)) {
                modules.add(new ImporterMavenModule());
                importerClass = MavenRepositoryImporter.class;
            } else if (repositoryType.equals(IVY_REPOSITORY_TYPE)) {
                modules.add(new ImporterIvyModule());
                importerClass = IvyRepositoryImporter.class;
            } else {
                throw new CmdLineException("Unrecognized type of repository. Only this repositories are supported: \n\tMaven, Nuxeo, Archiva, Artifactory, Ivy");
            }
            modules.add(new AbstractModule() {
                @Override
                protected void configure() {
                    ImportConfiguration configuration = new ImportConfiguration();
                    configuration.setBasePath(repositoryPath);
                    configuration.setRepositoryName(repositoryTargetName);
                    bind(ImportConfiguration.class).toInstance(configuration);
                    
                }
            });
            modules.add(Modules.override(new DormCoreModule()).with(new Module() {
                @Override
                public void configure(Binder binder) {
                    binder.bind(DormService.class).to(ImportDormService.class);
                }
            }));
            RepositoryImporter importer = Guice.createInjector(modules).getInstance(importerClass);
            importer.startImport();
            LOG.info("{} artifacts are import to the DORM server in {}", importer.getNumberOfImport(), importer.getTime());
        } catch (CmdLineException e) {
            LOG.error(e.getMessage());
            LOG.error("java -jar ******.jar [options...] arguments...");
            cmdLineParser.printUsage(System.out);
        }
    }

    // TODO: Refactor the Core to implement the modules initialisation
    private Set<Module> initModule() {
        Set<Module> modules = new HashSet<Module>();
        addRepositoryModule(modules);
//        modules.add(new DormCoreModule());
        addDAOModule(modules);
        return modules;
    }


    private void addRepositoryModule(Set<Module> modules) {
        Properties properties = new Properties();
        String repositoryClassStr = null;
        try {
            properties.load(this.getClass().getResourceAsStream("repository.properties"));
            repositoryClassStr = properties.getProperty("repository.class");
            Class<? extends DormRepository> repositoryClass = (Class<? extends DormRepository>) Class.forName(repositoryClassStr);
            String configFilePath = properties.getProperty("repository.config.file");
            modules.add(new DormRepositoryConfigurationModule(repositoryClass, configFilePath));
        } catch (IOException e) {
            throw new CoreException("Unable to read this properties file: repository.properties", e);
        } catch (ClassNotFoundException e) {
            throw new CoreException("Unable to find this class: " + repositoryClassStr, e);
        }
    }

    private void addDAOModule(Set<Module> modules) {
        try {
            Class<AbstractModule> guiceDAOClass = getDAOModuleClass();
            if (guiceDAOClass == null) {
                throw new CoreException("Can't load the Guice DAO module.");
            }
            modules.add(guiceDAOClass.newInstance());
        } catch (IOException ioe) {
            throw new CoreException(ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new CoreException(cnfe);
        } catch (InstantiationException ie) {
            throw new CoreException(ie);
        } catch (IllegalAccessException iae) {
            throw new CoreException(iae);
        }
    }

    private Class<AbstractModule> getDAOModuleClass() throws IOException, ClassNotFoundException {

        String daoClassStr = System.getProperty("dao.class");
        if (daoClassStr == null) {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("dao.properties"));
            daoClassStr = String.valueOf(properties.get("dao.class"));
        }

        if (daoClassStr != null) {
            Class<?> daoClass = Class.forName(daoClassStr);
            if (AbstractModule.class.isAssignableFrom(daoClass)) {
                return (Class<AbstractModule>) daoClass;
            }
        }

        return null;
    }
}
