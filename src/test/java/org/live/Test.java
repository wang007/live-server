package org.live;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mr.wang on 2017/4/3.
 */
public class Test {

    public static void main(String[] args) throws UnknownHostException {

        System.out.println(System.getProperty("os.name")) ;

        System.out.println("c:\\\\projectDir\\\\upload".replace("upload", "") ) ;

        InetAddress localHost = InetAddress.getLocalHost() ;
        System.out.println(localHost.getHostAddress());

    }
}
