FROM amazoncorretto:17-alpine-jdk

# Adiciona curl
RUN apk add --no-cache curl

# Define a versão do Maven
ARG MAVEN_VERSION=3.8.3
ARG SHA=1c12a5df43421795054874fd54bb8b37d242949133b5bf6052a063a13a93f13a20e6e9dae2b3d85b9c7034ec977bbc2b6e7f66832182b9c863711d78bfe60faa
ARG MAVEN_HOME_DIR=usr/share/maven
ARG APP_DIR="app"
ARG BASE_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries

# Instala o Maven
RUN mkdir -p /$MAVEN_HOME_DIR /$MAVEN_HOME_DIR/ref \
  && echo "[ECHO] Downloading maven" \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /$MAVEN_HOME_DIR --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /$MAVEN_HOME_DIR/bin/mvn /usr/bin/mvn

# Define variáveis de ambiente
ENV MAVEN_CONFIG "/${APP_DIR}/.m2"
ENV APP_NAME ms-calcula-preco-tarifado
ENV TZ="America/Sao_Paulo"

# Copia o código fonte e o POM
COPY ./src ./$APP_DIR/src
COPY pom.xml ./$APP_DIR

# Define o diretório de trabalho
WORKDIR /$APP_DIR

# Build e package com o profile de testes
RUN mvn clean package -DskipTests

# Copia o JAR final
RUN mv target/$APP_NAME.jar .

# Remove Maven e arquivos desnecessários
RUN rm -rf /$APP_DIR/src \
  && rm -f /$APP_DIR/pom.xml \
  && rm -rf /$APP_DIR/target \
  && rm -rf $MAVEN_CONFIG \
  && rm -rf /$MAVEN_HOME_DIR \
  && apk del --no-cache curl

# Expõe a porta
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["java", "-jar", "ms-calcula-preco-tarifado.jar"]
