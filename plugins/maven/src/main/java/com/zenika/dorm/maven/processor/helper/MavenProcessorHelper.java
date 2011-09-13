package com.zenika.dorm.maven.processor.helper;

import com.zenika.dorm.core.model.DependencyNode;
import com.zenika.dorm.maven.exception.MavenException;
import com.zenika.dorm.maven.model.MavenMetadata;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class MavenProcessorHelper {

    private MavenProcessorHelper() {
    }

    public static MavenMetadata getMavenMetadata(DependencyNode node) throws MavenException {
        try {
            return (MavenMetadata) node.getDependency().getMetadata().getExtension();
        } catch (NullPointerException e) {
            throw new MavenException("Node dosen't contains metadata.", e);
        } catch (ClassCastException e) {
            throw new MavenException("Node dosen't contains maven metadata.", e);
        }
    }
}
