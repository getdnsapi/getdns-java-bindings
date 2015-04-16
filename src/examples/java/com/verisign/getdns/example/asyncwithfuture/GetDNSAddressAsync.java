package com.verisign.getdns.example.asyncwithfuture;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextAsyncWithFuture;
import com.verisign.getdns.example.sync.GetDNSAddressSync;

/*
 * return the records in the DNS answer section 
 */

public class GetDNSAddressAsync {

	public static void main(String[] args) {
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);
		String queryString = "verisigninc.com";

		try {
			GetDNSFutureResult result = context.addressAsync(queryString, null);
			HashMap<String, Object> info = null;
			context.run();
			info = result.get();

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					GetDNSAddressSync.printAnswer(info);
					System.out.println(GetDNSUtil.getDnsStatus(info));
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
