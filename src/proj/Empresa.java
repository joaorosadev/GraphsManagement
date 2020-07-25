package proj;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;

public class Empresa extends GraphNode implements Serializable {

    private String nome;
    private int id;
    private String morada;
    private SeparateChainingHashST<Integer, Pessoa> colaboradores;
    private SeparateChainingHashST<String, Especializacao> especializacoes;
    private RedBlackBST<Date, Encontro> encontros;
    private ArrayList<Empresa> empresas_ligadas;
    private Ligacoes ligacoes;
    private Rede rede;

    public Empresa(String nome, int id, String morada, Rede rede, int x, int y) {
        super(0,nome,x,y);
        this.nome = nome;
        this.id = id;
        this.morada = morada;
        colaboradores = new SeparateChainingHashST<>();
        especializacoes = new SeparateChainingHashST<>();
        encontros = new RedBlackBST<>();
        ligacoes=new Ligacoes();
        this.rede=rede;
        if(!rede.getEmpresas().contains(this.getId_g()))
            rede.getEmpresas().put(this.getId_g(),this);
        System.out.println("Empresa " + nome + " criada com sucesso.");
        if(!rede.getEmpresas_pessoas().contains(this.getId_ep()))
            rede.getEmpresas_pessoas().add(this);
        if(!rede.getEncontros_pessoas().contains(this.getId_enc()))
            rede.getEncontros_pessoas().add(this);
        rede.getEncontros_pessoas2().add(this);
    }

