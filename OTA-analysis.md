# OTA功能需求和计划文档
内容：
- 项目简介
- 功能需求
- 项目进展

# 项目简介
 本项目是更新系统新版本的功能。当用户点击电脑设置中的“有无更新按钮”时，如果没有版本可更新，则界面显示
“当前没有可用更新”，如果有新版本，则自动开始下载，界面上会展示一个下载的进度条。镜像文件下载完毕后，用Md5或者SHA1校验
镜像文件的值。如果不通过，则界面上显示“更新界面不正确”，并自动删除文件，如果校验通过，则界面上显示“安装更新并重启的按钮”

### 当前开发人员（2016.08.01-2016.08.31）
陈渝: 系统重启进行本地ota更新(基于newinstaller)
于鹏: OTA的app实现(完成ota下载)

# 功能需求
## 基本功能
### OTA app界面的基本元素
         有无系统可更新的提示、进度条、Md5校验文件是否正确的提示、Md5校验文件正确后显示“安装更新并重启的按钮”
### OTA app界面的基本操作
    OTA界面没有手动的操作，用户点击电脑设置中的“有无更新按钮”时，会自动走完OTA 功能的流程

### [newinstaller with OTA功能描述](https://github.com/openthos/newinstaller_analysis/blob/master/docs/summary.md)


### [newinstaller with OTA操作说明](https://github.com/openthos/newinstaller_analysis/blob/master/docs/summary.md)

## 接口说明
### [OTA app与 newinstaller的接口说明](https://github.com/openthos/newinstaller_analysis/blob/master/docs/summary.md)


# 项目进展
| 计划 | 完成 | 开始时间 | 结束时间 |
|---|---|---|---|
| OTA功能框架的搭建 |×| 2016-8-3||
| 完成下载功能| × |2016-8-4|| 
| 实现OTA的下载过程中进度条的展示 | × |2016-8-5|| 
| 实现OTA对所下载文件进行md5的校验 |×|2016-8-8||
| 根据UI工程师的效果图搭建新的OTA框架 |×|||
| 实现OTA效果图中的全部功能|×|||
| 实现newinstaller with OTA |×|||

