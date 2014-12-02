package com.verisign.getdns;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.verisign.getdns.GetDNSConstants.*;

public class GetDNSUtil {
	

	public static String gettype(HashMap<String, Object> info){
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
	
	/*
	 * Convert a string to byte representation of the address.
	 */
	public static HashMap<String,Object> convertStringToAddress(String address) throws UnknownHostException{
		if (address== null || address.isEmpty()){
			throw new GetDNSException(GetDNSReturn.GETDNS_RETURN_BAD_DOMAIN_NAME);
		}
		HashMap<String, Object> addressDetails = new HashMap<>();
		InetAddress ip = InetAddress.getByName(address);
		addressDetails.put(ADDRESS_TYPE, ip instanceof Inet4Address?IPV4:IPV6);
		addressDetails.put(ADDRESS_DATA, ip.getAddress());
		return addressDetails;
	}
}
