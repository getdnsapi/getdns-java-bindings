package com.verisign.getdns.wrapper;
import java.util.HashMap;

public class GetDNS {
   static {
      System.loadLibrary("getdnsconnector");
   }

   public native Object createContext(int setFromOS);
   
   public native HashMap<String,Object> generalSync(Object context, String name, int requestType, 
		   								HashMap<String,Object> extensions)throws GetDNSException;
   
   public native void destroyContext(Object context);
}

