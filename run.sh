export CATALINA_HOME=/c/apache-tomcat-8.5.34
"C:/apache-maven-3.9.9/bin/mvn" clean package
cp target/lovely-project.war "$CATALINA_HOME/webapps/"
"$CATALINA_HOME/bin/shutdown.sh"
"$CATALINA_HOME/bin/startup.sh"