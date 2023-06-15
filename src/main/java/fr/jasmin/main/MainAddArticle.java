package fr.jasmin.main;


import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemDao;
import fr.jasmin.utils.Utils;


public class MainAddArticle {

	public static void main(String[] args) {
		//test upade article ok
		IItemDao itemDao = new ItemDao();
		ICategoryDao categoryDao = new CategoryDao();
		try {
				
			Item articleActuel = new Item();
			Category category = new Category();
			
			category = categoryDao.getCategory(17);
			
			articleActuel.setName("pantalonJean");
			articleActuel.setDescription("Blue");
			articleActuel.setPhotos("photos");
			articleActuel.setVideos("videosA");
			articleActuel.setRemise(5);
			articleActuel.setPrix((float) 20.00);
			articleActuel.setStock(5);
			articleActuel.setIsVendable(true);
			articleActuel.setCategory(category);
			
			itemDao.addArticle(articleActuel);
			
			Utils.trace("l'Article créé  : %s\n", articleActuel);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
