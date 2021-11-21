package dto;

public class Room {
	int RoomID;                 //unchangeable
	String RoomName;
	String Password;
	String HostDisplayName;     //unchangeable
	String HostIPAddress = "localhost";       //unchangeable
	int HostPort = 16969;               //unchangeable
	int GameMode;
	int CurrentPlayers = 0;
	int CurrentSpectators = 0;
	boolean AlowSpectator;
	public Room(int roomID, String roomName, String password, String hostDisplayName, String hostIPAddress,
			int hostPort, int gameMode, boolean alowSpectator) {
		super();
		RoomID = roomID;
		RoomName = roomName;
		Password = password;
		HostDisplayName = hostDisplayName;
		HostIPAddress = hostIPAddress;
		HostPort = hostPort;
		GameMode = gameMode;
		AlowSpectator = alowSpectator;
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
	public int getGameMode() {
		return GameMode;
	}
	public void setGameMode(int gameMode) {
		GameMode = gameMode;
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
	public boolean isAlowSpectator() {
		return AlowSpectator;
	}
	public void setAlowSpectator(boolean alowSpectator) {
		AlowSpectator = alowSpectator;
	}
	@Override
	public String toString() {
		return "Room [RoomID=" + RoomID + ", RoomName=" + RoomName + ", Password=" + Password + ", HostDisplayName="
				+ HostDisplayName + ", HostIPAddress=" + HostIPAddress + ", HostPort=" + HostPort + ", GameMode="
				+ GameMode + ", CurrentPlayers=" + CurrentPlayers + ", CurrentSpectators=" + CurrentSpectators
				+ ", AlowSpectator=" + AlowSpectator + "]";
	}
	
}
