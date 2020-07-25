package proj;

import edu.princeton.cs.algs4.RedBlackBST;
import java.util.ArrayList;

public class Pessoa extends GraphNode implements Comparable<Pessoa>{

    private String nome;
    private int id;
    private String morada;
    private String cargo;
    private int salario;
    private String nome_empresa;
    private String especializacao;
    private Historico historico;
    private ArrayList<String> areas_interesse;
    private RedBlackBST<Date, Encontro> encontros;
    private Ligacoes ligacoes;
    private Rede rede;
    private int experiencia;

    public Pessoa(String nome, int id, String morada, String especializacao, Rede rede, int experiencia,int x, int y) {
        super(2,nome,x,y);
        this.rede = rede;
        this.nome = nome;
        this.id = id;
        this.morada = morada;
        this.experiencia = experiencia;
        this.nome_empresa = "null"; // quando for contratado esta variavel vai ser alterada
        this.especializacao = especializacao;
        ligacoes = new Ligacoes();
        areas_interesse= new ArrayList<String>();
        historico = new Historico(experiencia);
        encontros = new RedBlackBST<>();
        System.out.println("Pessoa " + nome + " criada com sucesso.");
        if(!rede.getPessoas().contains(this.getId_g()))
            rede.getPessoas().put(this.getId_g(),this);
        if(!rede.getEmpresas_pessoas().contains(this.getId_ep()))
            rede.getEmpresas_pessoas().add(this);
        if(!rede.getEncontros_pessoas().contains(this.getId_enc()))
            rede.getEncontros_pessoas().add(this);
        if(!rede.getPessoas_pessoas().contains(this.getId_pp()))
            rede.getPessoas_pessoas().add(this);

        rede.getPessoas2().add(this);
        rede.getEncontros_pessoas2().add(this);
    }

    /**
     * @param e a given Encontro
     * @return void
     * confirms Person presence in a Meeting
     */
    public void  confirmar_presenca(Encontro e){
        boolean dataPreenchida = false;

        for (Date d: encontros.keys()){
            if(d.compareTo(e.getData_inicio()) == 0){
                dataPreenchida = true;
                System.out.println(nome + " já tem um encontro para esta data");
                break;
            }
        }
        if(dataPreenchida == false && !e.getParticipantes().contains(this.id)){
            encontros.put(e.getData_inicio(),e);
            e.getParticipantes().put(id,this);
            System.out.println(nome + " está confirmado para o encontro na data " + e.getData_inicio());
        }
    }

    @Override
    public int compareTo(Pessoa p){
        if(this.historico.getAnos_trabalho() < p.historico.getAnos_trabalho())
            return -1;
        else if(this.historico.getAnos_trabalho() > p.historico.getAnos_trabalho())
            return 1;
        return 0;
    }

    public int getExperiencia(){return experiencia;}

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int sizeOfAreas = areas_interesse.size();
        String histSiz = Integer.toString(historico.getEmpresas().size());

        sb.append(sizeOfAreas);
        for(int i = 0; i < sizeOfAreas; i++){
            sb.append(',');
            sb.append(areas_interesse.get(i));
            sb.append(',');
        }
        sb2.append(encontros.size());
        for (Date d: encontros.keys()){
            sb2.append(',');
            sb2.append(d.toString());
            sb2.append(',');
        }
        return nome + "," + id + "," + morada + "," + cargo + "," + salario + "," + nome_empresa + "," +
                especializacao + ","  + histSiz + historico.toString()  + ","
                + sb.toString() + "," + sb2.toString();
    }

      /*
      public void seguir_pessoa(Pessoa p) { this.ligacoes.getSeguir_pessoas().put(p.getId(), p); }

      public void seguir_empresa(Empresa e) {  this.ligacoes.getSeguir_empresas().put(e.getId(), e); }

      public void deixar_seguir_pessoa(Pessoa p) {
          this.ligacoes.getSeguidores_pessoas().put(p.getId(), p);
      }

      public void deixar_seguir_empresa(Empresa e) {
          this.ligacoes.getSeguidores_empresas().put(e.getId(), e);
      }
      */

    /**
     * @param area a given area
     * @return void
     * adds an interest to the person
     */
    public void inserir_interesse(String area){
        if(!areas_interesse.contains(area)) areas_interesse.add(area);
    }

    /**
     * @param area a given area
     * @return void
     * removes an interest to the person
     */
    public void remover_interesse(String area){
        if(areas_interesse.contains(area)) areas_interesse.remove(area);
    }

    /**
     * @param r a given Network
     * @return void
     * removes person from the network
     */
    public void remover_da_rede(Rede r){
        if(r.getPessoas().contains(this.id)){
            r.getPessoas().delete(this.id);
            r.getPessoas_removidas().add(this);
            for(Date d: encontros.keys()){
                encontros.get(d).getParticipantes().delete(this.id);
            }
            for(int id : r.getEmpresas().keys()){
                if(r.getEmpresas().get(id).getNome().compareTo(this.nome_empresa) == 0){
                    for(String s: r.getEmpresas().get(id).getEspecializacoes().keys()){
                        if(especializacao.compareTo(s) == 0){
                            r.getEmpresas().get(id).getEspecializacoes().get(s).remover_especialista(this);
                        }
                    }
                }
            }
        }
    }

    public void print_pessoa(){
        System.out.println("Nome: " +this.nome + "\n");
        System.out.println("Empresa: " +this.nome_empresa + "\n");
        System.out.println("Especializacao: " +this.especializacao + "\n");
    }

    /**
     * @param e a given Meeting
     * @return boolean true if attending, false if not
     */
    public boolean a_participar_em_encontro(Encontro e){
        return encontros.contains(e.getData_inicio());
    }

    /**
     * @param d a given Meeting Date
     * @return boolean true if attending, false if not
     */
    public boolean a_participar_em_encontro_na_data(Date d){
        return encontros.contains(d);
    }

    //gets e sets
    public String getNome() {
        return nome;
    }
    public void setNome(String nome){ this.nome = nome; }

    public int getId() {
        return id;
    }
    public void setId(int id){ this.id = id; }

    public String getNome_empresa() {
        return nome_empresa;
    }
    public void setNome_empresa(String nome_empresa){ this.nome_empresa = nome_empresa; }

    public String getMorada() {
        return morada;
    }
    public void setMorada(String morada) { this.morada = morada; }

    public ArrayList<String> getAreas_interese() {return areas_interesse;}
    public void setAreas_interesse(ArrayList<String> areas_interesse){ this.areas_interesse = areas_interesse; }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public int getSalario() {
        return salario;
    }
    public void setSalario(int salario) { this.salario = salario; }

    public String getEspecializacao() {
        return especializacao;
    }
    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }


    public Historico getHistorico() {
        return historico;
    }
    public void setHistorico(Historico historico) { this.historico = historico; }

    public RedBlackBST<Date,Encontro> getEncontros() {  return encontros; }
    public void setEncontros(RedBlackBST<Date,Encontro> encontros) { this.encontros = encontros; }

    public Ligacoes getLigacoes() {
        return ligacoes;
    }
    public void setLigacoes(Ligacoes ligacoes) { this.ligacoes = ligacoes; }
}