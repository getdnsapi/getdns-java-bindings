package com.verisign.getdns;


public class GetDNSFactory {

//	public static final String SET_FROM_OS = "";
	/*public static GetDNSContext create(HashMap<GetDNSPropertyName, Object> properties){
		return new GetDNSContext((Integer) properties.get(GetDNSPropertyName.setFromOs));
	}*/
	public static IGetDNSContext create(int setFromOs){
		return new GetDNSContext(setFromOs);
	}
}
