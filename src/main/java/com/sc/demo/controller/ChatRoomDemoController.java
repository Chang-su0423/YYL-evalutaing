package com.sc.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatroom")
public class ChatRoomDemoController {
    //使用map集合存储聊天室与聊天室中人员的对应关系(未进行持久化保存)
    //使用concurrentHashMap保证并发请求的线程安全
    //同时，concurrentHashMap相比hashTable效率高
    private final ConcurrentHashMap<String, Set<String>> roomMap=new ConcurrentHashMap<>();

    // 加入聊天室
    //发送请求时应在路径上带上userid和roomid
    @GetMapping("/join")
    public String joinRoom(@RequestParam String roomId, @RequestParam String userId) {
        //roomMap线程安全，无需加锁
        roomMap.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
        //加锁保证线程安全，防止有用户同时进入或者退出带来的数据不一致问题
        synchronized (roomMap.get(roomId)) {
            Set<String> roomUsers = roomMap.get(roomId);
            // 打印当前聊天室其他用户
            String otherUsers = roomUsers.stream()
                    .filter(id -> !id.equals(userId))
                    .collect(Collectors.joining(", "));
            System.out.println("当前聊天室（" + roomId + "）内的其他用户: " + otherUsers);

            // 将userID加入set集合
            roomUsers.add(userId);
        }
        return " 用户 " + userId + " 加入 " + roomId;
    }

    // 退出聊天室
    //发送请求时应在路径上带上userid和roomid
    @GetMapping("/leave")
    public String leaveRoom(@RequestParam String roomId, @RequestParam String userId) {
        //roomMap线程安全，无需加锁
        Set<String> roomUsers = roomMap.get(roomId);
        if (roomUsers != null) {
            //加锁保证线程安全，防止有用户同时进入或者退出带来的数据不一致问题
            synchronized (roomUsers) {
                // 打印当前聊天室其他用户
                String otherUsers = roomUsers.stream()
                        .filter(id -> !id.equals(userId))
                        .collect(Collectors.joining(", "));
                System.out.println("当前聊天室（" + roomId + "）内的其他用户: " + otherUsers);

                // 将userId从set集合中移除
                roomUsers.remove(userId);
                // 若聊天室为空，则删除聊天室
                if (roomUsers.isEmpty()) {
                    roomMap.remove(roomId);
                }
            }
        }
        return "用户 " + userId + " 离开 " + roomId;
    }

}
