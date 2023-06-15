package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import fr.jasmin.common.IConstant;
import fr.jasmin.control.impl.ItemMetier;
import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Comment;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemDao;
import fr.jasmin.utils.Utils;

@ManagedBean(name = "gestionarticlebean")
@SessionScoped
public class GestionArticlesBean extends MasterBean implements IConstant, Serializable {

	private static final long serialVersionUID = 1L;

	private final IItemMetier itemMetier = new ItemMetier();
	private final IUserMetier userMetier = new UserMetier();
	private final ICategoryDao categoryDao = new CategoryDao();
	private final IItemDao itemDao = new ItemDao();

	/// ***********************Attributs_article**************************

	private Integer idArticle;
	private Integer articleId;
	private String nomArticle;
	private String description;
	private Float prix;
	private Integer stock;
	private Integer remiseArticle;
	private String photosArticle;
	private String videosArticle;
	private Integer quantite = 0;
	private Boolean isVendable = true;
	private Boolean isCheckedArticle = false;
	private String articleSelectione;
	private Item articleActuel;
	private List<Item> listArticles;
	private List<Item> listArticlesCategorieActuel;

	/// ***********************Attributs_categorie**************************

	private Integer idCategorie;
	private Integer categorieId;
	private String nomCategorie;
	private Integer remiseCategorie;
	private String photoCategorie;
	private Boolean isRemiseCumulable;
	private Boolean isActif;
	private Category categorie;
	private String categorieSelectione;
	protected Category categorieActuel;
	private List<Category> listCategories;

	/// ***********************Attributs_divers**************************

	private List<Comment> commentaires;
	private List<ItemCart> panier;
	private String messageSuccess;
	private String messageError;
	private Boolean isDataTableRendred;
	private HashMap<Integer, Integer> mapQuantites = new HashMap<Integer, Integer>();

	/// ***********************Constructeurs**************************

	public GestionArticlesBean() {
		messageSuccess = "";
		messageError = "";
		isDataTableRendred = false;
//		articles = new ArrayList<Item>();

		if (this.getListCategories() == null || this.getListCategories().isEmpty()) {
			this.setListCategories(new ArrayList<Category>());
		}

		if (this.getListArticles() == null || this.getListArticles().isEmpty()) {
			this.setListArticles(new ArrayList<Item>());
		}

		try {
			this.listCategories = categoryDao.getCategoriess();
//			this.articles = itemDao.getArticles();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.getListCategories().size() > 0) { // prendre l'id (par défaut) du premier de la liste
			this.setCategorieId(this.getListCategories().get(0).getId());
			this.categorieActuel = listCategories.get(0);
			this.categorieActuel.getItems();

		} else {
			this.setCategorieId(1);
			this.categorieActuel = new Category(); // liste vide
//			this.articleActuel = new Item();
		}
//		this.setCategorieId(5);								// liste vide
//		this.setCurrentCategorie( this.getCategorieList()
//									.get(this.getCategorieId()));

	}

	// ------------------------------------action---------------------------------------------

	public String categorieChange(ValueChangeEvent e) {

		String pageReturn = null;
		Utils.trace("categorieChange  : %s\n", this.getCategorieId());
		Utils.trace("categorieChange  : %s\n", e.getNewValue().toString());
		this.setPromptStatus(String.format("Id cat : %d", e.getNewValue()));
		this.setCategorieId((int) e.getNewValue());
		Utils.trace("new categorieId  : %s\n", this.getCategorieId());

		try {
			this.categorieActuel = itemMetier.getCategory(this.getCategorieId());
			this.categorieActuel.getItems();
		} catch (Exception exception) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de categorie !\n" + exception.getMessage();
			exception.printStackTrace();

		}
		return pageReturn;
	}

	// ------------------------------------action---------------------------------------------
