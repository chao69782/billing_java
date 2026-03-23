FROM openjdk:21

# 设置工作目录
WORKDIR /app

# 复制jar文件到容器中
COPY target/*.jar app.jar

# 复制配置文件到容器中
COPY src/main/resources/application-prod.yml /app/config/application.yml

USER root
RUN mkdir -p /opt/upload && chmod 755 /opt/upload

# 暴露端口
EXPOSE 9527


# 运行应用程序，指定外部配置文件
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.yml"]