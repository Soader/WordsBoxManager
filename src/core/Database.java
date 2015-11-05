package core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Database {
	
	private String username, password, dbname, host;
	private	int port;
	private Connection connection;
	
	
	public Database(String username, String password, String dbname, String host, int port){

		this.username = username;
		this.password = password;
		this.dbname = dbname;
		this.port = port;
		this.host = host;
		
	}
	
	
	public Connection getConnection() {
		
		Connection connection = null;
		//String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:mysql://"+ host + ":" + port + "/" + dbname;
		try {
			//Class.forName(jdbcClassName);
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	
	public boolean connectToSource() {
		System.out.println("Connecting to database...");
		connection = getConnection();
		if (connection != null) {
			System.out.println("Connected successfully.");
			return true;
		} else {
			System.out.println("Failed to connect.");
		}
		return false;
	}
	
	/*public boolean storeNote(String author, String content) {
		PreparedStatement stmt = null;
		sql = "INSERT INTO "+ table +" (TIMESTAMP, DETAILS, LOGLEVEL) VALUES (?, ?, ?)";
		if (connection != null) {
			for (Event go : batch) {
				try {
					stmt = connection.prepareStatement(sql);
					stmt.setString(1, go.getTimestamp().toString());
				//	System.out.println(go.getDetails().toString());
					stmt.setString(2, go.getDetails());
					stmt.setString(3, go.getLoglevel().toString());
					stmt.addBatch();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				
				try {
					stmt.executeBatch();
					
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
			System.out.println(batch.size() + " records added to database.");
			return true;
		}
		else{
			System.out.println("No connection.");
		}
		return false;
	}*/
	
	public ArrayList<String> getLogs(int max) {
		if(connection == null)
			return null;
		ArrayList<String> notes = new ArrayList<String>();
	    Statement stmt;
		try {
			int i = 0;
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Data, User, Text FROM Messages");
			while (rs.next() && i < max) {
		    	
		        Date date = rs.getDate("Data");
		        String author = rs.getString("User");
		        String text = rs.getString("Text");
		        notes.add(date +" "+ author + " "+ text);
		        
		    }
			return notes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
