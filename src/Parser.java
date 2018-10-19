package fr.irit.ihcs.util;

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.util.*;

import fr.irit.ihcs.util.*; // cfg

// JAXP
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

// DOM
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

  private int levelStr = 1;
  private cfg c = new cfg();
  private Vector<cfg_bloc> cb = new Vector<cfg_bloc>();
    
  private int bloc_id = -1; 
  
  public Parser(String file)
  {
    try {
      // Get Document Builder Factory
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      // Leave off validation, and turn off namespaces
      factory.setValidating(false);
      factory.setNamespaceAware(false);

      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new File(file));

      // Print the document from the DOM tree and
      //   feed it an initial indentation of nothing
      printNode(doc, false);
    } 
    catch (ParserConfigurationException e) {
      System.out.println("The underlying parser does not support the requested features.");
    } 
    catch (FactoryConfigurationError e) {
      System.out.println("Error occurred obtaining Document Builder Factory.");
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
	}
   
  // =========================================================================================== 
  private void printNode(Node node, boolean tW)  {
    boolean toWrite = tW;
		
		
    switch (node.getNodeType()) {
      case Node.DOCUMENT_NODE:
        // recurse on each child
        NodeList nodes = node.getChildNodes();
        if (nodes != null) {
          for (int i=0; i<nodes.getLength(); i++) {
            printNode(nodes.item(i), toWrite);
          }
        }
        break;
      
      case Node.ELEMENT_NODE:
        String name = node.getNodeName();
        
        NodeList c = node.getChildNodes();
        if (c != null) {
        for (int k=0; k<c.getLength(); k++) {
          
          // On regarde si on a un bloc ...
          if (levelStr==3)  // items, ou bloc
          {
            if (name.compareTo("bloc")==0) // CONFIGURATION DES BLOCS
            {
              cfg_bloc leBloc = new cfg_bloc();
			
              NamedNodeMap attributes = node.getAttributes();
			
              for (int l=0; l<attributes.getLength(); l++) {
                Node CA = attributes.item(l);
                // Traitement des items
                if ((CA.getNodeName()).compareTo("id")==0)
                {
                  leBloc.set_id(Integer.parseInt(CA.getNodeValue()));					
                  // on rajoute le bloc de données ssi il n'est pas déjà archivé !
                  if (this.bloc_id != (leBloc.get_id() - 1)) // bloc de 0 au nombre qui va bien
                  {
                    this.bloc_id = leBloc.get_id() - 1;		
                    (this.cb).add(this.bloc_id, leBloc);
                  }
                }
              }  
            }
          }   
          levelStr++;
          printNode(c.item(k), toWrite);
          levelStr--;
      }
    }  
		
		if (levelStr==2)  // cfg, ou blocs
		{
		  if (name.compareTo("cfg")==0)
            // recherche des attributs
          {
            NamedNodeMap attributes = node.getAttributes();
            for (int k=0; k<attributes.getLength(); k++) 
            {
              Node CA = attributes.item(k);
				
              // Traitement des items
              if ((CA.getNodeName()).compareTo("id")==0)
              {
                (this.c).set_id(Integer.parseInt(CA.getNodeValue()));			
              }
              
              if ((CA.getNodeName()).compareTo("x0")==0)
              {
                (this.c).set_x0(Integer.parseInt(CA.getNodeValue()));	
              }
              if ((CA.getNodeName()).compareTo("y0")==0)
              {
                (this.c).set_y0(Integer.parseInt(CA.getNodeValue()));	
              }
             
              if ((CA.getNodeName()).compareTo("v")==0)
              {
                (this.c).set_v(Integer.parseInt(CA.getNodeValue()));	
              }
              if ((CA.getNodeName()).compareTo("h")==0)
              {
                (this.c).set_h(Integer.parseInt(CA.getNodeValue()));	
              }
              if ((CA.getNodeName()).compareTo("id_cible")==0)
              {
                (this.c).set_id_cible(Integer.parseInt(CA.getNodeValue()));	
              }
              if ((CA.getNodeName()).compareTo("couleur_cible")==0)
              {
                (this.c).set_couleur_cible(toColor(CA.getNodeValue()));
              }
              if ((CA.getNodeName()).compareTo("couleur_distracteur")==0)
              {
                (this.c).set_couleur_distracteur(toColor(CA.getNodeValue()));
	            }
              if ((CA.getNodeName()).compareTo("gradient")==0)
              {
                if (CA.getNodeValue().compareTo("true")==0)
                  (this.c).set_gradient(true);
                else
                  (this.c).set_gradient(false);
              }

              if ((CA.getNodeName()).compareTo("gradient_gauche")==0)
              {
                if (CA.getNodeValue().compareTo("true")==0)
                  (this.c).set_gradient_gauche(true);
                else
                  (this.c).set_gradient_gauche(false);
              }
              
              if ((CA.getNodeName()).compareTo("scintillement")==0)
              {
                if (CA.getNodeValue().compareTo("true")==0)
                  (this.c).set_scintillement(true);
                else
                  (this.c).set_scintillement(false);
              }
              if ((CA.getNodeName()).compareTo("gradient_sc")==0)
              {
                if (CA.getNodeValue().compareTo("true")==0)
                  (this.c).set_gradient_sc(true);
                else
                  (this.c).set_gradient_sc(false);
              }	
              // Frequence en Hz du scintillement
              if ((CA.getNodeName()).compareTo("frequence_sc")==0)
              {
                (this.c).set_frequence_sc(Float.parseFloat(CA.getNodeValue()));
	            }
              if ((CA.getNodeName()).compareTo("delta_sc")==0)
              {
                (this.c).set_delta_sc(Integer.parseInt(CA.getNodeValue()));	
              }
	            
          }
        } 
          
 		}
		
		if (levelStr==4) // CFG DES ITEMS 
	    {
			if (name.compareTo("item")==0)
            // Recurse sur chaque item
			{
				NamedNodeMap attributes = node.getAttributes();
            
				cfg_item laCase = new cfg_item();
            
				for (int k=0; k<attributes.getLength(); k++) {
					Node CA = attributes.item(k);
              
					// Traitement des items
					if ((CA.getNodeName()).compareTo("id")==0)
					{
						laCase.set_id(Integer.parseInt(CA.getNodeValue()));			
					}
					if ((CA.getNodeName()).compareTo("url")==0)
					{
						laCase.set_url(CA.getNodeValue());			
					}   
					if ((CA.getNodeName()).compareTo("cible")==0)
					{					
						if (CA.getNodeValue().compareTo("true")==0)
						{
							// System.out.println("> cible true ? " + CA.getNodeValue());
							laCase.set_cible(true);
						}
						else
						{
							laCase.set_cible(false);
							// System.out.println("> cible false ? " + CA.getNodeValue());
						}
					}          
				}           
				(this.c).add_case(laCase);
				// System.out.println(">> item :  " + laCase.toString());
         
			}
			
			if (name.compareTo("case")==0)
			{
				NamedNodeMap attributes = node.getAttributes();

				cfg_case C = new cfg_case();
				for (int k=0; k<attributes.getLength(); k++)
				{
					Node CA = attributes.item(k);
					
					if ((CA.getNodeName()).compareTo("id")==0)
					{
						C.set_id(Integer.parseInt(CA.getNodeValue()));			
					}
					if ((CA.getNodeName()).compareTo("id_item")==0)
					{
						C.set_id_item(Integer.parseInt(CA.getNodeValue()));			
					}
					if ((CA.getNodeName()).compareTo("dx")==0)
					{
						C.set_dx(Integer.parseInt(CA.getNodeValue()));			
					}
					if ((CA.getNodeName()).compareTo("dy")==0)
					{
						C.set_dy(Integer.parseInt(CA.getNodeValue()));			
					}
				}
				// AJOUT DE LA CASE DANS LE BLOC COURANT
				// System.out.println(">> " + C.toString());
				(cb.get(this.bloc_id)).add_case(C); 
			}
	  }	
        break;
        
      case Node.TEXT_NODE:
        break;
   
  }
  } 
    
  public Vector getBlocs() {
		return(this.cb);
	}  

  public cfg_bloc getBloc(int i) {
		return((this.cb).get(i));
	}  


  public cfg getConfig() {
		return(this.c);
	}  
	

  // =======================
  private Color toColor(String c)
  {
    // la couleur en entrée #xxxxxx
    int r =0;
    int g =0;
    int b =0;
    String couleur = "";
    
    couleur = c.substring(1,3);
    // System.out.println(">> R :" + couleur);
    r = Integer.parseInt(couleur,16);
    couleur = c.substring(3,5);
    // System.out.println(">> G :" + couleur);
    g = Integer.parseInt(couleur,16);
    couleur = c.substring(5,7);
    // System.out.println(">> B :" + couleur); 
    b = Integer.parseInt(couleur,16); 
    
    
    // décodage de la couleur en entrée
    
    
    Color color =new Color(r,g,b);
    // System.out.println(">>COLOR<< " + color.toString());
    return (color);
  }
}