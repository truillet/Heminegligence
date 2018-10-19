/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 2.1 du 29/06/2009
*/

package fr.irit.ihcs.experiment;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.lang.Integer;
import java.lang.System.*;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.*;
import info.clearthought.layout.TableLayout;
import fr.irit.ihcs.Component.*;
import fr.irit.ihcs.util.*;


class Heminegligence extends JFrame
{  

  JPanel tableau_bloc[][] = {null};
  cfg_bloc cb = new cfg_bloc();
  
  // fichier d'évenements
  public BufferedWriter output_xml;
  
  
  private static final long serialVersionUID = 0L;
  
  Heminegligence(String config_xml, String id_user, String session, String condition, String experimentateur, int timer)
  {
    // Lancement du timer si nécessaire dès la première capture de la tablette 
    
    
    cfg config = new cfg();
    // lecture des blocs
    
    Parser p = new Parser(config_xml);

    config = p.getConfig();
    
    this.start_xml(config_xml, id_user, session, condition, experimentateur);
    // on récupère les blocs de données 
    // Create a TableLayout for the frame
		Container contentPane = getContentPane();
		String where;
		
		// création en dynamique de l'ensemble des blocs --> A FAIRE
    /*double size[][] = {
			{180, 180, 180, 180, 180, 180, 180},
			{180, 180, 180,180, 180}
		};
		*/
		double size[][]={null};

    // on récupère les parties verticales et horizontales
		int h = config.get_h();
		int v = config.get_v();

    // on garde le max des 2 valeurs
    if (h<=v)  
      size = new double[2][v];
		else
      size = new double[2][h];
      
		System.out.println("h : " + config.get_h() + " v : " + config.get_v());
		
		for (int i=0;i<h;i++)
      size[0][i] = 180;
      
    for (int j=0;j<v;j++)
      size[1][j]=180;
		
		
		tableau_bloc = new JPanel [h][v];
		Bloc b = null;
		
    TableLayout layout = new TableLayout(size);
    contentPane.setLayout(layout);
    
    // i = ligne de 0 à h
    for (int i=0;i< h;i++)
      // j = colonne de 0 à v
      for (int j=0;j<v;j++)
      {
        where = i + "," + j;
        int index = i+ (j*h);
        
        cb = p.getBloc(i+ (j*h));
        b = new Bloc(config, cb , output_xml);
        
        tableau_bloc[i][j] = b;
        contentPane.add(tableau_bloc[i][j], where);
      } 

    addWindowListener ( new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            stop_xml();
            System.exit(1);
        }
    }); 
    
    setForeground(new Color(255,255,255,255)); 
     
    setBounds(config.get_x0(),config.get_y0(),h*60,v*60);
    setUndecorated(true);
    setVisible(true);
    
     // lancement du timer si nécessaire
    if (timer != 0)
    {
      Timer T =  create_timer(timer);
      T.start();
    }         
  }
       
    // GESTION DU FICHIER XML
    private void start_xml(String config_name, String id_user, String session, String condition, String experimentateur)
	  {
      Calendar cal = Calendar.getInstance();

      int Day = cal.get(Calendar.DATE);
      int Month = cal.get(Calendar.MONTH)+1;
      int Year = cal.get(Calendar.YEAR);
      
      
      /* Récupérer le nom de la configuration */
      String name = new String();
      StringTokenizer st = new StringTokenizer(config_name, "\\");
      while (st.hasMoreTokens()) {      
        // on passe au suivant
        name = st.nextToken();
      }
      // dans name, on a le nom de la config sans le chemin -> on enlève le .xml
      st = new StringTokenizer(name, ".");
      name = st.nextToken(".");    
      // System.out.println(">> dernier token " + name);
      
      
      /* Buffer de sortie du fichier xml */
      String output = "./resultats/" + id_user + "_[" + session + "-" + condition + "]_" + name + "_" + experimentateur + ".xml"; 
      
      
      boolean exists = (new File(output)).exists();
      if (exists) {
        // File or directory exists
        System.out.println("Le fichier existe déjà !\nOn sort du programme ...\n");
        System.exit(-1);
      }
 
      // on continue sinon 
      try
      {
        output_xml = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"));       
        output_xml.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
        
        output_xml.write("\n<experiment>\n");
        
        /* On insère la configuration --> traitement du fichier xml */
        output_xml.write("\n<configuration xml=\"" + config_name + "\">");

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(config_name), "UTF8"));
        
        String line = null;
        line = input.readLine(); 
        while ((line.matches(".*<?xml.*")==false))
        {
          line = input.readLine();
        }              
        while ((line = input.readLine()) != null)
        {
          output_xml.write("\n" + line);
        }      
        output_xml.write("\n</configuration>");
        
        output_xml.write("\n<parametres id_user=\"" + id_user + "\" session=\"" + session + "\" condition=\"" + condition + "\" experimentateur=\"" + experimentateur + "\" date=\"" + Day + "-" + Month + "-" + Year+ "\"/>");       
        output_xml.write("\n<events>");

      }
		catch (UnsupportedEncodingException ue) {}
		catch (IOException e) {}
		

    }
    
    private void stop_xml()
    {
      try
      {
        output_xml.write("\n</events>\n</experiment>");
        output_xml.close();
      }
      catch (UnsupportedEncodingException ue) {}
      catch (IOException e) {}
      System.exit(1);
    }
  
    private Timer create_timer (int time)
    {
    // Création d'une instance de listener associée au timer
    ActionListener action = new ActionListener ()
      {
        // Méthode appelée à chaque tic du timer
        public void actionPerformed (ActionEvent event)
        {
            stop_xml();
            System.exit(1);
        }
      };
      // Création d'un timer qui génère une action tous les time (en s) * 1000 millième de seconde
      return new Timer (time*1000, action);
    }
    
  public static void main(String[] args)
  {
    String config_xml=".\\configs\\configuration1.xml";
    String id_user = "0";
    String session = "0";
    String condition = "0";
    String experimentateur = "Unknown";
    int timer = 0;
    
    if (args.length == 6)
    {
      config_xml = args[0];
      id_user = args[1];
      session = args[2];
      condition = args[3];
      experimentateur = args[4];
      timer = Integer.parseInt(args[5]);
      
    }  
    Heminegligence h = new Heminegligence(config_xml, id_user, session, condition, experimentateur, timer);
    h.pack();
    h.setVisible(true);
  }
}
