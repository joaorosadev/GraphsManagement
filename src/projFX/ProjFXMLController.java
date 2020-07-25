package projFX;

import edu.princeton.cs.algs4.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import proj.*;
import proj.Date;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjFXMLController implements Initializable {

    public Group graphPPGroup;
    public Group graphEPGroup;
    public Group graphEncGroup;
    public Group subtitle;
    public Group subtitle2;
    public Group subtitle3;
    private double radius =15.0;
    @FXML
    private javafx.scene.control.TextField isBipartite1;
    @FXML
    private javafx.scene.control.TextField isBipartite2;
    @FXML
    private javafx.scene.control.TextField sourceID;
    @FXML
    private javafx.scene.control.TextField destinationID;
    @FXML
    private javafx.scene.control.TextField pessoaPP;
    @FXML
    private javafx.scene.control.TextField pessoaEP;
    @FXML
    private javafx.scene.control.TextField empresaEP;
    @FXML
    private javafx.scene.control.TextField pessoaEnc;
    @FXML
    private javafx.scene.control.TextField empresaEnc;
    @FXML
    private javafx.scene.control.TextField encontroEnc;
    @FXML
    private javafx.scene.control.TextField pontoEnc;
    @FXML
    private javafx.scene.control.TextField pessoaName;
    @FXML
    private javafx.scene.control.TextField pessoaID;
    @FXML
    private javafx.scene.control.TextField pessoaMorada;
    @FXML
    private javafx.scene.control.TextField pessoaEspecializacao;
    @FXML
    private javafx.scene.control.TextField pessoaExperiencia;
    @FXML
    private javafx.scene.control.TextField pessoaX;
    @FXML
    private javafx.scene.control.TextField pessoaY;
    @FXML
    private javafx.scene.layout.VBox addNodeVB;
    @FXML
    private javafx.scene.layout.VBox addEdgeVB;
    @FXML
    private javafx.scene.control.TextField fromID;
    @FXML
    private javafx.scene.control.TextField toID;
    @FXML
    private javafx.scene.layout.HBox bipHB1;
    @FXML
    private javafx.scene.layout.HBox bipHB2;
    @FXML
    private javafx.scene.control.TextField connectivityAnswer;
    @FXML
    private javafx.scene.control.Button removeEdge;
    @FXML
    private javafx.scene.control.Button addEdge;
    @FXML
    private javafx.scene.control.Button removeNode;
    @FXML
    private javafx.scene.control.Button addNodeButton;
    @FXML
    private javafx.scene.control.TextField xEdit;
    @FXML
    private javafx.scene.control.TextField yEdit;
    @FXML
    private javafx.scene.control.TextField idEdit;
    @FXML
    private javafx.scene.control.TextField nameEdit;
    @FXML
    private javafx.scene.control.TextField moradaEdit;
    @FXML
    private javafx.scene.control.TextField experienciaEdit;
    @FXML
    private javafx.scene.control.TextField especializacaoEdit;


    private String path1 = ".//data//Empresas.txt";
    private String path2 = ".//data//PontosIntermedios.txt";
    private String path3 = ".//data//Pessoas.txt";
    private String pathTo1 = ".//data//EmpresasDump.txt";
    private String pathTo2 = ".//data//PontosIntDump.txt";
    private String pathTo3= ".//data//PessoasDump.txt";
    private String pathBin1 = ".//data//Empresas.bin";
    private String pathBin2 = ".//data//PontosIntermedios.bin";
    private String pathBin3 = ".//data//Especializacoes.bin";
    private String pathBin4 = ".//data//Pessoas.bin";
    private String delimiter = ";";

    private Rede rede = new Rede();

    /**
     * @return void
     * Initializes data structures read from a text file
     */
    public void handleReadFileAction(ActionEvent actionEvent) {
        try {
            readEmpresasFromFile ();
            readPontosIntFromFile ();
            readPessoasFromFile ();

            generateInfo ();
            setAllVisible();


            graphPPGroup.getChildren ().clear ();
            createGraphGroup1 (rede.getPp().V());
            createPPSubtitle(2);
            graphEPGroup.getChildren ().clear ();
            createGraphGroup2 (rede.getEp().V());
            createEPSubtitle(2);
            graphEncGroup.getChildren ().clear ();
            createGraphGroup3 (rede.getEncontros_pessoas().size());
            createEncSubtitle(4);

            for(DirectedEdge e : rede.getPp().edges()){
                //System.out.println(e.from() +" " +e.to());
                int v = e.from();
                int w = e.to();
                StackPane spv = (StackPane) graphPPGroup.getChildren ().get (v);
                StackPane spw = (StackPane) graphPPGroup.getChildren ().get (w);
                Circle cv =(Circle)spv.getChildren().get(0);
                Circle cw =(Circle)spw.getChildren ().get (0);
                Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
                graphPPGroup.getChildren ().add (line);
            }
            for(DirectedEdge e : rede.getEp().edges()){
                //System.out.println(e.from() +" " +e.to());
                int v = e.from();
                int w = e.to();
                StackPane spv = (StackPane) graphEPGroup.getChildren ().get (v);
                StackPane spw = (StackPane) graphEPGroup.getChildren ().get (w);
                Circle cv =(Circle)spv.getChildren().get(0);
                Circle cw =(Circle)spw.getChildren ().get (0);
                Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
                graphEPGroup.getChildren ().add (line);
            }
            for(Edge e : rede.getEnc().edges()){
                int v = e.either();
                int w = e.other(v);
                StackPane spv = (StackPane) graphEncGroup.getChildren ().get (v);
                StackPane spw = (StackPane) graphEncGroup.getChildren ().get (w);
                Circle cv =(Circle)spv.getChildren().get(0);
                Circle cw =(Circle)spw.getChildren ().get (0);
                Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
                graphEncGroup.getChildren ().add (line);
            }
        } catch (IOException e) {
            Logger.getLogger (ProjFXMLController.class.getName ()).log (Level.SEVERE, null, e);
        }
    }

    /**
     * @return void
     * Makes all invisible elements visible
     */
    private void setAllVisible() {
        pessoaPP.setVisible(true);
        pessoaEP.setVisible(true);
        pessoaEnc.setVisible(true);
        empresaEP.setVisible(true);
        empresaEnc.setVisible(true);
        encontroEnc.setVisible(true);
        pontoEnc.setVisible(true);
        addNodeVB.setVisible(true);
        addEdgeVB.setVisible(true);
        bipHB1.setVisible(true);
        bipHB2.setVisible(true);
        removeEdge.setVisible(true);
        removeNode.setVisible(true);
    }

    /**
     * @return void
     * Reads companies from a file
     */
    public void readEmpresasFromFile() throws IOException {
        BufferedReader br = openBufferedReader (path1);
        if (br != null) {
            String line = br.readLine ();
            line = br.readLine ();
            while (line != null) {
                String[] dFields = line.split (delimiter);
                Empresa e = new Empresa (dFields[0],Integer.parseInt(dFields[1]), dFields[2],rede,
                        Integer.parseInt (dFields[3]), Integer.parseInt (dFields[4]));
                line = br.readLine ();
            }
            br.close ();
        }
    }
    /**
     * @return void
     * Reads intermediate points from a file
     */
    public void readPontosIntFromFile() throws IOException {
        BufferedReader br = openBufferedReader (path2);
        if (br != null) {
            String line = br.readLine ();
            line = br.readLine ();
            while (line != null) {
                String[] dFields = line.split (delimiter);
                PontoIntermedio p = new PontoIntermedio (Integer.parseInt(dFields[0]),Integer.parseInt(dFields[1]), rede);
                line = br.readLine ();
            }
            br.close ();
        }
    }

    /**
     * @return void
     * Reads people from a file
     */
    public void readPessoasFromFile() throws IOException {
        //BufferedReader br = openBufferedReader (path3);
        try {
            FileReader fr = new FileReader (path3);
            BufferedReader br = new BufferedReader (fr);
            if (br != null) {
                String line = br.readLine ();
                line = br.readLine ();
                while (line != null) {
                    String[] dFields = line.split (delimiter);
                    Pessoa p = new Pessoa (dFields[0],Integer.parseInt(dFields[1]), dFields[2], dFields[3],rede,
                            Integer.parseInt (dFields[4]),Integer.parseInt(dFields[5]),Integer.parseInt(dFields[6]));
                    line = br.readLine ();
                }
                br.close ();
                fr.close();
            }

        } catch (FileNotFoundException e) {
            Logger.getLogger (ProjFXMLController.class.getName ()).log (Level.SEVERE, null, e);
        }

    }

    /**
     * @return BufferedReader
     * Utility method that returns a BufferedReader for reading operations on text files
     */
    public BufferedReader openBufferedReader(String path) {
        try {
            FileReader fr = new FileReader (path);
            BufferedReader br = new BufferedReader (fr);
            return br;
        } catch (FileNotFoundException e) {
            Logger.getLogger (ProjFXMLController.class.getName ()).log (Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * @return void
     * Draws Person-Person graph
     */
    private void createGraphGroup1(int nVertices){
        for (int i=0;i<nVertices;i++){
            Random r =new Random ();
            double posX=50+r.nextDouble ()*500;
            double posY=50+r.nextDouble ()*300;
            Circle c = new Circle (posX,posY,radius);
            c.setFill (Color.HOTPINK);
            c.setId (""+i);
            Text text=new Text(""+i);
            StackPane stack=new StackPane ();
            stack.setLayoutX (posX-radius);
            stack.setLayoutY (posY-radius);
            stack.getChildren ().addAll (c,text);
            graphPPGroup.getChildren ().add (stack);
        }
    }
    /**
     * @return void
     * Draws Person-Company graph
     */
    private void createGraphGroup2(int nVertices){
        for (int i=0;i<nVertices;i++){
            Random r =new Random ();
            double posX=50+r.nextDouble ()*500;
            double posY=50+r.nextDouble ()*300;
            Circle c = new Circle (posX,posY,radius);
            if(rede.getEmpresas_pessoas().get(i).isPessoa())
                c.setFill (Color.HOTPINK);
            if(rede.getEmpresas_pessoas().get(i).isEmpresa())
                c.setFill (Color.BLUE);
            c.setId (""+i);
            Text text=new Text(""+i);
            StackPane stack=new StackPane ();
            stack.setLayoutX (posX-radius);
            stack.setLayoutY (posY-radius);
            stack.getChildren ().addAll (c,text);
            graphEPGroup.getChildren ().add (stack);
        }
    }

    /**
     * @return void
     * Draws meetings (localizations) graph
     */
    private void createGraphGroup3(int nVertices){
        for (int i=0;i<nVertices;i++){
            System.out.println(i);
            Circle c = new Circle (rede.getEncontros_pessoas2().get(i).getX()+200,rede.getEncontros_pessoas2().get(i).getY()+200,radius);
            double posX=c.getCenterX();
            double posY=c.getCenterY();
            if(rede.getEncontros_pessoas().get(i).isPessoa())
                c.setFill (Color.HOTPINK);
            if(rede.getEncontros_pessoas().get(i).isEmpresa())
                c.setFill (Color.BLUE);
            if(rede.getEncontros_pessoas().get(i).isEncontro())
                c.setFill (Color.GREEN);
            if(rede.getEncontros_pessoas().get(i).isPontoIntermedio())
                c.setFill (Color.RED);
            c.setId (""+i);
            Text text=new Text(""+i);
            StackPane stack=new StackPane ();
            stack.setLayoutX (posX-radius);
            stack.setLayoutY (posY-radius);
            stack.getChildren ().addAll (c,text);
            graphEncGroup.getChildren ().add (stack);
        }
    }

    /**
     * @return void
     * Draws Person-Person subtitle
     */
    private void createPPSubtitle(int nVertices){
            double posX=550;
            double posY=500;

            Circle c = new Circle (posX,posY,radius-2);
            c.setFill (Color.HOTPINK);
            //c.setFill (Color.BLUE);
            c.setId ("");
            Text text=new Text("");
            StackPane stack=new StackPane ();
            stack.setLayoutX (posX+15-radius);
            stack.setLayoutY (posY-radius);
            stack.getChildren ().addAll (c,text);
            subtitle.getChildren ().add (stack);
    }

    /**
     * @return void
     * Draws Person-Company subtitle
     */
    private void createEPSubtitle(int nVertices){
        for (int i=0;i<2;i++){
            double posX=550;
            double posY=500;
            double posX1=550;
            double posY1=535;

            Circle c = new Circle (posX,posY,radius-2);
            c.setFill (Color.HOTPINK);
            //c.setFill (Color.BLUE);
            c.setId (""+i);
            Text text=new Text("");
            StackPane stack=new StackPane ();
            stack.setLayoutX (posX+15-radius);
            stack.setLayoutY (posY-radius);
            stack.getChildren ().addAll (c,text);
            subtitle2.getChildren ().add (stack);

            Circle c1 = new Circle (posX1,posY1,radius-2);
            c1.setFill (Color.BLUE);
            c1.setId (""+i);
            Text text1=new Text("");
            StackPane stack1=new StackPane ();
            stack1.setLayoutX (posX1+15-radius);
            stack1.setLayoutY (posY1-radius);
            stack1.getChildren ().addAll (c1,text1);
            subtitle2.getChildren ().add (stack1);
        }
    }

    /**
     * @return void
     * Draws meetings (localization) subtitle
     */
    private void createEncSubtitle(int nVertices){
        for (int i=0;i<4;i++){
            double posX=550, posX1=550, posX2= 550, posX3 = 550;
            double posY=500, posY1=535, posY2= 570, posY3 = 605;

            Circle c = new Circle (posX,posY,radius-2);
            c.setFill (Color.HOTPINK);
            //c.setFill (Color.BLUE);
            c.setId (""+i);
            Text text=new Text("");
            StackPane stack=new StackPane ();
            stack.setLayoutX (posX+15-radius);
            stack.setLayoutY (posY-radius);
            stack.getChildren ().addAll (c,text);
            subtitle3.getChildren ().add (stack);

            Circle c1 = new Circle (posX1,posY1,radius-2);
            c1.setFill (Color.BLUE);
            c1.setId (""+i);
            Text text1=new Text("");
            StackPane stack1=new StackPane ();
            stack1.setLayoutX (posX1+15-radius);
            stack1.setLayoutY (posY1-radius);
            stack1.getChildren ().addAll (c1,text1);
            subtitle3.getChildren ().add (stack1);

            Circle c2 = new Circle (posX2,posY2,radius-2);
            c2.setFill (Color.GREEN);
            c2.setId (""+i);
            Text text2=new Text("");
            StackPane stack2=new StackPane ();
            stack2.setLayoutX (posX2+15-radius);
            stack2.setLayoutY (posY2-radius);
            stack2.getChildren ().addAll (c2,text2);
            subtitle3.getChildren ().add (stack2);

            Circle c3 = new Circle (posX3,posY3,radius-2);
            c3.setFill (Color.RED);
            c3.setId (""+i);
            Text text3=new Text("");
            StackPane stack3=new StackPane ();
            stack3.setLayoutX (posX3+15-radius);
            stack3.setLayoutY (posY3-radius);
            stack3.getChildren ().addAll (c3,text3);
            subtitle3.getChildren ().add (stack3);
        }
    }

    /**
     * @return void
     * Serves to generate some info and test methods
     */
    public void generateInfo(){
        Especializacao cp = new Especializacao("Computadores");
        Especializacao tm = new Especializacao("Telemoveis");
        Empresa google = rede.getEmpresas().get(0);
        Empresa sony = rede.getEmpresas().get(1);
        Pessoa joao = rede.getPessoas2().get(0);
        Pessoa pedro = rede.getPessoas2().get(1);
        Pessoa joana = rede.getPessoas2().get(2);
//        Pessoa ricardo = rede.getPessoas2().get(3);
        PontoIntermedio p1 = rede.getPontos2().get(0);
        PontoIntermedio p2 = rede.getPontos2().get(1);
        PontoIntermedio p3 = rede.getPontos2().get(2);

        google.adicionar_especializacao(cp);
        sony.adicionar_especializacao(cp);   // Sony - Computadores
        sony.adicionar_especializacao(tm);   // Sony -Telemoveis

        System.out.println(rede.getPessoas().size());
        System.out.println(rede.getEmpresas().get(0));

        joao.inserir_interesse("IA");
        joao.inserir_interesse("Algoritmos");
        pedro.inserir_interesse("Computacao Movel");
        pedro.inserir_interesse("Hardware");
        joana.inserir_interesse("Tecnologia");
        joana.inserir_interesse("Sensores");

        joao.getLigacoes().getSeguir_pessoas().put(joana.getId_pp(),joana);
        pedro.getLigacoes().getSeguir_pessoas().put(joao.getId_pp(),joao);
        joana.getLigacoes().getSeguir_pessoas().put(joao.getId_g(),joao);
        pedro.getLigacoes().getSeguir_pessoas().put(joana.getId_g(),joana);

        google.getLigacoes().getSeguir_pessoas().put(joao.getId_ep(),joao);  // 0,2
        google.getLigacoes().getSeguir_pessoas().put(joana.getId_ep(),joana);// 0,4
        sony.getLigacoes().getSeguir_pessoas().put(pedro.getId_ep(),pedro);  //1,3
        joao.getLigacoes().getSeguir_empresas().put(google.getId_ep(),google); // 2,0
        pedro.getLigacoes().getSeguir_empresas().put(sony.getId_ep(),sony);    //3,1
        joana.getLigacoes().getSeguir_empresas().put(sony.getId_ep(),sony);    //3,1

        google.contratar_colaborador(joao,"Tecnico", 2000);
        sony.contratar_colaborador(pedro,"Engenheiro",3000);
        //sony.contratar_colaborador(joana,"Chefe de vendas",2500);

        Encontro e1 = new Encontro(new Date(10,4,2019,11,0),170,-70,rede);
        Encontro e2 = new Encontro(new Date(2,7,2019,10,30),-100,80,rede);
        google.marcar_encontro(e1);
        sony.marcar_encontro(e2);
        google.confirmar_presenca(e1,joao);

        p1.getLigacoes().getSeguir_empresas().put(sony.getId_enc(),sony);     //2,1 --.
        p1.getLigacoes().getSeguir_pessoas().put(joao.getId_enc(),joao);      //2,5
        p1.getLigacoes().getLigar_a_encontros().put(e2.getId_enc(),e2);       //2,9 --.--
        p1.getLigacoes().getSeguir_pontos().put(p2.getId_enc(),p2);           //2,3 --.--
        p2.getLigacoes().getSeguir_pontos().put(p3.getId_enc(),p3);           //3,4 --.
        p2.getLigacoes().getSeguir_pessoas().put(pedro.getId_enc(),pedro);    //3,6
        p3.getLigacoes().getSeguir_pessoas().put(joana.getId_enc(),joana);    //4,7
        p3.getLigacoes().getSeguir_empresas().put(google.getId_enc(),google); //4,0 --.
        p3.getLigacoes().getLigar_a_encontros().put(e1.getId_enc(),e1);       //4,8 --.--

        rede.create_graphs();
        rede.preencher_grafo_pp2();
        rede.preencer_grapo_ep2();
        rede.preencher_grafo_enc2();
        //rede.pessoas_diretamente_ligadas_a_encontro(e1);
        ArrayList<Pessoa> pess = rede.pessoas_em_encontro(e1);
        for(Pessoa p: pess)
            System.out.println(p.getNome());
        ArrayList<Pessoa> pess2 = rede.pessoas_seguem_empresa(google);
        for(Pessoa p: pess2)
            System.out.println(p.getNome());
        /*ArrayList<Pessoa> pess3 = rede.pessoas_com_especializacao_sem_empresa(cp);
        for(Pessoa p: pess3)
            System.out.println(p.getNome());
        */
        rede.facilitar_relacoes(google,joana);
        ArrayList<Pessoa> pess4 = rede.desempregado_com_exp_e_esp(tm);
        for(Pessoa p: pess4)
            System.out.println(p.getNome());
        rede.encontros_num_intevalo(new Date(1,6,2019,11,0),new Date(3,12,2019,11,0));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * @return void
     * Handles Save File menu button click
     */
    public void handleSaveFileAction(ActionEvent actionEvent) {
        writeEmpresasToFile();
        writePontosIntToFile();
        writePessoasToFile();
    }

    /**
     * @return BufferedWriter
     * Return BufferedWriter to help perform writing operations
     */
    public BufferedWriter openBufferedWriter(String pathTo1) {
        try {
            FileWriter fw = new FileWriter (pathTo1);
            BufferedWriter bw = new BufferedWriter (fw);
            return bw;
        } catch (Exception e) {
            Logger.getLogger (ProjFXMLController.class.getName ()).log (Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * @return void
     * Writes Companies to text file
     */
    public void writeEmpresasToFile(){
        BufferedWriter bw = openBufferedWriter(pathTo1);
        try {
            if (bw != null) {
                bw.write("name;id;morada;x;y\n");
                for(int i = 0; i < rede.getEmpresas().size(); i++){
                    bw.write(rede.getEmpresas().get(i).getNome()+";");
                    bw.write(rede.getEmpresas().get(i).getId()+";");
                    bw.write(rede.getEmpresas().get(i).getMorada()+";");
                    bw.write(rede.getEmpresas().get(i).getX()+";");
                    bw.write(rede.getEmpresas().get(i).getY()+"\n");
                }
                bw.close();
            }
        }catch (IOException ex){

        }
    }

    /**
     * @return void
     * Writes Intermediate Points to text file
     */
    public void writePontosIntToFile(){
        BufferedWriter bw = openBufferedWriter(pathTo2);
        try {
            if (bw != null) {
                bw.write("x;y\n");
                for(int i = 0; i < rede.getPontos2().size(); i++){
                    bw.write(rede.getPontos2().get(i).getX()+";");
                    bw.write(rede.getPontos2().get(i).getY()+"\n");
                }
                bw.close();
            }
        }catch (IOException ex){

        }
    }

    /**
     * @return void
     * Writes People to text file
     */
    public void writePessoasToFile(){
        BufferedWriter bw = openBufferedWriter(pathTo3);
        try {
            if (bw != null) {
                bw.write("nome;id;morada;especializacao;experiencia\n");
                for(int i = 0; i < rede.getPessoas2().size(); i++){
                    bw.write(rede.getPessoas2().get(i).getNome()+";");
                    bw.write(rede.getPessoas2().get(i).getId()+";");
                    bw.write(rede.getPessoas2().get(i).getMorada() + ";");
                    bw.write(rede.getPessoas2().get(i).getEspecializacao()+";");
                    bw.write(rede.getPessoas2().get(i).getExperiencia()+";");
                    bw.write(rede.getPessoas2().get(i).getX()+";");
                    bw.write(rede.getPessoas2().get(i).getY()+"\n");
                }
                bw.close();
            }
        }catch (IOException ex){

        }
    }

    /**
     * @return void
     * Handle Close menu button click
     */
    public void handleExitFileAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * @return void
     * Handle isBipartite1 button click
     */
    public void handleIsBipartite1(ActionEvent actionEvent){
        boolean b = rede.bipartidoPP();
        if(b)
            isBipartite1.setText("Yes");
        else isBipartite1.setText("No");
    }

    /**
     * @return void
     * Handle isBipartite2 button click
     */
    public void handleIsBipartite2(ActionEvent actionEvent){
        boolean b = rede.bipartidoEP();
        if(b)
            isBipartite2.setText("Yes");
        else isBipartite2.setText("No");
    }

    /**
     * @return void
     * Handle check path button click
     */
    public void handlePathAction(ActionEvent actionEvent ){
        graphEncGroup.getChildren ().clear ();
        createGraphGroup3 (rede.getEncontros_pessoas().size());
        int v = Integer.parseInt(sourceID.getText());
        int w = Integer.parseInt(destinationID.getText());
        DijkstraUndirectedSP d = rede.path_to1(rede.getEncontros_pessoas().get(v),rede.getEncontros_pessoas().get(w));
        for(Edge e: d.pathTo(w)){
            int x = e.either();
            int y = e.other(x);
            StackPane spv = (StackPane) graphEncGroup.getChildren ().get (x);
            StackPane spw = (StackPane) graphEncGroup.getChildren ().get (y);
            Circle cv =(Circle)spv.getChildren().get(0);
            Circle cw =(Circle)spw.getChildren ().get (0);
            Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
            graphEncGroup.getChildren ().add (line);
        }
    }

    /**
     * @return void
     * Handle Add Node button click
     */
    public void handleAddNode(){
        String nome = pessoaName.getText(), morada = pessoaMorada.getText(),especializacao = pessoaEspecializacao.getText();
        int id = Integer.parseInt(pessoaID.getText()), experiencia = Integer.parseInt(pessoaExperiencia.getText());
        int x = Integer.parseInt(pessoaX.getText()),y = Integer.parseInt(pessoaY.getText());
        rede.getPessoas2().add(new Pessoa(nome,id,morada,especializacao,rede,experiencia,x,y));
        updatePPGraph();
    }

    /**
     * @return void
     * Updates People-People graph with new Node
     */
    public void updatePPGraph(){
        Random r =new Random ();
        double posX=50+r.nextDouble ()*500;
        double posY=50+r.nextDouble ()*300;
        Circle c = new Circle (posX,posY,radius);
        c.setFill (Color.HOTPINK);
        Pessoa p = rede.getPessoas2().get(rede.getPessoas2().size()-1);
        c.setId (""+ p.getId());
        Text text=new Text(""+p.getId());
        StackPane stack=new StackPane ();
        stack.setLayoutX (posX-radius);
        stack.setLayoutY (posY-radius);
        stack.getChildren ().addAll (c,text);
        graphPPGroup.getChildren ().add (stack);
    }

    /**
     * @return void
     * Handle Add Edge button click
     */
    public void handleAddEdge(){
        int v = Integer.parseInt(fromID.getText());
        int w = Integer.parseInt((toID.getText()));
        DirectedEdge edge = new DirectedEdge(v,w,0.0);
        rede.getPessoas2().get(v).getLigacoes().getSeguir_pessoas().put(w,rede.getPessoas2().get(w));
        rede.create_graphs();
        rede.preencher_grafo_pp2();

        graphPPGroup.getChildren ().clear ();
        createGraphGroup1 (rede.getPp().V());

        for(DirectedEdge e : rede.getPp().edges()){
            //System.out.println(e.from() +" " +e.to());
            v = e.from();
            w = e.to();
            StackPane spv = (StackPane) graphPPGroup.getChildren ().get (v);
            StackPane spw = (StackPane) graphPPGroup.getChildren ().get (w);
            Circle cv =(Circle)spv.getChildren().get(0);
            Circle cw =(Circle)spw.getChildren ().get (0);
            Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
            graphPPGroup.getChildren ().add (line);
        }
    }

    /**
     * @return void
     * Handle About menu button click
     */
    public void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("This project was developed by:");
        alert.setContentText("João Rosa and João Pinto");

        alert.showAndWait();
    }

    /**
     * @return void
     * Handle is connected button click
     */
    public void handleConnectivity(){
        boolean isConnected = true;
        for(int i=0; i<rede.getPessoas2().size();i++){
            DijkstraSP d = new DijkstraSP(rede.getPp(),rede.getPessoas2().get(i).getId_pp());
            for(int j = 0; j<rede.getPessoas2().size();j++){
                if(j == i) break;
                if(!d.hasPathTo(rede.getPessoas2().get(j).getId_pp())){
                    isConnected = false;
                }
            }
        }
        if(isConnected){
            connectivityAnswer.setText("Yes");
        } else connectivityAnswer.setText("No");
    }

    /**
     * @return void
     * Handle Remove Edge button click
     */
    public void handleRemoveEdge(){
        int v = Integer.parseInt(fromID.getText());
        int w = Integer.parseInt(toID.getText());
        rede.getPessoas2().get(v).getLigacoes().getSeguir_pessoas().delete(w);
        rede.create_graphs();
        rede.preencher_grafo_pp2();

        graphPPGroup.getChildren ().clear ();
        createGraphGroup1 (rede.getPp().V());

        for(DirectedEdge e : rede.getPp().edges()){
            v = e.from();
            w = e.to();
            StackPane spv = (StackPane) graphPPGroup.getChildren ().get (v);
            StackPane spw = (StackPane) graphPPGroup.getChildren ().get (w);
            Circle cv =(Circle)spv.getChildren().get(0);
            Circle cw =(Circle)spw.getChildren ().get (0);
            Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
            graphPPGroup.getChildren ().add (line);
        }
    }

    /**
     * @return void
     * Handle Remove Node button click
     */
    public void handleRemoveNode(){
        int v = Integer.parseInt(pessoaID.getText()),w;
        rede.getPessoas2().remove(v);

        rede.create_graphs();
        rede.preencher_grafo_pp2();

        graphPPGroup.getChildren ().clear ();
        createGraphGroup1 (rede.getPp().V());

        for(DirectedEdge e : rede.getPp().edges()){
            v = e.from();
            w = e.to();
            StackPane spv = (StackPane) graphPPGroup.getChildren ().get (v);
            StackPane spw = (StackPane) graphPPGroup.getChildren ().get (w);
            Circle cv =(Circle)spv.getChildren().get(0);
            Circle cw =(Circle)spw.getChildren ().get (0);
            Line line = new Line(cv.getCenterX (),cv.getCenterY (),cw.getCenterX (),cw.getCenterY ());
            graphPPGroup.getChildren ().add (line);
        }
    }

    /**
     * @return void
     * Handle Key Released in From TextField
     */
    public void handleKeyReleasedInFrom(){
        if(!fromID.getText().isEmpty() && !toID.getText().isEmpty()){
            removeEdge.setDisable(false);
            addEdge.setDisable(false);
        }else{
            removeEdge.setDisable(true);
            addEdge.setDisable(true);
        }
    }

    /**
     * @return void
     * Handle Key Released in To TextField
     */
    public void handleKeyReleasedInTo(){
        if(!fromID.getText().isEmpty() && !toID.getText().isEmpty()){
            removeEdge.setDisable(false);
            addEdge.setDisable(false);
        }else{
            removeEdge.setDisable(true);
            addEdge.setDisable(true);
        }
    }

    /**
     * @return void
     * Handle Key Released in X TextField
     */
    public void handleKeyReleasedX(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
           && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
    }

    /**
     * @return void
     * Handle Key Released in Y TextField
     */
    public void handleKeyReleasedY(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
                && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
    }
    /**
     * @return void
     * Handle Key Released in ID TextField
     */

    public void handleKeyReleasedID(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
                && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
        if(!pessoaID.getText().isEmpty()){
            removeNode.setDisable(false);
        }else removeNode.setDisable(true);
    }

    /**
     * @return void
     * Handle Key Released in Name TextField
     */
    public void handleKeyReleasedName(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
                && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
    }

    /**
     * @return void
     * Handle Key Released in Morada TextField
     */
    public void handleKeyReleasedMorada(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
                && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
    }

    /**
     * @return void
     * Handle Key Released in Experiencia TextField
     */
    public void handleKeyReleasedExperiencia(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
                && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
    }

    /**
     * @return void
     * Handle Key Released in Especializacao TextField
     */
    public void handleKeyReleasedEspecializacao(){
        if(!pessoaX.getText().isEmpty() && !pessoaY.getText().isEmpty() && !pessoaID.getText().isEmpty()
                && !pessoaMorada.getText().isEmpty() && !pessoaEspecializacao.getText().isEmpty() && !pessoaExperiencia.getText().isEmpty()
                && !pessoaName.getText().isEmpty()){
            addNodeButton.setDisable(false);
        }
    }

    /**
     * @return void
     * Handle SaveBinFile meny button click
     */
    public void handleSaveBinFileAction(){
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(pathBin1);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(rede);
            fos.close();
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * Handle Edit Person button click
     */
    public void handleEditPessoa(){
        int id = Integer.parseInt(idEdit.getText()),experiencia = Integer.parseInt(experienciaEdit.getText());
        String name = nameEdit.getText(), morada = nameEdit.getText(),especializacao = especializacaoEdit.getText();
        int x = Integer.parseInt(xEdit.getText());
        int y = Integer.parseInt(yEdit.getText());
        Pessoa p = new Pessoa(name,id,morada,especializacao,rede,experiencia,x,y);
        rede.getPessoas2().add(id,p);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucess");
        alert.setHeaderText("Pessoa Added with Success");
        alert.setContentText(p.getNome() +"        id:" + p.getId());

        alert.showAndWait();
    }
}
