package spire.request;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;

public class Response extends ResponseAbstract {

	private HttpResponse response;
	
	public Response() {
		// TODO Auto-generated constructor stub
	}
	
	public Response(HttpResponse response) {
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
