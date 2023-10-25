package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;

@ManagedBean(name = "loginbean")
@SessionScoped
public class LoginBean implements Serializable {


	private static final long serialVersionUID = 2L;
	private final IUserMetier userMetier = new UserMetier();
	private String email;
	private String password;
	private String messageSuccess;
	private String messageError;
	static User connectedUser = new User();;
	private UserBean userBean = new UserBean();
	
	public LoginBean() {
		messageSuccess = "";
		messageError = "";
	}

	public String seLoguer() throws Exception {

		connectedUser =userMetier.seLoguer(this.email, this.password);

		initializeUser();
		if (connectedUser == null) {
			messageSuccess = "";
			messageError = "Email et/ou mot de passe incorrect(s) ! Veuillez réessayer.";
			return "";
		}

		if (connectedUser.getProfile().equals(ProfileUserEnum.CLIENT.getValue())) {

			return "gestion_achats.xhtml";
		}
		if (connectedUser.getProfile().equals(ProfileUserEnum.ADMIN.getValue())) {
			return "gestion_admin.xhtml";
		}
		if (connectedUser.getProfile().equals(ProfileUserEnum.MAGASINIER.getValue())) {
			return "gestion_articles.xhtml";
		}
		if (connectedUser.getProfile().equals(ProfileUserEnum.WEBSERVICE.getValue())) {
			return "gestion_achats.xhtml";
		}
		messageError = "Votre profil est inconnu ! Redirection impossible.\nCode erreur = 987.";
				
		return "";

	}
	
	public String seDeconnecter() throws Exception {

		try {
			 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			 HttpSession session = (HttpSession) externalContext.getSession(false);
			 if (session != null) {
		            session.invalidate();
		        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/signIn.xhtml?faces-redirect=true";

	}
	

	public String enregistrer() throws Exception {
		userBean.initializeUser();
		return "register.xhtml";

	}

	private void initializeUser() {
		this.email = "";
		this.password = "";
		this.messageError = "";
		this.messageSuccess = "";
	}

	public String retour() {

		return "home.xhtml";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessageSuccess() {
		return messageSuccess;
	}

	public void setMessageSuccess(String messageSuccess) {
		this.messageSuccess = messageSuccess;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public User getConnectedUser() {
		return connectedUser;
	}

	public static void setConnectedUser(User connectedUser) {
		LoginBean.connectedUser = connectedUser;
	}
}