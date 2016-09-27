# 升级服务器提供的文件
## oto_ota.ver
此文件名固定，内容如下
```
Version=1.8.7
ReleaseNote=ReleaseNotes1_8_7.md
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
