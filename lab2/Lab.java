/*
Scott Campbell
cse383

Remote procedures exposed to the server
All public methods are exposed
*/

public class Lab {
	static MessageHandler mess = new MessageHandler();

	public String getAuthToken(String uid, byte code[]) {
		int sum=0;
		if (code.length<=0)
			throw new RuntimeException("Invalid token length");

		for (int i=0;i<code.length;i++) 
			sum += code[i];
		if (sum%13 != 0)
			throw new RuntimeException("Invalid token");


		System.out.println("uid: " + uid);
		String token = uid + " 10011";
		return token;
	}

	public String storeMessage(String token, String Message) {
		//validate token
		if(!token.endsWith("10011")){
			String badUser = token.substring(0, token.length()-5);
			throw new RuntimeException("Invalid token from: " + badUser);
		}
		//store message
		System.out.println("msg=" + Message);
		mess.addMessage(Message);

		return "OK";
	}

	public String getMessages() {
		
		return mess.getAll();
	}

	public int getNum(){
		return mess.getNum();
	}
	
	public void clearAll(String token){
		//validate token
		if(!token.endsWith("10011")){
			String badUser = token.substring(0, token.length()-5);
			throw new RuntimeException("Invalid token from: " + badUser);
		}
		mess.clearAll();
	}
}

