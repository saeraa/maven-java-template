FROM bitnami/wildfly:latest
COPY target/laborationsuppgift3-1.0-SNAPSHOT.war /app

EXPOSE 8080

ENTRYPOINT [ "/opt/bitnami/scripts/wildfly/entrypoint.sh" ]
CMD [ "/opt/bitnami/scripts/wildfly/run.sh" ]