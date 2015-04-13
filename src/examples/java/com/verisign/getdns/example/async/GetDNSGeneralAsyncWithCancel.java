package com.verisign.getdns.example.async;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * Given a DNS name and type, return the records in the DNS answer section  
 */

public class GetDNSGeneralAsyncWithCancel {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		String queryString = "getdnsapi.net";
		String type = "A";

		try {
			System.out.println("Initiating the query");
			GetDNSFutureResult result = context.generalAsync(queryString, RRType.valueOf(type), null);
			result.cancel(true);
			System.out.println("Cancel status of the request: " + result.isCancelled());
			HashMap<String, Object> info = null;
			System.out.println("Now checking for result");
			context.run();
			info = result.get(5000, TimeUnit.MILLISECONDS);

			if (info != null) {
				System.out.println("Something is wrong here, we got a response even after cancellation");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			context.close();
		}
		System.exit(0);

	}
}
