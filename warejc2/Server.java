/*

Julius Ware
CSE383

program1

UDP datagram server

*/

package warejc2;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
public class Server{

	DatagramSocket sock;
	DatagramPacket pkt;
	int portnum;

	//starting server
	public static void main(String a[]){
		int portnum = 0;
		
		try{
			portnum = Integer.parseInt(a[0]);

			} catch (Exception err) {
				System.err.println("Could not parse arguement");
				System.exit(-1);
			}

		try {
			new Server(portnum).Main();
			
			} catch (IOException err) {
				System.err.println("Port in use");
				System.exit(-1);
			}

	}

	public Server (int portnum) throws IOException {
		this.portnum = portnum;
		sock = new DatagramSocket(portnum);

	}
	public void Main(){
		String msg = "";
		System.out.println("Server Starting...");
		ArrayList<SocketAddress> clients = new ArrayList<SocketAddress>();
		//loop forever
		while(true){
			try{
				//receive packet/message/data
				byte b[] = new byte[1024];
				DatagramPacket pkt = new DatagramPacket(b,b.length);
				sock.receive(pkt);

				ByteArrayInputStream bis = new ByteArrayInputStream (b);
				DataInputStream dis = new DataInputStream (bis);
				String newmsg = dis.readUTF();
				System.out.println("Packet received from: " + pkt.getSocketAddress());
				System.out.println("Msg: " + newmsg);
				if(newmsg == "HELLO"){
					clients.add(pkt.getSocketAddress());
				} else if(newmsg == "GOODBYE"){
					clients.remove(pkt.getSocketAddress());
				}
				
				// echo/send message to client
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				dos.writeUTF(newmsg);
				byte bout[] = bos.toByteArray();
	
				for(int i = 0; i < clients.size(); i++){
				DatagramPacket reply = new DatagramPacket(bout, bout.length, clients.get(i));
				sock.send(reply);
	
				}
			} catch (IOException err){
				System.out.println("Error" + err);
	
			}	
	
		}

	}
}
