package cn.wbw.web.http;

import lombok.extern.log4j.Log4j2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端 单播通信
 *
 * @author wbw
 * @date 2019-12-21 22:33
 */
@Log4j2
public class SocketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            // 阻塞等待客户端连接，有连接便创建套接字返回
            Socket socket = serverSocket.accept();
            DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("服务器接收到客户端连接请求:\t" + inputStream.readUTF());
            stream.writeUTF("接受连接请求，连接成功");
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            log.error(e);
        }
    }
}
