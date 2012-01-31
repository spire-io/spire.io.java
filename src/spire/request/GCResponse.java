package spire.request;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;

public class GCResponse extends Response{

	private HttpResponse response;
	
	public GCResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public GCResponse(HttpResponse response) {
		this.response = response;
	}
	
	public int getStatusCode(){
		return response.getStatusCode();
	}
	
	public boolean isSuccessStatusCode(){
		return response.isSuccessStatusCode();
	}
	
	public <T> T parseAs(Class<T> dataClass) throws IOException{
		return response.parseAs(dataClass);
	}
	
	public String parseAsString() throws IOException{
		return response.parseAsString();
	}

}
