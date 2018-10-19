package fr.irit.ihcs.util;

import java.awt.Color;
import java.io.*;
import java.util.*;

import fr.irit.ihcs.util.cfg_item;


public class cfg implements Serializable
{
	private static final long serialVersionUID = -1L;
	
	private Vector<cfg_item> vector;
	private int id;
	private int x0; // coordonnées x0 initial
	private int y0; // coordonnées y0 initial
	private int v; // nombre de blocs en vertical
	private int h; // nombre de blocs en horizontal
	private int id_cible;
	private Color couleur_cible;
	private Color couleur_distracteur;
	private boolean gradient;
	
	private boolean scintillement;
	private boolean gradient_sc;
	private double frequence_sc;
	private int delta_sc;
	
	private boolean gradient_gauche; // Gradient à Gauche ou à Droite [par défaut] ?
	
	// définition des cases
	// private case
	
	public cfg()
	{
	 this.id=0;
	 this.x0=0;
	 this.y0=0;
	 this.v=0;
	 this.h=0;
	 this.id_cible=0;
	 this.couleur_cible = new Color(0,0,0,255);
	 this.couleur_distracteur = new Color(192,192,192,255);
	 this.gradient = false;
	 this.scintillement = false;
	 this.gradient_sc = false;
	 this.frequence_sc = 10.0; // Fréquence de 10 Hz
	 this.delta_sc= 20; // vibration sur 20 pixels
	 this.gradient_gauche = false;
	 
	 vector = new Vector<cfg_item>();
	}
	
	public void add_case(cfg_item c)
	{
		(this.vector).addElement(c);
		return;
	}
	
	// setter et getter
	public void set_id(int id)
	{
    this.id = id;
	}
	public int get_id()
	{
    return(this.id);
  }
	
	public void set_x0(int x)
	{
    this.x0 = x;
  }
  public int get_x0()
	{
    return(this.x0);
  }

	public void set_y0(int y)
	{
    this.y0 = y;
  }
  public int get_y0()
	{
    return(this.y0);
  }
	
	
	public void set_v(int v)
	{
    this.v = v;
  }
  public int get_v()
	{
    return(this.v);
  }
  
  
  public void set_h(int h)
	{
    this.h = h;
  }  
  public int get_h()
	{
    return(this.h);
  }  
  
  public void set_id_cible(int id_cible)
	{
    this.id_cible = id_cible;
  }
  public int get_id_cible()
	{
    return(this.id_cible);
  }
  
  
  public void set_couleur_cible(Color c)
  {
    this.couleur_cible = c;
  }
  public Color get_couleur_cible()
  {
    return(this.couleur_cible);
  }
  
  public void set_couleur_distracteur(Color c)
  {
    this.couleur_distracteur = c;
  }
    public Color get_couleur_distracteur()
  {
    return(this.couleur_distracteur);
  }
   
  
  public void set_gradient(boolean b)
  {
    this.gradient = b;
  }
  public boolean get_gradient()
  {
    return(this.gradient);
  }
  
  
  public void set_scintillement(boolean b)
  {
    this.scintillement = b;
  }
  public boolean get_scintillement()
  {
    return(this.scintillement);
  } 
  
  public void set_gradient_sc(boolean b)
  {
    this.gradient_sc = b;
  }
  public boolean get_gradient_sc()
  {
    return(this.gradient_sc);
  }
  
  public void set_frequence_sc(double f)
  {
    this.frequence_sc = f;
  }
  public double get_frequence_sc()
  {
    return(this.frequence_sc);
  }
 
	public void set_delta_sc(int delta)
	{
    this.delta_sc = delta;
  }
  public int get_delta_sc()
	{
    return(this.delta_sc);
  }

  public void set_gradient_gauche(boolean b)
  {
    this.gradient_gauche = b;
  }
  
  public boolean get_gradient_gauche()
  {
    return(this.gradient_gauche);
  }
   
  public cfg_item get_case(int i)
  {
    return((this.vector).get(i));
  } 
  
  public int get_nb_items()
  {
    return((this.vector).size());
  }
}