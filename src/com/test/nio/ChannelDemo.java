package com.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {

    public static void main(String[] args) {
        test1();
    }
    /**
     * 利用通道完成文件复制
     */
    public static void test1() {
        //创建通道
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        /*
        方式2：
        try {
            FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        try {
            fis = new FileInputStream("1.txt");
            fos = new FileOutputStream("2.txt");
            //Channel 根据流创建
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            //Channe通过buffer传输
            //buffer 根据对应类的allocate创建
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inChannel.read(buffer) != -1) {
                //将buffer切换到写模式
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
