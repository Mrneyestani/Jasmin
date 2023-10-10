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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import net.bytebuddy.asm.Advice.This;

@Entity
@Table(name = "item_cart")
public class ItemCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "quantity", nullable = false)
	private int quantite;
	
	@Column(name = "is_checked",nullable = false)
	private boolean isChecked;
	
	@Transient
	private int remiseTotal;
	
	@Transient
	private float prixUnitairApresRemise;
	
	@Transient
	private float prixTotalArticle;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToOne
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	public ItemCart() {
		quantite = 1;
		isChecked = false;
		remiseTotal = 0;
	}

	public ItemCart(int quantite) {
		this.quantite = quantite;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	

	public int getRemiseTotal() {
		
		if (getItem().getCategory().getIsRemiseCumulable()) {
			
			remiseTotal = getItem().getRemise() + getItem().getCategory().getRemiseCategorie();
		}else {
		
			remiseTotal = getItem().getRemise();}
		
			return remiseTotal;
	}

	public void setRemiseTotal(int remiseTotal) {
		this.remiseTotal = remiseTotal;
	}

	public float getPrixUnitairApresRemise() {
		
		prixUnitairApresRemise  = (getItem().getPrix() - getItem().getPrix()*getRemiseTotal()/100);
		
		return prixUnitairApresRemise;
	}

	public void setPrixUnitairApresRemise(float prixUnitairApresRemise) {
		this.prixUnitairApresRemise = prixUnitairApresRemise;
	}

	
	public float getPrixTotalArticle() {
		return prixTotalArticle = getPrixUnitairApresRemise()*getQuantite();
	}

	public void setPrixTotalArticle(float prixTotalArticle) {
		this.prixTotalArticle = prixTotalArticle;
	}

	@Override
	public String toString() {
		return "ItemCart [id=" + id + ", quantite=" + quantite + ", isChecked=" + isChecked + ", user=" + user
				+ ", item=" + item + "]";
	}


}
