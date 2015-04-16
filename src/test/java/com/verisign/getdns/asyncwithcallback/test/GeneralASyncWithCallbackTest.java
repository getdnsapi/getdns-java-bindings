package com.verisign.getdns.asyncwithcallback.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.verisign.getdns.GetDNSException;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.IGetDNSCallback;
import com.verisign.getdns.IGetDNSContextAsyncWithCallback;
import com.verisign.getdns.RRType;
import com.verisign.getdns.test.ErrorCodeMatcher;

public class GeneralASyncWithCallbackTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetDNSAsyncWithCallback() throws ExecutionException, TimeoutException, InterruptedException {
		final IGetDNSContextAsyncWithCallback context = GetDNSFactory.createAsyncWithCallback(1, null);
		final long a = System.currentTimeMillis();
		try {
			final String domain = "getdnsapi.net";
			context.generalAsync(domain, RRType.valueOf("A"), null, new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					try {
						Thread.sleep(5000);
						assertNotNull(response);
						System.out.println("Completed processing for: " + domain);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			context.run();
			Thread.sleep(10000);
		} finally {
			System.out.println("total wating time:  " + (System.currentTimeMillis() - a));
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncCancelWithCallback() throws ExecutionException, TimeoutException, InterruptedException {
		final IGetDNSContextAsyncWithCallback context = GetDNSFactory.createAsyncWithCallback(1, null);
		final long a = System.currentTimeMillis();
		try {
			final String domain = "getdnsapi.net";
			long transactionId = context.generalAsync(domain, RRType.valueOf("A"), null, new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					try {
						Thread.sleep(5000);
						fail("After cancelling the request,this block shouldn't executed");
						System.out.println("Completed processing for: " + domain);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			context.cancelRequest(transactionId);
			context.run();
			Thread.sleep(10000);
		} finally {
			System.out.println("total waiting time:  " + (System.currentTimeMillis() - a));
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncCancelWithCallback1() throws ExecutionException, TimeoutException, InterruptedException {
		final IGetDNSContextAsyncWithCallback context = GetDNSFactory.createAsyncWithCallback(1, null);
		final long a = System.currentTimeMillis();
		try {
			thrown.expect(GetDNSException.class);
			thrown.expect(new ErrorCodeMatcher("GETDNS_RETURN_UNKNOWN_TRANSACTION"));
			final String domain = "getdnsapi.net";
			long transactionId = context.generalAsync(domain, RRType.valueOf("A"), null, new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					try {
						Thread.sleep(5000);
						fail("After cancelling the request,this block shouldn't executed");
						System.out.println("Completed processing for: " + domain);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			context.cancelRequest(transactionId);
			context.run();
			context.cancelRequest(transactionId);
			Thread.sleep(10000);
		} finally {
			System.out.println("total waiting time:  " + (System.currentTimeMillis() - a));
			context.close();
		}
	}

	@Test
	public void testGetDNSAsyncCancelWithCallback2() throws ExecutionException, TimeoutException, InterruptedException {
		final IGetDNSContextAsyncWithCallback context = GetDNSFactory.createAsyncWithCallback(1, null);
		final long a = System.currentTimeMillis();
		try {
			final String domain1 = "getdnsapi.net";
			final String domain2 = "verisigninc.com";
			long transactionId = context.generalAsync(domain1, RRType.valueOf("A"), null, new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					try {
						Thread.sleep(5000);
						fail("After cancelling the request,this block shouldn't executed");
						System.out.println("Completed processing for: " + domain1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			context.cancelRequest(transactionId);
			context.run();
			context.generalAsync(domain2, RRType.valueOf("A"), null, new IGetDNSCallback() {

				@Override
				public void handleResponse(HashMap<String, Object> response, RuntimeException exception) {
					try {
						Thread.sleep(5000);
						assertNotNull(response);
						System.out.println("Completed processing for: " + domain2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			context.run();
			Thread.sleep(10000);
		} finally {
			System.out.println("total waiting time:  " + (System.currentTimeMillis() - a));
			context.close();
		}
	}
}
