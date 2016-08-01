# AppStore需求与设计实现文档

- 项目简介
- 功能需求
- 项目进展
- 设计实现
- 存在问题

# 项目简介

本项目属于openthos项目的一部分，提供AppStore基本功能和系统OTA管理功能

## 功能需求

* 排行功能
  * [x] 首页按下载量进行排行
  * [ ] 获取App详细信息和下载
* 游戏分类功能
  * [x] 通过点击相应的分类展现游戏app的列表
  * [ ] 获取App详细信息和下载
* 应用软件分类功能
  * [x] 通过点击相应的分类展现应用软件的列表
  * [ ] 获取App详细信息和下载
* 下载管理功能
  * [x] 软件下载的断点续传功能
  * [x] 实现下载，暂停，继续功能
  * [x] 显示和清除下载历史
  * [x] 进度条功能
  * [x] 下载完成之后安装功能
* App详情信息展示功能 
  * [x] 获取App的作者，下载次数，大小等信息
  * [x] 评论功能
  * [x] 下载功能
* OTA管理功能
  * [x] 下载系统镜像功能
  * [x] MD5文件校验功能
  * [x] 自动重启功能
  * [x] 版本检测功能
* 搜索功能
  * [ ] 模糊搜索
* 下拉刷新
  * [ ] 评论的下拉刷新功能
  * [ ] 软件列表的下拉刷新功能
* 用户管理
  * [ ] 用户注册
  * [ ] 用户登陆
  * [ ] 用户基本信息展示和修改
  * [ ] 同步用户下载记录
* App更新管理
  * [ ] 检测更新
  * [ ] 一键更新
  * [ ] 单个更新

