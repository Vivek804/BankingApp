package com.vivek.atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Working {
	float balance;
	private static Scanner scan = new Scanner(System.in);
	private static Connection con = null;
	
	public void menu() throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/Bank";
			String uname = "root";
			String password = "vivek";
			con = DriverManager.getConnection(url,uname,password);
			System.out.println("BANKING SYSTEM MENU");
			System.out.println("-------------------");
			System.out.println("1:CREATE ACCOUNT");
			System.out.println("2:WITHDRAWAL");
			System.out.println("3:CHECK BALANCE");
			System.out.println("4:DEPOSIT");
			System.out.println("5:EXIT");
			
			int option = scan.nextInt();
			switch (option)
			{
			case 1:
				System.out.println("Create Account");
				createAccount();
				break;
			case 2:
				System.out.println("Withdraw");
				withDrawMoney();
				break;
			case 3:
				System.out.println("Check Balance");
				checkBalance();
				break;
			case 4:
				System.out.println("Deposit");
				depositMoney();
				break;
			case 5:
				System.out.println("Exit");
				break;
			default:
				System.out.println("INVALID OPTION");
			}
//			menu();

			
		}catch(Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
	}	
		
	
	
	private void createAccount() throws SQLException{
		// TODO Auto-generated method stub
		String query = "insert into User values(?,?,?,?)";
		PreparedStatement prepareStatement = con.prepareStatement(query);
		
		System.out.println("Enter Account Details");
		
		System.out.println("Enter Account holder name");
		prepareStatement.setString(1, scan.next());
		
		System.out.println("Enter Account number (5 Digit)");
		prepareStatement.setInt(2, scan.nextInt());
		
		System.out.println("Enter Amount to deposit");
		prepareStatement.setFloat(3, scan.nextFloat());
		
		System.out.println("Enter password to login");
		prepareStatement.setString(4, scan.next());
		
		int rows = prepareStatement.executeUpdate();
		if(rows > 0) {
			System.out.println("Account created");
		}
		
		
		
	}



	public void checkBalance() throws SQLException{
		System.out.println("Enter Account number");
		int num = scan.nextInt();
		String query = "select username,amount from user where accountnumber =" + num;
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(query);
		while(result.next()) {
			String userdata = " ";
			for(int i =1;i<=2;i++) {
				userdata += result.getString(i) + ":";
			}System.out.println(userdata);
//			System.out.println(result.getFloat("amount"));
		}
		
		
	}

	
	public void depositMoney() throws SQLException {
		System.out.println("Enter Account number");
		int num = scan.nextInt();
		String q1 = "select amount from user where accountnumber = "+ num;
		System.out.println("Enter the money you want to deposit");
		float deposit = scan.nextFloat();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(q1);
//		System.out.println(result.getFloat("amount"));
		while(result.next()) {
			float totalamount = result.getFloat("amount") + deposit;
			String query = "update user set amount = "+totalamount+" where accountnumber = "+num;
			PreparedStatement prepareStatement = con.prepareStatement(query);
			
//			prepareStatement.setFloat(1, totalamount);
			int rows =prepareStatement.executeUpdate();
			if(rows>0) {
				System.out.println("Money Deposited");
			}
		}
		
		
		
		
	}
	
	public void withDrawMoney() throws SQLException{
		System.out.println("Enter Account number");
		int num = scan.nextInt();
		System.out.println("Enter the amount you want to withdraw");
		float withdraw = scan.nextFloat();
		String q1 = "select amount from user where accountnumber = "+num;
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(q1);
		while(result.next()) {
			float amount = result.getFloat("amount");
			if(withdraw > amount) {
				System.out.println("Insufficient balance");
				break;
			}
			amount -= withdraw;
			String query = "update user set amount = "+amount+"where accountnumber = "+num;
			PreparedStatement preparedStatement = con.prepareStatement(query);
			int rows = preparedStatement.executeUpdate();
			if(rows>0) {
				System.out.println("Money withdrawn");
			}
		}
	}
	
	


}
