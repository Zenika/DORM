import groovy.xml.XmlUtil
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder

def mavenHome = System.getenv()['M2_HOME'];
def file = new File(mavenHome + '/conf/settings.xml');
def backupFile = new File(mavenHome + '/conf/settings.xml.bak');

if (args[0] == "install") {

    backupFile << file.asWritable();

    def root = new XmlSlurper().parse(file);

    def servers = root.servers;
    def serverDORM = servers.server.id.find{ it.text() == 'DORM-Maven-integration-test' };

    if (serverDORM.text() == "") {
        servers.appendNode{
            server {
                id('DORM-Maven-integration-test')
	        username('admin')
                password('password')
            }
        }
    }

    def outputBuilder = new StreamingMarkupBuilder();
    def defaultNamespace = root.namespaceURI()
    outputBuilder.bind{ 
        namespaces << ["" : defaultNamespace]
        mkp.yield root
    }.writeTo(file.newWriter());
} else if (args[0] == "delete"){
    file.delete();
    file.createNewFile();
    file << backupFile.asWritable();
    backupFile.delete();
}
