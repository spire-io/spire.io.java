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
	
	/**
	 * Closes the content of HTTP Response object ignoring any contents.
	 * Avoid LEAKS if response content is never parsed
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException{
		response.ignore();
	}
	
	/**
	 * Alias of close
	 */
	@Override
	public void ignore() throws IOException{
		this.close();
	}
}
