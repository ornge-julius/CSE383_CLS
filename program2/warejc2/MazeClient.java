
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
		System.out.println("username: ");	
		String uname = in.next();	
		//check username with DB
		
		System.out.print("password: ");
		String pass = in.next();
		
		//will need to get from DB
		String dbpass = "";

		while(!pass.equals(dbpass)){
			System.out.print("Incorrect Password \npassword: ");
		}

		
                Object[] params = new Object[]{new String("Hello"), new String(" World")};
                String result = (String) client.execute("adder.append", params);
                System.out.println("Result = " + result);
        }
}


