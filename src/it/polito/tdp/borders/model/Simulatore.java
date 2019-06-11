package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	//Stato del sistema (Modello)
	private Graph<Country, DefaultEdge> grafo;
	
	//Tipi di evento - coda prioritaria
	private PriorityQueue<Evento> queue;
	
	//Parametri della simulazione
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	//Valori in output
	private int T;
	private Map<Country, Integer> stanziali;
	
	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		//inizializzo i parametri
		this.partenza = partenza;
		this.grafo = grafo;
		
		//imposto lo stato iniziale
		this.T=1;
		stanziali = new HashMap<Country, Integer>();
		for(Country c : this.grafo.vertexSet()) {
			stanziali.put(c,0);
		}
		queue = new PriorityQueue<Evento>();
		
		//inserisco il primo evento
		this.queue.add(new Evento(T, N_MIGRANTI, partenza));		
	}
	
	public void run() {
		//estraggo un evento dalla coda e lo eseguo, finché queue.isEmpty()==true
		Evento e;
		while((e = queue.poll())!=null) {
			//c'è almeno un evento -> lo eseguo
			this.T=e.getT(); //tengo traccia dell'ultimo evento
			int nPersone = e.getN();
			Country stato = e.getStato();
			List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);
			int migranti = (nPersone/2)/confinanti.size(); //persone che si spostano in ogni stato
			
			if(migranti>0) {
				//le persone si possono muovere
				for(Country conf : confinanti) {
					queue.add(new Evento(e.getT()+1, migranti, conf));	
				}
			}
			int stanziali = nPersone - migranti*confinanti.size(); //persone che si sono spostate in totale
			this.stanziali.put(stato, this.stanziali.get(stato)+stanziali);
		}
	}

	public int getLastT() {
		return T;
	}
	
	public Map<Country, Integer> getStanziali(){
		return this.stanziali;
	}
	
}
