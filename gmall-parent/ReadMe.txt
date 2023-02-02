gmall-user-service  8070
gmall-user-web      8080
gmall-manager-service 8071
gmall-manager-web     8081
其他端口没有写
OAuth协议授权:
1.进行信息获取的授权
2.获取授权的code
3.使用授权code交换access_token
4.使用access_token去访问获取用户的信息
下面这个项目就剩下分布式事务这块没做了