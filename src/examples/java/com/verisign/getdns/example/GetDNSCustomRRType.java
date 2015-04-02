package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.ContextOptionNames;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;
import com.verisign.getdns.ExtensionNames;
import com.verisign.getdns.GetDNSConstants;

/*
 * 
 * Given a DNS name and type, return the records for a custom type
 * 
 * 
 * 
 */

public class GetDNSCustomRRType {

	public static void main(String[] args) {
		HashMap<ContextOptionNames, Object> options = new HashMap<ContextOptionNames, Object>();
		options.put(ContextOptionNames.STUB, true);
		options.put(ContextOptionNames.DNS_TRANSPORT, 542);
		final IGetDNSContext context = GetDNSFactory.create(1, options);
		if (args.length != 2)
			throw new IllegalArgumentException("Need to pass string and type");
		String queryString = args[0];
		String type = args[1];
                HashMap<ExtensionNames, Object> extensions = new HashMap<ExtensionNames, Object>();
                extensions.put(ExtensionNames.DNSSEC_RETURN_STATUS, GetDNSConstants.GETDNS_EXTENSION_TRUE);


		try {
			HashMap<String, Object> info = context.generalSync(queryString, RRType.valueOf(type), extensions);
			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					//System.out.println(GetDNSUtil.printReadable(info));
                                        byte[] rdataRaw = (byte[])((HashMap<String,Object>)GetDNSUtil.getinfovalues(info, "rdata")).get("rdata_raw");
                                        System.out.println("Size: "+rdataRaw.length);
                                        System.out.println("Rdata: "+bytesToHex(rdataRaw));
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
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
