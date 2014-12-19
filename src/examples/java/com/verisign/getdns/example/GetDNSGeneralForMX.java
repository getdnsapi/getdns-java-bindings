

package com.verisign.getdns.example;

import java.util.ArrayList;
import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */


public class GetDNSGeneralForMX {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		if (args.length != 1)
			throw new IllegalArgumentException("Need to pass string and type");
		String queryString = args[0];
		

		try {
			HashMap<String, Object> info = context.generalSync(queryString,RRType.valueOf("GETDNS_RRTYPE_" + "MX"), null);

			if (info != null ){
				if(Integer.parseInt(info.get("status").toString()) == 900) {


					printAnswer(info);
					
				}

				else if(Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such name: "+queryString+"with type: "+"MX");
				}else{
					
					System.out.println("Error in query GETDNS Status =" + info.get("status").toString());
				}
			} else {
				System.out.println("No response form DNS SERVER");
			}
		} finally {
			context.close();
		}
		System.exit(0);

	}
	/*
	 * Method to parse the DNS response to get Required type Record 
	 */


	
	public static void printAnswer(HashMap<String, Object> info) {
		if (info != null) {
			ArrayList replies_tree = (ArrayList) info.get("replies_tree");
			if (replies_tree != null && replies_tree.size() > 0) {
				HashMap<String, Object> answers = (HashMap<String, Object>) replies_tree
						.get(0);
				if (answers != null) {
					ArrayList answer = (ArrayList) answers.get("answer");
					System.out.println(answer);
					

				}

			}

		}

	}

}
