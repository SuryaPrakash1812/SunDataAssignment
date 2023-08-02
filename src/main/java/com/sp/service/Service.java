package com.sp.service;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sp.model.Customer;

public class Service {
   ;

	public String authentication(String loginId, String password)
	{
		if(loginId.equals("test@sunbasedata.com") && password.equals("Test@123")) {
		String responseBody = null;
		String token=null;
		try {
			URL u=new URL( "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp");
			HttpsURLConnection con=  (HttpsURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

			  String requestBody = "{\"login_id\": \"" + loginId + "\", \"password\": \"" + password + "\"}";
			  
			  try (OutputStream os = con.getOutputStream()) {
	                os.write(requestBody.getBytes());
	                os.flush();
	            }
			  
			  
			  
			  int responseCode = con.getResponseCode();
			  System.out.println(responseCode);
	            if (responseCode == 200) {
	                // Read the response from the connection's input stream
	                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
	                    StringBuilder responseBuilder = new StringBuilder();
	                    String line;
	                    while ((line = br.readLine()) != null) {
	                        responseBuilder.append(line);
	                        // Assuming the response is in JSON format and contains a "token" field
	                         responseBody = responseBuilder.toString();
	                        System.out.println(responseBody);
	                      
	                    }
	                }
	              
	            JsonParser jsonParser = new JsonParser();
	            JsonElement jsonElement = jsonParser.parse(responseBody);

	            // Get the JSON object from the root element
	            JsonObject jsonObject = jsonElement.getAsJsonObject();

	            // Access the token from the JSON object
	             token = jsonObject.get("access_token").getAsString();

	            System.out.println("Token: " + token);
               
	    		return token;
	            } 
		
                else {
                   // If the request was not successful, handle the error
                   System.err.println("Error occurred during authentication. Status code: " + responseCode);
                  
               }
	            
	            }catch (IOException e) {
	                System.err.println("Error occurred during authentication: " + e.getMessage());
	            }
		return token;
		
			            
		}
		return "";
                
	}
	
	
	public ArrayList<Customer> getCustomerList(String token)
	{

	    StringBuilder responseBuilder = new StringBuilder();
		 ArrayList<Customer> modifiedCustomerList = null;
		ArrayList<Customer>  customerList= null;
		 HttpURLConnection connection=null;
		  String bearerToken = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";

	        try {
	         
	        	 URL url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp" + "?cmd=get_customer_list");
	              connection = (HttpURLConnection) url.openConnection();
	             connection.setRequestMethod("GET");

	             // Set the Authorization header with the bearer token
	             connection.setRequestProperty("Authorization", "Bearer " + token);

	             // Read the response from the connection's input stream
	             try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	                 
	            	 char[] buffer = new char[10000000];
	            	 int bytesRead;
	            	 while ((bytesRead = br.read(buffer)) != -1) {
	            	     responseBuilder.append(buffer, 0, bytesRead);
	            	 }

	             }
	             String response=   responseBuilder.toString();
	             System.out.println(response);
	            // Parse the JSON response into a list of Customer objects using Gson
	            Gson gson = new GsonBuilder().setLenient().create();
	            ObjectMapper objectMapper = new ObjectMapper();
	            customerList = (ArrayList<Customer>) objectMapper.readValue(response, new TypeReference<List<Customer>>() {});
	           
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        connection.disconnect();
	        return customerList;

	}
	
