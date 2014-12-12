

package com.verisign.getdns.example;

import java.util.ArrayList;
import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;
import com.verisign.getdns.GetDNSConstants;

/*
 * 
 * Given a DNS name check DNSSEC validation status of the domain.  
 * 
 * 
 * 
 */


public class GetDNSIPSecure {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		if (args.length != 1)
			throw new IllegalArgumentException("Need to pass string");
		String queryString = args[0];
		HashMap<String, Object> extensions = new HashMap<String, Object>();
		extensions.put(GetDNSConstants.RETURN_BOTH_V4_AND_V6, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		extensions.put(GetDNSConstants.DNSSEC_RETURN_ONLY_SECURE, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		extensions.put(GetDNSConstants.DNSSEC_RETURN_STATUS, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		extensions.put(GetDNSConstants.DNSSEC_RETURN_VALIDATION_CHAIN, GetDNSConstants.GETDNS_EXTENSION_TRUE);


		try {
			HashMap<String, Object> info = context.addressSync(queryString, extensions);

			if (info != null ){
				if(Integer.parseInt(info.get("status").toString()) == 900) {

					System.out.println("DNSSEC is validated. DNSSEC Status: "+getDNSSECStatus(info));
				}

				else if(Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such name: "+queryString);
				}
				else if(Integer.parseInt(info.get("status").toString()) == 903) {
					System.out.println("Error: No DNS response was determined to be secure through DNSSEC");
					System.out.println("DNSSEC status was: "+getDNSSECStatus(info));
				}
				else{

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

	public static String getDNSSECStatus(HashMap<String, Object> info) {
		if (info != null) {
//			System.out.println(info);
			ArrayList replies_tree = (ArrayList) info.get("replies_tree");
			if (replies_tree != null && replies_tree.size() > 0) {
				HashMap<String, Object> answers = (HashMap<String, Object>) replies_tree
						.get(0);
				if (answers != null) {
					return answers.get("dnssec_status").toString();
				}

			}

		}
		return null;

	}

}
