/*
 * Copyright (c) 2015, Verisign, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the names of the copyright holders nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Verisign, Inc. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.verisign.getdns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>
 * This class contains helper functions to extract specific data from dns
 * response.
 * </p>
 *
 */
public class GetDNSUtil {

	/**
	 * This methods converts byte array to hex String
	 * 
	 * @param bytes
	 *          input byte array
	 * @return Hexadecimal String
	 */
	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/**
	 * This method returns the getDns status text.
	 * 
	 * @param info
	 * @return
	 */
	static public String getDnsStatus(HashMap<String, Object> info) {
		int getDnsStatus = (int) getObject(info, "/status");
		GetDNSReturn ret = GetDNSReturn.fromInt(getDnsStatus);
		return "\nstatus: " + ret.toString();
	}

	/**
	 * This method returns DNSSEC status text.
	 * 
	 * @param info
	 * @return DNSSEC Status
	 */
	static public String getDnssecStatus(HashMap<String, Object> info) {

		int dnssecStatus = (int) GetDNSUtil.getObject(info, "/replies_tree[0]/dnssec_status");
		GetDNSReturn ret = GetDNSReturn.fromInt(dnssecStatus);
		String response = printReadable(info) + "\n";
		return response + " \n\n" + ret.toString();
	}

	/**
	 * This method print readable parts of the response to standard output.
	 * 
	 * @param info
	 * @return
	 */
	public static String printReadable(Object info) {
		if (info != null) {
			return info.toString().replaceAll(",", ",\n").replaceAll("=\\[\\{", "=\\[\n\\{\n").replaceAll("\\}", "\n\\}");
		}
		return null;
	}

	// extract if it's a ArrayList or return null
	private static Object getListObject(Map<String, Object> map, String path) {
		Object value = null;
		int indexOfArrayPath = path.indexOf('[');
		// System.out.println("indexofArrayPath:  " + indexOfArrayPath);
		if (indexOfArrayPath != -1) {
			Object val = map.get(path.substring(0, indexOfArrayPath));
			// System.out.println("val:  " + val);
			value = getListObject(val, path.substring(indexOfArrayPath));
			// System.out.println("value(get list object):  " + value);
		}
		return value;
	}

	private static Object getListObject(Object val, String path) {
		int indexOfArrayPath = path.indexOf('[');
		int indexOfArrayClosePath = path.indexOf(']');
		Object value = null;
		if (indexOfArrayPath != -1 && indexOfArrayClosePath != -1 && val instanceof ArrayList<?>) {
			try {
				value = ((ArrayList<?>) val).get(Integer.parseInt(path.substring(indexOfArrayPath + 1, indexOfArrayClosePath)));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid index specified: " + path, e);
			} catch (IndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Invalid index specified: " + path, e);
			}
			if (indexOfArrayClosePath != path.length() - 1)
				return getListObject(value, path.substring(indexOfArrayClosePath + 1));
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	public static Object getObject(Map<String, Object> map, String path) {
		// System.out.println("path:  " + path);
		int childIndex = path.indexOf('/', 1);

		// System.out.println("childIndex:  " + childIndex);

		String currentPath = childIndex != -1 ? path.substring(1, childIndex) : path.substring(1);

		// System.out.println("currentpath:  " + currentPath);
		Object value = null;
		// System.out.println("getlistonbject:   " + getListObject(map,
		// currentPath));
		if ((value = getListObject(map, currentPath)) == null) {
			// System.out.println("valule:1:  " + value);
			value = map.get(currentPath);
			// System.out.println("valule:2:  " + value);
		} else {
			// System.out.println("value:3:  " + value);
			// System.out.println("why");
		}
		if (childIndex == -1) {
			// System.out.println("testing-1");
			return value;
		} else if (value != null && (value instanceof Map)) {
			// System.out.println("testing-2");
			String next = path.substring(childIndex);
			// System.out.println("next:   " + next);
			return getObject((Map<String, Object>) value, next);
		} else {
			// System.out.println("testing-3");
			return null;
		}
	}

	/**
	 * <p>
	 * This method is used to extract an ArrayList from the response by providing
	 * the path.<br>
	 * Example:<br>
	 * {@code
	 * 1. ArrayList<Object> answers = GetDNSUtil.getAsArrayList(info, "/replies_tree[0]/answer");
	 * 
	 * 2. ArrayList<Object> authority = GetDNSUtil.getAsArrayList(info, "/replies_tree[0]/authority");
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * @param map
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> getAsArrayList(Map<String, Object> map, String path) {
		Object result = getObject(map, path);
		if (result instanceof ArrayList)
			return (ArrayList<Object>) result;
		return null;
	}

	/**
	 * <p>
	 * This method is used to extract an Map from the response by providing the
	 * path.<br>
	 * Example:<br>
	 * {@code
	 * 1. ArrayList<Object> rdata = GetDNSUtil.getAsArrayList(info, "/replies_tree[0]/authority[0]/rdata");
	 * 
	 * }
	 * </p>
	 * 
	 * @param map
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getAsMap(Map<String, Object> map, String path) {
		Object result = getObject(map, path);
		if (result instanceof HashMap)
			return (HashMap<String, Object>) result;
		return null;
	}

	/**
	 * <p>
	 * This method is used to extract an Map of ArrayList from the response by providing the
	 * path.<br>
	 * Example:<br>
	 * {@code
	 * 1. ArrayList<Map<String,Object>> authority = GetDNSUtil.getAsListOfMap(info, "/replies_tree[0]/authority")
	 * 
	 * }
	 * </p>
	 * 
	 * @param map
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Map<String, Object>> getAsListOfMap(Map<String, Object> map, String path) {
		Object result = getObject(map, path);
		if (result instanceof ArrayList) {
			for (Object element : ((ArrayList<Object>) result)) {
				if (!(element instanceof Map))
					return null;
			}
			return (ArrayList<Map<String, Object>>) result;
		}
		return null;
	}
}
