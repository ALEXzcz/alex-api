# 运行阶段基础镜像
FROM eclipse-temurin:21-jre

ARG APP_NAME=alex-api
ARG BUILD_TIME
ARG IMAGE_VERSION
ARG VCS_REF
ARG VCS_URL
ARG VCS_BRANCH

LABEL org.opencontainers.image.title="${APP_NAME}" \
      org.opencontainers.image.description="alex-api Spring Boot service" \
      org.opencontainers.image.version="${IMAGE_VERSION}" \
      org.opencontainers.image.created="${BUILD_TIME}" \
      org.opencontainers.image.revision="${VCS_REF}" \
      org.opencontainers.image.source="${VCS_URL}" \
      org.opencontainers.image.ref.name="${VCS_BRANCH}"

ENV APP_HOME=/app

# 安装健康检查工具并创建非 root 运行用户
RUN apt-get update \
    && apt-get install -y --no-install-recommends curl ca-certificates \
    && groupadd --system --gid 10001 app \
    && useradd --system --uid 10001 --gid app --home-dir ${APP_HOME} --shell /usr/sbin/nologin app \
    && mkdir -p ${APP_HOME}/logs \
    && chown -R app:app ${APP_HOME} \
    && rm -rf /var/lib/apt/lists/*

# 在容器里创建 app 目录并进入
WORKDIR ${APP_HOME}

# 复制 CI 阶段生成的 target/*.jar 到容器内
COPY --chown=app:app target/*.jar ./app.jar

# 开放容器端口
EXPOSE 8888

# 容器健康检查
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
    CMD curl -fsS http://127.0.0.1:8888/actuator/health || exit 1

# 切换为非 root 用户运行
USER app

# 启动命令
ENTRYPOINT ["java", "-jar", "./app.jar"]
