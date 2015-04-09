package com.verisign.getdns.example.asyncwithcallback;

import java.util.HashMap;
import java.util.concurrent.Executors;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSCallback;
import com.verisign.getdns.IGetDNSContextWithCallback;
import com.verisign.getdns.RRType;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */

public class GetDNSGeneralAsyncCallbackWithThreadPool {

	public static void main(String[] args) {
		final IGetDNSContextWithCallback context = GetDNSFactory.createWithCallback(1, null);
		context.setExecutor(Executors.newFixedThreadPool(5));

		try {
			String[] domains = {"verisign.com", "verisigninc.com", "google.com", "yahoo.com", "facebook.com"};
			for (final String domain : domains) {
				context.generalAsync(domain, RRType.valueOf("A"), null, new IGetDNSCallback() {
					
					@Override
					public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
						checkResponse(domain, "A", response);
						try {
							Thread.sleep(5000);
							System.out.println("Completed processing for: "+domain);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}});
			}
//			HashMap<String, Object> info = null;
//			info = result.get(5000, TimeUnit.MILLISECONDS);
			Thread.sleep(10000);

//			checkResponse(queryString, type, info);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
		System.exit(0);

	}

	private static void checkResponse(String queryString, String type,
			HashMap<String, Object> info) {
		if (info != null) {
			if (Integer.parseInt(info.get("status").toString()) == 900) {
//				System.out.println(GetDNSUtil.printReadable(info));
				System.out.println("Queried: "+queryString+" status: "+GetDNSUtil.getdnsStatus(info));
			}

			else if (Integer.parseInt(info.get("status").toString()) == 901) {
				System.out.println("no such name: " + queryString + "with type: " + type);
			} else {

				System.out.println("Error in query GETDNS Status =" + info.get("status").toString());
			}
		} else {
			System.out.println("No response form DNS SERVER");
		}
	}
}
