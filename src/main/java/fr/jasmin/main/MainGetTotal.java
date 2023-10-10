package fr.jasmin.main;

import fr.jasmin.ws.rest.jersey.entity.OrderDetailRest;


public class MainGetTotal {

	public static void main(String[] args) {



		try {
			OrderDetailRest orderDetailRest = new OrderDetailRest("s21","10","10","20","50");
			System.out.println("======================orderDetail===========================");
			System.out.println(orderDetailRest.getProductName());
			System.out.println(orderDetailRest.getSubtotal());
			System.out.println(orderDetailRest.getShipping());
			System.out.println(orderDetailRest.getTax());
			System.out.println(orderDetailRest.getTotal());

			
			System.out.println("==========================================");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
