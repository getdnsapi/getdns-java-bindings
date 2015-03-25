package com.verisign.getdns;

import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GetDNSHelperNegetiveTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Devanagari Script
	final String unicode = null;
	final String ascii = null;

	// Japanese Script
	final String unicode1 = "xn--オンラインショップ.コム";

	// final String ascii1 = "ecコムkl4b8bzf2dl3fc.xn--tckwe";

	@Test
	public void GetDNSUnicodeToAsciitest() {
		String ascii = GetDNSContext.ConvertUnicodeToAscii(unicode);
		assertNull(ascii);
	}

	@Test
	public void GetDNSAsciiToUnicodetest() {
		String unicode = GetDNSContext.ConvertAsciiToUnicode(ascii);
		assertNull(unicode);
	}

	@Test
	public void GetDNSUnicodeToAsciitest1() {
		try {
			thrown.expect(RuntimeException.class);
			GetDNSContext.ConvertUnicodeToAscii(unicode1);
		} finally {

		}
	}

}
