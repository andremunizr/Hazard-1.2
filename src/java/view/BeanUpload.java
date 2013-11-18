package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import model.User;
import org.primefaces.model.UploadedFile;

@Named(value = "beanUpload")
@RequestScoped
public class BeanUpload {

    @Inject
    private LoggBean bean;
    private UploadedFile file;

    public BeanUpload() {
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String path() {

        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return ctx.getRealPath("/");
    }

    private void transferFile(String fileName, InputStream in, String idUser) {

        String path = path() + "resources\\avImg\\";

        try {
            OutputStream out = new FileOutputStream(new File(path + idUser + "-" + fileName));
            int reader = 0;
            byte[] bytes = new byte[(int) getFile().getSize()];

            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void upload() throws UnknownHostException {

        System.out.println("Fazendo Upload...");
        
        String extValidate;

        if (getFile() != null) {
            String ext = getFile().getFileName();

            if (ext != null) {
                extValidate = ext.substring(ext.indexOf(".") + 1);
            } else {
                extValidate = "null";
            }

            if (extValidate.equals("jpg") || extValidate.equals("png")) {
                try {
                    transferFile(getFile().getFileName(), getFile().getInputstream(), bean.getSessionUser().getId());
                } catch (IOException e) {
                    Logger.getLogger(BeanUpload.class.getName()).log(Level.SEVERE, null, e);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Erro", "Um problema ocorreu ao enviar o arquivo!"));
                }
                
                refreshUserPic( getFile().getFileName() );

                FacesContext context = FacesContext.getCurrentInstance();
                //context.addMessage(null, new FacesMessage("Sucesso", "O envio do arquivo foi bem sucedido!"));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Erro", "Extensão de arquivo não permitida!"));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro", "Nenhum arquivo foi selecionado!"));
        }
    }

    private void refreshUserPic(String fileName) throws UnknownHostException {

        System.out.println("Foto: " + fileName);
        
        String idUser = bean.getSessionUser().getId();
        String realFileName = idUser + "-" + fileName;
        bean.getSessionUser().setImage( realFileName );
        User usuario = bean.save( bean.getSessionUser() );
        bean.setSessionUser( usuario );
    }
}
