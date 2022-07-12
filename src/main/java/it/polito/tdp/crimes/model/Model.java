package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private int max;
	private double min;
	private double pesoMediano;
	private List<Adiacenza>archi;
	private List<String > best;
	
	public Model() {
		this.dao=new EventsDao();
	}

	
	
	public List<String> getDay(){
		return dao.getDay();
	}
	
	public List<String> getCategory(){
		return dao.getCategotyID();
	}
	
	public void creaGrafo(String category, String day) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.archi=new ArrayList<>();
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(category, day));
		
		//aggiungo archi
		for(Adiacenza a: dao.getArchi(category, day)) {
			Graphs.addEdge(this.grafo, a.getVertice1(), a.getVertice2(), a.getPeso());
			
			archi.add(a);
		}
		Collections.sort(archi);
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	/*public double getPesoMin() {
		this.min=9999999.9;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)<min) {
				min=this.grafo.getEdgeWeight(e);
			}
		}
		return min;
		
	}
	public double getPesoMax() {
		this.max=0.0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>max) {
				max=this.grafo.getEdgeWeight(e);
			}
		}
		return max;
	}
	public double getPesoMediano() {
		this.pesoMediano=(this.max+this.min)/2;
		
		return pesoMediano;
	}
	public List<Adiacenza> getArchiMinMediano(){
		List<Adiacenza> result = new ArrayList<>();
		for(Adiacenza adiacenza : this.archi) {
			if(adiacenza.getPeso() <= pesoMediano) {
				result.add(adiacenza);
			}
		}
		
		return result;
	}*/
	public List<Adiacenza> getArchiMinMediano() {
		List<Adiacenza> result = new ArrayList<>();
		int pesoMin = 9999999;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)<pesoMin) {
				pesoMin=(int) this.grafo.getEdgeWeight(e);
			}
		}
		int pesoMax = 0;
		for(DefaultWeightedEdge e: this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>pesoMax) {
				pesoMax= (int) this.grafo.getEdgeWeight(e);
			}
		}
				
		double mediano = (pesoMax+pesoMin)/2;
		
		for(Adiacenza adiacenza : archi) {
			if(adiacenza.getPeso() <= mediano) {
				result.add(adiacenza);
			}
		}
		
		return result;
	}
	public List<Adiacenza> getArchi() {
		return archi;
	}
	
	public String cerca_cammino(Adiacenza a){
		this.best=new ArrayList<>();
		List<String> parziale= new ArrayList<>();
		this.max=0;
		String stampa="";
		parziale.add(a.getVertice2());
		cerca(parziale, a.getVertice2());
		for(String string : best) {
			stampa += string + "\n";
		}
		
		return stampa;
		
	}



	private void cerca(List<String> parziale, String arrivo) {
		if(parziale.get(parziale.size()-1).equals(arrivo)) {
			int peso = calcolaPeso(parziale);
			if(peso > max) {
				max = peso;
				best = new ArrayList<>(parziale);
			}
			return;
		}
		
		
		List<String> vicini = Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1)); // Vicini dell'ultimo elemento
		for(String string : vicini) {
			if(!parziale.contains(string)) {
				parziale.add(string);
				cerca(parziale, arrivo);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}



	private int calcolaPeso(List<String> parziale) {
		int peso = 0;
		
		for(int i=1; i < parziale.size(); i++) {
			peso += (int)grafo.getEdgeWeight(grafo.getEdge(parziale.get(i-1), parziale.get(i)));
		}
		return peso;
	}
	
}
