package com.verisign.getdns.example.asyncwithcallback;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSCallback;
import com.verisign.getdns.IGetDNSContextWithCallback;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */

public class GetDNSHostnameAsyncCallback {

	public static void main(String[] args) {
		final IGetDNSContextWithCallback context = GetDNSFactory.createWithCallback(1, null);
		final String queryString = "8.8.8.8";

		try {
			IGetDNSCallback callback = new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					System.out.println(GetDNSUtil.getObject(response, "/canonical_name"));
					System.out.println(GetDNSUtil.getdnsStatus(response));
				}
			};
			context.hostnameAsync(queryString, null, callback);
			context.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
		System.exit(0);

	}
}
