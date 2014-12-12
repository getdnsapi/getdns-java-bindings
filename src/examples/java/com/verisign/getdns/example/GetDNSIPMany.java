

package com.verisign.getdns.example;

import java.util.ArrayList;
import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * 
 * return the records in the DNS answer section for multiple domain 
 * 
 * 
 * 
 */


public class GetDNSIPMany {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		if (args.length <  1)
			throw new IllegalArgumentException("Need to pass address");
		String queryString = args[0];
		for(int i=0;i<args.length;i++)  {
			queryString = args[i];
		try {
			HashMap<String, Object> info = context.addressSync(queryString, null);

			if (info != null ){
				if(Integer.parseInt(info.get("status").toString()) == 900) {

					printAnswer(info);

				}
				else if(Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such address: "+queryString);
				}else{
					System.out.println("Error in query GETDNS Status =" + info.get("status").toString());
				}
			} else {
				System.out.println("No response form DNS SERVER");
			}
		} catch(Exception e){}
		}	
		 
			context.close();
			System.exit(0);

	}
	/*
	 * Method to parse the DNS response to get Required type Record 
	 */


	
	public static void printAnswer(HashMap<String, Object> info) {
		if (info != null) {
			ArrayList<HashMap<String, Object>> answers = (ArrayList<HashMap<String, Object>>) info.get("just_address_answers");
			System.out.println(answers);
			for (HashMap<String, Object> answer : answers) {
				
				if (answer != null) {
//					
					System.out.println(answer.get("address_type")+": "+answer.get("address_data"));
				
					
				}
				
			}

		}

	}

}
