package com.verisign.getdns.example;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContextSync;
import com.verisign.getdns.RRType;

public class DaneCertVerification {

	/**
	 * This method retrieve certificate from server
	 * 
	 * @param hostname
	 * @param port
	 * @return server certificate
	 * @throws Exception
	 */
	public static Certificate GetCertificate(String hostname, int port) throws Exception {
		SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
		System.out.println("Connecting to " + hostname + " on port " + port + "\n");
		SSLSocket socket = (SSLSocket) factory.createSocket(hostname, port);
		socket.startHandshake();
		Certificate[] serverCerts = socket.getSession().getPeerCertificates();
		System.out.println("Retreived Server's Certificate Chain\n");
		Certificate myCert = serverCerts[0];
		socket.close();
		return myCert;
	}

	/**
	 * This method extract Tlsa record from dns response
	 * 
	 * @param port
	 * @param proto
	 * @param hostname
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static HashMap<String, Object> GetTlsaRecord(int port, String proto, String hostname)
			throws UnsupportedEncodingException {
		String queryString = "_" + port + "._" + proto + "." + hostname;
		String type = "TLSA";
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContextSync context = GetDNSFactory.createSync(1, options);
		try {
			HashMap<String, Object> info = context.generalSync(queryString, RRType.valueOf(type), null);
			System.out.println(GetDNSUtil.printReadable(info));
			if (info != null) {
				if (Integer.parseInt(info.get("status").toString()) == 900) {
					return getRdata(info);
				} else if (Integer.parseInt(info.get("status").toString()) == 901) {
					System.out.println("No such name: " + queryString + " with type: " + type + "\n");
				} else {
					System.out.println("Error in query GETDNS Status =" + info.get("status").toString() + "\n");
				}
			} else {
				System.out.println("No response form DNS SERVER\n");
			}
		} finally {
			context.close();
		}
		return null;
	}

	/*
	 * 3 -- Certificate usage 3 is used to specify a certificate, or the public
	 * key of such a certificate, that MUST match the end entity certificate given
	 * by the server in TLS. This certificate usage is sometimes referred to as
	 * "domain-issued certificate" because it allows for a domain name
	 * administrator to issue certificates for a domain without involving a
	 * third-party CA. The target certificate MUST match the TLSA record. The
	 * difference between certificate usage 1 and certificate usage 3 is that
	 * certificate usage 1 requires that the certificate pass PKIX validation, but
	 * PKIX validation is not tested for certificate usage 3.
	 */
	protected static HashMap<String, Object> getRdata(HashMap<String, Object> info) {
		int i = 0;
		HashMap<String, Object> rdata = null;
		while (i < GetDNSUtil.getAsListOfMap(info, "/replies_tree[0]/answer").size()) {
			rdata = GetDNSUtil.getAsMap(info, "/replies_tree[0]/answer[" + i + "]/rdata");
			if (rdata.containsKey("certificate_usage") && (int) rdata.get("certificate_usage") == 3) {
				break;
			} else {
				rdata = null;
			}
			i++;
		}
		return rdata;
	}

	/**
	 * This method converts bytes array to hex String
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
	 * Verify TLSA certificate from DNS response to SSl
	 * 
	 * @param cert
	 * @param tlsRdataMap
	 * @return
	 * @throws CertificateEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static Boolean VerifyTlsa(Certificate cert, HashMap<String, Object> tlsRdataMap)
			throws CertificateEncodingException, NoSuchAlgorithmException {
		String hexDataFromRdata = null;
		String hexDataFromCert;
		byte[] certData;
		byte[] hash;
		if (tlsRdataMap.get("certificate_association_data") != null) {
			hexDataFromRdata = bytesToHexString((byte[]) tlsRdataMap.get("certificate_association_data"));
		}
		if ((int) tlsRdataMap.get("selector") == 0) {
			certData = cert.getEncoded();
		} else if ((int) tlsRdataMap.get("selector") == 1) {
			certData = cert.getPublicKey().getEncoded();
		} else {
			throw new IllegalArgumentException("Selector type " + tlsRdataMap.get("selector") + " not recognized");
		}
		if ((int) tlsRdataMap.get("matching_type") == 0) {
			hexDataFromCert = bytesToHexString(certData);
		} else if ((int) tlsRdataMap.get("matching_type") == 1) {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(certData);
			hexDataFromCert = bytesToHexString(hash);
		} else if ((int) tlsRdataMap.get("matching_type") == 2) {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			hash = digest.digest(certData);
			hexDataFromCert = bytesToHexString(hash);
		} else {
			throw new IllegalArgumentException("Matching type " + tlsRdataMap.get("matching_type") + " not recognized");
		}
		return hexDataFromRdata.equals(hexDataFromCert);
	}

	/**
	 * Validates Dane Certificate received in GetDns response against SSL
	 * 
	 * @param args
	 *          Domain name, port (Ex: good.dane.verisignlabs.com 443)
	 */
	public static void main(String[] args) {
		final String TCP = "tcp";

		String hostname = "good.dane.verisignlabs.com";
		int port = 443;
		try {
			if (hostname != null && port > 0) {

				HashMap<String, Object> tlsRdataMap = GetTlsaRecord(port, TCP, hostname);
				if (tlsRdataMap != null) {
					Certificate cert = GetCertificate(hostname, port);
					System.out.println("Public Key from server: " + cert.getPublicKey() + "\n");
					if (VerifyTlsa(cert, tlsRdataMap)) {
						System.out.println("Matching TLSA record found\n");
					} else
						System.out.println("Matching TLSA record is not found\n");
				} else {
					System.out.println("Required Certificate is not found in DNS response \n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
