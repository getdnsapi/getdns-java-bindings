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

/**
 * It contains predefined context options values, required at the time of
 * manual context configuration
 */
public enum ContextOptionValue {

	/* Namespace types */
	GETDNS_NAMESPACE_DNS(500), GETDNS_NAMESPACE_LOCALNAMES(501), GETDNS_NAMESPACE_NETBIOS(502), GETDNS_NAMESPACE_MDNS(503), GETDNS_NAMESPACE_NIS(
			504),

	/* Resolution types */
	GETDNS_RESOLUTION_STUB(520), GETDNS_RESOLUTION_RECURSING(521),

	/* Redirect policies */
	GETDNS_REDIRECTS_FOLLOW(530), GETDNS_REDIRECTS_DO_NOT_FOLLOW(531),

	/* Transport arrangements */
	GETDNS_TRANSPORT_UDP_FIRST_AND_FALL_BACK_TO_TCP(540), GETDNS_TRANSPORT_UDP_ONLY(541), GETDNS_TRANSPORT_TCP_ONLY(542), GETDNS_TRANSPORT_TCP_ONLY_KEEP_CONNECTIONS_OPEN(
			543),

	/* Append Names */
	GETDNS_APPEND_NAME_ALWAYS(550), GETDNS_APPEND_NAME_ONLY_TO_SINGLE_LABEL_AFTER_FAILURE(551), GETDNS_APPEND_NAME_ONLY_TO_MULTIPLE_LABEL_NAME_AFTER_FAILURE(
			552), GETDNS_APPEND_NAME_NEVER(553);

	private Integer value;

	private ContextOptionValue(int value) {

		this.value = value;
	}

	public Integer getvalue() {
		return value;
	}

}
