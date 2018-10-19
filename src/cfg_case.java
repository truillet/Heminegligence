package fr.irit.ihcs.util;

import java.io.*;

public class cfg_case implements Serializable
{
	private static final long serialVersionUID = -1L;
	
	private int id;
	private int id_item; 
	private int dx;
	private int dy;
	
	
	public cfg_case()
	{
		this.id = 0;
		this.id_item = 0;
		this.dx = 0;
		this.dy = 0;
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
	
	public void set_id_item(int id)
	{
		this.id_item = id;
	}
	public int get_id_item()
	{
		return(this.id_item);
	}
	
	public void set_dx(int x)
	{
		this.dx = x;
	}
	public int get_dx()
	{
		return(this.dx);
	}
	
	public void set_dy(int y)
	{
		this.dy = y;
	}
	public int get_dy()
	{
		return(this.dy);
	}
	
	public String toString()
	{
    String s ="id : " + this.id + " id_item : " + this.id_item + " dx : " + this.dx + " dy : " + this.dy;
    return(s);
	}
}