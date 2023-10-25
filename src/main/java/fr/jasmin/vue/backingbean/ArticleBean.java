package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import fr.jasmin.control.impl.ItemMetier;
import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.entity.Comment;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.entity.User;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.IItemDao;
import fr.jasmin.utils.Utils;

@ManagedBean(name = "articlebean")
public class ArticleBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private final IItemDao itemDao = new ItemDao();
	private final IItemMetier itemMetier = new ItemMetier();
	
	
	private Integer idCommande;
	private Integer numeroCommande;
	private Date dateCreation;
	private Date dateLivraison;
	private Integer totalRemise;
	private Float fraisExpediation;
	private Float totalGeneral;
	private String adresseFacturation;
	private String adresseLivraison;
	private String cartePaiementDefaut;
	
	/// ***********************Attributs_categorie**************************

	private Integer remiseCategorie;
	private Boolean isRemiseCumulable = true;

	/// ***********************Attributs_panier**************************

	private List<ItemCart> panier;
	private List<ItemCart> panierModifie;
	private Integer nombrePanier;
	private User userActuel;
	private ItemCart itemCartActuel;

	/// ***********************Attributs_divers**************************

	private List<Comment> commentaires;
	private String messageSuccess;
	private String messageError;
	private Boolean isDataTableRendred;
	private LoginBean loginBean = new LoginBean();
	
	
	private Integer id;
	private Integer itemId;
	
	private String nom;

	private String description;

	private Float prix;

	private Integer remise;

	private Integer stock;

	@ManagedProperty(name = "isVendable", value = "true")
	private Boolean isVendable;

	private String photos;
	private String videos;
	private List<Item> itemList;
	private static List<Item> listItem = new ArrayList<Item>();
	private List<Item> items;
	private Item item;
	private Item currentItem;
	private Integer quantite = 0;
	private Boolean isChecked = false;
	private HashMap<Integer, Integer> mapQuantites = new HashMap<Integer, Integer>();

	public ArticleBean() {
		
		messageSuccess = "";
		messageError = "";
		
		if (this.getItemList() == null) {
			this.setItemList(new ArrayList<Item>());
		}
		
		try {
			this.itemList = itemDao.getArticles();
		
		if (this.getItemList().size()>0 && this.getItemId()!= null) {		 
			
			for (Item item : this.getItemList()) {
				if (item.getId() == this.getItemId()) {
					this.setCurrentItem(item);
				}
			}
			Utils.trace("new itemId  : %s\n", this.getItemId());
			Utils.trace("new currentItemId  : %s\n", this.getCurrentItem());
			
		}else {
			this.setItemId(1);
			this.currentItem = new Item(); 
		}	
		
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la recuperation de currentItem !\n" + e.getMessage();
			e.printStackTrace();
		}
		
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public String submit() {
		return null;
	}

	public void addArticle() {
		Item item = new Item();
		item.setName(nom);
		item.setDescription(description);
		item.setPhotos(photos);
		item.setVideos(videos);
		item.setRemise(remise);
		item.setPrix(prix);
		item.setStock(stock);
		item.setIsVendable(isVendable);

		itemList.add(item);
		
		initialiseArticle();
	}

//------------------------Action------------------------------------
	
	public Item updateArticle() {
		
		   this.currentItem = new Item();
		for (Item item : this.getItemList()) {
			
			if (item.getId() == this.getItemId()) {
				this.setCurrentItem(item);
			}
		}
		Utils.trace("itemId  : %s\n", this.getItemId());

		
		try {
			Utils.trace("itemId  : %s\n", this.getCurrentItem());
			
			this.setCurrentItem(itemMetier.getArticle(this.getItemId()));
			this.setId(this.getCurrentItem().getId());
			this.setNom(this.getCurrentItem().getName());
			this.setDescription(this.getCurrentItem().getDescription());
			this.setPhotos(this.getCurrentItem().getPhotos());
			this.setVideos(this.getCurrentItem().getVideos());
			this.setRemise(this.getCurrentItem().getRemise());
			this.setPrix(this.getCurrentItem().getPrix());
			this.setStock(this.getCurrentItem().getStock());
			this.setIsVendable(this.getCurrentItem().getIsVendable());

			messageSuccess = "Article selectioné avec succès.";
			messageError = "";
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la modification d'article !\n" + e.getMessage();
			e.printStackTrace();
		}
		Utils.trace("currentItem  : %s\n", this.getCurrentItem());
		return this.currentItem;

	}

	public Item editeArticle() {
		
		Item itemModified = new Item();
		Utils.trace("ItemId  : %s\n", this.getItemId());
		
		try {
			Utils.trace("CurrentItem  : %s\n", this.getCurrentItem());
			Utils.trace("Id  : %s\n", this.getId());
			itemModified = itemMetier.getArticle(this.getId());
			
			itemModified.setName(this.nom);
			itemModified.setDescription(this.description);
			itemModified.setPhotos(this.photos);
			itemModified.setVideos(this.videos);
			itemModified.setRemise(this.remise);
			itemModified.setPrix(this.prix);
			itemModified.setStock(this.stock);
			itemModified.setIsVendable(this.isVendable);
			
			itemMetier.updateArticle(itemModified);
			itemList = itemDao.getArticles();
			
			messageSuccess = "Article a été mis à jour avec succès.";
			messageError = "";
		}catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la mis à jour d'article !\n" + e.getMessage();
			e.printStackTrace();
		}
		
		return itemModified;
		}

	public void deleteArticle() {
		try {
			itemMetier.removeArticle(this.getCurrentItem());
			
			messageSuccess = "Article est supprimé avec succès.";
			messageError = "";
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression d'article !\n" + e.getMessage();
			e.printStackTrace();
		}
	}

	public void deleteArticleById() {
		try {
			itemMetier.removeArticle(this.getCurrentItem().getId());
			
			messageSuccess = "Article est supprimé avec succès.";
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

	public void initialiseArticle() {
		nom = "";
		description = "";
		photos = "";
		videos = "";
		remise = null;
		prix = null;
		stock = null;

		isVendable = false;

	}

	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
		return listItem;
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

	public String getMessageSuccess() {
		return messageSuccess;
	}

	public void setMessageSuccess(String messageSuccess) {
		this.messageSuccess = messageSuccess;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	
	

}