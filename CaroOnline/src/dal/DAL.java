package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.Account;

public class DAL {
	private Connection conn = null;
	//
	//singleton
	//
	private DAL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/caro_pbl4", "root", "");
		}
		catch(Exception e) {
			System.out.println("Cuold not connect to Database");
			System.exit(0);
		}
	}
	private static DAL _Instance;
	public static DAL Instance() {
		if (_Instance == null) {
			_Instance = new DAL();
		}
		return _Instance;
	}
	//
	
	public boolean checkLogin(String username, String password) throws Exception
	{
		PreparedStatement st = conn.prepareStatement("select * from account where username = ? and password = ?");
		st.setString(1, username);
		st.setString(2, password);
		ResultSet rs = st.executeQuery();
		return rs.next();
	}
	
	public void resetAllAccountStatus() throws Exception
	{
		PreparedStatement st = conn.prepareStatement("update account set status = 'false'");
		st.executeUpdate();
	}
	
	public Account getAccountByUsername(String username) throws Exception
	{
		PreparedStatement st = conn.prepareStatement("select * from account where username = ?");
		st.setString(1, username);
		ResultSet rs = st.executeQuery();
		if (!rs.next()) throw new Exception("username not found");
		return new Account(rs.getInt("id_user"), rs.getString("username"), rs.getString("password")
				, rs.getBoolean("status"), rs.getString("display_name")
				, rs.getInt("battles_won"), rs.getInt("battles_lost"));
	}
	
	public void setAccountStatus(int id, boolean status) throws Exception
	{
		PreparedStatement st = conn.prepareStatement("update account set status = ? where id_user = ?");
		st.setBoolean(1, status);
		st.setInt(2, id);
		st.executeUpdate();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println(DAL.Instance().checkLogin("huunguyen", "4869"));
			System.out.println(DAL.Instance().getAccountByUsername("huunguyen").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
