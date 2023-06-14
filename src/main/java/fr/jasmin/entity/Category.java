package fr.jasmin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "category")
@XmlRootElement
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty
	@Column(name = "name", unique = true, nullable = false)
	private String nomCategorie;

	@Column(name = "discount", nullable = false)
	private Integer remiseCategorie;

	@Column(name = "is_discount_cumulate", nullable = false)
	private Boolean isRemiseCumulable;

	@Column(name = "picture", nullable = false)
	private String photoCategorie;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Item> items = new ArrayList<Item>();

	public Category() {

	}

	public Category(String nomCategorie, Integer remiseCategorie, Boolean isRemiseCumulable, String photoCategorie) {

		this.nomCategorie = nomCategorie;
		this.remiseCategorie = remiseCategorie;
		this.isRemiseCumulable = isRemiseCumulable;
		this.photoCategorie = photoCategorie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	public Integer getRemiseCategorie() {
		return remiseCategorie;
	}

	public void setRemiseCategorie(Integer remiseCategorie) {
		this.remiseCategorie = remiseCategorie;
	}

	public Boolean getIsRemiseCumulable() {
		return isRemiseCumulable;
	}

	public void setIsRemiseCumulable(Boolean isRemiseCumulable) {
		this.isRemiseCumulable = isRemiseCumulable;
	}

	public String getPhotoCategorie() {
		return photoCategorie;
	}

	public void setPhotoCategorie(String photoCategorie) {
		this.photoCategorie = photoCategorie;
	}

	public List<Item> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", nomCategorie=" + nomCategorie + ", remiseCategorie=" + remiseCategorie
				+ ", isRemiseCumulable=" + isRemiseCumulable + ", photoCategorie=" + photoCategorie + ", Items=" + items
				+ "]";
	}

}
