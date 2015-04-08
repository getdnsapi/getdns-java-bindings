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
    GETDNS_UNKNOWN(-1),
	
	
	GETDNS_DNSSEC_SECURE(400),
	GETDNS_DNSSEC_BOGUS (401),
	GETDNS_DNSSEC_INDETERMINATE(402),
	GETDNS_DNSSEC_INSECURE(403),
	GETDNS_DNSSEC_NOT_PERFORMED(404);
	
	
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
