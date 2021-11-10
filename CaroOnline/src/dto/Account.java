package dto;

import java.awt.image.BufferedImage;

public class Account {
	int id_user;
	String Username;
	String Password;
	boolean Status;
	//BufferedImage Avatar;
	String DisplayName;
	int BattleWon;
	int BattleLost;
	
	public Account(int id_user, String username, String password, boolean status, String displayName, int battleWon,
			int battleLost) {
		super();
		this.id_user = id_user;
		Username = username;
		Password = password;
		Status = status;
		DisplayName = displayName;
		BattleWon = battleWon;
		BattleLost = battleLost;
	}

	@Override
	public String toString() {
		return "Account [id_user=" + id_user + ", Username=" + Username + ", Password=" + Password + ", Status="
				+ Status + ", DisplayName=" + DisplayName + ", BattleWon=" + BattleWon + ", BattleLost=" + BattleLost
				+ "]";
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	
	
}
