package fr.jasmin.vue.backingbean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import fr.jasmin.utils.Utils;

@ManagedBean(name = "fileUploadMBean")
@ViewScoped
public class FileUploadMBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Part file1;
	private Part file2;
	private String message;
	private String uploadLocation = "D:/IT/Java/Projects_Java/ProjectsDoranco/TpRyadh/JasminV6/WEB-CONTENT/resources/images";
	private static String urlPhoto = "";
	private static String nomPhoto = "";

	public Part getFile1() {
		return file1;
	}

	public void setFile1(Part file1) {
		this.file1 = file1;
	}

	public Part getFile2() {
		return file2;
	}

	public void setFile2(Part file2) {
		this.file2 = file2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUploadLocation() {
		return uploadLocation;
	}

	public void setUploadLocation(String uploadLocation) {
		this.uploadLocation = uploadLocation;
	}


	public static String getUrlPhoto() {
		return urlPhoto;
	}

	public static void setUrlPhoto(String urlPhoto) {
		FileUploadMBean.urlPhoto = urlPhoto;
	}
	
	
	public static String getNomPhoto() {
		return nomPhoto;
	}

	public static void setNomPhoto(String nomPhoto) {
		FileUploadMBean.nomPhoto = nomPhoto;
	}

		// ========================================================

	// ========================================================
	public String uploadFile() throws IOException {
		boolean file1Success = false;
		GestionArticlesBean gestionArticlesBean = new GestionArticlesBean();
			if (file1 != null && file1.getSize() > 0) {
			nomPhoto = Utils.getFileNameFromPart(file1);
			urlPhoto = this.uploadLocation + "/" + nomPhoto;
			/**
			 * destination where the file will be uploaded
			 */

			Utils.trace("Url photo: " + getUrlPhoto());
			File savedFile = new File(this.uploadLocation, nomPhoto);
			Utils.trace("savedFile.toPath(): " + savedFile.toPath());
			GestionArticlesBean.setPhotosArticle(nomPhoto);
			Utils.trace("savedFile.toPath(): " + GestionArticlesBean.getPhotosArticle());
			try (InputStream input = file1.getInputStream()) {
				Files.copy(input, savedFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

			file1Success = true;
		}

		boolean file2Success = false;

		if (file2 != null && file2.getSize() > 0) {
			String nomPhoto = Utils.getFileNameFromPart(file2);
			File savedFile = new File(
					"D:/IT/Java/Projects_Java/ProjectsDoranco/TpRyadh/JasminV6/WEB-CONTENT/resources/images", nomPhoto);

			try (InputStream input = file2.getInputStream()) {
				Files.copy(input, savedFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

			file2Success = true;
		}

		if (file1Success || file2Success) {
			// System.out.println("File uploaded to : " + path);
			/**
			 * set the success message when the file upload is successful
			 */
			setMessage("File successfully uploaded");
		} else {
			/**
			 * set the error message when error occurs during the file upload
			 */
			setMessage("Error, select atleast one file!");
		}

		/**
		 * return to the same view
		 */
		return null;
	}

}