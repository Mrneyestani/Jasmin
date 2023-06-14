package fr.jasmin.main;

import java.util.List;
import fr.jasmin.entity.Category;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.vue.backingbean.CategorieBean;



public class MainGetCategory {

	public static void main(String[] args) {
		
		// test  category by id  ok
		// test List<category>  ok
		
		ICategoryDao categoryDao = new CategoryDao();
				
		try {
//		
//			System.out.println("=========get Category by id===========================");
//			Category category1 = categoryDao.getCategory(1);
//			System.out.println(category1);
//			category1.getItems().forEach(a -> System.out.println("  -> " + a));
			
			System.out.println("==========================================");
			
			System.out.println("========= get List Category ===========================");
//			
//			List<Category> categorys = categoryDao.getCategorieList();
//			for (Category category : categorys) {
//				System.out.println(category);
//				category.getItems().forEach(a -> System.out.println("  -> " + a));
//			}
			
			CategorieBean catego = new CategorieBean();
			catego.getListCategoryItems();
			System.out.println("==========================================");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
