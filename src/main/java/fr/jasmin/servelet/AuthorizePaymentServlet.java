package fr.jasmin.servelet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.base.rest.PayPalRESTException;

import fr.jasmin.entity.OrderDetail;
import fr.jasmin.entity.PaymentServices;

@WebServlet("/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String product;
	String subtotal;
	String shipping;
	String tax;
	String total;
	public AuthorizePaymentServlet() {
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		 this.product = request.getParameter("product");
		 this.subtotal = request.getParameter("subtotal");
		 response.getWriter().write("Données reçues depuis JSF Bean : " + subtotal);
		this.shipping = request.getParameter("shipping");
		this.tax = request.getParameter("tax");
		this.total = request.getParameter("total");
		response.getWriter().write("Données reçues depuis JSF Bean : " + total);
		
		OrderDetail orderDetail = new OrderDetail(this.product, this.subtotal, this.shipping, this.tax, this.total);		
		 
		 try {
		
		PaymentServices paymentServices = new PaymentServices();
		String approvalLink = paymentServices.authorizePayment(orderDetail);

		response.sendRedirect(approvalLink);
		
	} catch (PayPalRESTException ex) {
		request.setAttribute("errorMessage", ex.getMessage());
		ex.printStackTrace();
		request.getRequestDispatcher("error.jsp").forward(request, response);
	}
		
	}	

		protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

}
}
