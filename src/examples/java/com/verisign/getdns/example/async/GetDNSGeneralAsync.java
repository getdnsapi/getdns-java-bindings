package com.verisign.getdns.example.async;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * Given a DNS name and type, return the records in the DNS answer section  
 */

public class GetDNSGeneralAsync {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		String queryString = "verisigninc.com";
		String type = "A";

		try {
			GetDNSFutureResult result = context.generalAsync(queryString, RRType.valueOf(type), null);
			HashMap<String, Object> info = null;
			context.run();
			info = result.get();

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					System.out.println(GetDNSUtil.printReadable(info));
					System.out.println(GetDNSUtil.getdnsStatus(info));
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
}
