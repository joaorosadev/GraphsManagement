package proj;

import edu.princeton.cs.algs4.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Rede implements Serializable{

    private SeparateChainingHashST<Integer, Pessoa> pessoas;
    private ArrayList<Pessoa> pessoas2;
    private SeparateChainingHashST<Integer, Empresa> empresas;
    private SeparateChainingHashST<Date, Encontro> encontros;
    private SeparateChainingHashST<Integer, PontoIntermedio> pontosInt;
    private ArrayList<PontoIntermedio> pontos2;
    private ArrayList<Pessoa> pessoas_removidas;
    private ArrayList<Empresa> empresas_removidas;
    private ArrayList<GraphNode> empresas_encontros  = new ArrayList<>();
    private ArrayList<GraphNode> empresas_pessoas  = new ArrayList<>();
    //Contem todos os graphnodes do grafo enc
    private ArrayList<GraphNode> encontros_pessoas  = new ArrayList<>();
    private ArrayList<GraphNode> encontros_pessoas2 ;
    private ArrayList<GraphNode> pessoas_pessoas  = new ArrayList<>();
    private EdgeWeightedDigraph ep,pp;
    private EdgeWeightedGraph enc;

    public Rede(){
        setPessoas(new SeparateChainingHashST<>());
        setEmpresas(new SeparateChainingHashST<>());
        setPontos(new SeparateChainingHashST<>());
        pessoas2 = new ArrayList<>();
        pontos2 = new ArrayList<>();
        encontros_pessoas2 = new ArrayList<>();

        setPessoas_removidas(new ArrayList<>());
        setEmpresas_removidas(new ArrayList<>());

        setEncontros(new SeparateChainingHashST<>());
        System.out.println("Rede criada com sucesso.");
    }

    public ArrayList<Pessoa> getPessoas2(){return pessoas2;}
    public ArrayList<PontoIntermedio> getPontos2(){return pontos2;}

    public ArrayList<GraphNode> getEncontros_pessoas2() {
        return encontros_pessoas2;
    }

    /**
     * @return void
     * Instantiates graphs
     */
    public void create_graphs(){
        pp = new EdgeWeightedDigraph(this.pessoas2.size());
        ep = new EdgeWeightedDigraph(this.empresas_pessoas.size());
        enc = new EdgeWeightedGraph(this.encontros_pessoas.size()+this.empresas.size()+this.pontosInt.size());
    }

    /**
     * @param gn1 Node 1
     * @param gn2 Node 2
     * @return Straight line distance between given nodes
     */
    public double euclidean_distance(GraphNode gn1, GraphNode gn2){
        return Math.sqrt(Math.pow(gn2.getX() - gn1.getX(),2.0) + Math.pow(gn2.getY() - gn1.getY(),2.0));
    }

    /*
    public void add_ligacao_(EdgeWeightedGraph g,GraphNode g1,GraphNode g2,int peso){   //peso seria distancia por exemplo
        Edge e =  new Edge(g1.getId(),g2.getId(),peso);
        g.addEdge(e);
    }
    */

    /**
     * @return void
     * Fills enc (Localizations) graph
     */
    public void preencher_grafo_enc2(){
        PontoIntermedio pt = null, ptAux = null;
        Empresa e = null;
        Pessoa p = null;
        Encontro en = null;
        double weight = 0.0;
        for(int i : pontosInt.keys()){
            pt = pontosInt.get(i);
            for(int j : pt.getLigacoes().getSeguir_empresas().keys()){
                e = pt.getLigacoes().getSeguir_empresas().get(j);
                weight = euclidean_distance(pt,e);
                Edge newEdge = new Edge(pt.getId_enc(), e.getId_enc(),weight);
                enc.addEdge(newEdge);
            }
            for(int j : pt.getLigacoes().getSeguir_pessoas().keys()){
                p = pt.getLigacoes().getSeguir_pessoas().get(j);
                weight = euclidean_distance(pt,p);
                Edge newEdge = new Edge(pt.getId_enc(), p.getId_enc(),weight);
                enc.addEdge(newEdge);
            }
            for(int j : pt.getLigacoes().getSeguir_pontos().keys()){
                ptAux = pt.getLigacoes().getSeguir_pontos().get(j);
                weight = euclidean_distance(pt,ptAux);
                Edge newEdge = new Edge(pt.getId_enc(), ptAux.getId_enc(),weight);
                enc.addEdge(newEdge);
            }
            for(int j : pt.getLigacoes().getLigar_a_encontros().keys()){
                en = pt.getLigacoes().getLigar_a_encontros().get(j);
                weight = euclidean_distance(pt,en);
                Edge newEdge = new Edge(pt.getId_enc(), en.getId_enc(),weight);
                enc.addEdge(newEdge);
            }
        }
    }

    /**
     * @return void
     * Fills Person-Person relationships graph
     */
    public void preencher_grafo_pp2(){
        Pessoa p = null, p2 = null;
        for(int i: pessoas.keys()){
            p = pessoas.get(i);
            for(int j: p.getLigacoes().getSeguir_pessoas().keys()){
                p2 = p.getLigacoes().getSeguir_pessoas().get(j);
                DirectedEdge newEdge = new DirectedEdge(p.getId_pp(),p2.getId_pp(),0.0);
                pp.addEdge(newEdge);
            }
        }
    }

    /**
     * @return void
     * Fills Person-Company relationships graph
     */
    public void preencer_grapo_ep2(){
        Pessoa p = null;
        Empresa e = null;
        for(int i: pessoas.keys()){
            p = pessoas.get(i);
            for(int j : p.getLigacoes().getSeguir_empresas().keys()){
                e = p.getLigacoes().getSeguir_empresas().get(j);
                DirectedEdge newEdge = new DirectedEdge(p.getId_ep(),e.getId_ep(),0.0);
                ep.addEdge(newEdge);
            }
        }
        for(int i: empresas.keys()){
            e = empresas.get(i);
            for(int j : e.getLigacoes().getSeguir_pessoas().keys()){
                p = e.getLigacoes().getSeguir_pessoas().get(j);
                DirectedEdge newEdge = new DirectedEdge(e.getId_ep(),p.getId_ep(),0.0);
                ep.addEdge(newEdge);
            }
        }
    }

    /**
     * @param source Node
     * @param dest Node
     * @return void
     * Prints path between given nodes if there is one
     */
    public void path_to2(GraphNode source, GraphNode dest){
        DijkstraUndirectedSP d = new DijkstraUndirectedSP(enc,source.getId_enc());
        for(Edge e: d.pathTo(dest.getId_enc())){
            int v = e.either();
            int w = e.other(v);
            System.out.println(v + " - " + w);
        }
    }

    /**
     * @param source Node
     * @param dest Node
     * @return Dijkstra with shorthest path
     * Gives shortest Path between given nodes
     */
    public DijkstraUndirectedSP path_to1(GraphNode source, GraphNode dest){
        DijkstraUndirectedSP d = new DijkstraUndirectedSP(enc,source.getId_enc());
        for(Edge e: d.pathTo(dest.getId_enc())){
            int v = e.either();
            int w = e.other(v);
            System.out.println(v + " - " + w);
        }
        return d;
    }

    /**
     * @return void
     * Prints the enc_ids of every enc_node
     */
    public void display_enc_ids(){
        for(GraphNode g: encontros_pessoas){
            if(g.isPessoa()){
                System.out.println(((Pessoa)g).getNome() + " " + g.getId_enc());
            } else if(g.isEncontro()){
                System.out.println(((Encontro)g).getData_inicio() + " " + g.getId_enc());
            } else if(g.isEmpresa()){
                System.out.println(((Empresa)g).getNome() + " " + g.getId_enc());
            } else{
                System.out.println("Ponto Intermédio: (" + g.getX() +","+ g.getY()+")" + " " + g.getId_enc());
            }
        }
    }

    /**
     * @return True. if the Person-Company graph is bipartite
     * Checks if graph is bipartite
     */
    public boolean bipartidoEP(){
        Graph g = new Graph(ep.V());
        for(DirectedEdge e:ep.edges()){
            g.addEdge(e.from(),e.to());
        }

        Bipartite b = new Bipartite(g);
        return b.isBipartite();
    }

    /**
     * @return True. if the Person-Person graph is bipartite
     * Checks if graph is bipartite
     */
    public boolean bipartidoPP(){
        Graph g = new Graph(pp.V());
        for(DirectedEdge e:pp.edges()){
            g.addEdge(e.from(),e.to());
        }

        Bipartite b = new Bipartite(g);
        return b.isBipartite();
    }

    /**
     * @param encontro to check
     * @return void
     * Prints people directly connected to a meeting
     */
    public void pessoas_diretamente_ligadas_a_encontro(Encontro encontro) { // R11 b
        int v, w;
        Empresa e;
        for (Date d : this.encontros.keys()) {
            for (int j = 0; j < enc.V(); j++) {
                for (Edge edge : enc.adj(j)) {
                    v = edge.either();
                    w = edge.other(v);
                    if ((encontros_pessoas.get(v).isPessoa() == true && this.encontros.get(d) == encontro) || (encontros_pessoas.get(w).isPessoa() == true && this.encontros.get(d) == encontro)) {
                        if (this.pessoas2.get(v).isPessoa() == true) {
                            this.pessoas2.get(v).print_pessoa();
                        } else {
                            this.pessoas2.get(w).print_pessoa();
                        }
                    }
                }
            }
        }
    }

    /**
     * @param e Meeting to check
     * @return Array of people in given meeting
     * Returns ArrayList of people in given meeting
     */
    public ArrayList<Pessoa> pessoas_em_encontro(Encontro e){
        ArrayList<Pessoa> pessoasEnc = new ArrayList<>(); Pessoa p;
        for(int i: pessoas.keys()){
            p = pessoas.get(i);
            for(Date d: p.getEncontros().keys()){
                if(p.getEncontros().get(d) == e){
                    pessoasEnc.add(p);
                }
            }
        }
        return pessoasEnc;
    }

    /**
     * @param emp Company to check
     * @return Array of people that follow the given company
     * Returns ArrayList of people following the company
     */
    public ArrayList<Pessoa> pessoas_seguem_empresa(Empresa emp){
        ArrayList<Pessoa> ppl = new ArrayList<>();
        Empresa e = null;
        Pessoa p = null;
        for(int i = 0; i < ep.V(); i++){
            for(DirectedEdge edge : ep.adj(i)){
                int v = edge.from();
                int w = edge.to();
                if(empresas_pessoas.get(v).isPessoa() && empresas_pessoas.get(w).isEmpresa()){
                    e = (Empresa) empresas_pessoas.get(w);
                    p = (Pessoa) empresas_pessoas.get(v);
                    if(e.getNome().compareTo(emp.getNome())==0){
                        ppl.add(p);
                    }
                }
            }
        }
        return ppl;
    }

    /**
     * @param esp Speciality to check
     * @return ArrayList of people with given speciality and unemployed
     */
    public ArrayList<Pessoa> pessoas_com_especializacao_sem_empresa(Especializacao esp) { // R11 d
        ArrayList<Pessoa> ppl = new ArrayList<>();
        for(int i=0;i<ep.V();i++){
            for (DirectedEdge edge : ep.adj(i)) {
                int v = edge.from();
                if(empresas_pessoas.get(v).isPessoa()){
                    if(((Pessoa)empresas_pessoas.get(v)).getEspecializacao().compareTo(esp.toString()) == 0){
                        if(((Pessoa)empresas_pessoas.get(v)).getNome_empresa().compareTo("null")==0)
                            ppl.add(((Pessoa)empresas_pessoas.get(v)));
                    }
                }
            }
        }
        return ppl;
    }

    /**
     * @param emp Company to check
     * @return ArrayList of people that follow given company and that have either the same speciality or interest as one of the company's
     */
    //Sugere pessoas que seguem a empresa com base nas areas de interesse e especialização
    public ArrayList<Pessoa> sugerir_profissionais_a_empresa(Empresa emp){
        ArrayList<Pessoa> ppl = new ArrayList<>();
        int v,w;
        Pessoa p;
        for(int j = 0; j < ep.V(); j++){
            for(DirectedEdge edge: ep.adj(j)){
                v = edge.from();
                w = edge.to();
                if(empresas_pessoas.get(v).isPessoa() && empresas_pessoas.get(w).isEmpresa()){
                    if(((Empresa)empresas_pessoas.get(w)).getNome().compareTo(emp.getNome()) == 0){
                        p = (Pessoa) empresas_pessoas.get(v);
                        for(String s: p.getAreas_interese()) {
                            for(String s2: emp.getEspecializacoes().keys() ){
                                if(s.compareTo(s2) == 0){
                                    ppl.add(p);
                                }
                            }
                        }
                        for(String s: emp.getEspecializacoes().keys()){
                            if(s.compareTo(p.getEspecializacao())==0)
                                ppl.add(p);
                        }
                    }
                }
            }
        }
        return ppl;
    }

    /**
     * @return void
     * Calls suggestion method for all companies in the network
     */
    public void sugerir2(){
        for(int i:this.empresas.keys())
            sugerir_profissionais_a_empresas2(this.empresas.get(i));
    }

    /**
     * @param empresa Company to check
     * @return void
     * Suggests people to given company ordered by descending experience level
     */
    public void sugerir_profissionais_a_empresas2(Empresa empresa){
        SeparateChainingHashST<Integer,Pessoa> pessoas = new SeparateChainingHashST<>();
        int i=0;
        for(int j=0;j<ep.V();j++) {
            for (DirectedEdge edge : ep.adj(j)) {
                if (empresas_pessoas.get(edge.from()).isPessoa() == true && this.empresas.get(edge.to()) == empresa) {
                    for (String s : empresa.getEspecializacoes().keys()) {
                        if (this.pessoas2.get(edge.from()).getEspecializacao().compareTo(empresa.getEspecializacoes().get(s).getNome()) == 0) {
                            pessoas.put(this.pessoas2.get(edge.from()).getId_ep(), this.pessoas2.get(edge.from()));
                        }
                    }
                }
            }
        }

        Pessoa[] pessoas2 = new Pessoa[pessoas.size()];
        for(int k:pessoas.keys()){
            pessoas2[k]=this.pessoas2.get(k);
        }
        Arrays.sort(pessoas2);

        System.out.println("Sugestao de profissionais por ordem de experiencia profissional:\n");
        for(i=pessoas.size();i>=0;i--){
            pessoas2[i].print_pessoa();
            System.out.println("Anos de trabalho " + pessoas2[i].getHistorico().getAnos_trabalho());
        }
    }

    /**
     * @param e Company to check
     * @param p Person to check
     * @return void
     * Given a Company and a Person, which of the Person's friends can facilitate the relationship
     */
    public void facilitar_relacoes(Empresa e, Pessoa p) { // R11 f: Dada uma empresa e uma pessoa, qual o amigo que pode facilitar a relacao
        SeparateChainingHashST<Integer, Pessoa> pessoas = new SeparateChainingHashST<>();
        for (int i = 0; i < pp.V(); i++) {
            for (DirectedEdge edge : pp.adj(i)) {
                for(int ep:this.pessoas2.get(edge.from()).getHistorico().getEmpresas().keys()){
                    if(this.pessoas2.get(edge.from()).getHistorico().getEmpresas().get(ep) == e)
                        pessoas.put(this.pessoas2.get(edge.from()).getId_pp(), this.pessoas2.get(edge.from()));
                }

            }
        }
        for (int j = 0; j < pp.V(); j++) {
            for (DirectedEdge edge : pp.adj(j)) {
                for (int k : pessoas.keys()) {
                    if (this.pessoas2.get(edge.from()) == p && this.pessoas2.get(edge.to()) == this.pessoas2.get(k)) {
                        System.out.println("A pessoa: \n");
                        this.pessoas2.get(k).print_pessoa();
                        System.out.println("\n Facilita a relacao entre a empresa" + e.getNome() + " e o profissional\n");
                        p.print_pessoa();
                    }

                }
            }
        }

    }

    /**
     * @return ArrayList of people that are unemployed and have experience and a speciality
     * Checks if graph is bipartite
     */
    public ArrayList<Pessoa> desempregado_com_exp_e_esp(Especializacao esp){
        Pessoa p;
        ArrayList<Pessoa> ppl = new ArrayList<>();
        for(int i=0; i< ep.V(); i++){
            for(DirectedEdge edge: ep.adj(i)){
                int v = edge.from();
                if(empresas_pessoas.get(v).isPessoa()){
                    p = (Pessoa) empresas_pessoas.get(v);
                    if(p.getExperiencia() > 0 && p.getEspecializacao().compareTo(esp.toString()) == 0 && p.getNome_empresa().compareTo("null") == 0){
                        ppl.add(p);
                    }
                }
            }
        }
        return ppl;
    }

    /**
     * @param inicio Beggining of interval
     * @param fim End of interval
     * @return void
     * Checks which meetings are occurring during the given interval
     */
    public void encontros_num_intevalo(Date inicio, Date fim){  // R11 h
        int v;
        for (int j = 0; j < enc.V(); j++) {
            for (Edge edge : enc.adj(j)) {
                v = edge.either();
                if(encontros_pessoas.get(v).isEncontro()==true){
                    if(((Encontro)encontros_pessoas.get(v)).getData_inicio().afterDate(inicio) && ((Encontro)encontros_pessoas.get(v)).getData_inicio().beforeDate(fim))
                        System.out.println(((Encontro)encontros_pessoas.get(v)).getData_inicio());
                }
            }
        }
    }

    /**
     * @return void
     * prints people in their first job
     */
    public void pessoas_no_primeiro_trabalho(){
        System.out.println("Pessoas no seu primeiro emprego:");
        for(int id: pessoas.keys()){
            if(pessoas.get(id).getHistorico().getEmpresas().size() == 1 )
                System.out.println(pessoas.get(id).getNome());
        }
    }

    /**
     * @param e a given Company
     * @return Meetings of the given Company
     */
    public ArrayList<Encontro> encontros_da_empresa(Empresa e){
        System.out.println("Encontros da empresa: " + e.getNome());
        ArrayList<Encontro> encontros = new ArrayList<>();
        for(Date d: e.getEncontros().keys()){
            encontros.add(e.getEncontros().get(d));
        }
        for(Encontro en: encontros){
            System.out.println("Data do encontro: " + en.getData_inicio());
        }
        return encontros;
    }

    /**
     * @return int count
     * number of unemployed people
     */
    public int nr_desempregados(){
        int count=0;
        for(int id: pessoas.keys()){
            if(pessoas.get(id).getNome_empresa().compareTo("null") == 0) count++;
        }
        return count;
    }

    /**
     * @param n a given number
     * @return Encontros
     * Meetings with more than n people
     */
    public ArrayList<Encontro> encontros_mais_de_n_pessoas(int n){
        ArrayList<Encontro> e = new ArrayList<>();
        for(int i: this.getEmpresas().keys()) {
            for (Date d : this.getEmpresas().get(i).getEncontros().keys()) {
                if (this.getEmpresas().get(i).getEncontros().get(d).getParticipantes().size() > n)
                    e.add(getEmpresas().get(i).getEncontros().get(d));
            }
        }
        for(Encontro en: e){
            System.out.println("Encontro na data: " + en.getData_inicio() + " tem mais de " + n + " pessoa(s).");
        }
        return e;
    }

    public SeparateChainingHashST<Integer, Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(SeparateChainingHashST<Integer, Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public void setPontos(SeparateChainingHashST<Integer, PontoIntermedio> pontos){this.pontosInt = pontos;}

    public SeparateChainingHashST<Integer, PontoIntermedio> getPontos(){return pontosInt;}

    public SeparateChainingHashST<Integer, Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(SeparateChainingHashST<Integer, Empresa> empresas) {
        this.empresas = empresas;
    }

    public ArrayList<Pessoa> getPessoas_removidas() {
        return pessoas_removidas;
    }

    public void setPessoas_removidas(ArrayList<Pessoa> pessoas_removidas) {
        this.pessoas_removidas = pessoas_removidas;
    }

    public ArrayList<Empresa> getEmpresas_removidas() {
        return empresas_removidas;
    }

    public void setEmpresas_removidas(ArrayList<Empresa> empresas_removidas) {
        this.empresas_removidas = empresas_removidas;
    }

    public ArrayList<GraphNode> getEmpresas_encontros() {
        return empresas_encontros;
    }

    public void setEncontros(SeparateChainingHashST<Date, Encontro> encontros) {
        this.encontros = encontros;
    }

    public SeparateChainingHashST<Date, Encontro> getEncontros() {
        return encontros;
    }

    public void preencher_encontros(){
        for(int i:this.empresas.keys()) {
            for (Date d:this.empresas.get(i).getEncontros().keys()) {
                this.encontros.put(d, this.empresas.get(i).getEncontros().get(d));
            }
        }
    }

    public ArrayList<GraphNode> getEmpresas_pessoas() {
        return empresas_pessoas;
    }

    public ArrayList<GraphNode> getEncontros_pessoas() {
        return encontros_pessoas;
    }

    public ArrayList<GraphNode> getPessoas_pessoas() {
        return pessoas_pessoas;
    }

    /**
     * @return void
     * dumps Network info into a File
     */
    public void dumpInfoToFile() {
        File f1 = new File("C:\\Users\\joaor\\IdeaProjects\\LP_AED2\\src\\lp2\\dumpInfoEmp.txt");
        File f2 = new File("C:\\Users\\joaor\\IdeaProjects\\LP_AED2\\src\\lp2\\dumpInfoPes.txt");
        FileWriter fw1 = null; FileWriter fw2 = null;
        PrintWriter pw1 = null; PrintWriter pw2 = null;
        if (f1 != null){
            try {
                fw1 = new FileWriter(f1);
                pw1 = new PrintWriter(fw1);
                fw2 = new FileWriter(f2);
                pw2 = new PrintWriter(fw2);

                for (int id : empresas.keys()) pw1.println(empresas.get(id));
                for (int id : pessoas.keys()) pw2.println(pessoas.get(id));

                pw1.flush();
                pw2.flush();

            } catch (Exception e) {

            } finally {
                try {
                    if (pw1 != null) pw1.close();
                    if (fw1 != null) fw1.close();
                    if (pw2 != null) pw2.close();
                    if (fw2 != null) fw2.close();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * @ r a given Network
     * @return void
     * reads Network info from a File
     * (unfinished)


    public void readFromFile(Rede r){
    File f1 = null; File f2 = null;
    FileReader fr1 = null; FileReader fr2 = null;
    BufferedReader br1 = null; BufferedReader br2 = null;
    ArrayList<Pessoa> pessoas = new ArrayList<>();
    ArrayList<Empresa> empresas = new ArrayList<>();

    try{
    f1 = new File("C:\\Users\\joaor\\IdeaProjects\\LP_AED2\\src\\lp2\\dumpInfoEmp.txt\\");
    f2 = new File("C:\\Users\\joaor\\IdeaProjects\\LP_AED2\\src\\lp2\\dumpInfoPes.txt\\");
    fr1 = new FileReader(f1); fr2 = new FileReader(f2);
    br1 = new BufferedReader(fr1); br2 = new BufferedReader(fr2);

    String line = "", name ="", id ="", morada = "";
    int element = 0, siz1 = 0, siz2 = 0, siz3 = 0;
    ArrayList<Integer> colabs_id = new ArrayList<Integer>();
    ArrayList<String> especs = new ArrayList<String>();
    ArrayList<String> events = new ArrayList<String>();

    while((line=br1.readLine()) != null){
    StringTokenizer st1 = new StringTokenizer(line,",");
    while(st1.hasMoreTokens()){
    String token = st1.nextToken();
    switch (element){
    case 0: name = token; element++; break;
    case 1: id = token; element++; break;
    case 2: morada = token; element++; break;
    case 3: siz1 = Integer.parseInt(token);
    for(int i = 0; i < siz1; i++){
    token = st1.nextToken();
    colabs_id.add(Integer.parseInt(token));
    }
    element++;
    break;
    case 4: siz2 = Integer.parseInt(token);
    for(int i = 0; i < siz2; i++){
    token = st1.nextToken();
    especs.add(token);
    }
    element++;
    break;
    case 5: siz3 = Integer.parseInt(token);
    for(int i = 0; i < siz3; i++){
    token = st1.nextToken();
    events.add(token);
    }
    element++;
    break;
    }
    Empresa e = new Empresa(name,Integer.parseInt(id),morada,r);
    for(String s: especs){
    e.getEspecializacoes().put(s,new Especializacao(s));
    }
    for(String s: events){
    StringTokenizer st3 = new StringTokenizer(s,"/");
    int min = Integer.parseInt(st3.nextToken());
    int hora = Integer.parseInt(st3.nextToken());
    int dia = Integer.parseInt(st3.nextToken());
    int mes = Integer.parseInt(st3.nextToken());
    int ano = Integer.parseInt(st3.nextToken());
    e.marcar_encontro(new Encontro(new Date(dia,mes,ano,hora,min)));
    }
    empresas.add(e);
    }
    }
    element = 0;
    String cargo = "", nome_empresa ="", espec = "";
    ArrayList<String> historico = new ArrayList<>();
    ArrayList<String> interesses = new ArrayList<>();
    int salario = 0;

    while((line=br2.readLine()) != null){
    StringTokenizer st1 = new StringTokenizer(line,",");
    while(st1.hasMoreTokens()){
    String token = st1.nextToken();
    switch (element){
    case 0: name = token; element++; break;
    case 1: id = token; element++; break;
    case 2: morada = token; element++; break;
    case 3: cargo = token; element++; break;
    case 4: salario = Integer.parseInt(token); element++; break;
    case 5: nome_empresa = token; element++; break;
    case 6: espec = token; element++; break;
    case 7: siz1 = Integer.parseInt(token);
    for (int i = 0; i < siz1 ; i++){
    token = st1.nextToken();
    historico.add(token);
    }
    element++;
    break;
    case 8: siz2 = Integer.parseInt(token);
    for(int i = 0; i < siz2; i++){
    token = st1.nextToken();
    interesses.add(token);
    }
    element++;
    break;
    case 9: siz3 = Integer.parseInt(token);
    for(int i = 0; i < siz3; i++){
    token = st1.nextToken();
    events.add(token);
    }
    element++;
    break;
    }
    Pessoa p = new Pessoa(name,Integer.parseInt(id),morada,espec,r); int i = 0;
    for(String s: historico){
    token = st1.nextToken();
    p.getHistorico().getEmpresas().put(i,new Empresa(token,0,"",r));
    i++;
    }
    for(String s: interesses){
    token = st1.nextToken();
    p.getAreas_interese().add(token);
    }
    for(String s: events){
    StringTokenizer st3 = new StringTokenizer(s,"/");
    int min = Integer.parseInt(st3.nextToken());
    int hora = Integer.parseInt(st3.nextToken());
    int dia = Integer.parseInt(st3.nextToken());
    int mes = Integer.parseInt(st3.nextToken());
    int ano = Integer.parseInt(st3.nextToken());
    Empresa aux = new Empresa("",0,"",r);
    Encontro aux2 = new Encontro(new Date(dia,mes,ano,hora,min));
    aux.marcar_encontro(aux2);
    aux.confirmar_presenca(aux2,p);
    }
    pessoas.add(p);
    }
    }

    } catch (Exception e){

    } finally{
    try {
    if (fr1 != null) fr1.close();
    if (br1 != null) br1.close();
    if (fr2 != null) fr2.close();
    if (br2 != null) br2.close();
    } catch (Exception e) {

    }

    }
    }
     */
    public EdgeWeightedDigraph getEp() {
        return ep;
    }

    public EdgeWeightedDigraph getPp() {
        return pp;
    }

    public EdgeWeightedGraph getEnc() {
        return enc;
    }
}
