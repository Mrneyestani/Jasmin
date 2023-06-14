package fr.jasmin.main;

import fr.jasmin.model.dao.interfaces.ICategoryDao;

import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.model.dao.impl.CategoryDao;

public class AddCategory {

	public static void main(String[] args) {
		try {
			System.out.println("***********************début de test**********************");

			Item item1 = new Item("PC2", "dual coure2");
			item1.setRemise(2);
			Item item2 = new Item("IMPRIMANTE2", "LASER JET2");
			item2.setRemise(2);

			Category category = new Category("outil informatique2", 4, true, null);
			item1.setCategory(category);
			item2.setCategory(category);
			category.getItems().add(item1);
			category.getItems().add(item2);

			ICategoryDao categoryDao = new CategoryDao();

			categoryDao.addCategory(category);
			System.out.println(category);
			category.getItems().forEach(i -> System.out.println("  -> " + i));

			System.out.println("***********************FIN de test**********************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
