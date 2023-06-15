package fr.jasmin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "order_user")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "numero")
	private String number;

	@Column(name = "date_creation")
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	@Column(name = "date_livraison")
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	@Column(name = "total_remise")
	private double totalDiscount;
	@Column(name = "frais_expedition")
	private double shippingFee;
	@Column(name = "total_general")
	private double totalAmount;

	@OneToOne
	@JoinColumn(name = "address_facturation_id")
	private Address billingAddress;

	@OneToOne
	@JoinColumn(name = "address_livraison_id")
	private Address shippingAddress;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderLine> orderLines = new ArrayList<OrderLine>();

	public Order() {

	}

	public Order(String number, User user, List<OrderLine> orderLines) {

		this.number = number;
		this.user = user;
		this.orderLines = orderLines;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", number=" + number + ", creationDate=" + creationDate + ", deliveryDate="
				+ deliveryDate + ", totalDiscount=" + totalDiscount + ", shippingFee=" + shippingFee + ", totalAmount="
				+ totalAmount + ", billingAddress=" + billingAddress + ", shippingAddress=" + shippingAddress + " user="
				+ user + ", orderLines=" + orderLines + "]";
	}

}
