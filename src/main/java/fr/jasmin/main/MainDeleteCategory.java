package fr.jasmin.main;
import fr.jasmin.entity.Category;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;


public class MainDeleteCategory {

	public static void main(String[] args) {
		//test delete Category ok

		ICategoryDao CategoryDao = new CategoryDao();

		try {
			
			Category category = CategoryDao.getCategory(3);
			System.out.println(category);
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println(category.getId());
			CategoryDao.removeCategory(category.getId());
			System.out.println(category);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
