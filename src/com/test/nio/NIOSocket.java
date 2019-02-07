package com.test.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 模拟阻塞式NIO
 */

public class NIOSocket {

    @Test
    public void client() throws IOException {

        //创建socket
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8989));
        //创建读取的channel
        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //一遍读取一遍发送
        while (inChannel.read(buffer)!=-1) {
            buffer.flip();
            sChannel.write(buffer);
            buffer.clear();
        }
        //结束输出
        sChannel.shutdownOutput();

        //等待接受服务端的消息
        int len = 0;
        while ((len = sChannel.read(buffer))!=-1) {

            buffer.flip();
            System.out.println(new String(buffer.array(),0,len));
            buffer.clear();

        }



        inChannel.close();
        sChannel.close();

    }

    @Test
    public void server() throws IOException {

        //创建Socket
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //文件输出channel
        FileChannel outChannel = FileChannel.open(Paths.get("3.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //绑定连接
        ssChannel.bind(new InetSocketAddress(8989));
        //获取客户端连接通道
        SocketChannel sChannel = ssChannel.accept();
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (sChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        //返回数据给客户端
        buffer.put("服务端接收到数据".getBytes());
        buffer.flip();
        sChannel.write(buffer);

        sChannel.close();
        outChannel.close();
        ssChannel.close();

    }



}
