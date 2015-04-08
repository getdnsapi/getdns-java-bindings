package com.verisign.getdns.example;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */

public class GetDNSGeneralAsyncCancel {

	public static void main(String[] args) {
		final IGetDNSContext context = GetDNSFactory.create(1);
		if (args.length != 2)
			throw new IllegalArgumentException("Need to pass string and type");
		String queryString = args[0];
		String type = args[1];

		try {
			GetDNSFutureResult result = context.generalAsync(queryString, RRType.valueOf(type), null);
                        result.cancel(true);
                        System.out.println("Cancel status of the request: "+result.isCancelled());
			HashMap<String, Object> info = null;
                        System.out.println("Now checking for result");
			info = result.get(5000, TimeUnit.MILLISECONDS);

			if (info != null) {
                            System.out.println("Something is wrong here, we got a response even after cancellation");
			} else {
				System.out.println("No response from DNS SERVER");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
		System.exit(0);

	}
}
