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
	int id_ava;
	
	public Account(int id_user, String username, String password, boolean status, String displayName, int battleWon,
			int battleLost, int _id_ava) {
		super();
		this.id_user = id_user;
		Username = username;
		Password = password;
		Status = status; 
		DisplayName = displayName;
		BattleWon = battleWon;
		BattleLost = battleLost;
		id_ava = _id_ava;
	}

	public int getId_ava() {
		return id_ava;
	}

	public void setId_ava(int id_ava) {
		this.id_ava = id_ava;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
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

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

	public int getBattleWon() {
		return BattleWon;
	}

	public void setBattleWon(int battleWon) {
		BattleWon = battleWon;
	}

	public int getBattleLost() {
		return BattleLost;
	}

	public void setBattleLost(int battleLost) {
		BattleLost = battleLost;
	}
	
	
}
