package controleufla.ufla.controleufla;

import java.util.ArrayList;

/**
 * Created by Chrnoz on 05/12/2017.
 */

public class Sala {
    public ArrayList<Computador> getComputadores() {
        return computadores;
    }

    public void setComputadores(ArrayList<Computador> computadores) {
        this.computadores = computadores;
    }

    public String getNomeDaSala() {
        return nomeDaSala;
    }

    public void setNomeDaSala(String nomeDaSala) {
        this.nomeDaSala = nomeDaSala;
    }

    public Sala(String nomeDaSala, int nroDeComputadores, int nroDeComputadoresEmUso, ArrayList<Computador> computadores) {
        this.nomeDaSala = nomeDaSala;
        this.nroDeComputadores = nroDeComputadores;
        this.nroDeComputadoresEmUso = nroDeComputadoresEmUso;
        this.computadores = computadores;
    }

    private String nomeDaSala;
    private int nroDeComputadores;
    private int nroDeComputadoresEmUso;
    private ArrayList<Computador> computadores;


    public int getNroDeComputadores() {
        return nroDeComputadores;
    }

    public void setNroDeComputadores(int nroDeComputadores) {
        this.nroDeComputadores = nroDeComputadores;
    }

    public int getNroDeComputadoresEmUso() {
        return nroDeComputadoresEmUso;
    }

    public void setNroDeComputadoresEmUso(int nroDeComputadoresEmUso) {
        this.nroDeComputadoresEmUso = nroDeComputadoresEmUso;
    }

}