[点击查看详情信息](https://github.com/openthos/display-analysis/blob/master/repo/APP%20Store需求文档.pdf)

# 项目进展

<table>
<tr>
<th>人员</th>
<th>开始时间 </th>
<th>结束时间 </th>
<th>内容</th>
</tr>

<tr>
<td rowspan=10>石智强</td>
</tr>

<tr>
<td>2016-05-18 </td>
<td>2016-05-27 </td>
<td>调研AppStore服务端开发，了解客户端基本功能请求，熟悉IDE，数据库等一些基础工作，并写出服务端简单demo。</td>
</tr>

<tr>
<td>2016-05-30</td>
<td>2016-06-03</td>
<td>具体详细了解客户端的功能以及数据结构，在服务端进行建表，组织数据和代码，导入数据。使其服务端和客户端可以运行起来，并能实现数据的交互。</td>
</tr>

<tr>
<td>2016-06-06</td>
<td>2016-06-10</td>
<td>更改App软件下载后缀名,注意其中的路径问题。继续对代码进行优化，在客户端下载完成App的时候，客户端有时候造成崩溃，和薛海龙一起商量解决。修改APK后缀名更改中的一些小问题（当有请求来时，不能准确定位文件位置，由于if语句使用 不当造成的），增加下载统计功能（基本功能可以使用，但还是有一些Bug）</td>
</tr>

<tr>
<td>2016-06-13</td>
<td>2016-06-17</td>
<td>在审查appstore的时候，发现了bug，导致appstore客户端出现崩溃的现象，找出了问题所在并解决，解决app下载完之后无法解析安装的问题</td>
</tr>

<tr>
<td>2016-06-20</td>
<td>2016-06-24</td>
<td>把服务端从本地转移到dev.openthos.org的服务器上，遇到问题并解决。由于远程服务器是apache2 server https协议，导致应用商店无法请求后端，调研其原因。
</td>
</tr>

<tr>
<td>2016-06-27</td>
<td>2016-07-01</td>
<td>解决了openthos服务器http协议的问题，没有修改服务器上的https协议，对客户端进行了代码的修改，可正常访问。调研OTA相关信息。对OTA后台功能和数据表进行设计，初步实现其功能。
</td>
</tr>

<tr>
<td>2016-07-04</td>
<td>2016-07-15</td>
<td>
根据客户端的各种功能和BUG对服务端进行修改和添加功能并不断测试，完成appstore评论后端功能，协助余康完成http获取数据的问题。中间遇到并解决数据库乱码的问题。
</td>
</tr>

<tr>
<td>2016-07-18</td>
<td>2016-07-29</td>
<td>
为新来工程师，讲解后端的细节并讨论，对代码进行修改优化。其中也参与了客户端的一些功能的讨论。
</td>
</tr>
</table>

<table>
<tr>
<td rowspan=10>薛海龙</td>
</tr>

<tr>
<td>2016-05-12</td>
<td>2016-05-20</td>
<td>调研AppStore，确定可用的Demo源码，修改Client端并成功运行</td>
</tr>

<tr>
<td>2016-05-23</td>
<td>2016-05-27</td>
<td>调研AppStore Server端的开发，学习mysql的基本操作，并尝试把Server端搭起来</td>
</tr>

<tr>
<td>2016-05-30</td>
<td>2016-06-03</td>
<td>Client端和Server端实现交互，可以从Server下载App</td>
</tr>


<tr>
<td>2016-06-06</td>
<td>2016-06-10</td>
<td>实现在openthos的真机上调试App，修改Client端的主界面</td>
</tr>

<tr>
<td>2016-06-13</td>
<td>2016-06-17</td>
<td>解决下载多个App无法安装的Bug，修改App的评论界面</td>
</tr>

<tr>
<td>2016-06-20</td>
<td>2016-06-24</td>
<td>分析下载App的过程，实现App详情界面的下载功能</td>
</tr>

<tr>
<td>2016-06-27</td>
<td>2016-07-01</td>
<td>App详情界面的图标和正在下载App的图标可以动态显示</td>
</tr>

<tr>
<td>2016-07-04</td>
<td>2016-07-15</td>
<td>分析进度条的显示功能；在主界面添加进度条，下载完成之后可以直接安装</td>
</tr>

<tr>
<td>2016-07-18</td>
<td>2016-07-29</td>
<td>修改游戏和软件不同风格的界面，分析下载历史的图片不能动态显示的问题，完成AppStore的需求和设计文档</td>
</tr>

</table>

<table>
<tr>
<td  rowspan=10> 余康</td>
</tr>

<tr>
<td>2016-06-25</td>
<td>2016-07-01</td>
<td>（1）安装和熟悉app客户端的代码。（2）完成进度显示的分析。</td>
</tr>

<tr>
<td>2016-07-02</td>
<td>2016-07-08</td>
<td>1（1）新增OTA更新下载的功能。（2）新增OTA更新的界面</td>
</tr>

<tr>
<td>2016-07-09</td>
<td>2016-07-15</td>
<td>（1）完成对单个app评论功能的实现（根据评论的时间排序）、（2）实现评论的滚动浏览、（3）完成app_store首页的修改</td>
</tr>

<tr>
<td>2016-07-16</td>
<td>2016-07-22</td>
<td>（1）完成对下载的进度百分比显示、（2）完成OTA的下载、更新、重启的功能、（3）app详细信息页面的修改、（4）修复部分BUG</td>
</tr>

<tr>
<td>2016-07-23</td>
<td>2016-07-29</td>
<td>（1）完成appstore的需求文档和设计文档（2）参与完成对appstore的项目详述和工作总结（3）与工程师完成对appstore的任务交接</td>
</tr>

</table>

[具体Report](https://github.com/openthos/customized-android-analysis/issues?page=2&q=is%3Aissue+is%3Aopen)

# 设计实现

[客户端详情](https://github.com/openthos/display-analysis/blob/master/repo/AppStore%E8%AE%BE%E8%AE%A1%E6%96%87%E6%A1%A3.pdf)

[服务端详情](https://github.com/openthos/appstore-ota-analysis/blob/master/Server/README.md)

[OTA详情](https://github.com/openthos/appstore-ota-analysis/blob/master/Server/OTA.md)

# 存在问题

### 客户端

一，稳定性的设计问题

   * 请求Server端失败会被强制关闭
   * 在排行、软件、游戏主界面频繁切换会被强制关闭
   * 在下载App完成之后有时会被强制关闭
   * app详细页面没有加载图片等信息


### 服务端

一，代码组织和表的设计问题

   * 代码分层不清晰
   * 数据表的设计有不需要的多余字段

二，App存放目录结构问题

   * App存放的文件夹名称为AppID ，会造成频繁操作数据库。
    
三，OTA版本检测问题

   * 版本号的组织形式
   * 版本号在系统中的位置 

