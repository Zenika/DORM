DORM ECR DAO

Request the dao using the REST api.

Store :
curl -H Content-Type:application/xml -X PUT --user admin:admin --data "<dormMetadata><qualifier>thequalifier</qualifier><extensionName>maven</extensionName><version>1.0</version><properties><entry><key>groupId</key><value>org.foo</value></entry><entry><key>artifactId</key><value>bar</value></entry></properties></dormMetadata>" http://localhost:8090/ecr/dorm/save

Get :
curl -X GET --user admin:admin http://localhost:8090/ecr/dorm/get/thequalifier