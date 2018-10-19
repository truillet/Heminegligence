package fr.irit.ihcs.Component;
import java.awt.*;
import java.io.*;
import java.util.*; // classe Random
import javax.swing.*;
import info.clearthought.layout.TableLayout;
import fr.irit.ihcs.Component.Case;
import fr.irit.ihcs.util.*; // cfg, cfg_case



public class Bloc extends JPanel
{
  JPanel bloc[][] = {null};


 
 public Bloc(cfg config, cfg_bloc cb , BufferedWriter output_xml)
 {
    super();
    
    cfg_item ci = new cfg_item();
    cfg_case cc = new cfg_case();
        
    int index = 0;
    int id_item = 0;
    Color couleur_item = new Color(0,0,0,255);
    boolean scintillement = false;
    double frequence = 10.0; // fréquence de vacillement
    int delta = 20;
    
    // Create a TableLayout for the panel
    String where;
    double size[][] = {
			{60, 60, 60},
			{60, 60, 60}
		};
		
    bloc = new JPanel [3][3];
    Case c = null;
		
    TableLayout layout = new TableLayout(size);
    this.setLayout(layout);	
    
    // i = ligne de 0 à 2
    for (int i=0;i<3;i++)
      // j = colonne de 0 à 2
      for (int j=0;j<3;j++)
      {
        where = i + "," + j;
        // System.out.println(where);
        
        index = i + (j*3); 
              
        // extraire de chaque case l'id de la cible et l'afficher
        cc = (cb.get_case(index));
        id_item = cc.get_id_item();
        
        ci = config.get_case(id_item-1);       
       
       
        // TRAITEMENT DE LA COULEUR ET DU SCINTILLEMENT     
        // System.out.println(">> affichage des cases : " + (ci.get_cible())); 
        if (ci.get_cible()==true) // c'est la cible !!!
        {
          // 3 cas : 
          // * gradient = false et scintillement = false
          // * gradient = true
          // * scintillement = true
          couleur_item = config.get_couleur_cible();
          frequence = config.get_frequence_sc();

          if (config.get_gradient()==true)
          {
            int rc = couleur_item.getRed();
            int vc = couleur_item.getGreen();
            int bc = couleur_item.getBlue();
			
            int rd = (config.get_couleur_distracteur()).getRed();
            int vd = (config.get_couleur_distracteur()).getGreen();
            int bd = (config.get_couleur_distracteur()).getBlue();
			
			      int taille = config.get_h() * 3 ;
            // le pas : recalage sur le bloc (bloc-1) % 7 + ajout de la case (case-1) % 3 
            int pas =((cb.get_id()-1) % config.get_h())*3 + ((cc.get_id()-1) % 3) ;

            if (config.get_gradient_gauche()==true)
            {
              // A MODIFIER
              // couleur locale
              // (couleur_cible * (pas) + couleur_distracteur * (21 - pas))/21
              rc = (int) (((pas * rc) + ((taille-pas) * rd)) / taille);
              vc = (int) (((pas * vc) + ((taille-pas) * vd)) / taille);
              bc = (int) (((pas * bc) + ((taille-pas) * bd)) / taille);
            }
            else
            {
              // recalculer la couleur locale 
              // (couleur_cible * (21-pas) + couleur_distracteur * (pas))/21 pour r,v, b
              rc = (int) (((taille - pas) * rc + (pas * rd)) / taille);
              vc = (int) (((taille - pas) * vc + (pas * vd)) / taille);
              bc = (int) (((taille - pas) * bc + (pas * bd)) / taille);
            }
            couleur_item = new Color(rc,vc,bc);

          }
          
          // ça scintille ou pas ?
      		scintillement = config.get_scintillement();
      		delta = config.get_delta_sc();
          if (config.get_gradient_sc()==true)
          {
            // où se trouve t'on ?
           int colonne_courante =((cb.get_id()-1) % config.get_h())*3 + ((cc.get_id()-1) % 3) ;
           
            if (config.get_gradient_gauche()==true)
            {
              frequence = config.get_frequence_sc() * ((float) colonne_courante/20); // GRADIENT GAUCHE
            }
            else
            {
              frequence = config.get_frequence_sc() * ( 1 - ((float) colonne_courante/20));
            }  
          }

        }
        else
        {
          couleur_item = config.get_couleur_distracteur();
          scintillement = false;
        }
                
        c = new Case(cb.get_id(), cc.get_id(), ci.get_url(),cc.get_dx(),cc.get_dy(),
					ci.get_cible(), couleur_item,
					scintillement, frequence, delta,
					output_xml);
        bloc[i][j] = c;
        this.add(bloc[i][j], where);
      } 
 }
 
 
  // --- PRIVATE ---
  private int aleatoire(int x)
  {
     Random r = new Random();
     return(r.nextInt(x));
  }
   
}