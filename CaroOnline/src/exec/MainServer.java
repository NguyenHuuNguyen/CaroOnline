package exec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import bll.BLL;
import dal.DAL;
import dto.Account;
import dto.Requests;
import dto.Responses;

public class MainServer {
	//public static Vector<CaroClient> clients = new Vector<>();
	MainServer(){
		try {
			// reset status của các account về false khi khởi động
			BLL.Instance().resetAllAccountStatus();
			ServerSocket server = new ServerSocket(14972);
			System.out.println("Server is started");
			System.out.println("Waiting for client...");
			while(true) {
				Socket sk = server.accept();
				CaroClient a = new CaroClient(sk);
				//clients.add(a);
				a.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MainServer();
	}
}
class CaroClient extends Thread{
	Account user = null;
	Socket sk;
	DataInputStream dis;
	DataOutputStream dos;
	boolean connectable = true;
	String c;
	public CaroClient(Socket SK) {
		try {
			sk = SK;
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());
			c = sk.getRemoteSocketAddress().toString();
			System.out.println(c + " connected");
		}
		catch(Exception e) {
			System.out.println(e.toString());
			connectable = false;
		}
	}
	public void run() {
		try {
			while(connectable) {
				String s = dis.readUTF();
				//
				// xử lý request ở đây
				//
				if (s.equals(Requests.Login)) {
					loginRequest();
				}
				else if (s.equals(Requests.Logout)) {
					logoutRequest();
				}
				
				
				else dos.writeUTF(Responses.BadRequest);
			}
		}
		catch(Exception e) {
			// bắt được lỗi, đóng kết nối
			//System.out.println(e.toString());
			if (user != null)
				DAL.Instance().setAccountStatus(user.getId_user(), false);
			connectable = false;
			System.out.println(c + " disconnected");
			try {
				sk.close();
			}
			catch (Exception e1){}
		}
	}
	private void loginRequest() throws Exception
	{
		String username = dis.readUTF();
		String password = dis.readUTF();
		if (BLL.Instance().checkLogin(username, password))
		{
			if (BLL.Instance().getAccountByUsername(username).isStatus() == false) {
				dos.writeUTF(Responses.LoginSuccess);
				user = DAL.Instance().getAccountByUsername(username);
				BLL.Instance().setAccountStatus(user.getId_user(), true);
			}
			else {
				dos.writeUTF(Responses.AccountIsInUse);
			}
		}
		else dos.writeUTF(Responses.LoginFail);
		dos.flush();
	}
	private void logoutRequest() throws Exception
	{
		DAL.Instance().setAccountStatus(user.getId_user(), false);
		sk.close();
	}
}
