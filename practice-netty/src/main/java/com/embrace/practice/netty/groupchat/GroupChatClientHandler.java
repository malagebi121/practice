package com.embrace.practice.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author embrace
 * @describe  客户端端handler
 * @date created in 2021/1/7 21:30
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext context, String s) throws Exception {
        System.out.println(s.trim());
    }
}
