REM java and maven must be installed!
mvn clean install && mvn spring-boot:run -pl module-app -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"