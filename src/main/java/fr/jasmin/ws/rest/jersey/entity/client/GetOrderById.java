package fr.jasmin.ws.rest.jersey.entity.client;




import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

import com.paypal.api.payments.Order;

import fr.jasmin.ws.rest.jersey.entity.OrderDetailRest;

public class GetOrderById {

	private final static String CHARSET = ";charset=UTF-8";
	
	public static void main(String[] args) {
		
		ClientConfig config = new ClientConfig().register(LoggingFeature.class);
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("http://localhost:9991/rest")
									.path("orders")
									.path("id")
									.path("1");
		
		Response response = target.request(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.get();
		
		if (response.getStatus() == 200) {
			OrderDetailRest orderDetailRest = response.readEntity(OrderDetailRest.class);
			System.out.println(orderDetailRest);
		} else {
			String message = response.readEntity(String.class);
			System.out.println("ERROR : " + response.getStatus());
			System.out.println(message);
		}
	}
}
