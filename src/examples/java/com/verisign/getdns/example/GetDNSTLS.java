package com.verisign.getdns.example;

import java.util.HashMap;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.ContextOptionValue;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

/*
 * Given a DNS name and type, return the records in the DNS answer section
 */

public class GetDNSTLS {

	public static void main(String[] args) {
                HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
                options.put(ContextOptionName.STUB, true);
                Object[][] list = { { "185.49.141.38"}};
                options.put(ContextOptionName.UPSTREAMS, list);
                options.put(ContextOptionName.DNS_TRANSPORT, ContextOptionValue.TRANSPORT_TLS_ONLY_KEEP_CONNECTIONS_OPEN);
                IGetDNSContextSync context = null;
                try {
                        context = GetDNSFactory.createSync(1, options);
                        HashMap<String, Object> info = context.addressSync("google.com", null);
                        System.out.println("info: " + info);
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        context.close();
                }
	}
}
