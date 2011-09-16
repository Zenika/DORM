package com.zenika.dorm.core.test.dao.sql;

import com.google.inject.AbstractModule;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

/**
 * todo: fix from refactoring
 *
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
public class JdbcTestModule extends AbstractModule {

    @Override
    protected void configure() {
//        MapBinder<String, Class> metadataExtensionBinder = MapBinder.newMapBinder(binder(), String.class, Class.class);
//        metadataExtensionBinder.addBinding(DefaultDormMetadataExtension.EXTENSION_NAME).toInstance(DefaultDormMetadataExtension.class);
        bind(DataSource.class).toInstance(createDataSource());
//        bind(DormMetadataFactory.class);
    }


    private static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("DORM_DATA");
        dataSource.setPortNumber(5555);
        dataSource.setUser("dorm");
        dataSource.setPassword("admin");
        return dataSource;
    }
}