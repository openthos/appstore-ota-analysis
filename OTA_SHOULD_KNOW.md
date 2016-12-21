# OTA维护需知
## OTA维护人员放置升级包过程
1.将测试通过的升级包放到外部服务器  
2.修改oto_ota.ver中version描述,将版本号加上0.01  

### 详细升级过程:
1.编译升级包时将会从http://dev.openthos.org/openthos/oto_ota.ver中取得当前版本号,在此基础上加0.01例如
```
curl -sf -L http://dev.openthos.org/openthos/oto_ota.ver

Version=1.8.9
ReleaseNote=ReleaseNotes_1_8_8.md
FileName=openthos_V1.0.zip
```
那么编译出的升级包即为1.9.0  
2.将测试通过的升级包放到外部服务器  
3.修改oto_ota.ver中version描述,将版本号加上0.01:
```
Version=1.8.9      ->     Version=1.9.0
```
