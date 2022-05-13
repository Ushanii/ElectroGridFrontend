package com;

import java.sql.*;

public class Payment  {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	
	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Account Number</th>"+ "<th>Payment Amount</th>"
					+ "<th>Payment Date</th>" + "<th>Update</th>" + "<th>Delete</th>";
			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentId = Integer.toString(rs.getInt("paymentId"));
				String accountNum = rs.getString("accountNum");
				String amount = Double.toString(rs.getDouble("amount"));
				String date = rs.getString("date");
				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + paymentId
						+ "'>" + accountNum + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + date + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='"+paymentId+"'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertPayment(String account, String amount, String date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment(`paymentId`,`accountNum`,`amount`,`date`)" + " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, account);			
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, date);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":	 \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String ID, String account, String amount, String date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET accountNum=?,amount=?,date=?WHERE paymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, account);			
			preparedStmt.setDouble(2, Double.parseDouble(amount));
			preparedStmt.setString(3, date);
			preparedStmt.setInt(4, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deletePayment(String paymentId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where paymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}