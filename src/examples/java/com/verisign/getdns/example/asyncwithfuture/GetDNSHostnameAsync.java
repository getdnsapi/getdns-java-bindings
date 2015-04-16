package com.verisign.getdns.example.asyncwithfuture;

import java.util.HashMap;

import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextAsyncWithFuture;

/*
 * return the records in the DNS answer section 
 * 
 */
public class GetDNSHostnameAsync {

	public static void main(String[] args) {
		final IGetDNSContextAsyncWithFuture context = GetDNSFactory.createAsyncWithFuture(1,null);
		String queryString = "8.8.8.8";
		try {
			GetDNSFutureResult result = context.hostnameAsync(queryString, null);
			HashMap<String, Object> info = null;
			context.run();
			info = result.get();

			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					System.out.println(GetDNSUtil.getObject(info, "/canonical_name"));
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