	public String addUser(String token,String FirstName, String LastName, String Street, String Address, String City, String State,String Email, String Phone)
	{
		  URL url;
		try {
			url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create");
			 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("POST");
		        connection.setRequestProperty("Content-Type", "application/json");
		        connection.setRequestProperty("Authorization", "Bearer " +token);
		        connection.setDoOutput(true);
		        String requestBody =  "{\"first_name\":\"" + FirstName + "\",\"last_name\":\"" + LastName + "\",\"street\":\"" + Street + "\",\"address\":\"" + Address + "\",\"city\":\"" + City + "\",\"state\":\"" + State + "\",\"email\":\"" + Email + "\",\"phone\":\"" + Phone + "\"}";


		        OutputStream os = connection.getOutputStream(); 
	            os.write(requestBody.getBytes());
	            os.flush();
	            // Check the response status code
		        int statusCode = connection.getResponseCode();
		        if (statusCode == HttpURLConnection.HTTP_CREATED) {
		            // Successfully created
		            return "Successfully Created";
		        } else if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
		            // First Name or Last Name is missing
		            return "First Name or Last Name is missing";
		        } else {
		            // Other error occurred
		            return "Error occurred during customer creation. Status code: " + statusCode;
		        }
		} catch (MalformedURLException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
		return "";

	    }
	public String delete(String uuid,String token)
	{
		
		  URL url;
		try {
			url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp"+"?cmd=delete"+"&uuid="+uuid);
			  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("POST");

		        // Set the required headers
		        connection.setRequestProperty("Authorization", "Bearer " + token);
		        connection.setRequestProperty("Content-Type", "application/json");

		        // Enable output to send data in the request
		        connection.setDoOutput(true);

		        // Send the request
		        try (OutputStream os = connection.getOutputStream()) {
		           
		            os.flush();
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        // Get the response code to check if the request was successful
		        int responseCode = connection.getResponseCode();

		        if (responseCode == 200) {
		            System.out.println("Successfully deleted the customer");
		            return "Successfully deleted the customer";
		        } else if (responseCode == 500) {
		            System.err.println("Error occurred. Customer not deleted.");
		            return "Error occurred. Customer not deleted.";
		        } else if (responseCode == 400) {
		            System.err.println("UUID not found.");
		            return "UUID not found.";
		        } else {
		            System.err.println("Error occurred during HTTP request. Status code: " + responseCode);
		            return "Error occurred during HTTP request. Status code: ";
		        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      

	        // Create the request body with parameters
	       
			return token;
	    }
	public Customer getCostomer(String uuid,String token)
	{
	  StringBuilder responseBuilder = new StringBuilder();
		 ArrayList<Customer> modifiedCustomerList = null;
			ArrayList<Customer>  customerList= null;
			 HttpURLConnection connection=null;
			  String bearerToken = "dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM=";

		        try {
		         
		        	 URL url = new URL("https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp" + "?cmd=get_customer_list");
		              connection = (HttpURLConnection) url.openConnection();
		             connection.setRequestMethod("GET");

		             // Set the Authorization header with the bearer token
		             connection.setRequestProperty("Authorization", "Bearer " + token);

		             // Read the response from the connection's input stream
		             try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
		                 
		            	 char[] buffer = new char[10000000];
		            	 int bytesRead;
		            	 while ((bytesRead = br.read(buffer)) != -1) {
		            	     responseBuilder.append(buffer, 0, bytesRead);
		            	 }

		             }
		             String response=   responseBuilder.toString();
		             System.out.println(response);
		            // Parse the JSON response into a list of Customer objects using Gson
		            Gson gson = new GsonBuilder().setLenient().create();
		            ObjectMapper objectMapper = new ObjectMapper();
		            customerList = (ArrayList<Customer>) objectMapper.readValue(response, new TypeReference<List<Customer>>() {});
		          
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	for(Customer cus:customerList)
	{
	String id=	cus.getUuid();
	if(id.equals(uuid))
	{
		String uid=cus.getUuid();
		String first_name=cus.getFirst_name();
		 String last_name=cus.getLast_name();
		 String street=cus.getStreet();
		 String address=cus.getAddress();
		 String city=cus.getCity();
		String state=cus.getState();
		String email=cus.getEmail();
		String phone=cus.getPhone();
		Customer cc= new Customer(first_name,uid,last_name,street,address,city,state,email,phone);
		return cc;
		
	}
	}
	return null;
	
		
	}
	

	}
	
	

