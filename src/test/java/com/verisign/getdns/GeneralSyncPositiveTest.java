package com.verisign.getdns;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class GeneralSyncPositiveTest implements IGetDNSTestConstants {
	
	/*
	 * check unit test case for A Record
	 */
	
	

	@Test
	public void testGetDNSSyncForARecord(){
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
					
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME, RRType.GETDNS_RRTYPE_A, null);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			//assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_A.getValue(),Integer.parseInt(gettype(info)));
		
			 
		}finally {
			context.close();
		}
	}

	/*
	 * check unit test case for AAAA Record
	 */
	
	@Test
	public void testGetDNSSyncForAAAARecord(){
		
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME,RRType.GETDNS_RRTYPE_AAAA, null);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
	//		assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_AAAA.getValue(),Integer.parseInt(gettype(info)));
			
			
		}finally {
			context.close();
		}
	}
	/*
	 * check unit test case for txt Record
	 */
	@Test
	public void testGetDNSSyncForTXTRecord(){
		
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME,RRType.GETDNS_RRTYPE_TXT, null);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
		//	assertNotNull("Type is null and response was "+info, gettype(info));
			
			assertEquals(RRType.GETDNS_RRTYPE_TXT.getValue(),Integer.parseInt(gettype(info)));
			
		}finally {
			context.close();
		}
	}
	 
	/*
	 * check unit test case for A Record
	 */
	
	@Test
	public void testGetDNSSyncForMXRecord(){
		final IGetDNSContext context = GetDNSFactory.create(1);		
		try{
			HashMap<String, Object> info = context.generalSync(DOMAIN_NAME,RRType.GETDNS_RRTYPE_MX, null);
			assertNotNull(info);
			assertEquals("Time out error"+info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			//assertNotNull("Type is null and response was "+info, gettype(info));
			assertEquals(RRType.GETDNS_RRTYPE_MX.getValue(),Integer.parseInt(gettype(info)));
			
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
