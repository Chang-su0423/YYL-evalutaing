ChatRoomDemoApplication类为springboot的启动类
ChatRoomDemoController类用于处理请求

        1.添加用户：
                类型为GET,
                使用路径传参,
                示例：localhost:8080/chatroom/join?roomId=room1&userId=user1
测试：

![屏幕截图 2024-11-04 191451]([readme_img\屏幕截图 2024-11-04 191451.png](https://github.com/Chang-su0423/YYL-evalutaing/blob/master/readme_img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-11-04%20191451.png))    

![屏幕截图 2024-11-04 191534](readme_img\屏幕截图 2024-11-04 191534.png)

        2.移除用户：
                类型为GET,
                使用路径传参,
                示例：localhost:8080/chatroom/leave?roomId=room1&userId=user1

测试：

![屏幕截图 2024-11-04 191704](readme_img\屏幕截图 2024-11-04 191704.png)

![屏幕截图 2024-11-04 191714](readme_img\屏幕截图 2024-11-04 191714.png)
