package com.verisign.getdns.example.sync;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;

/*
 * return the records in the DNS answer section 
 * 
 */

public class GetDNSHostnameSync {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		String queryString = "216.58.220.46";
		try {
			HashMap<String, Object> info = context.hostnameSync(queryString, null);

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					System.out.println(GetDNSUtil.getObject(info, "/canonical_name"));
					System.out.println(GetDNSUtil.getdnsStatus(info));
				} else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such address: " + queryString);
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
