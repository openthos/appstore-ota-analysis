# OTA功能需求和计划文档
内容：
- 项目简介
- 功能需求
- 项目进展

# 项目简介
本项目是对系统的新版本进行下载。

### 当前开发人员（2016.08.01-2016.08.31）
陈渝: 系统重启进行本地ota更新(基于newinstaller)
于鹏: OTA的app实现(完成ota下载)

# 功能需求
### OTA app界面的基本元素
        当前版本的文字标识  标有系统版本号和下载按钮的各个条目
### OTA app界面的基本操作
     标有系统版本号的各个条目的左击，下载新版本对话框中忽略按钮的左击，
     下载新版本的对话框中下载按钮的左击，重启系统对话框确认按钮的左击
     重启系统对话框取消按钮的左击

### [newinstaller with OTA功能描述](https://github.com/openthos/newinstaller_analysis/blob/master/docs/summary.md)

### OTA app界面基本操作详细说明
|元素|操作|描述|
|---|---|---|
|标有系统版本号的各个条目|左击|弹出一个询问是否更新的窗体，这个窗体包含两个按钮，忽略、下载
|下载新版本的对话框中忽略按钮的|左击|返回到主页面
|下载新版本的对话框中下载按钮的|左击|返回到主页面，所选条目的下载按钮上会显示下载的进度
|重启系统对话框确认按钮|左击|返回主页面|
|重启系统对话框确认按钮|左击|返回主页面|

### [newinstaller with OTA操作说明](https://github.com/openthos/newinstaller_analysis/blob/master/docs/summary.md)

### [OTA app与 newinstaller的接口说明](https://github.com/openthos/newinstaller_analysis/blob/master/docs/summary.md)


# 项目进展
| 计划 | 完成 | 开始时间 | 结束时间 |
|---|---|---|---|
| OTA基本框架的搭建 |×| 2016-8-3||
| 完成下载功能| × |2016-8-4|| 
| 实现OTA的下载后各个条目的进度条的展示 |×|||
| 实现OTA的下载后各个条目点击暂停功能 |×|||
| 实现OTA对所下载文件进行md5的校验 |×|||
| 实现newinstaller with OTA |×|||

