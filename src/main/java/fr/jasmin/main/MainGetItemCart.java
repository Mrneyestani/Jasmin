package fr.jasmin.main;

import java.util.List;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemCartDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemCartDao;



public class MainGetItemCart {

	public static void main(String[] args) {
		
		// test  itemCart by id  ok
		// test List<ItemCart>  ok
		
		IItemCartDao itemCartDao = new ItemCartDao();
				
		try {
		
			System.out.println("=========get itemCart by id===========================");
			ItemCart itemCart = itemCartDao.getItemCart(2);
			System.out.println(itemCart);
			itemCart.getItem();
			System.out.println("  -> " + itemCart.getItem());
			itemCart.getUser();
			System.out.println("  -> " + itemCart.getUser());
			
			
			System.out.println("==========================================");
			
			System.out.println("========= get List itemCart ===========================");
			
			List<ItemCart> itemCartList = itemCartDao.getItemCartList();
			for (ItemCart itemCart1 : itemCartList) {
				System.out.println(itemCart1);
				itemCart.getItem();
				System.out.println("  -> " + itemCart.getItem());
				itemCart.getUser();
				System.out.println("  -> " + itemCart.getUser());	
			}
			
			System.out.println("==========================================");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
