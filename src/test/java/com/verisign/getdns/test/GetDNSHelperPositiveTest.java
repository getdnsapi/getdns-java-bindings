package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

import com.verisign.getdns.GetDNSContext;

public class GetDNSHelperPositiveTest {

	// Devanagari Script
	final String unicode = "verisign.कॉम";
	final String ascii = "verisign.xn--11b4c3d";

	// Japanese Script
	final String unicode1 = "オンラインショップ.コム";
	final String ascii1 = "xn--eckl4b8bzf2dl3fc.xn--tckwe";

	// Russian Script
	final String unicode2 = "онлайнмагазин.ком";
	final String ascii2 = "xn--80aaalvgcolgdgb.xn--j1aef";

	@Test
	public void GetDNSUnicodeToAsciitest() {
		String ascii = GetDNSContext.ConvertUnicodeToAscii(unicode);
		assertNotNull(ascii);
		assertEquals(this.ascii, ascii);
	}

	@Test
	public void GetDNSAsciiToUnicodetest() {
		String unicode = GetDNSContext.ConvertAsciiToUnicode(ascii);
		assertNotNull(unicode);
		assertEquals(this.unicode, unicode);
	}

	@Test
	public void GetDNSUnicodeToAsciitest1() {
		String ascii1 = GetDNSContext.ConvertUnicodeToAscii(unicode1);
		assertNotNull(ascii1);
		assertEquals(this.ascii1, ascii1);
	}

	@Test
	public void GetDNSAsciiToUnicodetest1() {
		String unicode1 = GetDNSContext.ConvertAsciiToUnicode(ascii1);
		assertNotNull(unicode1);
		assertEquals(this.unicode1, unicode1);
	}

	@Test
	public void GetDNSUnicodeToAsciitest2() {
		String ascii2 = GetDNSContext.ConvertUnicodeToAscii(unicode2);
		assertNotNull(ascii2);
		assertEquals(this.ascii2, ascii2);
	}

	@Test
	public void GetDNSAsciiToUnicodetest2() {
		String unicode2 = GetDNSContext.ConvertAsciiToUnicode(ascii2);
		assertNotNull(unicode2);
		assertEquals(this.unicode2, unicode2);
	}

	@Test
	public void GetDnsRootTrustAnchorTest() {
		assertNotNull(GetDNSContext.GetDnsRootTrustAnchor());
	}
}
