# mehrms
#本系统基于江南一点雨的vhr（基于Spring Boot+Vue微人事项目）进行的二次开发。
#若需要了解原系统，请移步https://github.com/lenve/vhr;

  #项目采用Spring Boot、MyBatis、Spring MVC、Rabbitmq、nginx、poi、redis、ElementUI、echarts、axios等技术，作者基于原系统，自行实现了人事信息管理功能（包括员工资料、奖惩、培训、调薪、    调动5个子功能）、员工薪资管理功能（包括工资表管理、月末处理、工资表表查询3个子功能）、统计管理功能（包括员工薪资统计、积分统计、人事信息统计、人事记录统计4个子功能）以及系统设置功能（包    括奖惩规则、系统管理、操作日志管理3个子功能），在邮件系统上实现了对员工入职邮件通知以及员工月薪资明细邮件的通知功能。

   #在该Spring Boot项目文件过多时，将项目重构为一个拥有三级子项目的工程，一级项目为总项目；二级项目分为两个，一个为邮件服务项目，一个为主项目；主项目中拥有三级子项目5个，分别为util层、model层，mapper层，model层，以及controller层。
   
   #博主自行将系统设计的大部分思路已书写呈现于xmind文件中【初次设计，不喜勿喷】。
   
   
   #项目开发环境及其运行环境：
   
            JDK8.0及以上
            Rabbitmq 3.8.0及以上
            nginx 1.18.0及以上
            MySQL 8.0及以上
            IDEA 2020.02
            Navicat Premium 任意版本
            Visual Studio Code 任意版本
            Node.js 12.0及以上
            
   #环境安装步骤：
   
            1、安装java1.8/7/9
            在环境变量Path中添加C:\Program Files\Java\jdk1.8.0_144\bin，在cmd中检测java #-verison

            2、配置maven3
            在环境变量中创建一个M2_HOME的变量，变量值为C:\apache-maven-3.6.3-bin\apache-ma#ven-3.6.3
            在path变量中添加%M2_HOME%\bin，在cmd中检测mvn -v

            3、配置Maven本地仓库
            在某个目录下新建maven-repository文件夹，该目录用作maven的本地库。

            打开maven\conf\settings.xml文件，
            更换
               <mirrors>
                <!-- mirror
                 | Specifies a repository mirror site to use instead of a given 
                   repository. The repository that
                 | this mirror serves has an ID that matches the mirrorOf element of 
                   this mirror. IDs are used
                 | for inheritance and direct lookup purposes, and must be unique a
                   cross the set of mirrors.
                |
               <mirror>
                 <id>mirrorId</id>
                 <mirrorOf>repositoryId</mirrorOf>
                 <name>Human Readable Name for this Mirror.</name>
                 <url>http://my.repository.com/repo/path</url>
               </mirror>
                -->
  
             <!-- 阿里云仓库 -->
                   <mirror>
                       <id>nexus-aliyun</id>
                       <name>Nexus aliyun</name>
                       <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
                       <mirrorOf>central</mirrorOf>
                   </mirror>

               <!-- 中央仓库1 -->
                   <!-- <mirror>
                       <id>repo2</id>
                       <name>Mirror from Maven Repo2</name>
                       <url>http://repo2.maven.org/maven2/</url>
                       <mirrorOf>central</mirrorOf>
                   </mirror> -->

               <!-- maven在UK架设的仓库 -->
                   <!-- <mirror>
                       <id>ui</id>
                       <name>Mirror from UK</name>
                       <url>http://uk.maven.org/maven2/</url>
                       <mirrorOf>central</mirrorOf>
                   </mirror> -->
               <!--JBoss的仓库-->
                   <!-- <mirror>
                       <id>jboss-public-repository-group</id>
                       <mirrorOf>central</mirrorOf>
                       <name>JBoss Public Repository Group</name>
                       <url>http://repository.jboss.org/nexus/content/groups/
                       public</url>
                   </mirror> -->
             </mirrors>
 


            4、找到<localRepository>D:\Maven_Repository</localRepository>
            将其更改为自己的仓库
            运行一下DOS命令 mvn help:system

            如果前面的配置成功，那么maven-repository文件夹会出现一些文件。

            5、安装idea
                        跳过配置，
                        激活

            6、RabbitMQ安装、配置与入门
            Erlang下载安装
            由于RabbitMQ使用Erlang语言编写，所以先安装Erlang语言运行环境。
            http://www.erlang.org/downloads
            下载OTP 22.0 Windows 64-bit Binary File (94094976)版本（考虑迅雷下载）
            下载后，直接单击Next下一步进行安装

            然后配置环境变量
            ERLANG_HOME
            C:\Program Files\erl10.4
            在path中增加%ERLANG_HOME%\bin

            RabbitMQ下载安装
            https://www.rabbitmq.com/install-windows.html
            在发布日志中找到3.7.x版本，在页面最下方找到下载（考虑迅雷）
            rabbitmq-server-3.7.26.exe
            配置环境变量
            RABBITMQ_SERVER=C:\Program Files\RabbitMQ Server\rabbitmq_server-3.7.15
            PATH中添加%RABBITMQ_SERVER%\sbin;

            开机启动
            rabbitmq-service enable
            启动服务
            rabbitmq-service start
            查询状态
            rabbitmqctl status
            查看用户
            rabbitmqctl list_users
            开启rabbitmq_management
            rabbitmq-plugins enable rabbitmq_management
            地址 http://localhost:15672/
            默认的用户名：guest
            默认的密码为：guest
            
            8、安装nginx
            下载zip文件，解压双击nginx.exe
            nginx -s reload

            9、安装mysql8
            使用.exe程序安装包，zip安装包在没有安装visual 
            studio等环境下，会造成插件不全安装失败等错误。
            在path中添加环境变量
            C:\Program Files\MySQL\MySQL Server 8.0\bin

            https://www.jb51.net/softs/683429.html
            
            10、node.js安装
            http://nodejs.cn/download/
            安装vue
            npm install vue
            安装vue-cli
            npm install vue-cli@2.9.x -g
            安装elementUI
            npm i element-ui -S
            安装webpack
            npm install webpack@4.27.x -g
            安装echarts
            npm install echarts --save

            11、navicat Premium安装
            
            可能出现一下问题
            连接出现1251错误，在workbench里面新建个sql语句
            
            1 #执行如下语句：
            2 #修改加密方式
            3 ALTER USER 'root'@'localhost' IDENTIFIED BY 'password' PASSWORD EXPIRE NEVER;
            4 #更新用户密码
            5 ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '此处写自己的密码';
            6 #刷新一下权限
            7 FLUSH PRIVILEGES;
            然后运行，navicat连接ok了。

            12、vscode代码
            https://code.visualstudio.com/Download
            
   
            
   项目运行配置:
   
            新建一个mehrms的数据库，导入sql文件即可。
            对于前端中的文件，使用一下指令
            npm install    #下载依赖项
            npm run serve  #开发环境中即时运行
            npm run build  #将已开发完成的项目进行编译并打包
                           打包完成后，复制build中的各个文件至nginx解压包的html或者其他指定项目运
                           行文件夹中，再在nginx.conf中通过配置正向代理接口等完成前端部署
            对于后端，则只需要将解压的文件使用IDEA打开，分别运行coreWebApplication和mailApplication文件即可。
            若需要部署，则直接在项目运行时，使用maven install指令将项目打包成jar包即可使用java -jar的指令运行【注意：一定要在运行时打包，否则，运行时可能报错】。
            若出现邮件系统连接错误，注意清空消息队列；
            若出现端口占用，请关掉占用端口的进程；
