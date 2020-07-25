package proj;

public class PontoIntermedio extends GraphNode {
    private Ligacoes ligacoes;

    public PontoIntermedio(int x, int y, Rede rede){
        super(3,"",x,y);
        ligacoes = new Ligacoes();
        if(!rede.getPontos().contains(this.getId_g()))
            rede.getPontos().put(this.getId_g(),this);
        if(!rede.getEncontros_pessoas().contains(this.getId_enc()))
            rede.getEncontros_pessoas().add(this);

        rede.getPontos2().add(this);
        rede.getEncontros_pessoas2().add(this);
    }

    public Ligacoes getLigacoes(){ return ligacoes;}
}
