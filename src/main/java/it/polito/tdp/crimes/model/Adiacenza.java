package it.polito.tdp.crimes.model;

public class Adiacenza implements Comparable<Adiacenza> {
	private String vertice1;
	private String vertice2;
	private int peso;
	
	public Adiacenza(String vertice1, String vertice2, int peso) {
		super();
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.peso = peso;
	}

	public String getVertice1() {
		return vertice1;
	}

	public void setVertice1(String vertice1) {
		this.vertice1 = vertice1;
	}

	public String getVertice2() {
		return vertice2;
	}

	public void setVertice2(String vertice2) {
		this.vertice2 = vertice2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return this.peso - o.peso;
	}

	@Override
	public String toString() {
		return vertice1 + ", " + vertice2 + ", peso = " + peso;
	}
	
	
	
}