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
	SocketAddress sendAddr;
	Log log;
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
		log = new Log("server.log");

	}
	public void Main(){
		String msg = "";
		System.out.println("Server Starting...");
		log.log("Server Starting...");
		ArrayList<SocketAddress> clients = new ArrayList<SocketAddress>();
		//loop forever
		while(true){
			try{
				//receive packet/message/data
				
				log.log("Waiting for packet..");
				String newmsg[] = getMessage(); 
				System.out.println("Packet received from: " + sendAddr);
				log.log("Packet received from: " + sendAddr);
				System.out.println("Msg: " + newmsg[1]);
				log.log("Msg: " + newmsg[1]);
				if(newmsg[0].equals("HELLO")){
					clients.add(sendAddr);
					log.log("Client " + sendAddr + " connected");
					sendMessage("HELLO-RESPONSE","", sendAddr);	
				} else if(newmsg[0].equals("GOODBYE")){
					log.log("Client " + sendAddr + " left");
					clients.remove(sendAddr);
					sendMessage("GOODBYE-RESPONSE", "", sendAddr);
				} else {
				
				// echo/send message to client
				}
				for(int i = 0; i < clients.size(); i++){
					sendMessage(newmsg[0], newmsg[1], clients.get(i));	
				}
			} catch (IOException err){
				System.out.println("Error" + err);
	
			}	
	
		}

	}

	public void sendMessage(String msg,String msg1, SocketAddress addr)  throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeUTF(msg);
		dos.writeUTF(msg1);
		byte byteArray[] = bos.toByteArray();
		SocketAddress sendto = addr;
		DatagramPacket sendMsg = new DatagramPacket(byteArray,byteArray.length, sendto);
		sock.send(sendMsg);
	}


	public String[] getMessage() throws IOException {
		String msg[] = new String[2];
		byte b[] = new byte[1024];
		DatagramPacket pkt = new DatagramPacket(b,b.length);
		sock.receive(pkt);
		sendAddr = pkt.getSocketAddress();
		ByteArrayInputStream bis= new ByteArrayInputStream(b);
		DataInputStream dis = new DataInputStream(bis);
		msg[0] = dis.readUTF();
		msg[1] = dis.readUTF();
		return msg;
	}



}
