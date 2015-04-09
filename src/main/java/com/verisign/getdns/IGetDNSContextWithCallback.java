package com.verisign.getdns;


public interface IGetDNSContextWithCallback extends IGetDNSContextSync, IGetDNSContextAsyncWithCallback{
	void close();
}
