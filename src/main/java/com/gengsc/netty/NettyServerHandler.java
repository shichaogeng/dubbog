package com.gengsc.netty;

import com.gengsc.invoke.InvokeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Description
 * 服务端业务逻辑处理类
 * @Author shichaogeng
 * @Create 2018-01-07 13:31
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 对传入的消息调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        String result = buf.toString(CharsetUtil.UTF_8);
        System.out.println("客户端传来的参数:"+result);

        buf.release();

        //客户端消息处理，这里是调用服务端的服务
        String response = InvokeUtil.invokeServiceMethod(result);

        byte[] responseBytes = response.getBytes();
        ByteBuf reponseEncoded = ctx.alloc().buffer(responseBytes.length).writeBytes(responseBytes);
        ctx.writeAndFlush(reponseEncoded);
        ctx.close();
    }


    /**
     * 最后的channelread
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {



    }

}
