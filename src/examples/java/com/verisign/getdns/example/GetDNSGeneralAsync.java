package com.verisign.getdns.example;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */

public class GetDNSGeneralAsync {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		if (args.length != 2)
			throw new IllegalArgumentException("Need to pass string and type");
		String queryString = args[0];
		String type = args[1];

		try {
			GetDNSFutureResult result = context.generalAsync(queryString, RRType.valueOf(type), null);
			HashMap<String, Object> info = null;
			info = result.get(5000, TimeUnit.MILLISECONDS);

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {

				System.out.println(GetDNSUtil.printReadable(info));
				}

				else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such name: " + queryString + "with type: " + type);
				} else {

					System.out.println("Error in query GETDNS Status =" + info.get("status").toString());
				}
			} else {
				System.out.println("No response form DNS SERVER");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
		System.exit(0);

	}

//	public static void printAnswer(HashMap<String, Object> info) {
//		if (info != null) {
//			ArrayList<HashMap<String, Object>> answers = (ArrayList<HashMap<String, Object>>) info
//					.get("just_address_answers");
//			for (HashMap<String, Object> answer : answers) {
//
//				if (answer != null) {
//					System.out.println(answer.get("address_type") + ": " + answer.get("address_data"));
//				}
//
//			}
//
//		}
//
//	}

}
