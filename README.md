# avbravoadmintemplate
avbravoadmintemplate usa adminfaces
Para crear un uberjar
java -jar payara-micro-5.2020.2.jar --deploy avbravoadmintemplate.war --outputUberJar avbravoadmintemplate.jar

#Crear  el Uberjar
java -jar    /home/avbravo/software/payara/payara-micro-5.2020.2.jar --deploy /home/avbravo/NetBeansProjects/adminfaces/avbravoadmintemplate/target/avbravoadmintemplate.war --outputUberJar /home/avbravo/Descargas/avbravoadmintemplate.jar


#Ejecutar el war

java -jar -Xmx512m /home/avbravo/software/payara/payara-micro-5.2020.2.jar  --deploy /home/avbravo/NetBeansProjects/adminfaces/avbravoadmintemplate/target/avbravoadmintemplate.war --nocluster --logo --port 8081

