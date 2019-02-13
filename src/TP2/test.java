package TP2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class test {
	public static void main(String args[])
	{
		Terme t= new Terme("hi", 15);
		System.out.println(t.isMyTerm("hi"));
	}
	
	
	
}
