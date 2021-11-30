package bll;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dal.DAL;
import dto.Account;

public class BLL {
	//
	//singleton
	//
	int n = 15;
	private BLL() {}
	private static BLL _Instance;
	public static BLL Instance() {
		if (_Instance == null) {
			_Instance = new BLL();
		}
		return _Instance;
	}
	//
	public boolean checkLogin(String username, String password)
	{
		return DAL.Instance().checkLogin(username, password);
	}
	
	public void resetAllAccountStatus()
	{
		DAL.Instance().resetAllAccountStatus();
	}
	
	public Account getAccountByUsername(String username)
	{
		return DAL.Instance().getAccountByUsername(username);
	}
	
	public void setAccountStatus(int id, boolean status)
	{
		DAL.Instance().setAccountStatus(id, status);
	}
	public int isFinishCaro5(int[][] board, int ix, int jx, int playervalue) {
		if (isFinishHorizontal(board, ix, jx, playervalue)
			|| isFinishVertical(board, ix, jx, playervalue)
			|| isFinishPrimaryDiagonal(board, ix, jx, playervalue)
			|| isFinishSubDiagonal(board, ix, jx, playervalue))
			return playervalue;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++)
				if (board[i][j] == 0) return 0;
		}
		return -1;
	}
	public boolean isFinishHorizontal(int[][] board, int ix, int jx, int playervalue) {
		int k = 1;     // đếm số x||o liên tiếp
		int p = 0;     // xem có chặn 2 đầu hay không
		for(int i = jx-1; i >= 0; i--) {
			if (board[ix][i] != playervalue) {
				if (board[ix][i] != 0) p+=1;
				break;
			}
			k+=1;
		}
		for(int i = jx+1; i < n; i++) {
			if (board[ix][i] != playervalue) {
				if (board[ix][i] != 0) p += 1;
				break;
			}
			k+=1;
		}
		if (k == 5 && p < 2) return true;
		return false;
	}
	public boolean isFinishVertical(int[][] board, int ix, int jx, int playervalue) {
		int k = 1;     // đếm số x||o liên tiếp
		int p = 0;     // xem có chặn 2 đầu hay không
		for(int i = ix-1; i >= 0; i--) {
			if (board[i][jx] != playervalue) {
				if (board[i][jx] != 0) p+=1;
				break;
			}
			k+=1;
		}
		for(int i = ix+1; i < n; i++) {
			if (board[i][jx] != playervalue) {
				if (board[i][jx] != 0) p += 1;
				break;
			}
			k+=1;
		}
		if (k == 5 && p < 2) return true;
		return false;
	}
	public boolean isFinishPrimaryDiagonal(int[][] board, int ix, int jx, int playervalue) {
		int k = 1;     // đếm số x||o liên tiếp
		int p = 0;     // xem có chặn 2 đầu hay không
		int i = ix-1, j = jx-1;
		while (i >= 0 && j >= 0) {
			if (board[i][j] != playervalue) {
				if (board[i][j] != 0) p+=1;
				break;
			}
			k+=1; i-=1; j-=1;
		}
		i = ix+1; j = jx+1;
		while (i < n && j < n) {
			if (board[i][j] != playervalue) {
				if (board[i][j] != 0) p+=1;
				break;
			}
			k+=1; i+=1; j+=1;
		}
		if (k == 5 && p < 2) return true;
		return false;
	}
	public boolean isFinishSubDiagonal(int[][] board, int ix, int jx, int playervalue) {
		int k = 1;     // đếm số x||o liên tiếp
		int p = 0;     // xem có chặn 2 đầu hay không
		int i = ix+1, j = jx-1;
		while (i < n && j >= 0) {
			if (board[i][j] != playervalue) {
				if (board[i][j] != 0) p+=1;
				break;
			}
			k+=1; i+=1; j-=1;
		}
		i = ix-1; j = jx+1;
		while (i >= 0 && j < n) {
			if (board[i][j] != playervalue) {
				if (board[i][j] != 0) p+=1;
				break;
			}
			k+=1; i-=1; j+=1;
		}
		if (k == 5 && p < 2) return true;
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
