package warejc2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.*;

public class MazeHandler {

	String usr = "mazeUser";
	String pwd = "hashingncaching";
	String dbURL = "jdbc:mysql://localhost/mazeDB";
	Connection conn = null;
	String [][] maze;
	public int row = 0;
	public int col =0;
	public int startx = 0;
	public int starty = 0;
	public int xnow =0;
	public int ynow = 0;
	public MazeHandler(){
		try{	
			this.connect();
			this.parseMaze();
		} catch(Exception err){
			System.err.println(err);
			err.printStackTrace();
		}
	}
	public void parseMaze(){
		try{
			File mazeFile = new File("/home/ubuntu/cse383/program2/warejc2/maze.in");
			ArrayList<String> temp = new ArrayList<String>();
			Scanner in = new Scanner(mazeFile);
			while(in.hasNextLine()){
				temp.add(in.nextLine());
				this.row += 1;
				this.col = temp.get(0).length();
				
			}
				
			maze = new String[this.row][this.col];
			in.close();
			String str = "";
			in = new Scanner("/home/ubuntu/cse383/program2/warejc2/maze.in");	
			for(int i = 0; i < this.row; i++){
				str = temp.get(i);
				for(int j = 0; j < this.col; j++){
					maze[i][j] = str.charAt(j) + "";
					if(maze[i][j].equals("S")){
						this.startx = i;
						this.starty = j;
						 
					}
				}
			}

			//test maze
		/*	for(int i = 0; i < this.row; i++){
				System.out.println("");
				for(int j = 0; j < this.col; j++){
					System.out.print(maze[i][j]);
				}
			}*/
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public void connect() throws IOException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL,usr,pwd);

			} catch (Exception err) {
				throw new IOException(err);
			}
	}
	
	public String connect2(String username, String password) throws SQLException{
			String uname = null;
			String pword = null;
			String token = null;
			try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT username, password, sessionid, xpos, ypos FROM user WHERE username = '"+username+"';");
			if(rs.next()){
				pword = rs.getString("password");
				if(!pword.equals(password)){
					System.out.println("Invalid Password");
					return "x";	
				}
				token = rs.getString("sessionid");
				this.xnow = rs.getInt("xpos");
				this.ynow = rs.getInt("ypos"); 
				
			} else {
				token = UUID.randomUUID().toString();
				stmt.executeUpdate("INSERT into user (username, password, sessionID, xstrpos, ystrpos, xpos, ypos) VALUES ('"+username+"', '"+password+"', '"+token+"', '"+this.startx+"', '"+this.starty+"', '"+this.startx+"','"+this.starty+"' );");
				this.xnow = this.startx;
				this.ynow = this.starty;	
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(token);
		return token;
	}

	public String look(String sessionID){
		String north ="";
		String south="";
		String east="";
		String west="";
		//wall
		if((this.xnow - 1) < 0 || maze[this.xnow -1][this.ynow].equals("X"))
			north  = "W";
		if((this.xnow + 1) > row -1 || maze[this.xnow +1][this.ynow].equals("X"))
			south = "W";
		if((this.ynow -1) < 0|| maze[this.xnow][this.ynow - 1].equals("X"))
			west = "W";
		if((this.ynow +1) > col -1 || maze[this.xnow][this.ynow +1].equals("X"))
			east = "W";
		//start
		if(maze[this.xnow -1][this.ynow].equals("S"))
			north  = "S";
		if(maze[this.xnow +1][this.ynow].equals("S"))
			south = "S";
		if(maze[this.xnow][this.ynow - 1].equals("S"))
			west = "S";
		if(maze[this.xnow][this.ynow +1].equals("S"))
			east = "S";
		//end	
		if(maze[this.xnow -1][this.ynow].equals("E"))
			north  = "E";
		if(maze[this.xnow +1][this.ynow].equals("E"))
			south = "E";
		if(maze[this.xnow][this.ynow - 1].equals("E"))
			west = "E";
		if(maze[this.xnow][this.ynow +1].equals("E"))
			east = "E";

		//open
		if(maze[this.xnow -1][this.ynow].equals(" "))
			north  = "O";
		if(maze[this.xnow +1][this.ynow].equals(" "))
			south = "O";
		if(maze[this.xnow][this.ynow - 1].equals(" "))
			west = "O";
		if(maze[this.xnow][this.ynow +1].equals(" "))
			east = "O";


		String str = north + " -> North\n" + south + " -> South\n" + east + " -> East\n" + west +" -> West";
		return str;
	}	
	public String close(String sessionID, String password){
		
		String ret = "OK";
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT username, password FROM user WHERE sessionid = '"+sessionID+"';");
			String upass = rs.getString("password");
			if(!upass.equals(password)){
				ret = "x";
				return ret;
			}
			stmt.executeUpdate("DELETE FROM user WHERE sessionid = '"+sessionID+"';");
		} catch (SQLException e){
			e.printStackTrace();
		}	
		return ret;
	}

	public String move(String token, String direction){
		if (direction.toLowerCase().equals("north")){
			this.xnow -= 1;
		} else if(direction.toLowerCase().equals("south")){
			this.xnow += 1;
		} else if(direction.toLowerCase().equals("east")){
			this.ynow += 1;
		} else if(direction.toLowerCase().equals("west")){
			this.ynow -= 1;
		} else {
			//do nothing	
		}	
		try{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE user SET xpos='"+this.xnow+"', ypos='"+this.ynow+"' WHERE sessionid = '"+token+"';");
		} catch (SQLException e){
			e.printStackTrace();
		}
		if(maze[this.xnow][this.ynow].equals("E"))
			return"DONE";
		if(maze[this.xnow][this.ynow].equals("P"))
			return"DIED";
		String str = this.look(token);
		return str;
	}

	public String get(){
		return "";
	}

	public static void main(String[] args) {
		MazeHandler myHandler = new MazeHandler();
		String un = "julius";
		String pass = "pass";
		String token = null;
		try{
			token = myHandler.connect2(un, pass);
			System.out.println(myHandler.look(token));
			System.out.println(myHandler.move(token, "east"));
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}


