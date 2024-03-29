package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Account;
import dto.Responses;

public class DAL {
	String url = "jdbc:mysql://localhost:3306/caro_pbl4";
	//String url = "jdbc:mysql://127.0.0.1:3307/caro_pbl4";
	String user = "root";
	String password = "";
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	//
	//singleton
	//
	private DAL() {}
	private static DAL _Instance;
	public static DAL Instance() {
		if (_Instance == null) {
			_Instance = new DAL();
		}
		return _Instance;
	}
	//
	
	public boolean checkLogin(String username, String pass)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("select * from account where username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at checkLogin()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
		return false;
	}
	
	public void resetAllAccountStatus()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set Status = false where id_user = id_user");
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at resetAllAccountStatus()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	
	public Account getAccountByUsername(String username)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("select * from account where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) throw new Exception("username not found");
			return new Account(rs.getInt("id_user"), rs.getString("username"), rs.getString("password")
					, rs.getBoolean("status"), rs.getString("display_name")
					, rs.getInt("battles_won"), rs.getInt("battles_lost"), rs.getInt("idAvatar"));
		}
		catch(Exception e) {
			System.out.println("DataBase Error at getAccountByUsername()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
		return null;
	}
	
	public void setAccountStatus(int id, boolean status)
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set status = ? where id_user = ?");
			ps.setBoolean(1, status);
			ps.setInt(2, id);
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at setAccountStatus()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	//
	public void changeDisplayNameUser(String display_name, int id_user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set display_name = ? where id_user = ?");
			ps.setString(1, display_name);
			ps.setInt(2, id_user);
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at changeDisplayNameUser()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void changePassUser(String passnew, int id_user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set password = ? where id_user = ?");
			ps.setString(1, passnew);
			ps.setInt(2, id_user);
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at changePassUser()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public String createNewUser(String username, String displayname, String pass, int id_ava) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("select * from account where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				ps = conn.prepareStatement("insert into account values (0,?,?,?,false,0,0,?)");
				ps.setString(1, username);
				ps.setString(2, pass);
				ps.setString(3, displayname);
				ps.setInt(4, id_ava);
				ps.executeUpdate();
				return "ok";
			}
			else return "no";
		}
		catch(Exception e) {
			System.out.println("DataBase Error at createNewUser()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
		return "";
	}
	public void changeAvatar(int id_ava, String _username) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set idAvatar = ? where username = ?");
			ps.setInt(1, id_ava);
			ps.setString(2, _username);
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at changeAvatar()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public int getBattlesWon(int id_user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("select * from account where id_user = ?");
			ps.setInt(1, id_user);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) throw new Exception("id_user not found");
			return rs.getInt("battles_won");
		}
		catch(Exception e) {
			System.out.println("DataBase Error at getBattlesWon()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
		return 0;
	}
	public int getBattlesLost(int id_user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("select * from account where id_user = ?");
			ps.setInt(1, id_user);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) throw new Exception("id_user not found");
			return rs.getInt("battles_lost");
		}
		catch(Exception e) {
			System.out.println("DataBase Error at getBattlesLost()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
		return 0;
	}
	public void setbattlesWon(int id_user, int n) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set battles_won = ? where id_user = ?");
			ps.setInt(1, n);
			ps.setInt(2, id_user);
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at setbattlesWon()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	public void setbattlesLost(int id_user, int n) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement("update account set battles_lost = ? where id_user = ?");
			ps.setInt(1, n);
			ps.setInt(2, id_user);
			ps.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("DataBase Error at setbattlesLost()");
		}
		finally {
			try { rs.close(); } catch (Exception e) { /* Ignored */ }
		    try { ps.close(); } catch (Exception e) { /* Ignored */ }
		    try { conn.close(); } catch (Exception e) { /* Ignored */ }
		}
	}
	
	public static void main(String[] args) {
		try {
			//System.out.println(DAL.Instance().checkLogin("huunguyen", "4869"));
//			System.out.println(DAL.Instance().getAccountByUsername("huunguyen").toString());
//			DAL.Instance().setAccountStatus(1, true);
//			System.out.println(DAL.Instance().getAccountByUsername("huunguyen").toString());
//			DAL.Instance().resetAllAccountStatus();
//			System.out.println(DAL.Instance().getAccountByUsername("huunguyen").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
