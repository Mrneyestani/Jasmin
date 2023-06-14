package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import fr.jasmin.common.IConstant;
import fr.jasmin.control.impl.ItemMetier;
import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.utils.Utils;

@ManagedBean(name = "categoriebean")
@SessionScoped
public class CategorieBean extends MasterBean implements IConstant, Serializable {

	private static final long serialVersionUID = 1L;

	private final IItemMetier itemMetier = new ItemMetier();
	private final IUserMetier userMetier = new UserMetier();
	private final ICategoryDao categoryDao = new CategoryDao();
	private final ArticleBean articleBean = new ArticleBean();
	

	private String nomCategorie;
	private Integer id;
	private Integer remiseCategorie;
	private Boolean isRemiseCumulable;
	private String photoCategorie;
	private Boolean isActif;
	private String messageSuccess;
	private String messageError;
	private Category category;
	private String selectedCategory;
	private List<Item> items;
	private List<String> categories;
	private List<Category> categorys;
	private List<ItemCart> panier;
	private Boolean isDataTableRendred;
	private Integer quantite;
	private Boolean isChecked;
	private List<Category> categorieList;
	private int  categorieId ;
	protected Category currentCategorie;
	protected Item currentItem;

	private HashMap<Integer, Integer> mapQuantites = new HashMap<Integer, Integer>();

	public CategorieBean() {
		messageSuccess = "";
		messageError = "";
		isDataTableRendred = false;
		items = new ArrayList<Item>();
		
		if (this.getCategorieList() == null) {
			this.setCategorieList(new ArrayList<Category>());
		}
		
		try {
			categorieList = categoryDao.getCategoriess();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (this.getCategorieList().size()>0 ) {				// prendre l'id (par défaut) du premier de la liste 
			this.setCategorieId(this.getCategorieList()
								.get(0)
								.getId());
			this.currentCategorie = categorieList.get(0);
			
		}else {
			this.setCategorieId(1);
			this.currentCategorie = new Category(); // liste vide
		}	
//		this.setCategorieId(5);								// liste vide
//		this.setCurrentCategorie( this.getCategorieList()
//									.get(this.getCategorieId()));
		
	}
	
	
	//------------------------------------action---------------------------------------------
	public String categorieChange(ValueChangeEvent e) {
		String pageReturn = null ;
		Utils.trace("categorieChange  : %s\n", this.getCategorieId());
		Utils.trace("categorieChange  : %s\n", e.getNewValue().toString());
		this.setPromptStatus(String.format("Id cat : %d", e.getNewValue()));
		this.setCategorieId((int)e.getNewValue());
		Utils.trace("new categorieId  : %s\n", this.getCategorieId());
		
		
		try {
			this.currentCategorie = itemMetier.getCategory(this.getCategorieId());
			this.currentCategorie.getItems();
		} catch (Exception exception) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de categorie !\n" + exception.getMessage();
			exception.printStackTrace();
			
		}
		return pageReturn;
	}
	
	//------------------------------------action---------------------------------------------
//	public Category get
//
//	try {
//		this.setCurrentCategorie(itemMetier.getCategory(this.getCategorieId()));
//	} catch (Exception exception) {
//		exception.printStackTrace();
//	}
	//------------------------------------action---------------------------------------------
	
