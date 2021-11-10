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
	public static Vector<CaroClient> clients = new Vector<>();
	MainServer(){
		try {
			// reset status của các account về false khi khởi động
			BLL.Instance().resetAllAccountStatus();
			ServerSocket server = new ServerSocket(14972);
			System.out.println("Server is started");
			while(true) {
				System.out.println("Waiting for client...");
				Socket sk = server.accept();
				System.out.println("client accepted");
				CaroClient a = new CaroClient(sk);
				clients.add(a);
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
	public CaroClient(Socket SK) {
		try {
			sk = SK;
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());
		}
		catch(Exception e) {
			System.out.println(e.toString());
			connectable = false;
		}
	}
	public void run() {
		try {
			while(true) {
				String s = dis.readUTF();
				//
				// xử lý request ở đây
				//
				if (s.equals(Requests.Login)) {
					loginRequest();
					return;
				}
				
				
				dos.writeUTF(Responses.BadRequest);
			}
		}
		catch(Exception e) {
			// bắt được lỗi, đóng kết nối
			System.out.println(e.toString());
			connectable = false;
			try {
				if (user != null) 
					DAL.Instance().setAccountStatus(user.getId_user(), false);
				sk.close();
			}
			catch (Exception e1){}
		}
	}
	private void loginRequest() throws Exception
	{
		String username = dis.readUTF();
		String password = dis.readUTF();
		try {
			if (BLL.Instance().checkLogin(username, password))
			{
				dos.writeUTF(Responses.LoginSuccess);
				user = DAL.Instance().getAccountByUsername(username);
				DAL.Instance().setAccountStatus(user.getId_user(), true);
			}
			else dos.writeUTF(Responses.LoginFail);
		}
		catch(Exception e) {
			dos.writeUTF(Responses.DataBaseError);
		}
	}
}
