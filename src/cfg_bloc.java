package fr.irit.ihcs.util;


import java.io.*;
import java.util.*;

import fr.irit.ihcs.util.cfg_bloc;


public class cfg_bloc implements Serializable
{
	private static final long serialVersionUID = -1L;
	
	private Vector<cfg_case> vector;
	private int id;

	
	public cfg_bloc()
	{
		this.id = 0;
		vector = new Vector<cfg_case>();
	}
	
	public void init()
	{
		this.id = 0;
		this.vector.removeAllElements();
	}
	
	
	public void add_case(cfg_case c)
	{
		(this.vector).addElement(c);
		return;
	}
	
	// getter et setter
	public void set_id(int id)
	{
		this.id = id;
	}
	public int get_id()
	{
		return(this.id);
	}
	
	public cfg_case get_case(int i)
    {
		return((this.vector).get(i));
    } 
  
	public int get_nb_cases()
	{
		return((this.vector).size());
	}
	
}