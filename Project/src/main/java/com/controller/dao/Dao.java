package com.project.dao;
import com.project.model.Coin;
import com.project.model.Bookmarked;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.cj.protocol.InternalTimestamp;




public class Dao {
	private String username="root";
	private String password="Nz@123456789";
	private String jdbcUrl ="jdbc:mysql://localhost:3306/cryptocurrencies?userSSL=false";
	
	private static final String SELECT_COIN_BY_ID = "select * from users where id=?;";
	private static final String INSERT_COIN = "INSERT INTO bookmarked"+" (coinid,purchaseDate,quantity,purchasedPrice,insertDate,updateDate) VALUES "+" (?,?,?,?,?,?);";
	private static final String UPDATE_COIN="UPDATE bookmarked SET purchaseDate?, quantity=?,purchasedPrice=?,updateDate=? where id=?;";
	private static final String DELETE_COIN="DELETE from coins where id=?;";
	private static final String SELECT_ALL_COINS="SELECT * from coins";
	
	protected Connection getConnection()
	{
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection(jdbcUrl,username,password);
		    	System.out.print("Successful connected");


		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	public Coin selectCoin(int id) {
		Coin coin = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COIN_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String startDate = rs.getString("startDate");
				double price = rs.getDouble("price");
				double volume24 = rs.getDouble("volume24");
				coin = new Coin(id, name,price,startDate,volume24);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return coin;
	}
	
	public List<Coin> selectALLCoins() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Coin> coins = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COINS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String startDate = rs.getString("startDate");
				double price = rs.getDouble("price");
				double volume24 = rs.getDouble("volume24");
				coins.add(new Coin(id, name,price,startDate,volume24));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return coins;
	}
	
	public void insertCoin(Bookmarked coin) throws SQLException{
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COIN)){
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			//String dateString = "22-03-2017 11:18:32";
			long pDate = sdf.parse(coin.getPurchasedDate()).getTime();
				preparedStatement.setInt(1, coin.getId());
				preparedStatement.setTimestamp(2,new Timestamp(pDate));
				preparedStatement.setDouble(3, coin.getQuantity());
				preparedStatement.setDouble(4, coin.getPurchasedPrice());
				preparedStatement.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
				preparedStatement.execute();}catch(Exception e) {
					e.printStackTrace();
		}	
		
	}
	
	public boolean updateCoin(Bookmarked coin) throws SQLException, ParseException{
		boolean rowUpdated;
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COIN);)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			//String dateString = "11-11-2022 11:18:32";
			long pDate = sdf.parse(coin.getPurchasedDate()).getTime();
				preparedStatement.setInt(5, coin.getId());
				preparedStatement.setTimestamp(1,new Timestamp(pDate));
				preparedStatement.setDouble(2, coin.getQuantity());
				preparedStatement.setDouble(3, coin.getQuantity());
				preparedStatement.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			rowUpdated = preparedStatement.executeUpdate()>0;
		}
		return rowUpdated;
	}
	
	public boolean deleteCoin(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_COIN);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
