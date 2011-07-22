package com.zenika.dorm.core.dao.neo4j;

import com.zenika.dorm.core.model.DormMetadataExtension;
import com.zenika.dorm.core.model.mapper.MetadataExtensionMapper;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * @author Antoine ROUAZE <antoine.rouaze AT zenika.com>
 */
@XmlRootElement
public class Neo4jOrigin implements DormMetadataExtension {

    private DormMetadataExtension extension;

    public Neo4jOrigin(DormMetadataExtension extension) {
        this.extension = extension;
    }

    @Override
    public String getQualifier() {
        return extension.getQualifier();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getExtension() {
        return extension.getExtension();  //To change body of implemented methods use File | Settings | File Templates.
    }

//
    @JsonProperty()
    public Map<String, String> getProperties(){
        return MetadataExtensionMapper.fromOrigin(extension);
    }


}
