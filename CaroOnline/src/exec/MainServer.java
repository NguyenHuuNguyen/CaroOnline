package exec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import bll.BLL;
import dal.DAL;
import dto.Account;
import dto.Requests;
import dto.Responses;
import dto.Room;

public class MainServer {

	static Vector<Room> vtRoom = new Vector<>();
	static int idroom = 1;
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
		Room a = new Room(0, "", "", "", "",0,"","");
		vtRoom.add(a);
		new MainServer();
	}
}
class CaroClient extends Thread{
	Account user = null;
	Socket sk;
	DataInputStream dis;
	DataOutputStream dos;
	boolean connectable = true;
	String ip_address;
	public CaroClient(Socket SK) {
		try {  
			sk = SK;
			dis = new DataInputStream(sk.getInputStream());
			dos = new DataOutputStream(sk.getOutputStream());
			//c = sk.getRemoteSocketAddress().toString();
			//ip_address = sk.getInetAddress().getLocalHost().getHostAddress();
			InetSocketAddress socketAddress = (InetSocketAddress) sk.getRemoteSocketAddress();
			ip_address = socketAddress.getAddress().getHostAddress();
			System.out.println(ip_address + " connected");
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
				else if (s.equals(Requests.CreateRoom)) {
					createRoom();
				}
				else if(s.equals(Requests.GetAllRoom)) {
					getAllRoom();
				}
				else if(s.equals(Requests.GetRoomByName)) {
					getIDRoomByName();
				}
				else if(s.equals(Requests.GetRoomByID)) {
					getRoomByID();
				}
				else if(s.equals(Requests.GetAccountbyUsername)) {
					getAccountbyUsername();
				}
				else if(s.equals(Requests.ChangeDisplayName)) {
					changeDisplayNameUser();
				}
				else if(s.equals(Requests.AddCurrentPlayer)) {
					addCurrentPlayer();
				}
				else if(s.equals(Requests.AddCurrentSpectator)) {
					addCurrentSpectaror();
				}
				else if(s.equals(Requests.ChangePass)) {
					changePassUser();
				}
				else if(s.equals(Requests.Player2Disconnected)) {
					Player2Disconnected();
				}
				else if(s.equals(Requests.HostDisconnected)) {
					HostDisconnected();
				}
				else if(s.equals(Requests.CreateNewUser)) {
					createNewUser();
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
			System.out.println(ip_address + " disconnected");
			try {
				sk.close();
			}
			catch (Exception e1){}
		}
	}
	private void createNewUser() throws Exception{
		String username = dis.readUTF();
		String displayname = dis.readUTF();
		String password = dis.readUTF();
		String tb = BLL.Instance().createNewUser(username, displayname, password);
		if (tb.equals("ok")) {
			dos.writeUTF(Responses.UserCreate_Success);
		}
		else if (tb.equals("no")) {
			dos.writeUTF(Responses.UserCreate_Fail);
		}
	}
	private void HostDisconnected() throws Exception {
		int idroom = dis.read();
		for (Room r : MainServer.vtRoom) {
			if (r.getRoomID() == idroom) {
				MainServer.vtRoom.remove(r);
				break;
			}
				
		}
	}
	private void Player2Disconnected() throws Exception {
		int idroom = dis.read();
		for (Room r : MainServer.vtRoom) {
			if (r.getRoomID() == idroom)
				r.setCurrentPlayers(r.getCurrentPlayers() - 1);
		}
	}
	private void changePassUser() throws Exception{
		String username = dis.readUTF();
		String passold = dis.readUTF();
		String passnew = dis.readUTF();
		Account a = BLL.Instance().getAccountByUsername(username);
		if (a.getPassword().equals(passold)) {
			BLL.Instance().changePassUser(passnew, a.getId_user());
			dos.writeUTF(Responses.ChangePassSuccess);
		}
		else dos.writeUTF(Responses.ChangePassFail);
		
	}
	private void addCurrentSpectaror() throws Exception{
		int id = dis.read();
		for (Room i : MainServer.vtRoom) {
			if (i.getRoomID() == id) {
				i.setCurrentSpectators(i.getCurrentSpectators() + 1);
				System.out.println(i.getCurrentSpectators());
			}
		}
	}
	private void addCurrentPlayer() throws Exception{
		int id = dis.read();
		for (Room i : MainServer.vtRoom) {
			if (i.getRoomID() == id) {
				i.setCurrentPlayers(i.getCurrentPlayers() + 1);
				//System.out.println(i.getCurrentPlayers());
			}
		}
	}
	private void changeDisplayNameUser() throws Exception{
		String displayname = dis.readUTF();
		int id_user = dis.read();
		BLL.Instance().changeDisplayNameUser(displayname, id_user);
	}
	private void getAccountbyUsername() throws Exception{
		String username = dis.readUTF();
		Account a = BLL.Instance().getAccountByUsername(username);
		dos.write(a.getId_user());
		dos.writeUTF(a.getDisplayName());
		dos.writeUTF(a.getPassword());
		dos.write(a.getBattleLost());
		dos.write(a.getBattleWon());
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
	}
	private void createRoom() throws Exception{
		// TODO Auto-generated method stub
		String userName = dis.readUTF();
		String roomname = dis.readUTF();
		String roompass = dis.readUTF();
		String mode = dis.readUTF();
		String alowspectator = dis.readUTF();
		int check = 1;
		for (Room i : MainServer.vtRoom) {
			if (i.getRoomName().equals(roomname)) {
				check = check +1;
			}
		}
		if (roomname.equals("")) dos.writeUTF(Responses.RoomCreate_Fail);
		else if (check == 1){
			String displayName = BLL.Instance().getDisplayName(userName);
			Room a = new Room(MainServer.idroom, roomname, roompass, displayName, ip_address, 16969,mode, alowspectator);
			MainServer.vtRoom.add(a);
			MainServer.idroom++;
			dos.writeUTF(Responses.RoomCreate_Success);
		}
		else dos.writeUTF(Responses.RoomName_Have);
	}
	private void getAllRoom() throws Exception{
		for (Room i : MainServer.vtRoom) {
			dos.writeUTF(Responses.Continue);
			dos.write(i.getRoomID());
			dos.writeUTF(i.getRoomName());
			if (i.getPassword().equals("")) dos.writeUTF("Không");
			else dos.writeUTF("Có");
			//dos.writeUTF(i.getMode());
			dos.writeUTF(i.getAlowSpectator_String());
			dos.writeUTF(i.getJoinButton());
		}
		dos.writeUTF(Responses.Theend);
	}
	private void getIDRoomByName() throws Exception{
		// TODO Auto-generated method stub
		String roomname = dis.readUTF();
		for (Room i : MainServer.vtRoom) {
			if (i.getRoomName().equals(roomname)) {
				dos.write(i.getRoomID());
			}
		}
	}
	private void getRoomByID() throws Exception{
		// TODO Auto-generated method stub
		int roomid = dis.read();
		for (Room i : MainServer.vtRoom) {
			if (i.getRoomID() == roomid) {
				dos.writeUTF("ok");
				dos.writeUTF(i.getRoomName());
				dos.writeUTF(i.getPassword());
				dos.writeUTF(i.getHostDisplayName());
				dos.writeUTF(i.getHostIPAddress());
				dos.write(i.getCurrentPlayers());
				dos.write(i.getCurrentSpectators());
				dos.writeUTF(i.getMode());
				dos.writeUTF(i.getAlowSpectator_String());
				//dos.writeUTF(i.getJoinButton());
				return;
			}
		}
		dos.writeUTF(Requests.RoomDoNotExist);
	}
}