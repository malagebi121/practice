package com.embrace.practice.netty.http;

import com.sun.jndi.toolkit.url.Uri;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author embrace
 * @describe SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 *  HttpObject 客户端和服务端通信的封装
 * @date created in 2021/1/7 10:28
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        System.out.println("当前的channel：" + channelHandlerContext.channel());
        System.out.println("当前的pipeline：" + channelHandlerContext.pipeline());
        System.out.println("当前的pipeline中的pipeline：" + channelHandlerContext.pipeline().channel());
        System.out.println("当前的hendler：" + channelHandlerContext.handler());


        if(httpObject instanceof HttpRequest){
            //不同的网站请求的不一样，同一网站每次请求也不一样，http 每次请求了就自动断掉，不是长连接
            System.out.println("pipeline 的 hashcode" + channelHandlerContext.pipeline().hashCode()
              + " handeler 的 hashCode ： " + this.hashCode() );
            HttpRequest request = (HttpRequest) httpObject;
            //网站会发送两个请求，一个页面URL的请求和网页图标的请求
            if("/favicon.ico".equals(request.uri())){
                System.out.println("请求了网站图片，不做任何处理");
                return;
            }
            System.out.println("msg 类型： " + httpObject.getClass());
            System.out.println("客户端地址： " + channelHandlerContext.channel().remoteAddress());
            ByteBuf byteBuf = Unpooled.copiedBuffer("你好啊，客户端", CharsetUtil.UTF_8);
            //返回的 response
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            //加上这个，不然会乱码  charset=utf-8
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());

            channelHandlerContext.writeAndFlush(response);
        }
    }
}
