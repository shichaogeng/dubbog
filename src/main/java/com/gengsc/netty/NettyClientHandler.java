package com.gengsc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-07 14:22
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private String sendMsg;
    private StringBuilder resultMsg;

    public NettyClientHandler(StringBuilder resultMsg, String sendMsg) {
        this.resultMsg = resultMsg;
        this.sendMsg = sendMsg;
    }

    /**
     * 与服务器连接成功后调用，我们在这里发送消息
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----------------channelActive----------------------");
        System.out.println(sendMsg);

        byte[] sendMsgBytes = sendMsg.getBytes();
        ByteBuf sendMsgEncoded = ctx.alloc().buffer(sendMsgBytes.length).writeBytes(sendMsgBytes);
        ctx.writeAndFlush(sendMsgEncoded);
    }

    /**
     * 服务端有消息响应，会调用此方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("----------------channelRead----------------------");

        ByteBuf buf = (ByteBuf) msg;
        String result = buf.toString(CharsetUtil.UTF_8);
        System.out.println("服务端响应信息:"+result);

        resultMsg.append(result);
        System.out.println(sendMsg);
        buf.release();
    }



}
