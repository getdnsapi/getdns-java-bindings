package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.GetDNSException;
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

public class GetDNSAddressAsyncCallback {

	public static void main(String[] args) {
		final IGetDNSContextWithCallback context = GetDNSFactory.createWithCallback(1, null);
		if (args.length != 1)
			throw new IllegalArgumentException("Need to pass address");
		final String queryString = args[0];

		try {
			IGetDNSCallback callback = new IGetDNSCallback() {
				
				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					GetDNSIP.printAnswer(response);
				}
			};
			context.addressAsync(queryString, null, callback);
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
}
