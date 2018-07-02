package validation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;

import connection.ConnectionI;
import connection.OracleConnection;
import daoOperation.InsertDao;
import daoOperation.InsertToDb;
import entityPojo_customer.Customer;

public class Rejection
{
	
	ValidationI vm=new ValidateMethods();
	HashSet<String> set=new HashSet<String>();
	
	
	public void recordLevel(String server,String str,Customer customer)
	{
		InsertDao dao=new InsertToDb();
		boolean code=vm.validCustomerCode(customer,set);
		set.add(customer.getCustomer_code());
		
		boolean name=vm.validCustomerName(customer);
		
		boolean pinCode=vm.validPinCode(customer);
		
		boolean record=vm.validRecordStatus(customer);
		
		boolean flag=vm.validFlag(customer);
		
		boolean email=vm.validEmail(customer);
		
		Connection conn=dao.connection();
		if(code&&name&&record&&pinCode&&flag&&email)
		{
			int rowsAffected=dao.inputbd(server, customer,conn);
			
			if(rowsAffected>0)
			System.out.println("done");
			
			else
				System.out.println("Some error ocuured");
			
			try 
			{
				conn.commit();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				FileWriter fw=new FileWriter("d:/errorLogFile.txt");
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(str);
				bw.newLine();
				bw.flush();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public void fileLevel(String server,String str,Customer customer)
	{
		InsertDao dao=new InsertToDb();
		boolean code=vm.validCustomerCode(customer,set);
		set.add(customer.getCustomer_code());
		
		boolean name=vm.validCustomerName(customer);
		
		boolean pinCode=vm.validPinCode(customer);
		
		boolean record=vm.validRecordStatus(customer);
		
		boolean flag=vm.validFlag(customer);
		
		boolean email=vm.validEmail(customer);
		
		Connection conn=dao.connection();
		
		if(code&&name&&pinCode&&flag&&email)
		{
			int rowsAffected=dao.inputbd(server, customer,conn);
		
		if(rowsAffected>0)
			System.out.println("done");
			else
				System.out.println("Some error ocuured");
		}
		else
		{
			try
			{
				FileWriter fw=new FileWriter("d:/errorLogFile.txt");
				BufferedWriter bw=new BufferedWriter(fw);
				bw.append(str);
				bw.newLine();
				bw.flush();
				conn.rollback();
				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			} 
			
		}
	}
}