    //so guardamos id das pessoas
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(colaboradores.size());
        for (int i: colaboradores.keys()){
            sb.append(',');
            sb.append(i);
            sb.append(',');
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(especializacoes.size());
        for (String s: especializacoes.keys()){
            sb2.append(',');
            sb2.append(s);
            sb2.append(',');
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(encontros.size());
        for (Date d: encontros.keys()){
            sb3.append(',');
            sb3.append(d.toString());
            sb3.append(',');
        }
        return nome + "," + id + "," + morada + "," + sb.toString() + "," + sb2.toString() + ","
                + sb3.toString();
    }

    /*
    public GraphNode create_node(String nome, String localizacao){
        GraphNode g = new GraphNode(this,nome,localizacao);
        return g;
    }
    */

    /**
     * @param e a given Especializacao
     * @return void
     * adds a spec to the company
     */
    public void adicionar_especializacao(Especializacao e){
        for(String s: especializacoes.keys()) {
            if (e.getNome().compareTo(s) == 0) {
                System.out.println("A especialização especificada já foi inserida previamente.");
                return;
            }
        }
        especializacoes.put(e.getNome(),e);
        System.out.println("Especialização " + e.getNome() + " adicionada à empresa" + nome + " com sucesso.");
    }

    /**
     * @param e a given Especializacao
     * @return void
     * removes a spec to the company
     */
    public void remover_especializacao(Especializacao e){
        if(especializacoes.contains(e.getNome())) especializacoes.delete(e.getNome());
    }

    /**
     * @param p a given pessoa
     * @param cargo a given cargo
     * @param salario a given salario
     * @return void
     * employs Pessoa p to the company
     */
    public void contratar_colaborador(Pessoa p, String cargo, int salario) {
        for(int i: colaboradores.keys()){
            if(p.getId() == i){
                System.out.println("O colaborador especificado já foi contratado previamente.");
                return;
            }
        }
        colaboradores.put(p.getId(), p);
        p.setNome_empresa(this.nome);
        p.setCargo(cargo);
        p.setSalario(salario);
        p.getHistorico().adicionar_empresa(this);
        System.out.println(nome + " contratou " + p.getNome() + " com sucesso.");
    }

    /**
     * @param p a given pessoa
     * @return void
     * fires Pessoa p of the company
     */
    public void despedir_colaborador(Pessoa p) {
        for(int id: colaboradores.keys()){
            if(p.getId() == id){
                colaboradores.delete(p.getId());
                p.setNome_empresa("null");
                return;
            }
        }
    }

    /**
     * @param p a given pessoa
     * @param cargo a given cargo
     * @param salario a given salario
     * @return void
     * changes Pessoa p data in the company
     */
    public void editar_colaborador(Pessoa p,String cargo,int salario){
        for(int id: colaboradores.keys()){
            if(p.getId() == id){
                p.setCargo(cargo);
                p.setSalario(salario);
                return;
            }
        }
    }

    /**
     * @param area a given area
     * @return ArrayList<Pessoa> People interested in given area
     */
    public ArrayList<Pessoa> interessados_area(String area){
        ArrayList<Pessoa> interessados = new ArrayList<>();
        for(int id : colaboradores.keys()){
            for(int i=0; i < colaboradores.get(id).getAreas_interese().size(); i++){
                if(colaboradores.get(id).getAreas_interese().get(i).compareTo(area)==0) {
                    interessados.add(colaboradores.get(id));
                    break;
                }
            }
        }
        return interessados;
    }

    /**
     * @param encontro a given Encontro
     * @return void
     * Schedules a meeting
     */
    public void marcar_encontro(Encontro encontro){
        if(!encontros.contains(encontro.getData_inicio())) {
            this.encontros.put(encontro.getData_inicio(), encontro);
            encontros.put(encontro.getData_inicio(), encontro);
            System.out.println("A data do inicico do encontro é " + encontro.getData_inicio());
        }
    }

    /**
     * @param d a given Date
     * @return void
     * Schedules a meeting
     */
    public void marcar_encontro(Date d, int x, int y,Rede r){
        Encontro e = new Encontro(d,x,y,r);
        if(!encontros.contains(d)) {
            encontros.put(d, e);
            System.out.println("Empresa " + nome + " criou encontro na data: " + e.getData_inicio());
        }
    }

    /**
     * @param e a given Encontro
     * @param d a given Date
     * @return void
     * changes a meeting
     */
    public void editar_encontro(Encontro e, Date d){
        if(encontros.contains(d)) {
            encontros.put(d, e);
            System.out.println("Empresa " + nome + " alterou data de encontro para: " + e.getData_inicio());
        }
    }

    /**
     * @param e a given Encontro
     * @return void
     * Cancels a meeting
     */
    public void remover_encontro(Encontro e){
        if(encontros.contains(e.getData_inicio())) {
            encontros.delete(e.getData_inicio());
            System.out.println("Empresa " + nome + " removeu encontro na data: " + e.getData_inicio());
        }
    }

    /**
     * @param e a given Encontro
     * @param p a given pessoa
     * @return void
     * confirms Person presence in the Meeting
     */
    public void  confirmar_presenca(Encontro e, Pessoa p){
        boolean dataPreenchida = false;
        if(encontros.contains(e.getData_inicio()) && colaboradores.contains(p.getId())){
            for (Date d: p.getEncontros().keys()){
                if(d.compareTo(e.getData_inicio()) == 0){
                    dataPreenchida = true;
                    System.out.println(p.getNome() + " já tem um encontro para esta data");
                    break;
                }
            }

            if(dataPreenchida == false && !e.getParticipantes().contains(p.getId())){
                p.getEncontros().put(e.getData_inicio(),e);
                e.getParticipantes().put(p.getId(),p);
                System.out.println(p.getNome() + " está confirmado para o encontro na data " + e.getData_inicio());

            }
        }
    }

    /**
     * @param e a given Encontro
     * @param p a given pessoa
     * @return void
     * cancels Person presence in the Meeting
     */
    public void desmarcar_presenca(Encontro e, Pessoa p){
        if(p.getEncontros().contains(e.getData_inicio())){
            p.getEncontros().delete(e.getData_inicio());
            e.getParticipantes().delete(p.getId());
        }
    }

    /**
     * @param d1 a given Date
     * @param d2 a given Date
     * @return ArrayList<Encontro> Meetings between d1 and d2
     */
    public ArrayList<Encontro> encontros_num_intervalo(Date d1, Date d2){
        ArrayList<Encontro> e = new ArrayList<>();

        for(Date d: encontros.keys()) {
            if ((d.afterDate(d1)==true) && (d.beforeDate(d2)==true))
                e.add(encontros.get(d));
        }
        return e;
    }

    /**
     * @param r a given Network
     * @return void
     * removes company from the network
     */
    public void remover_da_rede(Rede r){
        if(r.getEmpresas().contains(this.id)){
            r.getEmpresas().delete(this.id);
            r.getEmpresas_removidas().add(this);
            for(int id : r.getPessoas().keys()){
                for(Date d: encontros.keys()){
                    if(r.getPessoas().get(id).a_participar_em_encontro_na_data(d))
                        r.getPessoas().get(id).getEncontros().delete(d);
                }
            }
        }
    }

/*
  public void seguir_pessoa(Pessoa p) {
      this.ligacoes.getSeguir_pessoas().put(p.getId(), p);
  }

  public void seguir_empresa(Empresa e) {
    this.ligacoes.getSeguir_empresas().put(e.getId(), e);
}

  public void deixar_seguir_pessoa(Pessoa p) {
    this.ligacoes.getSeguidores_pessoas().put(p.getId(), p);
}

  public void deixar_seguir_empresa(Empresa e) {
    this.ligacoes.getSeguidores_empresas().put(e.getId(), e);
}
*/

    // gets e sets
    public String getNome() { return nome; }
    public void setName(String nome) { this.nome = nome;}

    public int getId() { return id; }
    public void setId(int id) { this.id = id;}

    public String getMorada() { return morada; }
    public void setMorada(String morada) { this.morada = morada;}

    public SeparateChainingHashST<Integer, Pessoa> getColaboradores() { return colaboradores; }
    public void setColaboradores(SeparateChainingHashST<Integer, Pessoa> colaboradores) {
        this.colaboradores = colaboradores; }

    public SeparateChainingHashST<String, Especializacao> getEspecializacoes() { return especializacoes; }
    public void setEspecializacoes(SeparateChainingHashST<String, Especializacao> especializacoes) {
        this.especializacoes = especializacoes;
    }

    public RedBlackBST<Date, Encontro> getEncontros() { return encontros; }
    public void setEncontros(RedBlackBST<Date, Encontro> encontros) { this.encontros = encontros; }
  /*
  public Ligacoes getLigacoes() { return ligacoes; }
  public void setLigacoes(Ligacoes ligacoes) { this.ligacoes = ligacoes;}
  */

    public ArrayList<Empresa> getEmpresas_ligadas() {
        return empresas_ligadas;
    }

    public void setEmpresas_ligadas(ArrayList<Empresa> empresas_ligadas) {
        this.empresas_ligadas = empresas_ligadas;
    }

    public void setLigacoes(Ligacoes ligacoes) {
        this.ligacoes = ligacoes;
    }

    public Ligacoes getLigacoes() {
        return ligacoes;
    }
}
