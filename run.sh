C:/apache-maven-3.9.9/bin/mvn clean package
cp target/lovely-project.war C:/apache-tomcat-8.5.34/webapps/
C:/apache-tomcat-8.5.34/bin/shutdown.sh
C:/apache-tomcat-8.5.34/bin/startup.sh 