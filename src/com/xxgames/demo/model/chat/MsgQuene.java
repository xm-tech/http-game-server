package com.xxgames.demo.model.chat;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

// 场景：世界聊天消息队列， 固定大小， 固定长度， 非阻塞。
public class MsgQuene<E> {

    private int cap;
    private BlockingQueue<E> q = new LinkedBlockingQueue<>();

    private BlockingDeque<E> dq = new LinkedBlockingDeque<>();
}
