

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


public class GetDNSService {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		if (args.length != 1)
			throw new IllegalArgumentException("Need to pass address");
		String queryString = args[0];
		try {
			HashMap<String, Object> info = context.addressSync(queryString, null);

			if (info != null ){
				if(Integer.parseInt(info.get("status").toString()) == 900) {

//					System.out.println("TYPE=" + gettype(info));
					printAnswer(info);
					/*if (gettype(info) != null) {
						if (RRType.valueOf("GETDNS_RRTYPE_" + type).getValue() == Integer
								.parseInt(gettype(info))) {
							System.out.println("For " + queryString + "Record =" + type
									+ "  Available");
						}
					}else{
						System.out.println(type + " not defined for "+queryString);
					}*/
				}

				else if(Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such address: "+queryString);
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

	/*public static String gettype(HashMap<String, Object> info) {
		if (info != null) {
			ArrayList replies_tree = (ArrayList) info.get("replies_tree");
			if (replies_tree != null && replies_tree.size() > 0) {
				HashMap<String, Object> answers = (HashMap<String, Object>) replies_tree
						.get(0);
				if (answers != null) {
					ArrayList classes = (ArrayList) answers.get("answer");
					if (classes != null) {
						HashMap<String, Object> class1 = (HashMap<String, Object>) classes
								.get(0);
						if (class1 != null) {
							return class1.get("type").toString();
						}
					}

				}

			}

		}

		return null;
	}*/
	
	public static void printAnswer(HashMap<String, Object> info) {
		if (info != null) {
			ArrayList<HashMap<String, Object>> answers = (ArrayList<HashMap<String, Object>>) info.get("just_address_answers");
			System.out.println(answers);
			for (HashMap<String, Object> answer : answers) {
				
				if (answer != null) {
//					ArrayList classes = (ArrayList) answer.get("IPSTRING");
					System.out.println(answer.get("address_type")+": "+answer.get("address_data"));
					/*if (classes != null) {
						HashMap<String, Object> class1 = (HashMap<String, Object>) classes
								.get(0);
						if (class1 != null) {
							System.out.println(class1);
						}
					}*/
					
				}
				
			}

		}

	}

}
