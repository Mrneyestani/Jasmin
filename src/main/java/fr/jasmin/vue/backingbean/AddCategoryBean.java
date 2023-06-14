package fr.jasmin.vue.backingbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.jasmin.common.IConstant;
import fr.jasmin.entity.Category;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.utils.DataTest;
import fr.jasmin.utils.Utils;

@ManagedBean
@SessionScoped
public class AddCategoryBean extends MasterBean implements IConstant {

	Category categorie;
	
	private boolean trueValue ;
	private boolean falseValue ;

	public AddCategoryBean() {
		
		this.setCategorie(new Category() );
		this.setCategorie(DataTest.genCategory());
		this.setFalseValue(false);
		this.setTrueValue(true);
	}

// %%%%%%%%%%%%%%%%%%%%%%%%%% action %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	public String addCategorie() {
		String landingWebPage = STOREKEEPER_HOME;
		ICategoryDao categorieDao = new CategoryDao();

		try {
			categorieDao.addCategory(this.getCategorie());
		} catch (Exception e) {
			Utils.trace("catch addcategorie %s\n", e.toString());
			return HOME;
		}
		return landingWebPage;
	}

// %%%%%%%%%%%%%%%%%%%%%%%%%% action %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	public String cheatCode() {

		this.setCategorie(DataTest.genCategory());

		if (this.getCategorie() != null)
			Utils.trace("cheatCode user %s\n", this.getCategorie());
		else
			Utils.trace("cheatCode Categorie null \n");
		return null;
	}
//--------------------------------------------------------------------------------
	public Category getCategorie() {
		return categorie;
	}

	public void setCategorie(Category categorie) {
		this.categorie = categorie;
	}

	public boolean isTrueValue() {
		return trueValue;
	}

	public void setTrueValue(boolean trueValue) {
		this.trueValue = trueValue;
	}

	public boolean isFalseValue() {
		return falseValue;
	}

	public void setFalseValue(boolean falseValue) {
		this.falseValue = falseValue;
	}

}
