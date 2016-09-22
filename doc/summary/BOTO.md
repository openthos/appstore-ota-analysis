# BOTO Install and OTA info
## Compile
I add 2 compile choice:
```
oto_img  #BOTO Image:Conclude data.img(preinstall app)
make oto_img
update_zip #(OTA update package)
```
##How to add new preinstall app
```
1.chaneg app's name
把下面代码保存到和app同级的目录下，并且增加可执行权限:chmod +x 文件名
#!/bin/sh
for apk in `ls *.apk`
do 
    #apktool d $apk;
    dir=`echo $apk|awk -F ".apk" '{print $1;}'`
    if [ -d $dir ]
    then 
        cat $dir/AndroidManifest.xml|sed -n 2p|awk '{for(i=1;i<=NF;i++)print $i;}'|grep "package"|awk -F "\"" '{print $2}' |tee tmp
        packagename=`cat tmp`
        echo $apk
        echo $packagename
        mkdir ../convert/$packagename
        mv $apk ../convert/$packagename/"$packagename".apk
    fi
done
2.copy app's dir to code,path is like the following:
[packages/apps/ExternalAPP/$packagename/"$packagename".apk]
3.rm out/target/product/x86_64/data.img
4.Compile
APP is included in android_x86_64_oto.img
```
##How to update via OTA
```
1.make update_zip
you should check the 'update.list' and 'version' file
cat bootable/newinstaller/otoinit/update.list
kernel
initrd.img
ramdisk.img
system.sfs
These string is the same as file's name packed into img/iso;Then you can add or sub;

bootable/newinstaller/otoinit/version 
OpenThos V1.0          #version
Function:              #new feature
Bugfix:                #fix bug
Cust:                  #different with Android-x86
```
##OTA server work
```
1.put the update zip into server
2.put the 'version' file with update zip
```
##OTA via APP
```
1.APP should get the current OS's version
Chenwei should provide the 'version' file to APP,APP read the specific file
2.APP Compare server's newest version with the current version
you can download the OTA server's 'version' file and compare with local 'version' file
3.download the update zip and put in the following path:
/data/media/0/System_OS/
update_zip
4.you must provide a flag which we can decide wether update
cat /data/media/0/System_OS/update
openthos_V1.0.zip      #update zip name
1                      #need update(maybe you should ask user,then set the flag)
```
