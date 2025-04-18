# 抖一抖去水印使用说明



平台解析内置下载代理，一键下载更方便，拒绝繁琐的拒绝访问 403 代码等

海外平台解析内置HTTP代理，无需梯子下载，可更换



❗ [**后端（Java服务端）**](https://github.com/Tuteri/douyidou-java)

❗ [**后台管理**](https://github.com/Tuteri/douyidou-admin)

❗ [**微信小程序**](https://github.com/Tuteri/douyidou-wx-routine)

## 平台支持说明 （20250418）

| 平台名称     | 标准版 | Pro版     | 解析内容类型             |
| ------------ | ---------- | :------------ | ------------------------ |
| 抖音         | ✅          | ✅（支持主页） | 全部                     |
| 快手         | ✅          | ✅（支持主页） | 全部                     |
| 微博         | ✅          | ✅             | 全部 |
| 哔哩哔哩     | ✅          | ✅            | 全部                     |
| 小红书       | ✅          | ✅             | 全部                     |
| 西瓜视频     | ✅          | ✅             | 视频                     |
| 好看视频   | ✅          | ✅| 视频                     |
| Acfun        | ✅          | ✅             | 视频                     |
| 新片场       | ✅          | ✅             | 视频                 |
| 汽水音乐     | ✅      | ✅             | 视频，歌曲         |
| TikTok       | ✅          | ✅             | 全部 |
| Twitter（X） | ✅          | ✅             | 视频、图文       |
| Youtube | ✅ | ✅ | 视频 |
| 更多平台（30+）持续更新中 | ✅ | ✅ | 视频、图文、音频等 |

## 其他功能说明

| 功能名称        | 标准版 | Pro版 | 功能说明                                     |
| --------------- | ------ | ----- | -------------------------------------------- |
| 视频OCR         | ❌      | ✅     | 提取视频中的文字                             |
| 图片去水印 (AI) | ❌      | ✅     | 图片ai一键去除水印，图片对比                 |
| 平台主页解析    | ❌      | ✅     | 抖音、快手，持续更新                         |
| 视频转码        | ✅      | ✅     | m3u8转mp4                                    |
| 视频md5修改     | ✅      | ✅     | 修改文件md5唯一值                            |
| 视频音频提取    | ✅      | ✅     | 分离视频中的音频                             |
| 更多功能        | ❌      | ✅     | 证件照，图片修复，老照片修复，音频文字识别等 |

**支持流量主广告，自定义广告id，广告间隔时长等，功能丰富**

## Pro版本搭建、解析接口

**解析接口限时优惠(2025.4.15-2025.5.31)，永久使用不限次数包更新**

QQ 2462774332、89236692

## 扫码体验

<img src="http://imgurl.diadi.cn/imgs/2025/03/a9e0576401f9045d.png" style="zoom:50%;" />

## 开发说明

### 小程序端

- 微信原生开发
- [TDesign组件库](https://tdesign.tencent.com/miniprogram/)

### 服务端（java）

- springboot3
- mysql
- redis

## web后台

- vue3+vite

## 部署说明

### 服务端

- jdk>=17 （java >= 17）
- mysql >= 8.0.17
- Redis >=  6

#### 安装步骤-宝塔

1. 数据库导入.sql文件
2. 上传项目到服务器

3. 配置config.properties
4. 新建启动java项目
5. 选择上传的jar包

6. 配置启动命令

```
--spring.profiles.active=prod
```

##### PS：如遇到mysql版本问题，也准备了基于docker的构建镜像

```
docker-compose.yml
端口 	   3307
用户名   root
密码     root
数据库名  d1dserver
```

项目目录运行命令

```
docker compose up -d
```

#### 反代配置

##### 小程序

新建一个项目，设置反向代理，并绑定域名，域名为小程序配置api

```
location ^~ /
{
    proxy_pass http://127.0.0.1:7999; // 同步java启动端口
    proxy_set_header Host 127.0.0.1;
    proxy_set_header X-Real-IP $remote_addr; 
    proxy_set_header REMOTE-HOST $remote_addr;
    proxy_set_header X-Host $host;
}
```

##### web后台

```

location ^~ /prod-api/
{
    proxy_pass http://127.0.0.1:7999/;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header REMOTE-HOST $remote_addr;
}
```



### web后台

- nodejs
- npm

如果不能运行或报错请升级你的node版本。

#### 1. 下载依赖

```
npm install
```

#### 2. 构建项目

```
npm run build:prod
```

**账号：**admin

**密码：**admin123

### 小程序

- 基础库 >= 2.19.2
- nodejs

#### 1. 下载依赖

```
npm install
```

#### 2. 打开微信开发者工具

工具=>构建npm

#### 3.配置请求地址

配置文件 config/config.js

你配置反向代理绑定的域名

```
baseURL: "https://example.com/api", // 你的 API 地址 以/api结束
```



## 📢免责声明（Disclaimer）

本项目为开源的去水印小程序（含前端与后端），**仅供学习与技术研究用途**。
 **请勿将本项目用于任何违反国家法律法规、平台使用协议或侵犯他人合法权益的用途**。

- ❗本项目不提供任何视频内容，仅对公开链接进行技术处理。
- ❗请勿将本项目用于商业用途或恶意传播行为。
- ❗使用本项目可能违反部分平台服务条款，使用者需自行判断和承担风险。

作者不对任何因使用本项目造成的法律责任或平台封禁后果承担任何责任。

## 📄授权协议（License）

本项目基于 MIT License 协议开源，您可以自由使用、修改、传播，但必须保留原作者信息和本声明。
