FROM openjdk:11
MAINTAINER Leonardo Delmiro
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV ANALYSIS_API=http://analise:9999/api/solicitacao
ENV CARD_API=http://contas:8888/api/cartoes
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
