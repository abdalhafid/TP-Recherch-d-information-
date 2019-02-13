package TP2;

public class Terme {
	private String terme;
	private double tf;
	private double idf;
	private int freq;
	
	
	public Terme(String Terme, int Freq)
	{
		this.terme = Terme;
		this.freq = Freq;
	}
	
	
	public Terme(String Terme)
	{
		this.terme = Terme;
	}
	
	public double getTf(int MaxFreq)
	{
		tf = (double) freq;
		return tf;
	}
	public String getTerme()
	{
		return terme;
	}
	
	public int getFreq()
	{
		return freq;
	}
	
	public double getTF()
	{
		if(tf != (double) freq)
			tf = freq;
		
		return tf;
	}
	
	public double getIDF()
	{
		return idf;
	}
	public void setFreq(int n)
	{
		freq = n;
	}
	
	public void setTF(int n)
	{
		tf = n;
	}
	
	public void setIDF(double d)
	{
		idf = d;
	}
	
	
	
	public double getTFSurIDF()
	{
		double r=this.tf/this.idf;
		return r;
	}
	
	public boolean isMyTerm(String s)
	{
		boolean ok = this.terme.equals(s);
		return ok;
	}
	
}
