# alex-api GitHub Flow 说明

## 1. 分支模型

- 长期分支只保留 `main`
- 日常开发从 `main` 拉出短生命周期分支
- 推荐命名：
  - `feature/user-login`
  - `feature/order-api`
  - `fix/user-query`

旧的 `master`、`release`、`develop` 不再作为长期分支使用。

## 2. CI 规则

CI 脚本文件：[`D:\git\alex-api\.jenkins\ci.Jenkinsfile`](D:/git/alex-api/.jenkins/ci.Jenkinsfile)

- `main` 分支：
  - 执行 `mvn clean verify`
  - 生成 `jar`
  - 构建运行时镜像
  - 推送镜像到 ACR

- 非 `main` 短分支 / Pull Request：
  - 执行 `mvn clean verify`
  - 生成 `jar`
  - 构建运行时镜像做封装验证
  - 不推送镜像到 ACR

这样可以保证：

- 所有变更先通过分支校验
- 只有进入 `main` 的代码才产出正式可部署镜像

## 3. CD 规则

CD 脚本文件：[`D:\git\alex-api\.jenkins\deploy.Jenkinsfile`](D:/git/alex-api/.jenkins/deploy.Jenkinsfile)

- CD 只消费 `main` 分支 CI 产出的镜像
- 环境通过参数决定，不再通过长期分支决定
- 核心参数：
  - `DEPLOY_ENV`: `test` / `prod`
  - `IMAGE_TAG`: 要部署的镜像标签

发布顺序建议：

1. 开发分支合入 `main`
2. `main` 触发 CI，生成镜像标签
3. 用该镜像标签先部署 `test`
4. 测试通过后，再部署同一个镜像到 `prod`

## 4. Jenkins 配置建议

### CI

- 任务类型：`Multibranch Pipeline`
- 脚本路径：`.jenkins/ci.Jenkinsfile`
- 建议保留：
  - `main`
  - `feature/*`
  - `fix/*`

### CD

- 任务类型：`Pipeline`
- 定义：`Pipeline Script from SCM`
- 脚本分支：`*/main`
- 脚本路径：`.jenkins/deploy.Jenkinsfile`

## 5. 现在这套发布思路

现在的发布模型已经变成：

- GitHub Flow 管代码流转
- Jenkins CI 产出制品
- Jenkins CD 按参数部署环境
- 测试环境和生产环境使用同一份镜像制品
 - 不再额外提升 release tag，直接部署同一个 `IMAGE_TAG`

这比 `release -> 测试`、`master -> 生产` 的环境分支模式更符合现代持续交付的思路。
