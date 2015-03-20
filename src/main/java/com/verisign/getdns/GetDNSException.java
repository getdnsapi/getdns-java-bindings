package com.verisign.getdns;

public class GetDNSException extends RuntimeException {
	private static final long serialVersionUID = 2810009795416124428L;

	public GetDNSException(GetDNSReturn returnCode) {
		this.returnCode = returnCode;
	}

	public GetDNSException(String message, GetDNSReturn returnCode) {
		super(message);
		this.returnCode = returnCode;
	}

	private GetDNSReturn returnCode;

	public GetDNSReturn getReturnCode() {
		return returnCode;
	}

	@Override
	public String toString() {
		return getMessage()+" Error code: " + returnCode;
	}

}
