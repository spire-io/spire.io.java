package io.spire.request;

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
	
	@Override
	public int getStatusCode(){
		return response.getStatusCode();
	}
	
	@Override
	public boolean isSuccessStatusCode(){
		return response.isSuccessStatusCode();
	}
	
	@Override
	public <T> T parseAs(Class<T> dataClass) throws IOException{
		return response.parseAs(dataClass);
	}
	
	@Override
	public String parseAsString() throws IOException{
		return response.parseAsString();
	}

}
