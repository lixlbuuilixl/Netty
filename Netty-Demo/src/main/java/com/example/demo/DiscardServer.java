package com.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    //接口
    private int port;
    //构造方法
    public DiscardServer(int port){
        this.port = port;
    }

    public void run() throws  Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();//用来接收信息
        EventLoopGroup workerGroup = new NioEventLoopGroup();//将接收的信息进行处理
        try{
            ServerBootstrap b = new ServerBootstrap();//
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new DiscardServerHandler());
                }
            });
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}
