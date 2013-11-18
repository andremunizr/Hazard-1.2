package util;

public enum SourceType{
    
    TAREFA("tarefa"), INSIGNIA("insignia");
    
    private String tipo;
    
    SourceType(String tipo){
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
}