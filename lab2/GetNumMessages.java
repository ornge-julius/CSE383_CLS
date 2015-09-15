
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import java.net.*;
import java.io.*;

public class GetNumMessages {
	XmlRpcClient xmlRPCclient = null;
	int port = -1;


	public static void main(String args[]) {
		if (args.length != 2) {
			System.out.println("Useage java GetNumMessages <host> <port>");
		}
		
		int port = -1;
		try {
			port = Integer.parseInt(args[1]);
		} catch (Exception err) {
			System.out.println("specify port");
			return;
		}

		try {
			GetNumMessages client = new GetNumMessages(args[0],port);
			int msgs = client.getNum();		//url to put
			System.out.println("Num Messages: " + msgs);
		}
		catch (Exception err) {
			System.err.println("error geting token " + err);
			return;
		}
	}

	/*
	   constructor
	 */
	public GetNumMessages(String host,int p) throws IOException {
		port = p;
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://" + host+":"+port));
		xmlRPCclient = new XmlRpcClient();
		xmlRPCclient.setConfig(config);
	}

	/*
	the server requires a token since it can not maintain state
	*/
	public int getNum() throws IOException,org.apache.xmlrpc.XmlRpcException {
		Object[] params = new Object[]{};
		int result= (int) xmlRPCclient.execute("lab.getNum", params);
		return result;
	}


}
