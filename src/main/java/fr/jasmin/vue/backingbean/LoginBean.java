package fr.jasmin.vue.backingbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;
import fr.jasmin.utils.Utils;

@ManagedBean(name = "loginbean")
@SessionScoped
public class LoginBean implements Serializable {


	private static final long serialVersionUID = 1L;

	private final IUserMetier userMetier = new UserMetier();

	private String email;

	private String password;

	private String messageSuccess;
	private String messageError;

	static User connectedUser = new User();;
//	private User user;
	private UserBean userBean = new UserBean();
//	static Integer nombrePanierInLoginBean;
	private GestionArticlesBean gestionArticlesBean = new GestionArticlesBean();;
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

		// en fonction de son profil on le redirige vers la bonne page html
		if (connectedUser.getProfile().equals(ProfileUserEnum.CLIENT.getValue())) {

//			gestionArticlesBean.setPanier(connectedUser.getPanier());
//			Utils.trace("Panier in Login: %s\n", connectedUser.getFirstName());
//			gestionArticlesBean.getPanierByUserId();
//			gestionArticlesBean.setNombrePanier(connectedUser.getPanier().size());
//			Utils.trace("NombrePanier in Login : %s\n", gestionArticlesBean.getNombrePanier());
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
//			UserBean userBean = new UserBean();
			 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			 HttpSession session = (HttpSession) externalContext.getSession(false);
			 if (session != null) {
		            // Invalider la session
		            session.invalidate();
		        }
//			 setConnectedUser(null);
//			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//			this.setNombrePanier(0);
//			this.setPanier(null);
//			userBean.setConnectedUser(getUserActuel());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/signIn.xhtml?faces-redirect=true";

	}
	
//	public User getConnectedUserInLoginBean() {
//		
//		return this.getConnectedUser();
//	}

//	public String seDeconnecter() throws Exception {
//		
//		try {
//		this.email = "";
//		this.password = "";
//		this.messageError = "";
//		this.messageSuccess = "";
//		setConnectedUser(null);
//		setNombrePanierInLoginBean(0);
//		gestionArticlesBean.setPanier(null);
//		gestionArticlesBean.setNombrePanier(0);
//		Utils.trace("ConnectedUser : %s\n", getConnectedUser());
//		Utils.trace("NombrePanier : %s\n", getnombrePanierInLoginBean());
////		Utils.trace("NombrePanier : %s\n", gestionArticlesBean.getNombrePanier());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		
		
//		return "signIn.xhtml";
//
//	}

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

//	public Integer getnombrePanierInLoginBean() {
//		return nombrePanierInLoginBean;
//	}
//
//	public static void setNombrePanierInLoginBean(Integer nombrePanier) {
//		LoginBean.nombrePanierInLoginBean = nombrePanier;
//	}







}