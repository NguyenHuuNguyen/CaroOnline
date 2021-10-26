package dto;

public class Room {
	int RoomID;                 //unchangeable
	int CurrentPlayers;
	int CurrentSpectators;
	int MaxSpectator;
	String RoomName;
	String Password;
	String HostDisplayName;     //unchangeable
	String HostIPAddress;       //unchangeable
	int HostPort;               //unchangeable
	int GameMode;
	public Room(int roomid, int maxspectators, String roomname, String password, String hostdisplayname
			, String hostipaddress, int hostport, int gamemode) 
	{
		RoomID = roomid;
		CurrentPlayers = 1;
		CurrentSpectators = 0;
		MaxSpectator = maxspectators;
		RoomName = roomname;
		Password = password;
		HostDisplayName = hostdisplayname;
		HostIPAddress = hostipaddress;
		HostPort = hostport;
		GameMode = gamemode;
	}
	public void RoomUpdate(int maxspectators, String roomname, String password, int gamemode
			, int currentplayers, int currentspectators) 
	{
		MaxSpectator = maxspectators;
		RoomName = roomname;
		Password = password;
		GameMode = gamemode;
		CurrentPlayers = currentplayers;
		CurrentSpectators = currentspectators;
	}
}
