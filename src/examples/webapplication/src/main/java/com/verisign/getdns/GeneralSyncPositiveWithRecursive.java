package com.verisign.getdns;

import java.util.HashMap;


import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

public class GeneralSyncPositiveWithRecursive {

	public HashMap<String, Object> getDNSSyncForResourceRecord(String domainName, String recordType) {
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, false);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1, options);
		HashMap<String, Object> info = null;
		try {

			info = context.generalSync(domainName, RRType.valueOf(recordType), null);
                        if (info != null) {
                             String temp = GetDNSUtil.printReadable(info);
                             info = new HashMap<String, Object>();   
                             info.put("Response:\n", temp);
                        }

		} finally {
			context.close();
		}
		return info;
	}
}
