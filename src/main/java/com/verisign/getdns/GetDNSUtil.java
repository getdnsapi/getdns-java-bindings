package com.verisign.getdns;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * This class generally extracts the DNS response and gives the required output
 * 
 * @author Vinay Soni
 *
 */
public class GetDNSUtil {
	@SuppressWarnings("unchecked")
	static public Object getinfovalues(HashMap<String, Object> info, String name) {
		HashMap<String, Object> answers = null;
		if (info != null) {

			if (name.equalsIgnoreCase("IPv6")) {
				if (info.containsKey("just_address_answers")) {
					ArrayList ipv6List = (ArrayList) info.get("just_address_answers");
					if (ipv6List.toString().contains("IPv6")) {
						return ipv6List.get(1).toString();
					} else
						return null;
				}
			}

			if (name.equalsIgnoreCase("validation_chain")) {
				if (info.containsKey("validation_chain")) {
					if (info.get("validation_chain").toString().length() > 2) {
						return info.get("validation_chain").toString();
					} else {
						return null;
					}
				}
			}
			ArrayList replies_trees = (ArrayList) info.get("replies_tree");

			if (replies_trees != null && replies_trees.size() > 0) {
				HashMap<String, Object> replies_tree_4 = (HashMap<String, Object>) replies_trees.get(0);
				if (name.equalsIgnoreCase("dnssec_status")) {
					return replies_tree_4.get("dnssec_status");
				}
				ArrayList answerList = (ArrayList) replies_tree_4.get("answer");
				if (answerList != null) {
					HashMap<String, Object> answerMap = (HashMap<String, Object>) answerList.get(0);
					if (name.equalsIgnoreCase("class")) {
						return answerMap.get("class");
					} else if (name.equalsIgnoreCase("type")) {
						return answerMap.get("type");
					} else if (name.equalsIgnoreCase("rdata")) {
						int i = 0;
						HashMap<String, Object> rdata = null;
						// System.out.println("size: " + answerList.size());
						while (i < answerList.size()) {
							answerMap = (HashMap<String, Object>) answerList.get(i);
							rdata = (HashMap<String, Object>) answerMap.get("rdata");
							if (rdata.containsKey("certificate_usage") && (int) rdata.get("certificate_usage") == 3) {
								break;
							} else {
								rdata = null;
							}
							i++;
						}
						return rdata;
					}

				}
			}
		}

		return null;
	}

	/**
	 * this methods converts bytes array to hex String
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * This methods reutrns DNSSEC status
	 * 
	 * @param info
	 * @return DNSSEC Status
	 */
	static public String getDnssecStatus(HashMap<String, Object> info) {

		int dnssecStatus = (int) GetDNSUtil.getinfovalues(info, "dnssec_status");
		// String status = (GetDNSReturn.fromInt(dnssecStatus).toString());
		GetDNSReturn ret = GetDNSReturn.fromInt(dnssecStatus);
		String response = printReadable(info) + "\n";
		switch (ret) {
		case GETDNS_DNSSEC_SECURE:
			return response + GetDNSConstants.GETDNS_DNSSEC_SECURE;
		case GETDNS_DNSSEC_BOGUS:
			return response + GetDNSConstants.GETDNS_DNSSEC_BOGUS;
		case GETDNS_DNSSEC_INDETERMINATE:
			return response + GetDNSConstants.GETDNS_DNSSEC_INDETERMINATE;
		case GETDNS_DNSSEC_INSECURE:
			return response + GetDNSConstants.GETDNS_DNSSEC_INSECURE;
		case GETDNS_DNSSEC_NOT_PERFORMED:
			return response + GetDNSConstants.GETDNS_DNSSEC_NOT_PERFORMED;
		default:
			return null;
		}
	}

	/**
	 * This method returns validation chain from DNS response
	 * 
	 * @param info
	 * @return
	 */
	public static String getValidationChain(HashMap<String, Object> info) {

		return "-------Validation_Chain----------\n\n  "
				+ printReadable(GetDNSUtil.getinfovalues(info, "validation_chain"));
	}

	public static String printReadable(Object info) {
		if (info != null) {
			return info.toString().replaceAll(",", ",\n").replaceAll("=\\[\\{", "=\\[\n\\{\n").replaceAll("\\}", "\n\\}");
		}
		return null;
	}
}
