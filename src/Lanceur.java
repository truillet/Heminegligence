/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 1.8 du 20/03/2009
*/

package fr.irit.ihcs.experiment;

import java.awt.*;
import java.awt.event.*;

import java.io.*; // DONT FILE

import javax.swing.*;
import info.clearthought.layout.TableLayout;
import fr.irit.ihcs.Component.*;

class Lanceur extends JFrame implements ActionListener
{
  private static final long serialVersionUID = 0L;

	JPanel pId;
	JTextField tfId = new JTextField("1", 30);
	JPanel pCondition;
	JTextField tfCondition = new JTextField("1", 30);	
	JPanel pSession;
	JTextField tfSession = new JTextField("1", 30);	
	JPanel pExperimentateur;
	JTextField tfExperimentateur = new JTextField("Expe", 30);
	JList dataList = new JList(); 
	
	JPanel pTimer;
	JTextField tfTimer = new JTextField("120", 4);	
	
	JScrollPane spConfig;
	JButton bLaunch; 
	File f;
	
	Lanceur()
	{
		super(".:. Lanceur .:.");
		
		JLabel pId = new JLabel("Sujet :");
		// JTextField tfId = new JTextField("1", 30);

		JLabel pSession = new JLabel("N° de Session :");
		// JTextField tfCondition = new JTextField("1", 30);
		
		JLabel pCondition = new JLabel("N° de Condition :");
		// JTextField tfCondition = new JTextField("1", 30);
		
		JLabel pExperimentateur = new JLabel("Expérimentateur :");
		// JTextField tfExperimentateur = new JTextField("BOSS", 30);
		
		JLabel pTimer = new JLabel("Durée (0 = temps illimité) :");
		
		// Retrieve all configurations
		f = new File("./configs");
		
		String[]data = f.list();
		
		// dataList = new JList(data);
		dataList.setListData(data);
		spConfig = new JScrollPane();
		spConfig.getViewport().setView(dataList);


		JButton bLaunch =new JButton("Lancer !");
		
		Container contentPane = getContentPane();
		String where;
		double size[][] = {
			{20, 130, 100, 20, 150, 20},
			{20, 20, 20, 20, 20, 20, 20, 20, 20, 20}
		};
		
    TableLayout layout = new TableLayout(size);
    contentPane.setLayout(layout);

	contentPane.add(pId, "1,1");
	contentPane.add(tfId, "2,1");
 
	contentPane.add(pSession, "1,3");
	contentPane.add(tfSession, "2,3");

	contentPane.add(pCondition, "1,4");
	contentPane.add(tfCondition, "2,4");
	
	contentPane.add(pExperimentateur, "1,5");
	contentPane.add(tfExperimentateur, "2,5");

	contentPane.add(pTimer, "1,6");
	contentPane.add(tfTimer, "2,6");

	contentPane.add(spConfig, "4,1,4,8");

	// RIEN EN ()
	contentPane.add(bLaunch, "1,8");
	// Ajout d'un listener sur le bouton 
	bLaunch.addActionListener(this);
  setSize(390,200);
  setVisible(true);   	
	}


  // LISTENER
  public void actionPerformed(ActionEvent e)
  {
    String id="0" ; 
    String session ="0";
    String condition="0";
    String experimentateur="Unknown";
    String  config = "./configs/configuration1.xml"; 
    String timer = "0";
     
    
    try {
      id= tfId.getText();
      session = tfSession.getText();
      condition=tfCondition.getText();
      experimentateur = tfExperimentateur.getText();
      config = f.getPath() + "\\" + dataList.getSelectedValue();
      timer = tfTimer.getText();
    }
    catch (NullPointerException npe)
    {
      System.out.println("Exception levée : adresse vide");     
    }   
    Runtime runtime = Runtime.getRuntime();
    
    try {
      // System.out.println("expe.bat "+ config + " " + id + " " + session + " " + condition + " " + experimentateur);

      runtime.exec(new String[] { "expe.bat", config, id, session, condition, experimentateur, timer} );
    }
    catch (Exception ex) {}  
    System.exit(0);       
  }
  
  
  public static void main(String[] args)
  {
    try {
	    // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }

    Lanceur l = new Lanceur();
    l.pack();
    l.setVisible(true);
  }
}