package proj;

import edu.princeton.cs.algs4.SeparateChainingHashST;

public class Ligacoes {

    private SeparateChainingHashST<Integer, Pessoa> seguidores_pessoas;
    private SeparateChainingHashST<Integer, Empresa>  seguidores_empresas;
    private SeparateChainingHashST<Integer, Pessoa>  seguir_pessoas;
    private SeparateChainingHashST<Integer, Empresa>  seguir_empresas;
    private SeparateChainingHashST<Integer, PontoIntermedio> seguir_pontos;
    //SO PARA PONTOS INTERMEDIOS
    private SeparateChainingHashST<Integer, Encontro> ligar_a_encontros;

    public Ligacoes (){
        seguidores_pessoas = new SeparateChainingHashST<>();
        seguidores_empresas = new SeparateChainingHashST<>();
        seguir_pessoas = new SeparateChainingHashST<>();
        seguir_empresas = new SeparateChainingHashST<>();
        seguir_pontos = new SeparateChainingHashST<>();
        ligar_a_encontros = new SeparateChainingHashST<>();
    }

    public SeparateChainingHashST<Integer, Encontro> getLigar_a_encontros() {
        return ligar_a_encontros;
    }

    public SeparateChainingHashST<Integer, Pessoa> getSeguidores_pessoas() {
        return seguidores_pessoas;
    }

    public void setSeguidores_pessoas(SeparateChainingHashST<Integer, Pessoa> seguidores_pessoas) {
        this.seguidores_pessoas = seguidores_pessoas;
    }

    public SeparateChainingHashST<Integer, Empresa> getSeguidores_empresas() {
        return seguidores_empresas;
    }

    public void setSeguidores_empresas(SeparateChainingHashST<Integer, Empresa> seguidores_empresas) {
        this.seguidores_empresas = seguidores_empresas;
    }

    public SeparateChainingHashST<Integer, Pessoa> getSeguir_pessoas() {
        return seguir_pessoas;
    }

    public void setSeguir_pessoas(SeparateChainingHashST<Integer, Pessoa> seguir_pessoas) {
        this.seguir_pessoas = seguir_pessoas;
    }

    public SeparateChainingHashST<Integer, Empresa> getSeguir_empresas() {
        return seguir_empresas;
    }

    public void setSeguir_empresas(SeparateChainingHashST<Integer, Empresa> seguir_empresas) {
        this.seguir_empresas = seguir_empresas;
    }

    public SeparateChainingHashST<Integer, PontoIntermedio> getSeguir_pontos() {
        return seguir_pontos;
    }

    public void setSeguir_pontos(SeparateChainingHashST<Integer, PontoIntermedio> seguir_pontos) {
        this.seguir_pontos = seguir_pontos;
    }
}


