package fr.jasmin.vue.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import com.paypal.base.rest.PayPalRESTException;

import fr.jasmin.common.IConstant;
import fr.jasmin.control.impl.ItemMetier;
import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Comment;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.entity.OrderDetail;
import fr.jasmin.entity.User;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemCartDao;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemCartDao;
import fr.jasmin.model.dao.interfaces.IItemDao;
import fr.jasmin.utils.Utils;

@ManagedBean(name = "gestionarticlebean")
@SessionScoped
public class GestionArticlesBean extends MasterBean implements IConstant, Serializable {

	private static final long serialVersionUID = 1L;

	private final IItemMetier itemMetier = new ItemMetier();
	private final ICategoryDao categoryDao = new CategoryDao();

	/// ***********************Attributs_article**************************

	private Integer idArticle;
	private Integer articleId;
	private String nomArticle;
	private String description;
	private Float prix;
	private Integer stock;
	private Integer remiseArticle;
	private static String photosArticle;
	private String videosArticle;
	private Integer quantite = 1;
	private Integer quantiteMax = 10;
	private Boolean isChecked = false;
	private Boolean isVendable = true;
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
	private Boolean isRemiseCumulable = true;
	private Boolean isActif = true;
	private Category categorie;
	private Category categorieSelectione;
	private Category categorieActuel;
	private List<Category> listCategories;

	/// ***********************Attributs_panier**************************

	private List<ItemCart> panier;
	private List<ItemCart> panierModifie;
	private List<ItemCart> panierSelected;
	private List<String> listItemQuantite;
	private String itemQiantite;
	private Integer nombrePanier;
	private Float prixTotal;
	private Float livraison;
	private Float prixTotalGeneral;
	private Integer idPanier;
	private User userActuel;
	private static ItemCart itemCartActuel;
	private ItemCart itemCartSelected = new ItemCart();

	/// ***********************Attributs_divers**************************

	private List<Comment> commentaires;
	private String messageSuccess;
	private String messageError;
	private Boolean isDataTableRendred;
	private HashMap<Integer, Integer> mapQuantites = new HashMap<Integer, Integer>();

	/// ***********************Order details**************************
	private String product;
	private String subtotal;
	private String shipping;
	private String tax;
	private String total;

	/// ***********************Constructeurs**************************

