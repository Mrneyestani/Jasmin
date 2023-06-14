package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import fr.jasmin.control.impl.ItemMetier;
import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Comment;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;

@ManagedBean(name = "articlebean")
public class ArticleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String nom;

	private static String description;

	private static Float prix;

	private static Integer remise;

	private static Integer stock;

	@ManagedProperty(name = "isVendable", value = "true")
	private static Boolean isVendable;

	private final IItemMetier itemMetier = new ItemMetier();
	private static String photos;
	private static String videos;
	private List<Comment> commentaires = new ArrayList<Comment>();
	private static List<Item> itemList = new ArrayList<Item>();
	private List<Item> items = new ArrayList<Item>();
	private Item item = new Item();
	private Integer quantite = 0;
	private Boolean isChecked = false;
	private String messageSuccess;
	private String messageError;
	private HashMap<Integer, Integer> mapQuantites = new HashMap<Integer, Integer>();
	private List<ItemCart> panier;
	CategorieBean categorieBean = new CategorieBean();

	public ArticleBean() {
	}

	public String submit() {
		return null;
	}

	public void addArticle() {
		Item item = new Item();
		item.setName(nom);
		item.setDescription(description);
		item.setPicture(photos);
		item.setVideo(videos);
		item.setRemise(remise);
		item.setPrice(prix);
		item.setStock(stock);
		item.setIsVendable(isVendable);

		itemList.add(item);
	}

	public String addArticlesToPanier() {
		try {

			submit();
			items = categorieBean.getItems();
			for (Item item : items) {
				if (item.getIsChecked() == true) {

					mapQuantites.put(item.getId(), item.getQuantite());
				}
			}
		} catch (Exception e) {
			messageError = "...";
		}
		return "";
	}

	public void updateArticle(Item article) {
		try {
			itemMetier.updateArticle(article);
			;
			messageSuccess = "Article modifié avec succès.";
			messageError = "";
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la modification d'article !\n" + e.getMessage();
			e.printStackTrace();
		}

	}

	public void deleteArticle(Item article) {
		try {
			itemMetier.removeArticle(article);
			messageSuccess = "Article supprimé avec succès.";
			messageError = "";
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression d'article !\n" + e.getMessage();
			e.printStackTrace();
		}
	}

	public void deleteArticleById(Integer id) {
		try {
			itemMetier.removeArticle(id);
			messageSuccess = "Article supprimé avec succès.";
			messageError = "";
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression d'article !\n" + e.getMessage();
			e.printStackTrace();
		}
	}

	public String validerPanier() {
		return "command.xhtml";
	}

	public static void initializeArticle() {
		nom = "";
		description = "";
		photos = "";
		videos = "";
		remise = null;
		prix = null;
		stock = null;

		isVendable = false;

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		ArticleBean.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public Integer getRemise() {
		return remise;
	}

	public void setRemise(Integer remise) {
		this.remise = remise;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getIsVendable() {
		return isVendable;
	}

	public void setIsVendable(Boolean isVendable) {
		this.isVendable = isVendable;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<ItemCart> getPanier() {
		return panier;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getVideos() {
		return videos;
	}

	public void setVideos(String videos) {
		this.videos = videos;
	}

	public List<Comment> getCommentaires() {
		return commentaires;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public static List<Item> getListItem() {
		return itemList;
	}

	public List<Item> getItems() {
		return items;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

}