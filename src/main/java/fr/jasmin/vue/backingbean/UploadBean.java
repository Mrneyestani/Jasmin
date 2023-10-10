package fr.jasmin.vue.backingbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@SessionScoped
@MultipartConfig
@ManagedBean(name = "uploadBean")
public class UploadBean
{
    @Inject
    @Push
    private PushContext uploadProgress;

    private static final Logger logger = Logger.getLogger(UploadBean.class.getName());
    private Part file;

    public Part getFile()
    {
        return file;
    }

    public void setFile(Part file)
    {
        this.file = file;
    }

    public void upload()
    {

        if (file != null)
        {

            logger.info("File Details:");
            logger.log(Level.INFO, "File name:{0}", file.getName());
            logger.log(Level.INFO, "Content type:{0}", file.getContentType());
            logger.log(Level.INFO, "Submitted file name:{0}", file.getSubmittedFileName());
            logger.log(Level.INFO, "File size:{0}", file.getSize());

            try (InputStream inputStream = file.getInputStream(); FileOutputStream outputStream = new FileOutputStream("C:" + File.separator + "jsf_files_test_for_delete" + File.separator + file.getSubmittedFileName()))
            {

                long lastTimestamp = System.currentTimeMillis();
                int pushInterval = 1000;
                long totalRead = 0;
                long size = file.getSize();

                int bytesRead = 0;
                final byte[] chunck = new byte[1024];
                while ((bytesRead = inputStream.read(chunck)) != -1)
                {
                    outputStream.write(chunck, 0, bytesRead);
                    totalRead += bytesRead;

                    if (System.currentTimeMillis() > lastTimestamp + pushInterval)
                    {
                        lastTimestamp = System.currentTimeMillis();
                        uploadProgress.send(100.0 * totalRead / size); // Make sure this isn't sent more often than once per second.
                    }
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload successfully ended!"));
            }
            catch (IOException e)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Upload failed!"));
            }
        }
    }
}