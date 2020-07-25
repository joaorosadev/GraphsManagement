package proj;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Edge;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.*;

/**
 *
 * @author Joao Rosa e Joao Pinto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test_inits();
    }

    /**
     * @return void
     * method testing
     */
    private static void test_inits() {

        LocalDateTime d = LocalDateTime.now();
        LocalDate d2 = LocalDate.now();
        LocalTime d3 = LocalTime.now();
        DayOfWeek d4 = DayOfWeek.FRIDAY;
        System.out.println(d);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);

        Rede r = new Rede();
        Empresa google = new Empresa("Google",0,"Rua da Google",r,10,15);
        Empresa sony = new Empresa("Sony",1,"Rua da Sony",r,10,-5);
        PontoIntermedio p1 = new PontoIntermedio(13,2,r);
        PontoIntermedio p2 = new PontoIntermedio(-1,4,r);
        PontoIntermedio p3 = new PontoIntermedio(5,5,r);

        Especializacao computadores = new Especializacao("Computadores");
        Especializacao telemoveis = new Especializacao("Telemoveis");

        google.adicionar_especializacao(computadores);
        sony.adicionar_especializacao(computadores);
        sony.adicionar_especializacao(telemoveis);

        /*Pessoa joao = new Pessoa("Joao",0,"Rua do Joao","Computadores",r,4);
        Pessoa pedro = new Pessoa("Pedro",1,"Rua do Pedro","Telemoveis",r,2);
        Pessoa joana = new Pessoa("Joana",2,"Rua da Joana","Telemoveis",r,3);
        joao.inserir_interesse("IA");joao.inserir_interesse("Algoritmos");
        pedro.inserir_interesse("Computação móvel"); pedro.inserir_interesse("Hardware");
        joana.inserir_interesse("Tecnologia"); joana.inserir_interesse("Sensores");

        joao.getLigacoes().getSeguir_pessoas().put(pedro.getId_pp(),pedro);
        joao.getLigacoes().getSeguir_pessoas().put(joana.getId_pp(),joana);
        pedro.getLigacoes().getSeguir_pessoas().put(joao.getId_pp(),joao);
        pedro.getLigacoes().getSeguir_pessoas().put(joana.getId_pp(),joana);
        joana.getLigacoes().getSeguir_pessoas().put(pedro.getId_pp(),pedro);
        joana.getLigacoes().getSeguir_pessoas().put(joao.getId_pp(),joao);

        google.getLigacoes().getSeguir_pessoas().put(joao.getId_ep(),joao);  // 0,2
        google.getLigacoes().getSeguir_pessoas().put(joana.getId_ep(),joana);// 0,4
        sony.getLigacoes().getSeguir_pessoas().put(pedro.getId_ep(),pedro);  //1,3
        joao.getLigacoes().getSeguir_empresas().put(google.getId_ep(),google); // 2,0
        pedro.getLigacoes().getSeguir_empresas().put(sony.getId_ep(),sony);    //3,1


        google.contratar_colaborador(joao,"Tecnico", 2000);
        sony.contratar_colaborador(pedro,"Engenheiro",3000);
        sony.contratar_colaborador(joana,"Chefe de vendas",2500);

        Encontro e1 = new Encontro(new Date(10,4,2019,11,0),20,-7,r);
        Encontro e2 = new Encontro(new Date(2,7,2019,10,30),13,8,r);
        google.marcar_encontro(e1);
        sony.marcar_encontro(e2);
        google.confirmar_presenca(e1,joao);
        sony.confirmar_presenca(e2,pedro);
        sony.confirmar_presenca(e2,joana);


        p1.getLigacoes().getSeguir_empresas().put(sony.getId_enc(),sony);     //2,1 --.
        p1.getLigacoes().getSeguir_pessoas().put(joao.getId_enc(),joao);      //2,5
        p1.getLigacoes().getLigar_a_encontros().put(e2.getId_enc(),e2);       //2,9 --.--
        p1.getLigacoes().getSeguir_pontos().put(p2.getId_enc(),p2);           //2,3 --.--
        p2.getLigacoes().getSeguir_pontos().put(p3.getId_enc(),p3);           //3,4 --.
        p2.getLigacoes().getSeguir_pessoas().put(pedro.getId_enc(),pedro);    //3,6
        p3.getLigacoes().getSeguir_pessoas().put(joana.getId_enc(),joana);    //4,7
        p3.getLigacoes().getSeguir_empresas().put(google.getId_enc(),google); //4,0 --.
        p3.getLigacoes().getLigar_a_encontros().put(e1.getId_enc(),e1);       //4,8 --.--


        r.create_graphs();
        r.preencher_grafo_pp2();
        r.preencer_grapo_ep2();
        r.preencher_grafo_enc2();
        System.out.println(r.getEnc().V());



        for(int i=0;i<r.getEnc().V();i++) {
            for (Edge d : r.getEnc().adj(i))
                System.out.println(d.either() + " " + d.other(d.either()) + " Weight: " + d.weight());

        }
        r.display_enc_ids();
        System.out.println();
        r.path_to2(joao,e2);

        System.out.println(r.bipartidoEP());
        System.out.println(r.bipartidoPP());

        /*
        for(int i=0;i<r.getEp().V();i++) {
            for (DirectedEdge d : r.getEp().adj(i))
                System.out.println(d.from() + " " + d.to());
        }
        */
/*
        r.pessoas_no_primeiro_trabalho();
        r.encontros_da_empresa(google);
        System.out.println("O numero de desempregados na rede é: " + r.nr_desempregados());
        sony.despedir_colaborador(joana);
        System.out.println("O numero de desempregados na rede é: " + r.nr_desempregados());
        r.encontros_mais_de_n_pessoas(1);
        google.contratar_colaborador(joana,"Chefe de vendas", 2600);

        r.dumpInfoToFile();
        r.readFromFile(r);
*/
    }
}
