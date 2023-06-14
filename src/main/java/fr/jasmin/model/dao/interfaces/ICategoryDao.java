package fr.jasmin.model.dao.interfaces;

import java.util.List;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.User;

public interface ICategoryDao {

	Category addCategory(Category category) throws Exception;

	Category getCategory(Integer id) throws Exception;

	Category getCategoryByNom(String nomCategory) throws Exception;
	
	List<String> getCategories() throws Exception;

	List<Category> getCategorieList() throws Exception;

	void updateCategory(Category category) throws Exception;

	void removeCategory(Integer id) throws Exception;

	void removeCategory(Category category) throws Exception;

	List<Category> getCategoriess() throws Exception;

}
