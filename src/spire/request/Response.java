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
	
	public <T> T parseAs(Class<T> dataClass) throws IOException{
		return response.parseAs(dataClass);
	}

}
