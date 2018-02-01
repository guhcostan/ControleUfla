package controleufla.ufla.controleufla;

/**
 * Created by Chrnoz on 05/12/2017.
 */

public class Computador {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private boolean ocupado;

    public Computador(int id, boolean ocupado, String ultimoUsuario) {
        this.id = id;
        this.ocupado = ocupado;
        this.ultimoUsuario = ultimoUsuario;
    }

    public Computador(){}
    private String ultimoUsuario;

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        this.ultimoUsuario = ultimoUsuario;
    }
}