//	public Category get
//
//	try {
//		this.setCurrentCategorie(itemMetier.getCategory(this.getCategorieId()));
//	} catch (Exception exception) {
//		exception.printStackTrace();
//	}
	// ------------------------------------action---------------------------------------------

	public Category addCategorie() throws Exception {

		categorieActuel = new Category();

		try {

			categorieActuel.setNomCategorie(this.nomCategorie);
			Utils.trace("nom catgo  : %s\n", categorieActuel.getNomCategorie());
			categorieActuel.setRemiseCategorie(this.remiseCategorie);
			categorieActuel.setIsRemiseCumulable(this.isRemiseCumulable);
			categorieActuel.setPhotoCategorie(this.photoCategorie);

//			for (Item item : getListArticlesCategorieActuel()) {
//				item.setCategory(categorieActuel);
//				categorieActuel.getItems().add(item);
//			}

			itemMetier.addCategory(categorieActuel);
			listCategories = categoryDao.getCategoriess();
			categorieId = categorieActuel.getId();
			listArticlesCategorieActuel = categorieActuel.getItems();
			messageSuccess = "La categorie : '"+categorieActuel.getNomCategorie()+"' a été ajouté avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Error lors d'ajouger de categirie : '"+categorieActuel.getNomCategorie()+"'!\n Vérifiez si le nom de categorie exist déjà !\n"
					+ e.getMessage();
			e.printStackTrace();
			return null;
		}

//		getListArticles().clear();
		initialiseArticle();
		initialiseCategorie();

		return categorieActuel;
	}

	// ------------------------------------action---------------------------------------------

	public Category getCategoryByNom() {

		try {
			categorie = itemMetier.getCategoryByNom(getNomCategorie());
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de categorie !\n" + e.getMessage();
			e.printStackTrace();

		}

		return categorie;
	}

	// ------------------------------------action---------------------------------------------

	public List<String> getCategories() {
		try {
			return itemMetier.getCategories();
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de la liste des categories !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	// ------------------------------------action---------------------------------------------

	public List<Category> getCategoryList() {
		try {
			return itemMetier.getCategoryList();
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de la liste des categories !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	// ------------------------------------action---------------------------------------------

	public String getListCategoryItems() throws Exception {
		listCategories = itemMetier.getListCategoriesItems();
		ItemCart ic = new ItemCart();
		for (Category category : listCategories) {
			System.out.println(category);
			category.getItems().forEach(a -> System.out.println("  -> " + a));

		}

		return "";
	}

	// ------------------------------------action---------------------------------------------
	public String setItemsByCategory(ValueChangeEvent e) {

		System.out.println("Ancienne valeur : " + categorieSelectione);
		System.out.println("Nouvelle valeur : " + e.getNewValue());
		isCheckedArticle = false;
		quantite = 0;
		try {
			listArticles = itemMetier.getItemsByCategory(e.getNewValue().toString());
			if (listArticles != null && !listArticles.isEmpty()) {
				isDataTableRendred = true;
			}
		} catch (Exception expect) {
			messageError = "";
			expect.printStackTrace();
		}

		return "";
	}

	public String submit() {
		return null;
	}

	// ------------------------------------action---------------------------------------------

	public String validerPanier() {

		ItemCart ic = new ItemCart();

		return "command.xhtml";
	}

	// ------------------------------------action---------------------------------------------

	public void initialiseCategorie() {
		this.nomCategorie = "";
		this.remiseCategorie = null;
		this.isRemiseCumulable = null;
		this.photoCategorie = "";

	}

	// ------------------------------------action---------------------------------------------

	public Category selectCategorieActuel() {

//		Category categoryUpdate = new Category();

		try {
//			categoryUpdate = getCategorieActuel();
			Utils.trace("categorie selectioné  : %s\n", getCategorieActuel());
			this.setNomCategorie(getCategorieActuel().getNomCategorie());
			this.setRemiseCategorie(getCategorieActuel().getRemiseCategorie());
			this.setIsRemiseCumulable(getCategorieActuel().getIsRemiseCumulable());
			this.setPhotoCategorie(getCategorieActuel().getPhotoCategorie());
			this.setIdCategorie(getCategorieActuel().getId());

//			for (Item item : getListArticlesCategorieActuel()) {
//				item.setCategory(getCategorieActuel());
//				getCategorieActuel().getItems().add(item);
//			}

			messageSuccess = "Categorie actuel a été séléctioné.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de récupération de categorie actuel !\n" + e.getMessage();
			e.printStackTrace();
		}
		return categorieActuel;
	}

	// ------------------------------------action---------------------------------------------

	public Category updateCategorieSelected() {

//		Category categoryModified = new Category();

		try {
			Utils.trace("Categorie avant mis à jour  : %s\n", getCategorieActuel());
//			categoryModified = this.getCategorieActuel();
			getCategorieActuel().setNomCategorie(this.nomCategorie);
			Utils.trace("nomCategory  : %s\n", nomCategorie);
			getCategorieActuel().setRemiseCategorie(this.remiseCategorie);
			getCategorieActuel().setIsRemiseCumulable(this.isRemiseCumulable);
			getCategorieActuel().setPhotoCategorie(this.photoCategorie);

//			for (Item item : getListArticlesCategorieActuel()) {
//				item.setCategory(getCategorieActuel());
//				getCategorieActuel().getItems().add(item);
//			}

			itemMetier.updateCategory(getCategorieActuel());
			listCategories = categoryDao.getCategoriess();
			this.setCategorieId(getCategorieActuel().getId());
//			setCategorieActuel(categoryModified);
//			listArticlesCategorieActuel = categoryModified.getItems();
			messageSuccess = "Categorie actuel a été mis à jour avec succès.";
			messageError = "";

//			getListArticles().clear();

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de mettre à jour de cotegorie actuel !\n" + e.getMessage();
			e.printStackTrace();
		}
		Utils.trace("Categorie après mis à jour  : %s\n", getCategorieActuel());

		initialiseCategorie();
		initialiseArticle();

		return categorieActuel;

	}

	// ------------------------------------action---------------------------------------------

	public void deleteCategorie() {

		try {

			Utils.trace("CategorieActuel  : %s\n", getCategorieActuel());
			itemMetier.removeCategory(getCategorieActuel());
			setListCategories(categoryDao.getCategoriess());

			messageSuccess = "La categorie : " + getCategorieActuel().getNomCategorie()
					+ " avec tous ses articles a été supprimé avec succès.";
			messageError = "";

			if (getListCategories().size() > 0) {
				this.setCategorieActuel(getListCategories().get(0));

			} else {

				this.categorieActuel = new Category();
			}

//			for (Item item : ArticleBean.getListItem()) {
//				item.setCategory(currentCategorie);
//				currentCategorie.getItems().add(null);
//			}

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de categorie : " + getCategorieActuel().getNomCategorie()
					+ " !\n" + e.getMessage();
			e.printStackTrace();
		}
	}

	// ------------------------------------action---------------------------------------------

	public Item addArticle() {

		articleActuel = new Item();

		try {

			articleActuel.setName(nomArticle);
			articleActuel.setDescription(description);
			articleActuel.setPhotos(photosArticle);
			articleActuel.setVideos(videosArticle);
			articleActuel.setRemise(remiseArticle);
			articleActuel.setPrix(prix);
			articleActuel.setStock(stock);
			articleActuel.setIsVendable(isVendable);
			articleActuel.setCategory(getCategorieActuel());

			getCategorieActuel().getItems().add(articleActuel);

//		listArticlesCategorieActuel.add(articleActuel);
//		getCategorieActuel().getItems().add(articleActuel);

			Utils.trace("Categorie Actuel  : %s\n", getCategorieActuel());
			itemMetier.addArticle(getArticleActuel());

			Utils.trace("article ajouté  : %s\n", getArticleActuel());
//			setListArticlesCategorieActuel(getCategorieActuel().getItems());
			messageSuccess = "Article : " + nomArticle + " a été ajouté avec succès. Voici les details : ";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de l'ajout d'article : " + nomArticle + " !\n" + e.getMessage();
			e.printStackTrace();
		}

		initialiseArticle();
		initialiseCategorie();

		return articleActuel;
	}

	// ------------------------------------action---------------------------------------------

	public String addArticlesToPanier() {
		submit();
		getListArticles();
		LoginBean loginBean = new LoginBean();
		try {

			ItemCart itemCart = new ItemCart();
			itemCart.setUser(loginBean.getConnectedUser());
			for (Item item : listArticles) {
				if (item.getIsChecked() == true) {
					// mapQuantites.put(item.getId(), item.getQuantite());
					itemCart.setItem(item);
					itemCart.setQuantite(item.getQuantite());
					getPanier().add(itemCart);

				}
			}
			userMetier.addPanier(getPanier());

		} catch (Exception e) {
			messageError = "...";
		}
		return "";
	}

	// ------------------------------------action---------------------------------------------

	public Item selectArticleActuel() {

//		this.articleActuel = new Item();
////		   CategorieBean categorieBean = new CategorieBean();
//		   Utils.trace("CurrentCategorie  : %s\n", categorieBean.getCurrentCategorie());
//		   setItemList(categorieBean.getCurrentCategorie().getItems());

//		for (Item item : this.getListArticles()) {

//			if (item.getId() == this.getArticleId()) {
//				this.setArticleActuel(item);
//			}
//		}

		try {
			Utils.trace("itemId  : %s\n", getArticleId());
			Utils.trace("itemId  : %s\n", getArticleActuel());
			selectCategorieActuel();
//			this.setArticleActuel(itemMetier.getArticle(this.getArticleId()));
			this.setNomArticle(getArticleActuel().getName());
			this.setDescription(getArticleActuel().getDescription());
			this.setPhotosArticle(getArticleActuel().getPhotos());
			this.setVideosArticle(getArticleActuel().getVideos());
			this.setRemiseArticle(getArticleActuel().getRemise());
			this.setPrix(getArticleActuel().getPrix());
			this.setStock(getArticleActuel().getStock());
			this.setIsVendable(getArticleActuel().getIsVendable());

//			itemList.add(item);

			messageSuccess = "l'Article actuel a été selectioné avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la séléction de l'article actuel !\n" + e.getMessage();
			e.printStackTrace();
		}
		Utils.trace("AtricleActuel  : %s\n", this.getArticleActuel());

		return this.articleActuel;

	}

	// ------------------------------------action---------------------------------------------

	public Item updateArticleSelected() {

//		Item itemModified = new Item();

		Utils.trace("Article avant mis à jour  : %s\n", getArticleActuel());
//			item = itemMetier.getArticle(getId());
//			Utils.trace("Id  : %s\n", this.getIdArticle());
//			itemModified = itemMetier.getArticle(this.getIdArticle());
//			itemModified = this.getCurrentItem();
//			Utils.trace("Id  : %s\n", this.getId());
//			Utils.trace("Id  : %s\n", this.getCurrentItem());
//			Utils.trace("currentItem  : %s\n", this.currentItem);
		getArticleActuel().setName(this.nomArticle);
		getArticleActuel().setDescription(this.description);
		getArticleActuel().setPhotos(this.photosArticle);
		getArticleActuel().setVideos(this.videosArticle);
		getArticleActuel().setRemise(this.remiseArticle);
		getArticleActuel().setPrix(this.prix);
		getArticleActuel().setStock(this.stock);
		getArticleActuel().setIsVendable(this.isVendable);

//			getCategorieActuel().getItems().add(getArticleActuel());

		try {

			itemMetier.updateArticle(getArticleActuel());
			setCategorieActuel(getArticleActuel().getCategory());

//			listArticles = itemDao.getArticles();
//			CategorieBean.currentCategorie.getItems();

			messageSuccess = "Article a été mis à jour avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la mis à jour d'article !\n" + e.getMessage();
			e.printStackTrace();
		}
		Utils.trace("Article après mis à jour  : %s\n", getArticleActuel());

		initialiseCategorie();
		initialiseArticle();

		return getArticleActuel();
	}

	// ------------------------------------action---------------------------------------------

	public void deleteArticle() {

		try {
			Utils.trace("Article Actuel : %s\n", getArticleActuel());
			itemMetier.removeArticle(getArticleActuel());
//		itemList = itemDao.getArticles();
			listCategories = categoryDao.getCategoriess();
			setCategorieActuel(categoryDao.getCategoryByNom(getCategorieActuel().getNomCategorie()));
			setCategorieId(getCategorieActuel().getId());
			messageSuccess = "l'Article : " + getArticleActuel().getName() + " a été supprimé avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de l'Article : " + getArticleActuel().getName() + " !\n"
					+ e.getMessage();
			e.printStackTrace();
		}
	}

//------------------------------------action---------------------------------------------

	public void deleteArticleById() {
		Item articleSupprime = new Item();
try {
			Utils.trace("Article Actuel : %s\n", getArticleActuel());
			articleSupprime = getArticleActuel();
			itemMetier.removeArticle(getArticleActuel().getId());
			listCategories = categoryDao.getCategoriess();
			setCategorieActuel(categoryDao.getCategoryByNom(getCategorieActuel().getNomCategorie()));
			setCategorieId(getCategorieActuel().getId());
			
			messageSuccess = "l'Article : " + articleSupprime.getName() + " a été supprimé avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de l'Article : " + articleSupprime.getName() + " !\n"+ e.getMessage();
			e.printStackTrace();
		}
	}

//------------------------------------action---------------------------------------------

	public void initialiseArticle() {
		this.nomArticle = "";
		this.description = "";
		this.photosArticle = "";
		this.videosArticle = "";
		this.remiseArticle = null;
		this.prix = null;
		this.stock = null;

		this.isVendable = false;

	}

	// ------------------------------------getters/setters---------------------------------------------

	public Integer getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(Integer idArticle) {
		this.idArticle = idArticle;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
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

	public Integer getRemiseArticle() {
		return remiseArticle;
	}

	public void setRemiseArticle(Integer remiseArticle) {
		this.remiseArticle = remiseArticle;
	}

	public Boolean getIsVendable() {
		return isVendable;
	}

	public void setIsVendable(Boolean isVendable) {
		this.isVendable = isVendable;
	}

	public String getPhotosArticle() {
		return photosArticle;
	}

	public void setPhotosArticle(String photosArticle) {
		this.photosArticle = photosArticle;
	}

	public String getVideosArticle() {
		return videosArticle;
	}

	public void setVideosArticle(String videosArticle) {
		this.videosArticle = videosArticle;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public List<Comment> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Comment> commentaires) {
		this.commentaires = commentaires;
	}

	public List<Item> getListArticles() {
		return listArticles;
	}

	public void setListArticles(List<Item> listArticles) {
		this.listArticles = listArticles;
	}

	public Item getArticleActuel() {
		return articleActuel;
	}

	public void setArticleActuel(Item articleActuel) {
		this.articleActuel = articleActuel;
	}

	public String getArticleSelectione() {
		return articleSelectione;
	}

	public void setArticleSelectione(String articleSelectione) {
		this.articleSelectione = articleSelectione;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public Boolean getIsCheckedArticle() {
		return isCheckedArticle;
	}

	public void setIsCheckedArticle(Boolean isCheckedArticle) {
		this.isCheckedArticle = isCheckedArticle;
	}

	public Integer getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Integer idCategorie) {
		this.idCategorie = idCategorie;
	}

	public Integer getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(Integer categorieId) {
		this.categorieId = categorieId;
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

	public Boolean getIsActif() {
		return isActif;
	}

	public void setIsActif(Boolean isActif) {
		this.isActif = isActif;
	}

	public Category getCategorie() {
		return categorie;
	}

	public void setCategorie(Category categorie) {
		this.categorie = categorie;
	}

	public String getCategorieSelectione() {
		return categorieSelectione;
	}

	public void setCategorieSelectione(String categorieSelectione) {
		this.categorieSelectione = categorieSelectione;
	}

	public List<Category> getListCategories() {
		return listCategories;
	}

	public void setListCategories(List<Category> listCategories) {
		this.listCategories = listCategories;
	}

	public Category getCategorieActuel() {
		return categorieActuel;
	}

	public void setCategorieActuel(Category categorieActuel) {
		this.categorieActuel = categorieActuel;
	}

	public List<ItemCart> getPanier() {
		return panier;
	}

	public void setPanier(List<ItemCart> panier) {
		this.panier = panier;
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

	public Boolean getIsDataTableRendred() {
		return isDataTableRendred;
	}

	public void setIsDataTableRendred(Boolean isDataTableRendred) {
		this.isDataTableRendred = isDataTableRendred;
	}

	public HashMap<Integer, Integer> getMapQuantites() {
		return mapQuantites;
	}

	public void setMapQuantites(HashMap<Integer, Integer> mapQuantites) {
		this.mapQuantites = mapQuantites;
	}

	public List<Item> getListArticlesCategorieActuel() {
		return listArticlesCategorieActuel;
	}

	public void setListArticlesCategorieActuel(List<Item> listArticlesCategorieActuel) {
		this.listArticlesCategorieActuel = listArticlesCategorieActuel;
	}

}
