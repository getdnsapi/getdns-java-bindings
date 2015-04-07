package com.verisign.getdns.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;

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
			HashMap<String, Object> info = context.serviceSync(queryString, null);

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					//System.out.println(GetDNSUtil.printReadable(info));
					printAnswer(info);
				}

				else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such address: " + queryString);
				} else {

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
		boolean foundSRV = false;
		if (info != null) {
                        ArrayList<Map<String, Object>> answers = GetDNSUtil.getAsListOfMap(info, "/replies_tree[0]/answer");
                        if (answers != null) {
                            for (Map<String, Object> answer : answers) {
                                if (answer != null && answer.get("type") != null && answer.get("type").toString().equals("33")) {
                                    HashMap<String, Object> rdata = GetDNSUtil.getAsMap(answer, "/rdata");
                                    System.out.println("SRV " + answer.get("name") + ", Priority: " + rdata.get("priority") + ", "
                                                         + " port: " + rdata.get("port") + " target: " + rdata.get("target"));
                                    foundSRV = true;
                                }
                            }
                        }
		}
		if (!foundSRV)
			System.out.println("No SRV records found");
	}

}
