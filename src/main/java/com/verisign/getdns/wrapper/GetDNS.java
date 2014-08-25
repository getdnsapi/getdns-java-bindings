package com.verisign.getdns.wrapper;
import java.util.HashMap;

public class GetDNS {
   static {
      System.loadLibrary("getdnsconnector");
   }
   public native Object createContext(int setFromOS);
   public native HashMap<String,Object> general(Object context, String name);
   public native void destroyContext(Object context);
}

