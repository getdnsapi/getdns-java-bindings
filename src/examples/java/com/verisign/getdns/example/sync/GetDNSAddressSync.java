package com.verisign.getdns.example.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;

/*
 * return the records in the DNS answer section 
 */

public class GetDNSAddressSync {

	public static void main(String[] args) {
		final IGetDNSContextSync context = GetDNSFactory.createSync(1, null);
		String queryString = "verisigninc.com";
		try {
			HashMap<String, Object> info = context.addressSync(queryString, null);

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					printAnswer(info);
					System.out.println(GetDNSUtil.getDnsStatus(info));
				} else if (Integer.parseInt(info.get("status").toString()) == 901) {
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
		if (info != null) {
			ArrayList<Map<String, Object>> answers = GetDNSUtil.getAsListOfMap(info, "/just_address_answers");
			for (Map<String, Object> answer : answers) {
				if (answer != null)
					System.out.println(answer.get("address_type") + ": " + answer.get("address_data"));

			}

		}

	}

}
