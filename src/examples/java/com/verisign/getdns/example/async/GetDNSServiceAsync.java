package com.verisign.getdns.example.async;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.example.sync.GetDNSServiceSync;

/*
 *Given a DNS name and type, return the records in the DNS answer section 
 * 
 */

public class GetDNSServiceAsync {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		String queryString = "_xmpp-server._tcp.google.com.";
		try {
			GetDNSFutureResult result = context.serviceAsync(queryString, null);
			HashMap<String, Object> info = null;
			context.run();
			info = result.get();

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					GetDNSServiceSync.printAnswer(info);
					System.out.println(GetDNSUtil.getdnsStatus(info));
				}

				else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such name: " + queryString);
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
