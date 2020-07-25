package proj;

import edu.princeton.cs.algs4.SeparateChainingHashST;

public class Historico {

    private SeparateChainingHashST<Integer, Empresa> empresas;
    private int anos_trabalho;

    public Historico(int anos) {
        empresas = new SeparateChainingHashST<>();
        this.anos_trabalho = anos;
    }

    /**
     * @param e a given Company
     * @return void
     * adds a company
     */
    public void adicionar_empresa(Empresa e) {
        for (int id : empresas.keys()) {
            if (id == e.getId()) {
                return;
            }
        }
        empresas.put(e.getId(), e);
    }

    //Guardamos so o nome da empresa, "empresas" vai ser preenchido por um metodo a fazer
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i : empresas.keys()) {
            sb.append(empresas.get(i).getNome());
            sb.append(",");
        }
        return sb.toString();
    }

    public SeparateChainingHashST<Integer, Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(SeparateChainingHashST<Integer, Empresa> empresas) {
        this.empresas = empresas;
    }

    public void setAnos_trabalho(int anos_trabalho) {
        this.anos_trabalho = anos_trabalho;
    }

    public int getAnos_trabalho() {
        return anos_trabalho;
    }
}