	public GestionArticlesBean() {
		messageSuccess = "";
		messageError = "";
		isDataTableRendred = false;
		setLivraison(0f);
		categorieSelectione = new Category();

		if (this.getListCategories() == null || this.getListCategories().isEmpty()) {
			this.setListCategories(new ArrayList<Category>());
		}

		if (this.getListArticles() == null || this.getListArticles().isEmpty()) {
			this.setListArticles(new ArrayList<Item>());
		}
		if (this.getPanier() == null || this.getPanier().isEmpty()) {
			this.panier = new ArrayList<ItemCart>();
			nombrePanier = getPanier().size();
		}

		try {
			this.listCategories = itemMetier.getCategoriess();
			if (LoginBean.connectedUser != null) {
			
			getPanierByUserId();
			updateTotalPrix();
			}
			setPanier(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.listCategories.size() > 0 && this.getCategorieActuel() != null) { // prendre l'id (par défaut) du
																					// premier de la liste
			this.setCategorieId(this.getCategorieActuel().getId());

			for (Item item : this.getCategorieActuel().getItems()) {
				if (item.getIsVendable()) {
					this.categorieSelectione.getItems().add(item);
				}
			}

		} else if (this.listCategories.size() > 0 && this.getCategorieActuel() == null) {

			this.setCategorieId(this.listCategories.get(0).getId());
			this.setCategorieActuel(this.listCategories.get(0));

			for (Item item : this.getCategorieActuel().getItems()) {
				if (item.getIsVendable()) {
					this.categorieSelectione.getItems().add(item);
				}
			}

		} else {
			this.categorieActuel = new Category(); // liste vide
		}
	}

	// ------------------------------------action---------------------------------------------
	public String seDeconnecter() throws Exception {

		try {
			 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			 HttpSession session = (HttpSession) externalContext.getSession(false);
			 if (session != null) {
		            // Invalider la session
		            session.invalidate();
		        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/signIn.xhtml?faces-redirect=true";

	}
	// ------------------------------------action---------------------------------------------

	public String categorieChange(ValueChangeEvent e) {
		categorieSelectione = new Category();
		String pageReturn = null;
		Utils.trace("categorieChange  : %s\n", this.getCategorieId());
		Utils.trace("categorieChange  : %s\n", e.getNewValue().toString());
		this.setPromptStatus(String.format("Id cat : %d", e.getNewValue()));
		this.setCategorieId((int) e.getNewValue());
		Utils.trace("new categorieId  : %s\n", this.getCategorieId());

		try {
			this.setCategorieActuel(itemMetier.getCategory(this.getCategorieId()));
			Utils.trace("Les Items de categorie actuel  : %s\n", this.getCategorieActuel().getItems());
			for (Item item : this.categorieActuel.getItems()) {
				if (item.getIsVendable()) {
					this.categorieSelectione.getItems().add(item);
				}
			}
			this.categorieSelectione.getItems();
			Utils.trace("categorieSelection  : %s\n", this.categorieSelectione.getItems());
		} catch (Exception exception) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de categorie !\n" + exception.getMessage();
			exception.printStackTrace();

		}
		initialiseArticle();
		initialiseCategorie();
		return pageReturn;
	}

	// ------------------------------------action---------------------------------------------

	public String addCategorie() throws Exception {

		categorieActuel = new Category();

		try {

			categorieActuel.setNomCategorie(this.nomCategorie);
			Utils.trace("nom catgo  : %s\n", categorieActuel.getNomCategorie());
			categorieActuel.setRemiseCategorie(this.remiseCategorie);
			categorieActuel.setIsRemiseCumulable(this.isRemiseCumulable);
			categorieActuel.setPhotoCategorie(this.photoCategorie);

			itemMetier.addCategory(categorieActuel);
			listCategories = categoryDao.getCategoriess();
			categorieId = categorieActuel.getId();
			listArticlesCategorieActuel = categorieActuel.getItems();
			messageSuccess = "La categorie : '" + categorieActuel.getNomCategorie() + "' a été ajouté avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Error lors d'ajouger de categirie : '" + categorieActuel.getNomCategorie()
					+ "'!\n Vérifiez si le nom de categorie exist déjà !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}

		initialiseArticle();
		initialiseCategorie();

		return "gestion_articles.xhtml";
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
		isChecked = false;
		quantite = 1;
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

	public void initialiseCategorie() {
		this.nomCategorie = "";
		this.remiseCategorie = null;
		this.isRemiseCumulable = true;
		this.photoCategorie = "";

	}

	// ------------------------------------action---------------------------------------------

	public String selectCategorieActuel() {

		try {
			Utils.trace("categorie selectioné  : %s\n", getCategorieActuel());
			this.setNomCategorie(getCategorieActuel().getNomCategorie());
			this.setRemiseCategorie(getCategorieActuel().getRemiseCategorie());
			this.setIsRemiseCumulable(getCategorieActuel().getIsRemiseCumulable());
			this.setPhotoCategorie(getCategorieActuel().getPhotoCategorie());
			this.setIdCategorie(getCategorieActuel().getId());

			messageSuccess = "Categorie actuel a été séléctioné.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de récupération de categorie actuel !\n" + e.getMessage();
			e.printStackTrace();
		}
		return "gestion_articles_addUpdate.xhtml";
	}

	// ------------------------------------action---------------------------------------------

	public String updateCategorieSelected() {

		try {
			Utils.trace("Categorie avant mis à jour  : %s\n", getCategorieActuel());
			getCategorieActuel().setNomCategorie(this.nomCategorie);
			Utils.trace("nomCategory  : %s\n", nomCategorie);
			getCategorieActuel().setRemiseCategorie(this.remiseCategorie);
			getCategorieActuel().setIsRemiseCumulable(this.isRemiseCumulable);
			getCategorieActuel().setPhotoCategorie(this.photoCategorie);

			itemMetier.updateCategory(getCategorieActuel());
			listCategories = categoryDao.getCategoriess();
			this.setCategorieId(getCategorieActuel().getId());
			messageSuccess = "Categorie actuel a été mis à jour avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de mettre à jour de cotegorie actuel !\n" + e.getMessage();
			e.printStackTrace();
		}
		Utils.trace("Categorie après mis à jour  : %s\n", getCategorieActuel());

		initialiseCategorie();
		initialiseArticle();

		return "gestion_articles.xhtml";

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

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de categorie : " + getCategorieActuel().getNomCategorie()
					+ " !\n" + e.getMessage();
			e.printStackTrace();
		}
	}

	// ------------------------------------action---------------------------------------------

	public String addArticle() {
		articleActuel = new Item();

		try {

			articleActuel.setName(nomArticle);
			articleActuel.setDescription(description);
			articleActuel.setPhotos(FileUploadMBean.getNomPhoto());
			articleActuel.setVideos(videosArticle);
			articleActuel.setRemise(remiseArticle);
			articleActuel.setPrix(prix);
			articleActuel.setStock(stock);
			articleActuel.setIsVendable(isVendable);
			articleActuel.setCategory(getCategorieActuel());

			getCategorieActuel().getItems().add(articleActuel);

			itemMetier.addArticle(getArticleActuel());

			messageSuccess = "Article : " + nomArticle + " a été ajouté avec succès. Voici les details : ";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de l'ajout d'article : " + nomArticle + " !\n" + e.getMessage();
			e.printStackTrace();
		}

		initialiseArticle();
		initialiseCategorie();

		return "gestion_articles.xhtml";
	}

	// ------------------------------------action---------------------------------------------

	public String selectArticleActuel() {

		try {
			if (LoginBean.connectedUser == null) {
				setPanier(null);
			}
			Utils.trace("itemId  : %s\n", getArticleId());
			Utils.trace("itemId  : %s\n", this.articleActuel);
			selectCategorieActuel();
			this.setNomArticle(getArticleActuel().getName());
			this.setDescription(getArticleActuel().getDescription());
			Utils.trace("photo article actuel : %s\n", getArticleActuel().getPhotos());
			setPhotosArticle(getArticleActuel().getPhotos());
			this.setVideosArticle(getArticleActuel().getVideos());
			this.setRemiseArticle(getArticleActuel().getRemise());
			this.setPrix(getArticleActuel().getPrix());
			this.setStock(getArticleActuel().getStock());
			this.setIsVendable(getArticleActuel().getIsVendable());

			messageSuccess = "l'Article actuel a été selectioné avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la séléction de l'article actuel !\n" + e.getMessage();
			e.printStackTrace();
		}
		Utils.trace("AtricleActuel  : %s\n", this.getArticleActuel());

		return "";

	}

	// ------------------------------------action---------------------------------------------

	public String updateArticleSelected() {

		getArticleActuel().setName(this.nomArticle);
		getArticleActuel().setDescription(this.description);
		getArticleActuel().setPhotos(photosArticle);
		getArticleActuel().setVideos(this.videosArticle);
		getArticleActuel().setRemise(this.remiseArticle);
		getArticleActuel().setPrix(this.prix);
		getArticleActuel().setStock(this.stock);
		getArticleActuel().setIsVendable(this.isVendable);

		try {

			itemMetier.updateArticle(getArticleActuel());
			setCategorieActuel(getArticleActuel().getCategory());

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

		return "gestion_articles.xhtml";
	}

	// ------------------------------------action---------------------------------------------

	public void deleteArticle() {

		try {
			Utils.trace("Article Actuel : %s\n", getArticleActuel());
			itemMetier.removeArticle(getArticleActuel());
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
			messageError = "Erreur lors de la suppression de l'Article : " + articleSupprime.getName() + " !\n"
					+ e.getMessage();
			e.printStackTrace();
		}
	}

	// ------------------------------------action---------------------------------------------

	public String addArticlesToPanier() {

		LoginBean loginBean = new LoginBean();

		itemCartActuel = new ItemCart();
		try {

			if (LoginBean.connectedUser==null&& getPanier()==null){
				
				return loginBean.seDeconnecter();
			}
			setArticleActuel(itemMetier.getArticle(getArticleActuel().getId()));
			Utils.trace("itemId  : %s\n", getArticleActuel());
			Utils.trace("connected user  : %s\n", LoginBean.connectedUser);
			itemCartActuel.setUser(LoginBean.connectedUser);
			Utils.trace("itemId  : %s\n", getArticleActuel().getId());
			itemCartActuel.setItem(getArticleActuel());
			itemCartActuel.setQuantite(this.getQuantite());
			Utils.trace("itemId  : %s\n", itemCartActuel);
			itemMetier.addItemCart(itemCartActuel);
			getPanierByUserId();

			messageSuccess = "l'Article : " + getArticleActuel().getName() + " a été ajouté au panier avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de l'ajout de l'article : " + getArticleActuel().getName() + " au panier !\n"
					+ e.getMessage();
			e.printStackTrace();
		}
		initialiseIscheckedQuentite();
		return "";
	}

	// ------------------------------------action---------------------------------------------

	public String voirPanier() {

		try {
			if(nombrePanier==0) {
				return "gestion_achats.xhtml";
			}
			getPanierByUserId();
			this.setNombrePanier(getPanier().size());
			messageSuccess = "";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la validation du panier !\n" + e.getMessage();
			e.printStackTrace();
		}

		return "panier.xhtml";

	}

//------------------------------------action---------------------------------------------

	public List<ItemCart> getPanierByUserId() {

		try {
			
			this.setPanier(itemMetier.getPanierByUserId(LoginBean.connectedUser.getId()));
			this.setNombrePanier(this.getPanier().size());
			messageSuccess = "";
			messageError = "";
//			}
			return null;
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de panier!\n" + e.getMessage();
			e.printStackTrace();
		}
		return panier;
	}

	// ------------------------------------action---------------------------------------------

	public Integer nombreArticleCommand() {
		
		Integer nombreArticleCommand = 0;
		
		for (ItemCart itemCart : getPanierModifie()) {

		nombreArticleCommand += itemCart.getQuantite();
		
		}
		
		return nombreArticleCommand;

	}
		// ------------------------------------action---------------------------------------------
		
		public String validerPanier() {

		if (this.panierSelected() == null || this.panierSelected().isEmpty()) {

			messageError = "Veuillez sélectioner au moins un article dans le panier";
			messageSuccess = "";
			return "";
		}
		
		for (ItemCart itemCart : getPanier()) {

			if (itemCart.getQuantite() > itemCart.getItem().getStock()) {
				
				messageError = "La quantité disponible en stock pour l'article : " + itemCart.getItem().getName()
						+ "  est : " + itemCart.getItem().getStock()
						+ " ,veuillez choisir une quantité inferieur ou égale à "+ itemCart.getItem().getStock();
				messageSuccess = "";
				return null;
			}
			}
		panierSelected();
		getPanierModifie();
		messageSuccess = "Panier a été mis à jour avec succès.";
		messageError = "";
		return "/command.xhtml";

	}

	// ------------------------------------action---------------------------------------------

	public List<ItemCart> panierSelected() {
		panierModifie = new ArrayList<ItemCart>();
		try {
			for (ItemCart itemCart : getPanier()) {

				if (itemCart.getIsChecked()) {
					this.panierModifie.add(itemCart);
				}

			}

			messageSuccess = "Panier a été séléctioné avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la séléction de panier." + "!\n" + e.getMessage();
			e.printStackTrace();
		}

		return panierModifie;

	}
	// ------------------------------------action---------------------------------------------

	public List<ItemCart> panierUpdate() {
		panierModifie = new ArrayList<ItemCart>();
		try {
			if ( getPanier()== null) {
				return null;
			}
			Utils.trace("getPanier  : %s\n",getPanier() );
			for (ItemCart itemCart : getPanier()) {
				if (itemCart.getQuantite() <= itemCart.getItem().getStock()) {

					this.panierModifie.add(itemCart);
					itemMetier.updateItemCart(itemCart);
				}

				messageError = "La quantité disponible en stock pour l'article : " + itemCart.getItem().getName()
						+ "  est : " + itemCart.getItem().getStock()
						+ " ,veuillez choisir une quantité inferieur ou égale à "+ itemCart.getItem().getStock();
				messageSuccess = "";
			
			}
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la séléction de panier." + "!\n" + e.getMessage();
			e.printStackTrace();
		}

		return panierModifie;

	}

	// ------------------------------------action---------------------------------------------

	public void updateTotalPrix() {

		panierSelected = new ArrayList<ItemCart>();

		try {
			if (getPanier() != null) {
			System.out.println("===== Checked/unCheked =====");
			
			panierUpdate();
			Utils.trace("panier : %s\n", getPanier());
			
			for (ItemCart itemCart : getPanier()) {

				if (itemCart.getIsChecked() && itemCart.getQuantite() <= itemCart.getItem().getStock() ) {

					this.panierSelected.add(itemCart);
						itemMetier.updateItemCart(itemCart);
				}

			}
			if (panierSelected != null) {

				Utils.trace("panier : %s\n", getPanier());
				Utils.trace("panier modifie : %s\n", getPanierModifie());
				Utils.trace("panier selected : %s\n", getPanierSelected());
				calculPrixTotal();
				calculLivraison();
				calculPrixTotalAvecLivraison();
				setPanierModifie(getPanierSelected());

			} else {
				setPrixTotal(0.f);
				setPrixTotalGeneral(0.f);
				setLivraison(0.f);
				System.out.println("===== Un Checked =====");
				messageError = "Veuillez selectioner un article dans le panier ou choisir une quantité inferieur à stock";
				messageSuccess = "";

			}
			
			}
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la mise à jour de prisTotal !\n" + e.getMessage();
			e.printStackTrace();
		}
		
	}

	// ------------------------------------action---------------------------------------------

	public float calculTax() {

		float tax = calculPrixTotal() / 5;

		tax = OrderDetail.withBigDecimal(tax, 2);

		return tax;
	}
	// ------------------------------------action---------------------------------------------

	public float calculLivraison() {

		livraison = 0f;

		if (calculPrixTotal() < 100.0f && calculPrixTotal() >= 50.0f) {

			livraison = 10.0f;
		}
		if (calculPrixTotal() < 50.0f && calculPrixTotal() >= 10.0f) {

			livraison = 5.0f;
		}
		if (calculPrixTotal() < 10.0f && calculPrixTotal() > 0f) {

			livraison = 2.0f;
		}

		setLivraison(OrderDetail.withBigDecimal(livraison, 2));
		return getLivraison();
	}
	// ------------------------------------action---------------------------------------------

	public Float calculPrixTotal() {
		
		prixTotal = 0f;

		for (ItemCart itemCart : getPanierSelected()) {
			prixTotal += itemCart.getPrixTotalArticle();
		}
		

		setPrixTotal(OrderDetail.withBigDecimal(prixTotal, 2));
		return getPrixTotal();
	}
	// ------------------------------------action---------------------------------------------

	public Float calculPrixTotalAvecLivraison() {
		prixTotalGeneral = 0.0f;
		prixTotalGeneral = calculPrixTotal() + calculLivraison();

		setPrixTotalGeneral(OrderDetail.withBigDecimal(prixTotalGeneral, 2));
		return getPrixTotalGeneral();
	}

	// ------------------------------------action---------------------------------------------
	public void deleteItemCartById() {

		try {
			Utils.trace("ItemCart Actuel : %s\n", getItemCartSelected().getId());

			itemMetier.removeItemCartById(getItemCartSelected().getId());
			getPanierByUserId();
			nombrePanier = getPanier().size();

			messageSuccess = "l'itemCart a été supprimé avec succès.";
			messageError = "";

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de l'itemCart!\n" + e.getMessage();
			e.printStackTrace();
		}

	}
	// ------------------------------------action---------------------------------------------

	public void sendDataToServlet() throws IOException {
		setProduct("SumsungS21");
		setSubtotal(Float.toString(calculPrixTotal() - calculTax()));
		setShipping(Float.toString(calculLivraison()));
		setTax(Float.toString(calculTax()));
		setTotal(Float.toString(calculPrixTotalAvecLivraison()));
		Utils.trace("SubTotal : %s\n", getSubtotal());
		Utils.trace("Livraison : %s\n", getShipping());
		Utils.trace("Tax : %s\n", getTax());
		Utils.trace("PrixTotal : %s\n", getTotal());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("authorize_payment?product=" + getProduct() + "&subtotal=" + getSubtotal()
				+ "&shipping=" + getShipping() + "&tax=" + getTax() + "&total=" + getTotal());
	}

//------------------------------------action---------------------------------------------

	public String payer() throws PayPalRESTException {

		return "/authorize_payment";
	}

//===========================================================	
	public String goToHome() throws Exception {
		
		initialiseUserPanier();
		messageSuccess = "";
		messageError = "";
		return "home.xhtml";
	}
//===========================================================	
	public String goToPageAddUpdateInitialised() {
		
		initialiseArticle();
		messageSuccess = "";
		messageError = "";
		
		return "gestion_articles_addUpdate.xhtml";
	}

//===========================================================	
	public String goToPageAddUpdateArticleSelected() {

		selectArticleActuel();
		messageSuccess = "";
		messageError = "";

		return "gestion_articles_addUpdate.xhtml";
	}

//===========================================================	
	public String goToPageAchatUnArticle() {

		selectArticleActuel();
		messageSuccess = "";
		messageError = "";

		return "gestion_achats_unArticle.xhtml";
	}
//===========================================================	
	public String goToPageAchatUnArticleHome() {
		
		selectArticleActuel();
		messageSuccess = "";
		messageError = "";
		
		return "gestion_achats_unArticleHome.xhtml";
	}
//------------------------------------action---------------------------------------------

	public void initialiseArticle() {
		this.nomArticle = "";
		this.description = "";
		GestionArticlesBean.photosArticle = "";
		this.videosArticle = "";
		this.remiseArticle = null;
		this.prix = null;
		this.stock = null;
		this.isVendable = true;

	}
//------------------------------------action---------------------------------------------
	
	public void initialiseUserPanier() {
		LoginBean.setConnectedUser(null); 
			setPanier(null);
	}

	// ---------------------------------------------------------------------------------

	public void initialiseIscheckedQuentite() {
		this.quantite = 1;
		this.isChecked = false;
	}

	// ---------------------------------------------------------------------------------
	public String retourEnAchat() {
		
		messageSuccess = "";
		messageError = "";
		
		if (LoginBean.connectedUser == null) {
			return "home.hxtml";
		}

		return "gestion_achats.xhtml";
	}

	// ---------------------------------------------------------------------------------
	public String retourEnGestionArticle() {

		messageSuccess = "";
		messageError = "";

		return "gestion_articles.xhtml";
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

	public Integer getIdPanier() {
		return idPanier;
	}

	public void setIdPanier(Integer idPanier) {
		this.idPanier = idPanier;
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

	public static String getPhotosArticle() {
		return photosArticle;
	}

	public static void setPhotosArticle(String photosArticle) {
		GestionArticlesBean.photosArticle = photosArticle;
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

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
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

	public Category getCategorieSelectione() {
		return categorieSelectione;
	}

	public void setCategorieSelectione(Category categorieSelectione) {
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

	public Integer getNombrePanier() {
		return nombrePanier;
	}

	public void setNombrePanier(Integer nombrePanier) {
		this.nombrePanier = nombrePanier;
	}

	public User getUserActuel() {
		return userActuel;
	}

	public void setUserActuel(User userActuel) {
		this.userActuel = userActuel;
	}

	public static ItemCart getItemCartActuel() {
		return itemCartActuel;
	}

	public static void setItemCartActuel(ItemCart itemCartActuel) {
		GestionArticlesBean.itemCartActuel = itemCartActuel;
	}

	public List<ItemCart> getPanierModifie() {
		return panierModifie;
	}

	public void setPanierModifie(List<ItemCart> panierModifie) {
		this.panierModifie = panierModifie;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Float getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(Float prixTotal) {
		this.prixTotal = prixTotal;
	}

	public ItemCart getItemCartSelected() {
		return itemCartSelected;
	}

	public void setItemCartSelected(ItemCart itemCartSelected) {
		this.itemCartSelected = itemCartSelected;
	}

	public Float getPrixTotalGeneral() {
		return prixTotalGeneral;
	}

	public void setPrixTotalGeneral(Float prixTotalGeneral) {
		this.prixTotalGeneral = prixTotalGeneral;
	}

	public List<String> getListItemQuantite() {
		listItemQuantite = new ArrayList<String>();

		for (Integer i = 1; i <= 10; i++) {

			listItemQuantite.add(i.toString());
		}
		return listItemQuantite;
	}

	public void setListItemQuantite(List<String> listItemQuantite) {
		this.listItemQuantite = listItemQuantite;
	}

	public Integer getQuantiteMax() {
		return quantiteMax;
	}

	public void setQuantiteMax(Integer quantiteMax) {
		this.quantiteMax = quantiteMax;
	}

	public String getItemQiantite() {
		return itemQiantite;
	}

	public void setItemQiantite(String itemQiantite) {
		this.itemQiantite = itemQiantite;
	}

	public List<ItemCart> getPanierSelected() {
		return panierSelected;
	}

	public void setPanierSelected(List<ItemCart> panierSelected) {
		this.panierSelected = panierSelected;
	}

	public Float getLivraison() {
		return livraison;
	}

	public void setLivraison(Float livraison) {
		this.livraison = livraison;
	}
}
