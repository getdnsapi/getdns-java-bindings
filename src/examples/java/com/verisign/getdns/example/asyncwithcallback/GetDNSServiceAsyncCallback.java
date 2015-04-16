package com.verisign.getdns.example.asyncwithcallback;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSCallback;
import com.verisign.getdns.IGetDNSContextAsyncWithCallback;
import com.verisign.getdns.example.sync.GetDNSServiceSync;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */

public class GetDNSServiceAsyncCallback {

	public static void main(String[] args) {
		final IGetDNSContextAsyncWithCallback context = GetDNSFactory.createAsyncWithCallback(1, null);
		final String queryString = "_xmpp-server._tcp.google.com.";

		try {
			IGetDNSCallback callback = new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					GetDNSServiceSync.printAnswer(response);
					System.out.println(GetDNSUtil.getDnsStatus(response));
				}
			};
			context.serviceAsync(queryString, null, callback);
			context.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
		System.exit(0);

	}
}
