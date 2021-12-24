package dto;

public class Room {
	int RoomID;                 //unchangeable
	String RoomName;
	String Password;
	String HostDisplayName;     //unchangeable
	String HostIPAddress = "localhost";       //unchangeable
	int HostPort = 16969;               //unchangeable
	int CurrentPlayers = 0;
	int CurrentSpectators = 0;
	//
	String Mode;
	String AlowSpectator_String;
	String JoinButton;
	
	public Room(int _roomID, String _RoomName, String _Password, String _hostDisplayName, String _hostIPAddress, 
			int _hostport,String _mode, String _AlowSpectator) {
		super();
		RoomID = _roomID;
		RoomName = _RoomName;
		Password = _Password;
		HostDisplayName = _hostDisplayName;
		HostIPAddress = _hostIPAddress;
		CurrentPlayers = CurrentPlayers + 1;
		CurrentSpectators = 0;
		HostPort = 16969;
		Mode = _mode;
		AlowSpectator_String = _AlowSpectator;
		JoinButton = "THAM GIA "+ _roomID;
	}
	public int getRoomID() {
		return RoomID;
	}
	public void setRoomID(int roomID) {
		RoomID = roomID;
	}
	public String getRoomName() {
		return RoomName;
	}
	public void setRoomName(String roomName) {
		RoomName = roomName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getHostDisplayName() {
		return HostDisplayName;
	}
	public void setHostDisplayName(String hostDisplayName) {
		HostDisplayName = hostDisplayName;
	}
	public String getHostIPAddress() {
		return HostIPAddress;
	}
	public void setHostIPAddress(String hostIPAddress) {
		HostIPAddress = hostIPAddress;
	}
	public int getHostPort() {
		return HostPort;
	}
	public void setHostPort(int hostPort) {
		HostPort = hostPort;
	}
	public int getCurrentPlayers() {
		return CurrentPlayers;
	}
	public void setCurrentPlayers(int currentPlayers) {
		CurrentPlayers = currentPlayers;
	}
	public int getCurrentSpectators() {
		return CurrentSpectators;
	}
	public void setCurrentSpectators(int currentSpectators) {
		CurrentSpectators = currentSpectators;
	}
	@Override
	public String toString() {
		return "Room [RoomID=" + RoomID + ", RoomName=" + RoomName + ", Password=" + Password + ", HostDisplayName="
				+ HostDisplayName + ", HostIPAddress=" + HostIPAddress + ", HostPort=" + HostPort + ", GameMode="
				+ Mode + ", CurrentPlayers=" + CurrentPlayers + ", CurrentSpectators=" + CurrentSpectators
				+ ", AlowSpectator=" + AlowSpectator_String + "]";
	}
	public String getMode() {
		return Mode;
	}
	public void setMode(String _mode) {
		Mode = _mode;
	}
	public String getAlowSpectator_String() {
		return AlowSpectator_String;
	}
	public void setAlowSpectator_String(String _aspec) {
		AlowSpectator_String = _aspec;
	}
	public String getJoinButton() {
		return JoinButton;
	}
}