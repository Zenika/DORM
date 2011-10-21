package com.zenika.dorm.maven.test.client;

import org.apache.maven.wagon.Wagon;
import org.apache.maven.wagon.providers.http.LightweightHttpWagon;
import org.sonatype.aether.connector.wagon.WagonProvider;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public class MavenClientWagonProvider implements WagonProvider {

    public Wagon lookup(String roleHint) throws Exception {

        if ("http".equals(roleHint)) {
            return new LightweightHttpWagon();
        }

        return null;
    }

    public void release(Wagon wagon) {

    }
}