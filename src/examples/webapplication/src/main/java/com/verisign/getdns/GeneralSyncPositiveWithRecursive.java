package com.verisign.getdns;

import java.util.HashMap;


import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

public class GeneralSyncPositiveWithRecursive {

	public String getDNSSyncForResourceRecord(String domainName, String recordType) {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, false);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1, options);
		HashMap<String, Object> info = null;
                String result = "Sorry, some error has occurred..";
		try {

			info = context.generalSync(domainName, RRType.valueOf(recordType), null);
                        if (info != null) {
                             result = GetDNSUtil.printReadable(info);
                        }

		} finally {
			context.close();
		}
		return result;
	}
}
