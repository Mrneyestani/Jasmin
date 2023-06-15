package fr.jasmin.main;

import java.util.List;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemDao;

public class getItemsByCat {

	public static void main(String[] args) throws Exception {
		
		IItemDao itemDao = new ItemDao();
		ICategoryDao categoryDao = new CategoryDao();
		Category category = null;
		
		category = categoryDao.getCategory(2);
		
		List<Item> itemList =itemDao.getItemsByCategory("FEMME");
		
		System.out.println("Category:" +category);
		System.out.println("la liste" +itemList);

	}

}
