/**
 * OrderDetail class - represents payment details.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package fr.jasmin.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import fr.jasmin.vue.backingbean.GestionArticlesBean;

public class OrderDetail {
	private String product;
	private float subtotal;
	private float shipping;
	private float tax;
	private float total;
	GestionArticlesBean gestionArticlesBean = new GestionArticlesBean();
	public static float withBigDecimal(float value, int places) {
	    BigDecimal bigDecimal = new BigDecimal(value);
	    bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
	    return bigDecimal.floatValue();
	}
	
	public OrderDetail() {
		
	}
	
	public OrderDetail(String product, String subtotal, 
			String shipping, String tax, String total) {
		this.product = product;
		this.subtotal = withBigDecimal(Float.parseFloat(subtotal),2);
		this.shipping = withBigDecimal(Float.parseFloat(shipping),2);
		this.tax = withBigDecimal(Float.parseFloat(tax),2);
		this.total = withBigDecimal(Float.parseFloat(total),2);
	}
	
//	public OrderDetail(
//			String productName = "Samsung S21, 
//			String subtotal = "", 
//			String shipping ="", 
//			String tax ="", 
//			String total="") {
//		this.productName = productName;
//		this.subtotal = withBigDecimal(Float.parseFloat(subtotal),2);
//		this.shipping = withBigDecimal(Float.parseFloat(shipping),2);
//		this.tax = withBigDecimal(Float.parseFloat(tax),2);
//		this.total = withBigDecimal(Float.parseFloat(total),2);
//	}
	
//	public OrderDetail(String productName, String subtotal, 
//			String shipping, String tax, String total) {
//		this.productName = gestionArticlesBean.getArticleActuel().getName();
//		this.setProductName("Samsung S21");
//		this.subtotal = withBigDecimal(gestionArticlesBean.getArticleActuel().getPrix(),2);
//		this.setSubtotal(withBigDecimal(456.555f,2));
//		this.setShipping(withBigDecimal(10.564f,2));
//		this.setTax(withBigDecimal(10.786f,2));
//		this.total = withBigDecimal(gestionArticlesBean.getItemCartActuel().getPrixTotal(),2);
//		this.setTotal(withBigDecimal(425.265f,2));
		
//			}
	

	public String getProduct() {
		return product;
	}

	public String getSubtotal() {
		
		return Float.toString(subtotal);
	}

	public String getShipping() {
		return Float.toString(shipping);
	}

	public String getTax() {
		return Float.toString(tax);
	}
	
	public String getTotal() {
		return Float.toString(total);
	}


	public void setProduct(String product) {
		this.product = product;
	}


	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}


	public void setShipping(float shipping) {
		this.shipping = shipping;
	}


	public void setTax(float tax) {
		this.tax = tax;
	}


	public void setTotal(float total) {
		this.total = total;
	}
	
	
}
