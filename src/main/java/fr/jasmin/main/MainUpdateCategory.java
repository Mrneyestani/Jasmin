package fr.jasmin.main;


import fr.jasmin.entity.Category;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;


public class MainUpdateCategory {

	public static void main(String[] args) {
		//test upader category ok
		ICategoryDao CategoryDao = new CategoryDao();
		
		try {
					
			Category category = CategoryDao.getCategory(2);
			System.out.println(category);
			category.setNomCategorie(category.getNomCategorie() + "-MAJ");
			category.setPhotoCategorie(category.getPhotoCategorie() + "-MAJ");
			category.setRemiseCategorie(7);
			CategoryDao.updateCategory(category);
			
			System.out.println(category);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
