package com.verisign.getdns.wrapper;

import java.util.HashMap;
import java.util.Map;

/*
 * TODO: Included a new error GETDNS_UNKNOWN for now, can be removed later.
 */
public enum GetDNSReturn{
	GETDNS_RETURN_GOOD(0),
	GETDNS_RETURN_GENERIC_ERROR(1),
    GETDNS_RETURN_BAD_DOMAIN_NAME(300),
    GETDNS_RETURN_BAD_CONTEXT(301),
    GETDNS_RETURN_CONTEXT_UPDATE_FAIL(302),
    GETDNS_RETURN_UNKNOWN_TRANSACTION(303),
    GETDNS_RETURN_NO_SUCH_LIST_ITEM(304),
    GETDNS_RETURN_NO_SUCH_DICT_NAME(305),
    GETDNS_RETURN_WRONG_TYPE_REQUESTED(306),
    GETDNS_RETURN_NO_SUCH_EXTENSION(307),
    GETDNS_RETURN_EXTENSION_MISFORMAT(308),
    GETDNS_RETURN_DNSSEC_WITH_STUB_DISALLOWED(309),
    GETDNS_RETURN_MEMORY_ERROR(310),
    GETDNS_RETURN_INVALID_PARAMETER(311),	
	GETDNS_UNKNOWN(-1);
	
	private final int value;

	private GetDNSReturn(int returnValue) {
		this.value = returnValue;
	}
	
    public int getValue() { return value; }
    
    private static final Map<Integer, GetDNSReturn> intToTypeMap = new HashMap<Integer, GetDNSReturn>();
    static {
        for (GetDNSReturn type : GetDNSReturn.values()) {
            intToTypeMap.put(type.value, type);
        }
    }
    
    public static GetDNSReturn fromInt(int i) {
    	GetDNSReturn type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null) 
            return GetDNSReturn.GETDNS_UNKNOWN;
        return type;
    }
}
