package com.verisign.getdns.test;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import com.verisign.getdns.GetDNSException;


public class ErrorCodeMatcher extends BaseMatcher<GetDNSException> {
	private final String expectedCode;

	public ErrorCodeMatcher(String expectedCode) {
		this.expectedCode = expectedCode;
	}

	public boolean matches(Object item) {
		GetDNSException e = (GetDNSException)item;
		return expectedCode.equals(e.getReturnCode().toString());
	}

	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}
}