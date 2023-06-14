package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import fr.jasmin.control.impl.ItemMetier;
import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;

@ManagedBean(name = "categoriebean")
@SessionScoped
public class CategorieBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final IItemMetier itemMetier = new ItemMetier();
	private final IUserMetier userMetier = new UserMetier();

	private String nomCategorie;
	private Integer remiseCategorie;
	private Boolean isRemiseCumulable;
	private String photoCategorie;
	private Boolean isActif;
	private String messageSuccess;
	private String messageError;
	private Category category = null;
	private String selectedCategory;
	private List<Item> items;
	private List<String> categories;
	private List<Category> categorys;
	private List<ItemCart> panier;
	private Boolean isDataTableRendred;
	private Integer quantite;
	private Boolean isChecked;

	private HashMap<Integer, Integer> mapQuantites = new HashMap<Integer, Integer>();

	public CategorieBean() {
		messageSuccess = "";
		messageError = "";
		isDataTableRendred = false;
		items = new ArrayList<Item>();
	}

	public String addCategorie() throws Exception {
		category = new Category();

		category.setNomCategorie(nomCategorie);
		category.setRemiseCategorie(remiseCategorie);
		category.setIsRemiseCumulable(isRemiseCumulable);
		category.setPhotoCategorie(photoCategorie);

		for (Item item : ArticleBean.getListItem()) {
			item.setCategory(category);
			category.getItems().add(item);
		}
		itemMetier.addCategory(category);

		messageSuccess = "Categorie créé avec succès.";
		messageError = "";

		ArticleBean.getListItem().clear();
		ArticleBean.initializeArticle();

		initializeCategorie();

		return "";

	}

	public List<String> getCategories() throws Exception {
		try {
			return itemMetier.getCategories();
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de la liste des categories !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	// la methode pour afficher les articles de magasinier
	public List<Category> getCategoryList() throws Exception {
		try {
			return itemMetier.getCategoryList();
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération de la liste des categories !\n" + e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	public String getListCategoryItems() throws Exception {
		categorys = itemMetier.getListCategoriesItems();
		ItemCart ic = new ItemCart();
		for (Category category : categorys) {
			System.out.println(category);
			category.getItems().forEach(a -> System.out.println("  -> " + a));

		}

		return "";
	}

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

	public String validerPanier() {

		ItemCart ic = new ItemCart();

		return "command.xhtml";
	}

	private void initializeCategorie() {
		this.nomCategorie = "";
		this.remiseCategorie = null;
		this.isRemiseCumulable = null;
		this.photoCategorie = "";

	}

	// on arrive pas à modifier la catégorie
	public String editCategorie(Category category) {

		try {
			updateCategorie(category);
			category.setNomCategorie(category.getNomCategorie());
			System.out.println("nomCategory" + category.getNomCategorie());
			category.setRemiseCategorie(remiseCategorie);
			category.setIsRemiseCumulable(isRemiseCumulable);
			category.setPhotoCategorie(photoCategorie);

			for (Item item : ArticleBean.getListItem()) {
				item.setCategory(category);
				category.getItems().add(item);
			}

			// editCategory;
			itemMetier.updateCategory(category);
			messageSuccess = "Categorie créé avec succès.";
			messageError = "";

		} catch (Exception expect) {
			messageError = "";
			expect.printStackTrace();
		}

		return "";

	}

// getCategorieById is ok 
// on arrive à remplir les champs de categorie 
	public Category updateCategorie(Category category) {

		try {

			category = itemMetier.getCategory(category.getId());
			this.setNomCategorie(category.getNomCategorie());

			category.setNomCategorie(category.getNomCategorie());
			this.setNomCategorie(category.getNomCategorie());

			category.setRemiseCategorie(category.getRemiseCategorie());
			this.setRemiseCategorie(category.getRemiseCategorie());

			category.setIsRemiseCumulable(category.getIsRemiseCumulable());
			this.setIsRemiseCumulable(category.getIsRemiseCumulable());

			category.setPhotoCategorie(category.getPhotoCategorie());
			this.setPhotoCategorie(category.getPhotoCategorie());

			for (Item item : ArticleBean.getListItem()) {
				item.setCategory(category);
				category.getItems().add(item);
			}

			messageSuccess = "Categorier modifié avec succès.";
			messageError = "";
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la modification de categorie !\n" + e.getMessage();
			e.printStackTrace();
		}
		return category;

	}

	public void deleteCategorie(Category categorie) {
		try {
			itemMetier.removeCategory(categorie);
			messageSuccess = "Categorier supprimé avec succès.";
			messageError = "";
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la suppression de categorie !\n" + e.getMessage();
			e.printStackTrace();
		}
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

	public Category getCategorie() {
		return category;
	}

	public void setCategorie(Category categorie) {
		this.category = categorie;
	}

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public List<Item> getItems() {
		return items;
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
