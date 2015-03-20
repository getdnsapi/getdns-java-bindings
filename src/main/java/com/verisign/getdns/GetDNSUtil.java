package com.verisign.getdns;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class GetDNSUtil {

	private static final int HashMap = 0;

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
//						if ((int)answerMap.get("type") == RRType.valueOf("GETDNS_RRTYPE_TLSA").getValue()) {
//							HashMap<String, Object> rdataMap = (HashMap<String, Object>) answerMap.get("rdata");
//									
//									
//							int selector = rdata.get("selector").toString());
//							int matching_type = Integer.parseInt(rdata.get("matching_type").toString());
//							byte[] caData = (byte[]) rdata.get("certificate_association_data");
//							int certUsage = Integer.parseInt(rdata.get("certificate_usage").toString());
//							 String certData = bytesToHexString(caData);
//							
//							 if (certUsage == requestedUsage) {
//								tlsaRdataSet.put("Certificate Usage", certUsage);
//								tlsaRdataSet.put("Selector", selector);
//								tlsaRdataSet.put("Matching Type", matching_type);
//								// tlsaRdataSet.put("Certificate", certData);
//							}
						return (HashMap<String, Object>) answerMap.get("rdata");
					}

				}
			}
		}

		return null;
	}

		/**
		 * converting bytes array to hex String
		 */
		public static String bytesToHexString(byte[] bytes) {
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		}
		
		
	static public String getDnssecStatus(HashMap<String, Object> info) {

		int dnssecStatus = (int) GetDNSUtil.getinfovalues(info, "dnssec_status");
		String status = (GetDNSReturn.fromInt(dnssecStatus).toString());
		if (status.equalsIgnoreCase("GETDNS_DNSSEC_SECURE")) {
			return GetDNSConstants.GETDNS_DNSSEC_SECURE;
		} else if (status.equalsIgnoreCase("GETDNS_DNSSEC_BOGUS")) {
			return GetDNSConstants.GETDNS_DNSSEC_BOGUS;
		} else if (status.equalsIgnoreCase("GETDNS_DNSSEC_INDETERMINATE")) {
			return GetDNSConstants.GETDNS_DNSSEC_INDETERMINATE;
		} else if (status.equalsIgnoreCase("GETDNS_DNSSEC_INSECURE")) {
			return GetDNSConstants.GETDNS_DNSSEC_INSECURE;
		} else if (status.equalsIgnoreCase("GETDNS_DNSSEC_NOT_PERFORMED ")) {
			return GetDNSConstants.GETDNS_DNSSEC_NOT_PERFORMED;
		} else {
			return null;
		}
	}

	public static String getValidationChain(HashMap<String, Object> info) {

		return "Validation_Chain:  " + GetDNSUtil.getinfovalues(info, "validation_chain");
	}

	public static HashMap<String, Object> getTlaRdataSet(
			ArrayList<HashMap<String, ArrayList<HashMap<String, Object>>>> replies, int requestedUsage)
			throws UnsupportedEncodingException {
		HashMap<String, Object> tlsaRdataSet = new HashMap();
		for (HashMap<String, ArrayList<HashMap<String, Object>>> reply : replies) {
			ArrayList<HashMap<String, Object>> answer = reply.get("answer");
			for (HashMap<String, Object> rr : answer) {
				int rrtype = (int) rr.get("type");
				int tlsaType = RRType.valueOf("GETDNS_RRTYPE_TLSA").getValue();
				if (rrtype == tlsaType) {
					HashMap<String, Object> rdata = (HashMap<String, Object>) rr.get("rdata");
					int selector = Integer.parseInt(rdata.get("selector").toString());
					int matching_type = Integer.parseInt(rdata.get("matching_type").toString());
					byte[] caData = (byte[]) rdata.get("certificate_association_data");
					int certUsage = Integer.parseInt(rdata.get("certificate_usage").toString());
					// String certData = bytesToHexString(caData);
					if (certUsage == requestedUsage) {
						tlsaRdataSet.put("Certificate Usage", certUsage);
						tlsaRdataSet.put("Selector", selector);
						tlsaRdataSet.put("Matching Type", matching_type);
						// tlsaRdataSet.put("Certificate", certData);
					}
				}
			}
		}
		return tlsaRdataSet;
	}

	public static void printAnswer(HashMap<String, Object> info) {
		if (info != null) {
			ArrayList<HashMap<String, Object>> answers = (ArrayList<HashMap<String, Object>>) info
					.get("just_address_answers");
			for (HashMap<String, Object> answer : answers) {

				if (answer != null) {
					//
					System.out.println(answer.get("address_type") + ": " + answer.get("address_data"));

				}

			}

		}

	}

}
