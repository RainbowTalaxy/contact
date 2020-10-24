# 通讯录

## 简介
该应用采用TCP通信，服务器端为IDEA项目，客户端为Xcode项目

## 支持
* 服务器（Linux）
    - MySQL推荐使用 `8.0` 版本以上
    - JDK不低于 `11.0.5`
* 客户端（iOS）
    - 系统版本不低于 `14.0`

## 快速使用
### 服务器部署
开放服务器的 8078 端口

将 [Contact-Server.jar](Contact-Server/out/artifacts/Contact_Server/Contact-Server.jar) 下载到你的服务器上

然后使用：
```
java -jar [服务器文件地址]
```
或者使用 screen 指令（如果安装了 screen 指令）：
```
screen java -jar [服务器文件地址]
```
运行后可以看到执行信息：
```
Start listening on port 8078...
```

### 客户端使用
在访达中打开 [Contact-Client](Contact-Client/Contact.xcodeproj)

点击导航栏运行按钮