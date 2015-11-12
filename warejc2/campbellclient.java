package warejc2;

import com.mashape.unirest.http.*;
import com.mashape.unirest.request.*;
import com.mashape.unirest.http.exceptions.*;
import org.json.*;

public class campbellclient {
	private String key = null;
	
	public campbellclient(){
		HttpResponse<JsonNode> keyresponse = null;
		try{
		
		keyresponse = Unirest.get("http://campbest.383.csi.miamioh.edu:8080/story/rest//getkey/test/test").asJson();
		JSONObject obj = keyresponse.getBody().getObject();
		key = obj.getString("key");
		System.out.println(key);
			} catch (UnirestException err){
				System.out.println(err);
				return ;
			}
	}

	public String getStories(){
		HttpResponse<JsonNode> response = null;

		try{
			response = Unirest.get("http://campbest.383.csi.miamioh.edu:8080/story1/rest/"+key+"/storylist").asJson();

		} catch (UnirestException err){
			System.out.println(err);
			return null;
		}

		return "test";
	}

	public static void main(String a[]){
		campbellclient c = new campbellclient();
	}


}
