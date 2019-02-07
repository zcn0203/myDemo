package com.test.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class NonBlockingSocket {

    @Test
    public void client() throws IOException {

        //获取服务端通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8989));

        //将通道设置成非阻塞
        sChannel.configureBlocking(false);

        //获取缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String str = sc.next();

            //向服务端发送数据
            buffer.put((new Date().toString() + "\n"+str ).getBytes());
            buffer.flip();
            sChannel.write(buffer);
            buffer.clear();

        }

        sChannel.close();

    }

    @Test
    public void server()throws IOException{

        //创建服务连接
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //将通道设置成非阻塞
        ssChannel.configureBlocking(false);

        //绑定连接
        ssChannel.bind(new InetSocketAddress(8989));

        //获取选择器
        Selector selector = Selector.open();

        //将通道注册到选择器上，并且指定监听接受事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        //轮训的获取选择器上已经准备好的事件
        while (selector.select() > 0) {
            //轮训当前选择器中所有注册的 选择键（已经准备就绪的事件）
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //遍历迭代器
            while (it.hasNext()) {
                //获取准备金就绪的事件
                SelectionKey key = it.next();

                //判断具体是什么事件准备就绪
                if (key.isAcceptable()) {
                    //如果准备就绪，就获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    //切换到非阻塞模式
                    sChannel.configureBlocking(false);
                    //将通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);

                } else if (key.isReadable()) {
                    //获取当前选择器上准备就绪的通道
                    SocketChannel sChannel = (SocketChannel) key.channel();
                    //创建缓冲区读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = sChannel.read(buffer)) != -1) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(),0,len));
                        buffer.clear();
                    }

                }
                //取消选择键 selectionKey
                it.remove();
            }


        }


    }

}
