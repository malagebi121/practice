package com.embrace.practice.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author embrace
 * @describe
 * @date created in 2021/1/7 20:48
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    
    //定义一个channel组，装载所有的 channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //实现点对点聊天思路 , 用户id和channel绑定，发起消息的用户把 另一个用户的 id 发过来，在转发给该用户
    private Map<String,Channel> map = new ConcurrentHashMap<>();

    //时间
    SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String s) throws Exception {
        Channel channel = chc.channel();
        channelGroup.forEach(c -> {
            if(c != channel){
                c.writeAndFlush(simpleDateFormat.format(new Date()) + " \n"+ "客户端：" +channel.remoteAddress() + " : "+ s);
            }else{
                c.writeAndFlush(simpleDateFormat.format(new Date()) + " \n" +"自己:"+channel.remoteAddress() + " : "+ s);
            }
        });
    }

    //handlerAdded 表示建立连接
    // 新的 channel 进入，加入group，并且通知其他
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + " 进入了聊天室");
        channelGroup.add(channel);
    }

    //表示channel 活动中
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端：" + ctx.channel().remoteAddress() + " 上线了");
    }

    //不活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端：" + ctx.channel().remoteAddress() + " 离线了");
    }

    //离线
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        channelGroup.remove(channel); //这个可以不用，自动回减少
        channelGroup.writeAndFlush("客户端：" + channel.remoteAddress() + " 离开了聊天室");
        System.out.println(channelGroup.size());
    }
}
