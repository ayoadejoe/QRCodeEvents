package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSonConverter {
	private String jsonString;
	private Map<String, Object> allocated = new TreeMap<>();
	private List<String> allocatedIDs = new ArrayList<>();
	private List<String> unallocatedIDs = new ArrayList<>();
	private Object[][] tableAllocated;
	private int no;

	public Map<String, Object> iDs(String jsonString){
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			 for (Object o : jsonArray) {
				 try {
					 if(o instanceof String) {
					 JSONArray jsonO = new JSONArray((String)o);
						 for (Object inner : jsonO) {
							 JSONObject jsonLineItem = (JSONObject) inner;
						        String key = jsonLineItem.getString("ID");
						        Object val = jsonLineItem.get("FirstName" );
						        if(!val.equals(null)) {
						        	List<Object> items = new ArrayList<>();
						        	//items.add(++no);
						        	Object ID = key; items.add(ID);
							        Object firstname = jsonLineItem.get("FirstName" ); items.add(firstname);
							        Object lastname = jsonLineItem.get("LastName" ); items.add(lastname);
							        Object email = jsonLineItem.get("Email" ); items.add(email);
							       
							        Object mobile = jsonLineItem.get("MobileNumber" );items.add(mobile);
							        Object job = jsonLineItem.get("JobTitle" ); items.add(job);
							        Object company = jsonLineItem.get("Company" ); items.add(company);
							        
							        Object industry = jsonLineItem.get("Industry" ); items.add(industry);
							        Object otherind = jsonLineItem.get("OtherIndustry" ); items.add(otherind);
							        Object country = jsonLineItem.get("Country" ); items.add(country);
							        
							        Object attend = jsonLineItem.get("AttendingInPerson" ); items.add(attend);
							        Object passtype = jsonLineItem.get("PassType" ); items.add(passtype);
							        
							        allocated.put(key, items);
							        allocatedIDs.add(key);
						        }else {
						        	try {
						        		unallocatedIDs.add(key);
						        	}catch(NumberFormatException x) {
						        		
						        	}
						        }
						 }
					 }
				 }catch(ClassCastException | JSONException c) {
					 c.printStackTrace();
					 continue;
				 }
			    }
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		if(allocated.size()>0) {
			//get column size
			List<Object> items = (List<Object>) allocated.get(((String)allocatedIDs.get(0)));
			//System.out.println("items:"+items);
			tableAllocated = new Object[allocated.size()][items.size()];
			System.out.println("allocated size:"+allocated.size());
			Iterator<Entry<String, Object>> entries = allocated.entrySet().iterator();
			int row = 0;
			while(entries.hasNext()) {
				int column = 0;
				Entry<String, Object> entry = entries.next();
				
				List<Object> item = (List<Object>) entry.getValue();
				//System.out.println(row+ " -> "+item);
				for(Object value : item) {
					tableAllocated[row][column] = value;
					//System.out.println("["+row+"]"+"["+column+"] = "+value);
					column++;
				}
				row++; 
			}
		}
		return allocated;
		//System.out.println("allocated:"+allocated);
		//System.out.println("unallocated:"+unallocatedIDs);
		
	}

	public String getJsonString() {
		return jsonString;
	}


	public Object[][] getTableAllocated() {
		return tableAllocated;
	}

	public List<String> getUnallocatedIDs() {
		return unallocatedIDs;
	}


	public Map<String, Object> getAllocated() {
		return allocated;
	}
	
	
}
