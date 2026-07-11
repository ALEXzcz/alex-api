# 运行阶段基础镜像
FROM eclipse-temurin:21-jdk
# 在容器里创建 app 目录并进入
WORKDIR /app
# 在容器内创建 logs 目录并设置权限
RUN mkdir -p logs && chmod 777 logs
# 复制 CI 阶段生成的 target/*.jar 到容器内
COPY target/*.jar ./app.jar
# 开放容器端口
EXPOSE 8888
# 启动命令
ENTRYPOINT ["java", "-jar", "./app.jar"]