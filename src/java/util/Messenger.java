
package util;

public enum Messenger {
    
    NOVA_TAREFA("Você recebeu uma nova tarefa","detalhar-tarefa.xhtml"),
    NOVA_INSIGNIA("Você recebeu uma nova insígnia","profile.xhtml"),
    NOVO_PROJETO("Você criou um novo projeto","meus-projetos.xhtml");
    
    private String msg;
    private String link;
    
    Messenger(String msg, String link){
        this.msg = msg;
        this.link = link;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
}