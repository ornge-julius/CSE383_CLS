
package warejc2;
import java.sql.*;

public class Methods {
        
	static MazeHandler mh = new MazeHandler();	

	public String connect2(String Username, String Password){
		String ret = "";
		try{
			ret =  mh.connect2(Username, Password);
		} catch (SQLException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public void close(String sessionID, String Password){
		mh.close(sessionID, Password);
	}

	public String look(String sessionID){
		return mh.look(sessionID);
		
	}

	public String move(String Username, String direction){
		return mh.move(Username, direction);
	}

	public String get(){
		return mh.get();
	}
}

