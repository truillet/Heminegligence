package fr.irit.ihcs.util;

import java.io.*;

public class cfg_item implements Serializable
{
	private static final long serialVersionUID = -1L;
	
	
	private int id;
	private String url; // url
	private boolean cible;
	
	
	public cfg_item()
	{
		this.id = 0;
		this.url = "";
		this.cible= false;
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
	
	
	public void set_url(String url)
	{
    this.url = url;
	}
	public String get_url()
	{
    return(this.url);
	}	
	
	
	public void set_cible(boolean b)
	{
    this.cible = b;
	}
	public boolean get_cible()
	{
    return(this.cible);
	}
	
	public String toString()
	{
    String s ="id : " + this.id + " url : " + this.url + " cible : " + this.cible;
    return(s);
	}
	
}