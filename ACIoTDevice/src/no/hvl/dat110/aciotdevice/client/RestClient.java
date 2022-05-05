package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");
	
	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		AccessMessage msg = new AccessMessage(message);
		
		OkHttpClient client = new OkHttpClient();
		
		Gson json = new Gson();

		RequestBody body = RequestBody.create(JSON, json.toJson(msg));
		
		Request request = new Request.Builder()
				.url("http://" + Configuration.host + ":" + Configuration.port + logpath)
				.put(body)
				.build();

		System.out.println(request.toString());

		try (Response response = client.newCall(request).execute()) {
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		
		OkHttpClient client = new OkHttpClient();
		
		Gson json = new Gson();

		Request request = new Request.Builder()
		  .url("http://" + Configuration.host + ":" + Configuration.port + codepath)
		  .get()
		  .build();

		System.out.println(request.toString());
		
		try (Response response = client.newCall(request).execute()) {
		      String body = response.body().string();
		      System.out.println(body);
		      code = json.fromJson(body, AccessCode.class);
		    }
	   catch (IOException e) {
		   e.printStackTrace();
	   }
		
		return code;
	}
}
