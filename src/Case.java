package fr.irit.ihcs.Component;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.Math;
import java.util.Random; // classe Random
import javax.swing.*;


public class Case extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
	int id_bloc=0;
	int id_case=0;
	boolean cible=false;
	private Color c;
	private Color original_c;
	private Image img; 
	private String imgName;
	private int taille_x = 60;
	private int taille_y = 60;
	private int taille_img = 40;
	private int dx;
	private int dy; 
	private boolean vacillement = false;
	private double frequence = 10.0;
	private int delta_max = 20;
	private int delta_min = 0;
	
	// coordonnées initiales
	private int x0 = 0;
	private int y0 = 0;
	
	
	private BufferedWriter output;
	  
	private boolean pressed = false;
	
	protected Timer t;
	private int sens = +1;

	/* Constructeurs */
	public Case() {
		super();

    taille_x=60;
		taille_y=60;
		taille_img = 50;
    
		dx = 0;
		dy = 0;
    		
		img = getToolkit().getImage("./img/cloche.gif");
		this.setPreferredSize(new Dimension(taille_x, taille_y));
		this.c = new Color(0,0,0,255);
		
		repaint();

	}
	
	public Case(int id_bloc, int id_case, String imgName, int dx, int dy, 
	             boolean cible, Color c,
	             boolean vacillement,
	             /* traitement du gradient de vacillement ? */ 
	             double frequence_vs, int delta_sc,
	             BufferedWriter output_xml)
	{
    
		this.id_bloc = id_bloc;
		this.id_case = id_case;
		this.dx = dx;
		this.dy = dy;
		this.cible = cible;
		this.vacillement = vacillement;
		this.frequence = frequence_vs;
		this.delta_max = this.dx + delta_sc; // on récupère le dx auquel on ajoute le delta
		this.delta_min = this.dx ; // on récupère le dx
		
		this.c = c;
		this.imgName = imgName;
		this.output = output_xml;
		
		// replacement par rapport à l'ensemble 
		x0 = ((this.id_bloc-1) % 7) * (3 * this.taille_x) + ((this.id_case-1) % 3) * this.taille_x;
		// 7 = nombre de bloc par ligne  <-> 3 = nombre de case par ligne
		y0 = ((this.id_bloc-1) / 7) * (3 * this.taille_y) + ((this.id_case-1) / 3) * this.taille_y; 

		
		img = getToolkit().getImage(imgName);
		this.setPreferredSize(new Dimension(taille_x, taille_y));
		
		setBackground(new Color(255,255,255,255));
		
		addMouseMotionListener(this); 
		addMouseListener(this);
		
		if (this.vacillement == true)
			vacille();
			
		repaint();
	}
	
	
	// A gérer, x et y différentes, taillex, taille y différentes 
	
	/* Gestion des couleurs */
	public void setColor(Color c) {
		this.c = c;
		repaint();
	}
	
	public Color getColor(Color c) {
		return(this.c);
	}
	
	// getter et setter à compléter


 	public void setCaseDefault() {	
	
		this.c = this.original_c;
		repaint();
	}
	
	public void setCaseDownColor() {
		// Down -> GRIS CLAIR
		this.original_c= this.c;
		this.pressed = true;
		
		// this.c = new Color(192,192,192,150);
		repaint();
	}
	
	public void setCaseUpColor() {
		// UP -> BLANC
		this.c = this.original_c;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		if (img == null) return;
		// dessine le composant
		g.setColor(this.c);
		
		if (this.vacillement == true)
		{
			if (this.dx >= this.delta_max)
			{
				sens = -Math.abs(sens);	// on inverse de sens			
			}	
			if (this.dx <= this.delta_min)
			{
				sens = Math.abs(sens); // on inverse de sens
			}
			this.dx = this.dx + sens;			
		}
		g.fillRect(this.dx,this.dy,this.taille_img,this.taille_img);
		g.drawImage(this.img, this.dx, this.dy, this);

		if (this.pressed == true)
		{
				g.setColor(new Color(0,0,0));
				g.drawLine(this.dx+13,this.dy+5,this.dx+5,this.dy+18);		
				g.drawLine(this.dx+5,this.dy+18,this.dx+23,this.dy+8);		
				g.drawLine(this.dx+23,this.dy+8,this.dx+8,this.dy+32);	
				g.drawLine(this.dx+8,this.dy+32,this.dx+34,this.dy+7);						
				g.drawLine(this.dx+34,this.dy+7,this.dx+12,this.dy+34);		
				g.drawLine(this.dx+12,this.dy+34,this.dx+38,this.dy+19);
				g.drawLine(this.dx+38,this.dy+19,this.dx+22,this.dy+38);	
				g.drawLine(this.dx+22,this.dy+38,this.dx+37,this.dy+34);					
		}
	}

    // GESTION DES EVENEMENTS
   public void mouseMoved(MouseEvent e)
    {   
      try
      {
        output.write("\n<MouseMoved x=\"" + (this.x0 + e.getX()) + "\" y=\"" + (this.y0 + e.getY()) + "\" t=\"" + System.nanoTime() + "\"/>");
      } 
      catch (UnsupportedEncodingException ue) {}
		  catch (IOException ioe) {
		  }  
    }	
   public void mousePressed(MouseEvent e)
   {
	setCaseDownColor();
    try
      {
        output.write("\n<MousePressed cible=\"" + this.cible + "\" imgName=\"" + this.imgName + "\" color= \"" + this.toString(this.c) + 
        "\" id_bloc=\"" + this.id_bloc + "\" id_case=\"" + this.id_case +    
        "\" x=\"" + (this.x0 + e.getX()) + "\" y=\"" + (this.y0 + e.getY()) + "\" t=\"" + System.nanoTime() + "\"/>");
      } 
    catch (UnsupportedEncodingException ue) {}
	catch (IOException ioe) {}    
   }
   
   
   public void mouseReleased(MouseEvent e)
   {
   try
      {
        output.write("\n<MouseReleased cible=\"" + this.cible + "\" imgName=\"" + this.imgName + "\" color= \"" + this.toString(this.c) + 
        "\" id_bloc=\"" + this.id_bloc + "\" id_case=\"" + this.id_case +    
        "\" x=\"" + (this.x0 + e.getX()) + "\" y=\"" + (this.y0 + e.getY()) + "\" t=\"" + System.nanoTime() + "\"/>");
      } 
    catch (UnsupportedEncodingException ue) {}
	catch (IOException ioe) {}       
   }
   
   
   
