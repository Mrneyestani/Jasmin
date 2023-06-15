package fr.jasmin.control.interfaces;

import java.util.List;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;

public interface IItemMetier {

	Category addCategory(Category category) throws Exception;

	Category getCategory(Integer id) throws Exception;

	Category getCategoryByNom(String nom) throws Exception;
	
	List<String> getCategories() throws Exception;

	void updateCategory(Category category) throws Exception;

	void removeCategory(Integer id) throws Exception;

	void removeCategory(Category category) throws Exception;

	List<Item> getItemsByCategory(String categoryName) throws Exception;

	List<Category> getListCategoriesItems() throws Exception;

	List<Category> getCategoryList() throws Exception;

	void updateArticle(Item article) throws Exception;

	void removeArticle(Integer id) throws Exception;

	void removeArticle(Item article) throws Exception;

	Item getArticle(Integer id) throws Exception;

	void updateArticleById(Integer id) throws Exception;

	Item addArticle(Item article) throws Exception;


}
