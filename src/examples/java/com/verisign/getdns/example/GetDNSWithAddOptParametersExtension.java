package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.ExtensionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

public class GetDNSWithAddOptParametersExtension {

	public static void main(String args[]) {
		String queryString = "getdnsapi.net";
		String type = "A";
		final IGetDNSContextSync context = GetDNSFactory.createSync(1,null);
		HashMap<String, Object> optParams = new HashMap<String, Object>();
		optParams.put(ExtensionName.ADD_OPT_PARAM_EXTENDED_RCODE.getName(), 128);
		HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
		extensions.put(ExtensionName.ADD_OPT_PARAMETERS, optParams);
		try {
			HashMap<String, Object> info = context.generalSync(queryString, RRType.valueOf(type), extensions);
			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					System.out.println(GetDNSUtil.printReadable(info));
					System.out.println(GetDNSUtil.getDnsStatus(info));
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
