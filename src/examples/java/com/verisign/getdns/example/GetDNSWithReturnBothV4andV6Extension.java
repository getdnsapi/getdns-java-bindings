package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.ExtensionName;
import com.verisign.getdns.GetDNSConstants;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

public class GetDNSWithReturnBothV4andV6Extension {

	public static void main(String args[]) {

		String queryString = "getdnsapi.net";
		String type = "A";
		final IGetDNSContext context = GetDNSFactory.create(1);
		HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
		extensions.put(ExtensionName.RETURN_BOTH_V4_AND_V6, GetDNSConstants.GETDNS_EXTENSION_TRUE);
		try {
			HashMap<String, Object> info = context.generalSync(queryString, RRType.valueOf(type), extensions);
			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					System.out.println(GetDNSUtil.printReadable(info));
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
		} finally {
			context.close();
		}
		System.exit(0);
	}

}
