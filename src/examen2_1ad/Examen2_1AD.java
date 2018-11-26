/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2_1ad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author oracle
 */
public class Examen2_1AD {

    /**
     * @param args the command line arguments
     */
    public static Connection conexion = null;

    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void closeConexion() throws SQLException {
        conexion.close();
    }

    public static void main(String[] args) throws IOException, XMLStreamException, SQLException {
        // TODO code application logic here
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader("platos.xml"));
        String codp = null;
        String nome = null;
        String codc;
        String peso;
        String graxa;
        int parcial;
        int total = 0;
        File ficheiro=new File("totalgraxas.txt");
        while (reader.hasNext()) {
            Platos plato=new Platos();
            reader.next();
            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                String a = reader.getLocalName();
                if (a == "Plato") {

                    codp = reader.getAttributeValue(0);
                    if(total!=0){
                        System.out.println("TOTAL DE GRAXAS DO PLATO "+total);
                        total=0;
                    }
                    System.out.println("CODIGO DO PLATO: " + codp);

                }
                if (a == "nomep") {
                    nome = reader.getElementText();
                    System.out.println("nome do plato: " + nome);

                    conexion = getConexion();

                    String liña = "";

                    FileReader fiRe = new FileReader("composicion.txt");
                    BufferedReader buRe = new BufferedReader(fiRe);
                    while ((liña = buRe.readLine()) != null) {
                        String[] atributos = liña.split("#");

                        if (atributos[0].equalsIgnoreCase(codp)) {
                            codc = atributos[1];
                            peso = atributos[2];
                            PreparedStatement sta = conexion.prepareStatement("select componentes.graxa from componentes where componentes.codc=" + "'" + codc + "'");

                            ResultSet resultado = sta.executeQuery();
                            while (resultado.next()) { //en cada resultado que obtengamos de la consulta
                                graxa = resultado.getString("graxa");

                                System.out.println("codigo do componente: " + codc + " -> graxa por cada 100 gr=" + graxa);
                                System.out.println("peso: " + peso);
                                parcial=(Integer.parseInt(graxa)*Integer.parseInt(peso)/100);
                                System.out.println("total de graxa do compoñente="+parcial+"\n");
                                total=total+parcial;
                                
                            }
                           

                        } 
                        
                    }   
                    
                       
                        buRe.close();
                }
                  plato.setCodigo(codp);
                  plato.setNome(nome);
                  plato.setGraxaTotal(total);

                  FileOutputStream filo = new FileOutputStream(ficheiro);
            ObjectOutputStream obo=new ObjectOutputStream(filo);
            obo.writeObject(plato); // escribimos o obxeto a serializar
             obo.close(); // cerramos o fluxo
            }
            
            reader.close();

        }
        System.out.println("TOTAL DE GRAXAS DO PLATO "+total); 
    }

}
