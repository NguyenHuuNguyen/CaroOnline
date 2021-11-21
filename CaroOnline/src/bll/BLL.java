package bll;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dal.DAL;
import dto.Account;

public class BLL {
	//
	//singleton
	//
	private BLL() {}
	private static BLL _Instance;
	public static BLL Instance() {
		if (_Instance == null) {
			_Instance = new BLL();
		}
		return _Instance;
	}
	//
	public boolean checkLogin(String username, String password)
	{
		return DAL.Instance().checkLogin(username, password);
	}
	
	public void resetAllAccountStatus()
	{
		DAL.Instance().resetAllAccountStatus();
	}
	
	public Account getAccountByUsername(String username)
	{
		return DAL.Instance().getAccountByUsername(username);
	}
	
	public void setAccountStatus(int id, boolean status)
	{
		DAL.Instance().setAccountStatus(id, status);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