	public Category addCategorie() throws Exception {
		

		currentCategorie = new Category();
		
		try {
		currentCategorie.setNomCategorie(this.nomCategorie);
		System.out.println("nom catgo " +currentCategorie.getNomCategorie());
		currentCategorie.setRemiseCategorie(this.remiseCategorie);
		currentCategorie.setIsRemiseCumulable(this.isRemiseCumulable);
		currentCategorie.setPhotoCategorie(this.photoCategorie);

		for (Item item : ArticleBean.getListItem()) {
			item.setCategory(currentCategorie);
			currentCategorie.getItems().add(item);
		}
		
		
			itemMetier.addCategory(currentCategorie);
			categorieList = categoryDao.getCategoriess();
			categorieId = currentCategorie.getId();
			messageSuccess = "Categorie créé avec succès.";
			messageError = "";
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Error lors d'ajouger de categiry!\n Vérifiez si le nom de category exist déjà !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}
		

		articleBean.getItemList().clear();
		articleBean.initialiseArticle();
		initialiseCategorie();

		return category;
	}

	//------------------------------------action---------------------------------------------
	
	public Category getCategoryByNom() {
		try {
			category = itemMetier.getCategoryByNom(getNomCategorie());
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de categorie !\n" + e.getMessage();
			e.printStackTrace();
			
		}
		
		return category;
	}
	
	//------------------------------------action---------------------------------------------	
	
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

	
	//------------------------------------action---------------------------------------------

	public List<Category> getCategoryList(){
		try {
			return itemMetier.getCategoryList();
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de la liste des categories !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	//------------------------------------action---------------------------------------------
	
	public String getListCategoryItems() throws Exception {
		categorys = itemMetier.getListCategoriesItems();
		ItemCart ic = new ItemCart();
		for (Category category : categorys) {
			System.out.println(category);
			category.getItems().forEach(a -> System.out.println("  -> " + a));

		}

		return "";
	}

	
	//------------------------------------action---------------------------------------------
	public String setItemsByCategory(ValueChangeEvent e) {

		System.out.println("Ancienne valeur : " + selectedCategory);
		System.out.println("Nouvelle valeur : " + e.getNewValue());
		isChecked = false;
		quantite = 0;
		try {
			items = itemMetier.getItemsByCategory(e.getNewValue().toString());
			if (items != null && !items.isEmpty()) {
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

	
	//------------------------------------action---------------------------------------------
	
	public String addArticlesToPanier() {
		submit();
		getItems();
		LoginBean loginBean = new LoginBean();
		try {

			ItemCart itemCart = new ItemCart();
			itemCart.setUser(loginBean.getConnectedUser());
			for (Item item : items) {
				if (item.getIsChecked() == true) {

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
	//------------------------------------action---------------------------------------------
	
	public String validerPanier() {

		ItemCart ic = new ItemCart();

		return "command.xhtml";
	}

	public void initialiseCategorie() {
		this.nomCategorie = "";
		this.remiseCategorie = null;
		this.isRemiseCumulable = null;
		this.photoCategorie = "";

	}

	
	//------------------------------------action---------------------------------------------

		public Category updateCategorie() {
			
				Category categoryUpdate = new Category();
				
			try {
				categoryUpdate = this.getCurrentCategorie();
				System.out.println(categoryUpdate);
				this.setNomCategorie(categoryUpdate.getNomCategorie());
				this.setRemiseCategorie(categoryUpdate.getRemiseCategorie());
				this.setIsRemiseCumulable(categoryUpdate.getIsRemiseCumulable());
				this.setPhotoCategorie(categoryUpdate.getPhotoCategorie());
				this.setId(categoryUpdate.getId());

//				for (Item item : ArticleBean.getListItem()) {
//					item.setCategory(categoryUpdate);
//					categoryUpdate.getItems().add(item);
//				}

				messageSuccess = "Categorie séléctioné avec succès.";
				messageError = "";
				
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors de la modification de categorie !\n" + e.getMessage();
				e.printStackTrace();
			}
			return categoryUpdate;
		}

		//------------------------------------action---------------------------------------------
		
	public Category editCategorie() {
		
		
		Category categoryModified = new Category();
		
		try {
			
			
			categoryModified = this.getCurrentCategorie();
			categoryModified.setNomCategorie(this.nomCategorie);
			System.out.println("nomCategory : " + nomCategorie);
			categoryModified.setRemiseCategorie(this.remiseCategorie);
			System.out.println("remiseCategorie : " + remiseCategorie);
			categoryModified.setIsRemiseCumulable(this.isRemiseCumulable);
			System.out.println("isRemiseCumulable: " + isRemiseCumulable);
			categoryModified.setPhotoCategorie(this.photoCategorie);
			System.out.println("photoCategorie : " + photoCategorie);
			
			for (Item item : ArticleBean.getListItem()) {
				item.setCategory(categoryModified);
				categoryModified.getItems().add(item);
			}
			
			// editCategory;
			itemMetier.updateCategory(categoryModified);
			categorieList = categoryDao.getCategoriess();
			
			messageSuccess = "Categorie mis à jour avec succès.";
			messageError = "";
			
			articleBean.getItemList().clear();
			articleBean.initialiseArticle();

			initialiseCategorie();
			
		} catch (Exception expect) {
			messageError = "";
			expect.printStackTrace();
		}
		
		return categoryModified;
		
	}

	//------------------------------------action---------------------------------------------
	
	public void deleteCategorie() {
		
		try {
			itemMetier.removeCategory(this.getCurrentCategorie());
			categorieList = categoryDao.getCategoriess();
			
			messageSuccess = "Categorier supprimé avec succès.";
			messageError = "";
						
			
			if (categorieList.size()>0 ) {	
				this.currentCategorie = categorieList.get(0);
				
			}else {
				
				this.currentCategorie = new Category();
			}	

		
//			for (Item item : ArticleBean.getListItem()) {
//				item.setCategory(currentCategorie);
//				currentCategorie.getItems().add(null);
//			}
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de categorie !\n" + e.getMessage();
			e.printStackTrace();
		}
	}

	//------------------------------------action---------------------------------------------
	
//	public Item updateArticle() {
//		
//		   Item itemUpdate = new Item();
//
//		   Utils.trace("CurrentCategorie  : %s\n", getCurrentCategorie());
//		   Utils.trace("CurrentCategorie  : %s\n", getCurrentItem());
//		   this.setItems(getCurrentCategorie().getItems());
//		for (Item item : this.getItems()) {
//			
//			if (item.getId() == this.getItemId()) {
//				this.setCurrentItem(item);
//			}
//		}
//		Utils.trace("itemId  : %s\n", this.getItemId());

		
//		try {
//			
////			this.setCurrentItem(itemMetier.getArticle(this.getItemId()));
//			this.setId(this.getCurrentItem().getId());
//			this.setNom(this.getCurrentItem().getName());
//			this.setDescription(this.getCurrentItem().getDescription());
//			this.setPhotos(this.getCurrentItem().getPhotos());
//			this.setVideos(this.getCurrentItem().getVideos());
//			this.setRemise(this.getCurrentItem().getRemise());
//			this.setPrix(this.getCurrentItem().getPrix());
//			this.setStock(this.getCurrentItem().getStock());
//			this.setIsVendable(this.getCurrentItem().getIsVendable());
//
////			itemList.add(item);
//			
//			messageSuccess = "Article selectioné avec succès.";
//			messageError = "";
//		} catch (Exception e) {
//			messageSuccess = "";
//			messageError = "Erreur lors de la modification d'article !\n" + e.getMessage();
//			e.printStackTrace();
//		}
//		Utils.trace("currentItem  : %s\n", this.getCurrentItem());
//		
//		return this.currentItem;
//
//	}
	
	//------------------------------------action---------------------------------------------
	
	
	
	//------------------------------------geter/seter---------------------------------------------
	
	
	
	
	public List<Category> getCategorieList() {
		return categorieList;
	}


	public Item getCurrentItem() {
		return currentItem;
	}


	public void setCurrentItem(Item currentItem) {
		this.currentItem = currentItem;
	}


	public void setCategorieList(List<Category> categorieList) {
		this.categorieList = categorieList;
	}


	public int getCategorieId() {
		return categorieId;
	}


	public void setCategorieId(int categorieId) {
		this.categorieId = categorieId;
	}



	public Category getCurrentCategorie() {
		return currentCategorie;
	}


	public void setCurrentCategorie(Category currentCategorie) {
		this.currentCategorie = currentCategorie;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public List<Category> getCategorys() {
		return categorys;
	}

	public Boolean getIsDataTableRendred() {
		return isDataTableRendred;
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

//	public Category getCategorie() {
//		return category;
//	}
//
//	public void setCategorie(Category categorie) {
//		this.category = categorie;
//	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<Item> getItems() {
		return items;
	}

	
	public void setItems(List<Item> items) {
		this.items = items;
	}


	public Boolean isDataTableRendred() {
		return isDataTableRendred;
	}

	public void setIsDataTableRendred(Boolean isDataTableRendred) {
		this.isDataTableRendred = isDataTableRendred;
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

	public List<ItemCart> getPanier() {
		return panier;
	}

	public HashMap<Integer, Integer> getMapQuantites() {
		return mapQuantites;
	}

}
