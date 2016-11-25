
## OTA升级步骤
### 1）编译，在原有img基本上编译update
```
source build/envsetup.sh
lunch
m -j32 update_zip
```
### 2)复制到服务器上，并修改相应md5文件
```
cp /home/lh/ssd/lmm/lollipop-x86/out/target/product/x86_64/openthos_V1.0.zip /var/www/html/openthos/
md5sum --tag openthos_V1.0.zip > openthos_V1.0.zip.md5
```
