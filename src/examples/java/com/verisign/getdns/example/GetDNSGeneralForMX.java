package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
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
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		String queryString = "verisigninc.com";

		try {
			HashMap<String, Object> info = context.generalSync(queryString, RRType.valueOf("MX"), null);

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {

					System.out.println(GetDNSUtil.getObject(info, "/replies_tree[0]/answer[0]/rdata/exchange"));
					System.out.println(GetDNSUtil.getDnsStatus(info));

				}

				else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such name: " + queryString + "with type: " + "MX");
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

}
