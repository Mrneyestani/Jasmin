package fr.jasmin.main;

import java.util.List;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemDao;

public class CategoryMain {

	private static final ICategoryDao categoryDao = new CategoryDao();
	private static final IItemDao itemDao = new ItemDao();

	public static void main(String[] args) {

		try {
//			List<Category> categories = categoryDao.getCategories();
//			categories.forEach(c -> System.out.println("  -> " + c.getId() + " | " + c.getNomCategorie()));

			List<Item> items = itemDao.getItemsByCategory("ENFANT");
			items.forEach(i -> System.out.println("  -> " + i.getId() + " | " + i.getName()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
