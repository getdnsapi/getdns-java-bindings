package com.verisign.getdns.wrapper;
import java.util.HashMap;

/**
 * The design here is slightly different from the C API in the following ways.
 * 1.  
 *
 */
public class GetDNS {
   static {
      System.loadLibrary("getdnsconnector");
   }

   public native Object contextCreate(int setFromOS);
   
   public native HashMap<String,Object> generalSync(Object context, String name, int requestType, 
		   								HashMap<String,Object> extensions)throws GetDNSException;
   
   public native void contextDestroy(Object context);
}

