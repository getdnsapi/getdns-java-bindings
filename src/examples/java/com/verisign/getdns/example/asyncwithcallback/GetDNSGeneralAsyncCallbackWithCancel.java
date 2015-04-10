package com.verisign.getdns.example.asyncwithcallback;

import java.util.HashMap;

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

public class GetDNSGeneralAsyncCallbackWithCancel {

	public static void main(String[] args) {
		final IGetDNSContextWithCallback context = GetDNSFactory.createWithCallback(1, null);

		try {
			final String queryString = "verisigninc.com";
			long transactionId = context.generalAsync(queryString, RRType.valueOf("A"), null, new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					System.out.println("response:  " + response);
					checkResponse(queryString, "A", response);
					try {
						Thread.sleep(5000);
						System.out.println("Completed processing for: " + queryString);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			context.cancelRequest(transactionId);
			Thread.sleep(20000);
			System.out
					.println("Respnose didn't come within 20 secs, it shows that the Request had been succesfully canceled");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
		System.exit(0);

	}

	private static void checkResponse(String queryString, String type, HashMap<String, Object> info) {
		if (info != null) {
			if (Integer.parseInt(info.get("status").toString()) == 900) {
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
	}
}
