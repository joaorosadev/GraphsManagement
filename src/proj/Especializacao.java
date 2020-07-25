package proj;

import edu.princeton.cs.algs4.SeparateChainingHashST;

public class Especializacao{

    private String nome;
    private SeparateChainingHashST<Integer, Pessoa>  especialistas;

    public Especializacao(String nome) {
        this.nome = nome;
        especialistas = new SeparateChainingHashST<>();
        System.out.println("Especialização " + nome + " criada com sucesso.");
    }

    //Ao ler do ficheiro "especialistas" começa a null, e vai ser preenchido por um método que verifica quais
    //pessoas têm essa especializaçao
    @Override
    public String toString(){
        return nome;
    }

    /**
     * @param r a given Network
     * @return void
     * adds people to the especialization automatically
     */
    public void adicionar_pessoas_auto(Rede r){
        for(int id: r.getPessoas().keys()){
            if(r.getPessoas().get(id).getEspecializacao().compareTo(this.nome) == 0){
                especialistas.put(id,r.getPessoas().get(id));
            }
        }
    }

    /**
     * @return void
     * updates people in the especialization
     */
    public void atualizar_pessoas() {
        for(int id: especialistas.keys()){
            if(especialistas.get(id).getEspecializacao() != this.nome){
                especialistas.delete(id);
            }
        }
    }

    /**
     * @param p a given Person
     * @return void
     * removes a specialist from the list
     */
    public void remover_especialista(Pessoa p){
        if(especialistas.contains(p.getId())) especialistas.delete(p.getId());
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public SeparateChainingHashST<Integer, Pessoa> getEspecialistas() {
        return especialistas;
    }

    public void setEspecialistas(SeparateChainingHashST<Integer, Pessoa> especialistas) {
        this.especialistas = especialistas;
    }
}
