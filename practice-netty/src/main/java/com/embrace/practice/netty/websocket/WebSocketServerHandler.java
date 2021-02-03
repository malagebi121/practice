package com.embrace.practice.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;

/**
 * @author embrace
 * @describe  TextWebSocketFrame 文本数据帧
 * @date created in 2021/1/8 9:49
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //读取问题
    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame text) throws Exception {
        System.out.println("收到的文本数据" + text.text());
        context.channel().writeAndFlush(new TextWebSocketFrame("服务器时间: " + LocalDate.now() + text.text()) );
    }

    //客户连接之后
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 调用" + ctx.channel().id().asShortText());
    }

    //客户断开之后
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
    }
}
