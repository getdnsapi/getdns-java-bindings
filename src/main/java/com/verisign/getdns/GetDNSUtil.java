package com.verisign.getdns;

import java.util.ArrayList;
import java.util.HashMap;

public class GetDNSUtil {
	public static String gettype(HashMap<String, Object> info){
		if(info != null){
			ArrayList replies_tree = (ArrayList) info.get("replies_tree");	
			 if(replies_tree != null && replies_tree.size() > 0){
				 HashMap<String, Object> answers =  (HashMap<String, Object>) replies_tree.get(0);
				 if(answers != null){
					 ArrayList classes = (ArrayList) answers.get("answer");
					 if(classes !=null){
					  HashMap<String, Object> class1 = (HashMap<String, Object>) classes.get(0);
					  if(class1 != null ){
						 return class1.get("type").toString();
					  }
					 }
							 
				 }
				 
			 }
			 
			
		}
		
		return null;
	}
}
