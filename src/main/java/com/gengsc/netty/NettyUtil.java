package com.gengsc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-06 23:38
 */
public class NettyUtil {

    /**
     * 启动服务端
     * @param port
     * @throws Exception
     */
    public static void startServer(String host, String port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();//boss线程,管理工作者线程,负责调度
        EventLoopGroup workGroup = new NioEventLoopGroup();//工作者线程,负责工作

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();//启动类
            //启动引导类
            serverBootstrap.
                    group(bossGroup, workGroup).//指定处理和连接的线程
                    channel(NioServerSocketChannel.class).//指定nio的serversocketchannel(设置channel类型)
                    childHandler(new ChannelInitializer<SocketChannel>() {//一个新的连接接入时，会创建一个子channel
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 每个 Channel 都拥有一个与之相关联的 ChannelPipeline，其持有一个 ChannelHandler 的
                             * 实例链
                             */
                            socketChannel.pipeline().addLast(new NettyServerHandler());//添加响应事件的处理类到channle pileline中
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128);

            ChannelFuture future = serverBootstrap.bind(host, Integer.parseInt(port)).sync();//异步绑定服务器，阻塞直到绑定完成(sync)
            future.channel().closeFuture().sync();//阻塞直到服务器channel关闭
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static String sendMsg(String host, String port, final String sendMsg) throws Exception{

        EventLoopGroup workGroup = new NioEventLoopGroup();
        final StringBuilder resultMsg = new StringBuilder();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyClientHandler(resultMsg, sendMsg));
                }
            });

            //这个是连接服务端，一直在等待着服务端的返回消息，返回的信息封装到future，可以监控线程的返回
            bootstrap.connect(host, Integer.parseInt(port)).channel().closeFuture().await();
            return resultMsg.toString();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