/*
   private void redispatch(MouseEvent e) {
      //Point origin = e.getComponent().getLocation();
      // e.translatePoint(origin.x, origin.y);
      e.getComponent().getParent().dispatchEvent(e);
	}
	
*/	
	// Evenement ignorés ici
    public void mouseClicked(MouseEvent e)
    {} 
    public void mouseEntered(MouseEvent e)
    {} 
    public void mouseExited(MouseEvent e)
    {} 

  // Evenement ignorés ici
    public void mouseDragged(MouseEvent e)
    {} 
    
    public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
    
    
    
  // METHODES PRIVEES
  private String toString(Color c)
  {
    int r = c.getRed();
    int v = c.getGreen();
    int b = c.getBlue();
    String couleur = "#";
    
    if (r < 16)
      couleur = couleur + "0" + Integer.toHexString(r).toUpperCase();
    else 
      couleur = couleur + Integer.toHexString(r).toUpperCase();
  
    if (v <16)
      couleur = couleur + "0" + Integer.toHexString(v).toUpperCase();
    else 
      couleur = couleur + Integer.toHexString(v).toUpperCase();

    if (b <16)
      couleur = couleur + "0" + Integer.toHexString(b).toUpperCase();
    else 
      couleur = couleur + Integer.toHexString(b).toUpperCase();

    return (couleur);
  } 
  
  private void vacille()
  {
	if (t == null)
	{
		// féquence temps de revenir à l'image de départ -> 40 pixels pour aller et retour
		// On calcule le dx en fonction de la fréquence
		
		if (this.frequence != 0) // mouvement demandé :
    {
      double facteur = 1000 / ((this.delta_max- this.delta_min)*2);
      double delai = (1/this.frequence)*facteur; //1000 ms /xx px à parcourir)    
      // pas le temps de réafficher, on incrémente le pas (sens)
      this.sens = this.sens * (int) (16/delai) +1;      
      //delaiAnimation = 16 ms ;
      t = new Timer(16, this);
      t.start();
    }
   } 
   else
   {
     if (!t.isRunning())
       t.restart();
   }
  } 
  
  private void stop_vacille()
  {
	t.stop();
  }
     
}
