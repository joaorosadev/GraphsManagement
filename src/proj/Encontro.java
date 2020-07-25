package proj;

import edu.princeton.cs.algs4.SeparateChainingHashST;

public class Encontro extends GraphNode{

    private Date data_inicio;
    private SeparateChainingHashST<Integer, Pessoa> participantes;

    public Encontro(Date inicio,int x,int y,Rede rede){
        super(1,"",x,y);
        data_inicio=inicio;
        participantes = new SeparateChainingHashST<>();

        if(!rede.getEncontros().contains(data_inicio))
            rede.getEncontros().put(data_inicio,this);
        if(!rede.getEncontros_pessoas().contains(this.getId_enc()))
            rede.getEncontros_pessoas().add(this);
        rede.getEncontros_pessoas2().add(this);
    }

    //Para preencher participantes percorremos os encontros de todas as pessoas, e aquele em que date_inicio =
    // data_inicio do encontro da pessoa, adicionamos a partipantes
    @Override
    public String toString (){
        return data_inicio.toString();
    }


    public Date getData_inicio() { return  data_inicio; }

    public void setData_inicio(Date data_inicio) { this.data_inicio = data_inicio; }

    /**
     * @return list of participants
     */
    public SeparateChainingHashST<Integer, Pessoa> getParticipantes() { return participantes;}

    public void setParticipantes(SeparateChainingHashST<Integer, Pessoa> participantes) {
        this.participantes = participantes;
    }

}
