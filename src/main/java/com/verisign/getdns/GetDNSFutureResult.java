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

import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * This is used for Asyc call implementation
 *
 */
public class GetDNSFutureResult implements Future<HashMap<String, Object>>, IGetDNSCallback {

	private HashMap<String, Object> response = null;
	private GetDNSException exception;
	private Long transactionId;
	private GetDNSContext context = null;
	private boolean isCancelled = false;

	public GetDNSFutureResult(GetDNSContext context) {
		this.context = context;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) throws GetDNSException {
		context.cancelRequest(transactionId);
		isCancelled = true;
		return isCancelled;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public boolean isDone() {
		return response != null;
	}

	@Override
	public HashMap<String, Object> get() throws InterruptedException, ExecutionException {
		if (exception != null)
			throw new ExecutionException(exception);
		if(isCancelled)
			throw new CancellationException("This request is already cancelled");
		return response;
	}

	@Override
	public HashMap<String, Object> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException {
		if (unit == null)
			throw new IllegalArgumentException("Timeunit cannot be null");
		
		synchronized (this) {
			if (!isCancelled && response == null && exception == null)
				this.wait(unit.toMillis(timeout));
		}
		return get();
	}

	@Override
	public void handleResponse(HashMap<String, Object> response) {
		synchronized (this) {
			this.response = response;
			this.notify();
		}
	}

	@Override
	public void handleException(GetDNSException exception) {
		synchronized (this) {
			this.exception = exception;
			this.notify();
		}
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

}
