# 升级服务器提供的文件
## oto_ota.ver
此文件名固定，内容如下
```
Version=1.8.８
ReleaseNote=ReleaseNotes1_8_８.md
FileName=update.zip
```
Version字段描述了升级包的版本号，ReleaseNote及ReleaseNote分别代表要求下载的*“更新说明”*文件及*“升级包”*文件的文件名。
## ReleaseNotes1_8_8.md
当用户点击*“更新说明”*将显示本文件的内容，其名称由oto_ota.ver文件ReleaseNote字段指定，其内容标记语言为markdown
```markdown
# <font color=orange> OUTI 1.8.8(稳定版)更新说明 </font>

* 基于Android 6.0桌面操作系统
* 全新设计的窗口管理器
* 全新设计的桌面管理器
***
## 系统  
* 修复　在某些情况下程序假死的问题  
* 新增　对4K分辨率显示设备的支持  
## 桌面  
* 修复　在某些情况下，电池电容显示不正确的问题  
* 新增　桌面dash控件支持  
## 应用商店  
* 优化　APK下载速度  
* 新增　分享应用到朋友圈、微博的功能　
```
## update.zip
升级包的包文件名称，此文件名不固定，由oto_ota.ver文件的Version字段指定
## update.zip.md5
升级包的md5值，文件名为升级包文件名.md5
```
MD5 (update.zip) = 2271ee49332f62c71cafe633af33b48a
```
# 升级方法
1. 用户打开“检查更新”程序后，先从服务器（测试阶段为192.168.0.180）获取oto_ota.ver文件
2. 当oto_ota.ver字段标明的版本高于本地版本后，提示有更新可用
3. 当用户点击“更新说明”时，下载并显示更新说明
4. 当用户点击“立即更新”后，开始下载udpate.zip及update.zip.md5
5. 对update.zip进行md5校验，如果校验通过写入/data/media/0/System_Os/update,其末两行分别为升级包文件名update.zip和１
```
update.zip
1
```
