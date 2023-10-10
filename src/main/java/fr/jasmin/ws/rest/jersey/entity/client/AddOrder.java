package fr.jasmin.ws.rest.jersey.entity.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

import fr.jasmin.ws.rest.jersey.entity.OrderDetailRest;

public class AddOrder {

	public static void main(String[] args) {
		
		ClientConfig config = new ClientConfig().register(LoggingFeature.class);
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("http://localhost:9991/rest")
									.path("orders")
									.path("create");
		
		OrderDetailRest orderDetailRest = new OrderDetailRest("s21", "160", "10", "10", "180");
		
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(orderDetailRest, MediaType.APPLICATION_JSON));
		
		if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
			int id = response.readEntity(Integer.class);
			System.out.println("L'ordre a été créé avec succès : id = " + id);
			
		} else {
			String message = response.readEntity(String.class);
			System.out.println("ERROR : " + response.getStatus());
			System.out.println(message);
		}
	}
}
