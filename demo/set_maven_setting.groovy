import groovy.xml.XmlUtil
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder

def mavenHome = System.getenv()['M2_HOME'];

def cli = new CliBuilder(usage: 'set_maven_settings.groovy [options]')
cli.install('Configure the settings.xml file')
cli.delete('Delete and restore the original settings.xml')
cli.u(longOpt:'username', args:1, argName:'username', 'DORM username')
cli.pw(longOpt:'password', args:1, argName:'password', 'DORM password')
def options = cli.parse(args)

def file = new File(mavenHome + '/conf/settings.xml');
def backupFile = new File(mavenHome + '/conf/settings.xml.bak');

if (options.install) {
    def user = options.u
    def passwd = options.pw
    user == "" ? 'admin' : user
    passwd == "" ? 'password' : passwd

    backupFile << file.asWritable();

    def root = new XmlSlurper().parse(file);

    def servers = root.servers;
    def serverDORM = servers.server.id.find{ it.text() == 'DORM-Maven-integration-test' };

    if (serverDORM.text() == "") {
        servers.appendNode{
            server {
                id('DORM-Maven-integration-test')
	        username(user)
                password(passwd)
            }
        }
    }

    def outputBuilder = new StreamingMarkupBuilder();
    def defaultNamespace = root.namespaceURI()
    outputBuilder.bind{ 
        namespaces << ["" : defaultNamespace]
        mkp.yield root
    }.writeTo(file.newWriter());
} else if (options.delete){
    file.delete();
    file.createNewFile();
    file << backupFile.asWritable();
    backupFile.delete();
}
