package com.verisign.getdns;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * This is used for Asyc call implementation
 * 
 * @author Pritvhi
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
	public boolean cancel(boolean mayInterruptIfRunning) throws GetDNSException{
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
		return isCancelled || response != null;
	}

	@Override
	public HashMap<String, Object> get() throws InterruptedException, ExecutionException {
		if (exception != null)
			throw new ExecutionException(exception);
		return response;
	}

	@Override
	public HashMap<String, Object> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException {
		if (unit == null)
			throw new IllegalArgumentException("Timeunit cannot be null");
		synchronized (this) {
			if (response == null && exception == null)
				this.wait(unit.toMillis(timeout));
		}
		return get();
		// throw new UnsupportedOperationException("We do not support this as yet");
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
