package proj;

public class GraphNode {

    private int id_g;
    private int id_pp = 0, id_ep = 0, id_enc = 0;
    private static int count = 0,count1 = 0,count2= 0, count4=0;
    private String node_name;
    private double x,y;
    private boolean empresa = false;
    private boolean encontro = false;
    private boolean pessoa = false;
    private boolean pontoIntermedio = false;

    public GraphNode(int opc,String nome, int x,int y)
    {
        switch(opc){
            case 0:
                empresa=true;
                this.x=x;this.y=y;
                this.node_name=nome;
                id_ep=count2++;
                id_enc=count4++;
                break;

            case 1:
                encontro=true;
                this.x=x;this.y=y;
                id_enc = count4++;
                break;

            case 2:
                pessoa=true;
                node_name=nome;
                id_ep=count2++;
                id_pp=count1++;
                id_enc=count4++;
                break;

            case 3: pontoIntermedio = true;
                    this.x=x;this.y=y;
                    node_name = "";
                    id_enc = count4++;
                    break;
        }

        this.id_g = count++;
    }

    public double calculate_dist(GraphNode n){

        return Math.sqrt(Math.pow(this.x - n.x,2) + Math.pow(this.y - n.y,2));
    }

    public GraphNode(){}

    public int getId_g() {
        return id_g;
    }

    public static int getCount() {
        return count;
    }

    public String getGraph_name() {
        return node_name;
    }

    public void setGraph_name(String graph_name) {
        this.node_name = graph_name;
    }

    public boolean isEmpresa() {
        return empresa;
    }

    public boolean isEncontro() {
        return encontro;
    }

    public boolean isPessoa() {
        return pessoa;
    }

    public boolean isPontoIntermedio(){return pontoIntermedio;}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setEmpresa(boolean empresa) {
        this.empresa = empresa;
    }

    public void setEncontro(boolean encontro) {
        this.encontro = encontro;
    }

    public void setPessoa(boolean pessoa) {
        this.pessoa = pessoa;
    }

    public int getId_pp() {
        return id_pp;
    }

    public int getId_ep() {
        return id_ep;
    }

    public int getId_enc() {
        return id_enc;
    }
}
