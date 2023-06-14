package fr.jasmin.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_line")
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "unit_price")
	private float unitPrice;

	@Column(name = "item_discount")
	private float itemDiscount;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	public OrderLine() {

	}

	public OrderLine(int quantity, Order order, Item item) {

		this.setQuantity(quantity);
		this.order = order;
		this.item = item;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public double getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(float itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", itemDiscount="
				+ itemDiscount + ", order=" + order + ", item=" + item + "]";
	}

}
