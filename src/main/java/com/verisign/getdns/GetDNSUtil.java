package com.verisign.getdns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
							break; // May cause some issues due to commenting the below code.
							// if (rdata.containsKey("certificate_usage") && (int)
							// rdata.get("certificate_usage") == 3) {
							// } else {
							// rdata = null;
							// }
							// i++;
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
        private static Object getListObject(Map<String, Object> map, String path) {
		Object value = null;
		int indexOfArrayPath = path.indexOf('[');
		if(indexOfArrayPath!=-1){
			Object val = map.get(path.substring(0, indexOfArrayPath));
			value = getListObject(val, path.substring(indexOfArrayPath));
		}
		return value;
	}

	private static Object getListObject(Object val, String path) {
		int indexOfArrayPath = path.indexOf('[');
		int indexOfArrayClosePath = path.indexOf(']');
		Object value = null;
		if(indexOfArrayPath != -1 && indexOfArrayClosePath != -1 && val instanceof ArrayList<?>)
		{
			try{
				value = ((ArrayList<?>) val).get(Integer.parseInt(path.substring(indexOfArrayPath+1,indexOfArrayClosePath)));
			}catch(NumberFormatException e){
				throw new IllegalArgumentException("Invalid index specified: "+path, e);
			}
			catch(IndexOutOfBoundsException e){
				throw new IllegalArgumentException("Invalid index specified: "+path, e);
			}
			if(indexOfArrayClosePath != path.length()-1)
				return getListObject(value, path.substring(indexOfArrayClosePath+1));
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	private static Object getObject(Map<String, Object> map, String path) {
		int childIndex = path.indexOf('/', 1);
		String currentPath = childIndex != -1?path.substring(1, childIndex):path.substring(1);
		Object value = null;
		
		if((value = getListObject(map, currentPath)) == null)
			value = map.get(currentPath);
		if(childIndex == -1)
			return value;
		else if(value != null && (value instanceof Map)){
			String next = path.substring(childIndex);
			return getObject((Map<String, Object>) value, next);
		}
		else
			return null;
	}
        @SuppressWarnings("unchecked")
	public static ArrayList<Object> getAsArrayList(Map<String, Object> map, String path){
		Object result = getObject(map, path);
		if(result instanceof ArrayList)
			return (ArrayList<Object>) result;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,Object> getAsMap(Map<String, Object> map, String path){
		Object result = getObject(map, path);
		if(result instanceof HashMap)
			return (HashMap<String, Object>) result;
		return null;
	}
        @SuppressWarnings("unchecked")
	public static ArrayList<Map<String, Object>> getAsListOfMap(Map<String, Object> map, String path){
		Object result = getObject(map, path);
		if(result instanceof ArrayList){
			for (Object element : ((ArrayList)result)) {
				if(!(element instanceof Map))
					return null;
			}
			return (ArrayList<Map<String, Object>>) result;
		}
		return null;
	}
}
