package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.sound.midi.VoiceStatus;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement
public class Neo4jMetadataExtension extends Neo4jNode implements DormMetadataExtension {

    private String qualifier;
    private String extension;

    public Neo4jMetadataExtension(){

    }

    public Neo4jMetadataExtension(DormMetadataExtension extension) {
        this.extension = extension.getExtension();
        this.qualifier = extension.getQualifier();
    }

    @Override
    public String getQualifier() {
        return qualifier;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getExtension() {
        return extension;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Neo4jMetadataExtension{" +
                "qualifier='" + qualifier + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
