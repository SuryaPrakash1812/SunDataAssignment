package com.sp.controller;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sp.model.Customer;
import com.sp.service.Service;

@Controller
public class Contr {
	Service service= new Service();
	String token;
	@RequestMapping("log")
	public ModelAndView dis(String loginId,String password)
	{
		ModelAndView mv=new ModelAndView();


	 token=	service.authentication(loginId, password);
	if(token.equals("dGVzdEBzdW5iYXNlZGF0YS5jb206VGVzdEAxMjM="))
	{			
		ArrayList<Customer>  customerList=service.getCustomerList(token);
		 mv.addObject("customer",customerList);
 		mv.addObject("token", token);
 		mv.setViewName("customerlist.jsp");
		
	}
	else
	{
		mv.setViewName("NF.jsp");
	}
		
		return mv;
		}
	
	
	
	
	@RequestMapping("addNew")
	public ModelAndView addNewUser(String token)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("token",token);
		mv.setViewName("AddUser.jsp");
		return mv;
		
	}
	
	
	
	@RequestMapping("add")
	public ModelAndView addNew( String token ,String FirstName, String LastName, String Street, String Address, String City, String State,String Email, String Phone)
	{ModelAndView mv=new ModelAndView();
	
		System.out.println(token);
		String status=service.addUser(token,FirstName,LastName,Street,Address,City,State,Email,Phone);
		if(status.equals("Successfully Created"))
		{
			  StringBuilder responseBuilder = new StringBuilder();
					ArrayList<Customer>  customerList= null;
					 HttpURLConnection connection=null;
					 

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
				           
				            mv.addObject("customer",customerList);
				    		mv.addObject("token", token);
				    		mv.setViewName("customerlist.jsp");
				    		return mv;
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
				        connection.disconnect();
		}
		else {
			mv.setViewName("NF.jsp");
		}
			return mv;
		
	}
	
	@RequestMapping("delete")
	public ModelAndView delete(String uuid,String token)
	{ModelAndView mv=new ModelAndView();
		
	String status=	service.delete(uuid,token);
		if(status.equals("Successfully deleted the customer"))
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
			           
			            mv.addObject("customer",customerList);
			    		mv.addObject("token", token);
			    		mv.setViewName("customerlist.jsp");
			    		return mv;
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			        connection.disconnect();

			
		}
		else {
			mv.setViewName("NF.jsp");
			
		}
 		return mv;
	}
	@RequestMapping("edit")
	public ModelAndView edit(String uuid, String token)
	{
		ModelAndView mv= new ModelAndView();
		Customer cc=service.getCostomer(uuid, token);
		mv.addObject("customer",cc);
		mv.addObject("token",token);
		mv.setViewName("AddUser.jsp");
		return mv;
		
	}
	@RequestMapping("update")
	public ModelAndView update(String uuid, String token,String FirstName, String LastName, String Street, String Address, String City, String State,String Email, String Phone)
	{
		ModelAndView mv= new ModelAndView();
		ArrayList<Customer> cus=service.getCustomerList(token);
		mv.addObject("customer",cus);
		mv.setViewName("customerlist.jsp");
		return mv;
		
	}
	
	}


