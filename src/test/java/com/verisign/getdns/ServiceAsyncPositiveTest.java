package com.verisign.getdns;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class ServiceAsyncPositiveTest implements IGetDNSTestConstants {
	
	

	@Test
	public void testGetDNSService() throws ExecutionException, TimeoutException{
		
	
		final IGetDNSContext context = GetDNSFactory.create(1);		
	
		try{
			GetDNSFutureResult futureResult = context.serviceAsync(DOMAIN_NAME, null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(GetDNSUtil.gettype(info)));
		}finally {
			context.close();
		}
	}

	
	@Test
	public void testGetDNSNXDDomain() throws ExecutionException, TimeoutException{
		
	
		final IGetDNSContext context = GetDNSFactory.create(1);		
	
		try{
			GetDNSFutureResult futureResult = context.serviceAsync(UNREGDOMAIN, null);
			HashMap<String, Object> info = null;
			try {
				info = futureResult.get(5000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(info);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(GetDNSUtil.gettype(info)));
		}finally {
			context.close();
		}
	}
	
	public String gettype(HashMap<String, Object> info){
		if(info != null){
			ArrayList replies_tree = (ArrayList) info.get("replies_tree");	
			 if(replies_tree != null && replies_tree.size() > 0){
				 HashMap<String, Object> answers =  (HashMap<String, Object>) replies_tree.get(0);
				 if(answers != null){
					 ArrayList classes = (ArrayList) answers.get("answer");
					 if(classes !=null){
					  HashMap<String, Object> class1 = (HashMap<String, Object>) classes.get(0);
					  if(class1 != null ){
						 return class1.get("type").toString();
					  }
					 }
							 
				 }
				 
			 }
			 
			
		}
		
		return null;
	}

}
