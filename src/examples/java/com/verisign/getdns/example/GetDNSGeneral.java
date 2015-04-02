package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

/*
 * 
 * Given a DNS name and type, return the records in the DNS answer section 
 * 
 * 
 * 
 */

public class GetDNSGeneral {

	public static void main(String[] args) {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		options.put(ContextOptionName.DNS_TRANSPORT, 542);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		if (args.length != 2)
			throw new IllegalArgumentException("Need to pass string and type");
		String queryString = args[0];
		String type = args[1];

		try {
			HashMap<String, Object> info = context.generalSync(queryString, RRType.valueOf(type), null);
			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					System.out.println(GetDNSUtil.printReadable(info));
				}

				else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("no such name: " + queryString + "with type: " + type);
				} else {

					System.out.println("Error in query GETDNS Status =" + info.get("status").toString());
				}
			} else {
				System.out.println("No response form DNS SERVER");
			}
		} finally {
			context.close();
		}
		System.exit(0);

	}

}
