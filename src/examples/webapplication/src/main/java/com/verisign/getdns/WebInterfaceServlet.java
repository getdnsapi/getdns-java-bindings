package com.verisign.getdns;
import java.io.*;
import java.util.HashMap;
import java.lang.String;

import javax.servlet.*;
import javax.servlet.http.*;

public class WebInterfaceServlet extends HttpServlet {
	private static final String PATH_RECORD = "record";
	private static final String PARAM_DN = "domain";
	private static final String PARAM_TYPE = "rrtype";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		String message = "Please provide domain name and record type.";
		if (request.getPathInfo().contains(PATH_RECORD)) {
			String domainName = request.getParameter(PARAM_DN);
			String rrType = request.getParameter(PARAM_TYPE);
			if (domainName != null && !"".equals(domainName.trim())
					&& rrType != null && !"".equals(rrType.trim())) {
				GeneralSyncPositiveWithRecursive generalSync = new GeneralSyncPositiveWithRecursive();
				try {
					HashMap<String, Object> result = generalSync
							.getDNSSyncForResourceRecord(domainName,
									rrType.toUpperCase().trim());
					message = result.toString();
				} catch (Exception ex) {
					message = "Sorry, some error occured while processing, please check if your request is valid.";
				}
			}
		}

		PrintWriter out = response.getWriter();
		out.println(message);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
