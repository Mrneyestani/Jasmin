/**
 * OrderDetail class - represents payment details.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package fr.jasmin.ws.rest.jersey.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fr.jasmin.vue.backingbean.GestionArticlesBean;

@XmlRootElement(name = "orderDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderDetailRest implements Serializable{
	
	private static final long serialVersionUID = 2758169351644901292L;
	
	@XmlElement
	private Integer id;
	
	@XmlElement
	private String productName;
	
	@XmlElement
	private float subtotal;
	
	@XmlElement
	private float shipping;
	
	@XmlElement
	private float tax;
	
	@XmlElement
	private float total;
	
	
	GestionArticlesBean gestionArticlesBean = new GestionArticlesBean();
	public static float withBigDecimal(float value, int places) {
	    BigDecimal bigDecimal = new BigDecimal(value);
	    bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
	    return bigDecimal.floatValue();
	}
	
	public OrderDetailRest() {
	}
	
	public OrderDetailRest(String productName, String subtotal, 
			String shipping, String tax, String total) {
		this.productName = productName;
		this.subtotal = withBigDecimal(Float.parseFloat(subtotal),2);
		this.shipping = withBigDecimal(Float.parseFloat(shipping),2);
		this.tax = withBigDecimal(Float.parseFloat(tax),2);
		this.total = withBigDecimal(Float.parseFloat(total),2);
	}
	
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
	

	public String getProductName() {
		return productName;
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


	public void setProductName(String productName) {
		this.productName = productName;
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

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", productName=" + productName + ", subtotal=" + subtotal + ", shipping="
				+ shipping + ", tax=" + tax + ", total=" + total + "]";
	}
	
}
