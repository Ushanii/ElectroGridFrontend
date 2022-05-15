package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@WebServlet("/PaymentsAPI")
public class PaymentsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	Payment paymentObj = new Payment();
	
    public PaymentsAPI() {
        super();        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
    
    
    // insert payments
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = paymentObj.insertPayment(request.getParameter("accountNum"),
				 		request.getParameter("amount"),				
				 		request.getParameter("date"));
		
		response.getWriter().write(output);		
	}
    
    
    // Convert request parameters to a Map
    private static Map getParasMap(HttpServletRequest request) {
    	
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
			
		} catch (Exception e) {
		}
		return map;
	}

    
    // update payments
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = paymentObj.updatePayment(paras.get("hidPaymentIDSave").toString(),
				 			paras.get("accountNum").toString(),
				 			paras.get("amount").toString(),
				 			paras.get("date").toString());	
		 
		response.getWriter().write(output);
	}

    
    // delete payments
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = paymentObj.deletePayment(paras.get("paymentId").toString());
		 
		response.getWriter().write(output); 
	}


}
