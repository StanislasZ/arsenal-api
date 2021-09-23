FROM csbase.registry.cmbchina.cn/paas/cmb-oraclejdk-1.8.0:latest
WORKDIR /opt/deployments
COPY ./target/mortgage-info-api.jar /opt/deployments/
RUN unzip -q mortgage-info-api.jar && rm -rf mortgage-info-api.jar
EXPOSE 8080
CMD java ${JAVA_OPTS} -cp /opt/deployments/. org.springframework.boot.loader.JarLauncher
