package fr.jasmin.main;


import fr.jasmin.entity.Item;
import fr.jasmin.model.dao.impl.ItemDao;
import fr.jasmin.model.dao.interfaces.IItemDao;
import fr.jasmin.utils.Utils;


public class MainUpdateArticle {

	public static void main(String[] args) {
		//test upader category ok
		IItemDao itemDao = new ItemDao();
		
		try {
					
			Item item = itemDao.getArticle(19);
			Utils.trace("l'Article avant mis à jour  : %s\n", item);
			item.setName(item.getName() + "-MAJ");
			item.setRemise(5);
			item.setIsVendable(false);
			
			itemDao.updateArticle(item);
			
			Utils.trace("l'Article après mis à jour  : %s\n", item);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
