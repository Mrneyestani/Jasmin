package fr.jasmin.control.impl;

import java.util.List;

import fr.jasmin.control.interfaces.IItemMetier;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemDao;

public class ItemMetier implements IItemMetier {

	private final ICategoryDao categoryDao = new CategoryDao();
	private final IItemDao itemDao = new ItemDao();
	Category category = null;

	@Override
	public Category addCategory(Category category) throws Exception {
		if (category == null) {
			throw new NullPointerException("Le category à créer ne doit pas être null !");
		}
		categoryDao.addCategory(category);

		return category;
	}

	@Override
	public Category getCategory(Integer id) throws Exception {
		return categoryDao.getCategory(id);
	}

	@Override
	public List<String> getCategories() throws Exception {
		return categoryDao.getCategories();
	}

	// La list pour afficher des articles de magasinier
	@Override
	public List<Category> getCategoryList() throws Exception {
		return categoryDao.getCategorieList();
	}

	@Override
	public List<Category> getListCategoriesItems() throws Exception {
		return categoryDao.getCategorieList();
	}

	@Override
	public void updateCategory(Category category) throws Exception {
		categoryDao.updateCategory(category);

	}

	@Override
	public void removeCategory(Integer id) throws Exception {
		categoryDao.getCategory(id);

	}

	@Override
	public void removeCategory(Category category) throws Exception {
		categoryDao.removeCategory(category);

	}

	@Override
	public List<Item> getItemsByCategory(String categoryName) throws Exception {

		return itemDao.getItemsByCategory(categoryName);

	}

	@Override
	public void updateArticle(Item article) throws Exception {
		itemDao.updateArticle(article);

	}

	@Override
	public void removeArticle(Integer id) throws Exception {
		itemDao.removeArticle(id);

	}

	@Override
	public void removeArticle(Item article) throws Exception {
		itemDao.removeArticle(article);

	}

	@Override
	public Item getArticle(Integer id) throws Exception {
		return itemDao.getArticle(id);

	}
}
