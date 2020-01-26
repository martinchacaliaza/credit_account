FROM openjdk:8
VOLUME /tmp
EXPOSE 8003
ADD ./target/microservicios.mongodb.banco.credit_account-0.0.1-SNAPSHOT.jar cuentasCreditos.jar
ENTRYPOINT ["java","-jar","/cuentasCreditos.jar"]