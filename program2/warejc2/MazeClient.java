
package warejc2;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import java.net.*;
import java.util.*;
import java.io.*;

public class MazeClient {
        public static void main(String args[]) throws Exception {
                XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
                config.setServerURL(new URL("http://127.0.0.1:5555"));
                XmlRpcClient client = new XmlRpcClient();
                client.setConfig(config);
		Scanner in = new Scanner(System.in);
		System.out.print("username: ");	
		String uname = in.next();	
		String move = "";
		//check username with DB
		
		System.out.print("password: ");
		String pass = in.next();
		Object[] params = new Object[]{uname, pass}; 
		String token = (String)client.execute("adder.connect2", params);
		while(token.equals("x")){
			System.out.print("Incorrect Password \npassword: ");
			pass = in.next();
			params[1] = pass;
			token = (String)client.execute("adder.connect2", params);
		}
		params = new Object[]{token};
		String aroundMe = (String)client.execute("adder.look", params);
		while(true){	
			System.out.println(aroundMe);
			System.out.print("Enter you next move: ");
			move = in.next();
			if(move.toLowerCase().equals("q")){
				System.out.println("Goodbye");
				return;
			}
			
			if(move.toLowerCase().equals("c")){
				System.out.print("Password: ");
				pass = in.next();
				while(pass.equals("x")){
					System.out.println("Invalid password \nPassword:");
					pass = in.next();
				}
				//params[0] = token;
				//params[1] = pass;
				params = new Object[]{token, pass};
				client.execute("adder.close", params);
			}
			params = new Object[]{token, move};
			aroundMe = (String)client.execute("adder.move", params);
			System.out.println(aroundMe);
		}
			
        }
}


