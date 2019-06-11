package it.polito.tdp.borders.model;


public class Evento implements Comparable<Evento> {
	
	private int t; //tempo in cui si verifica l'evento
	private int n; //numero di persone che sono arrivate (e che si spostano)
	private Country stato; //Paese in cui le persone arrivano e da cui si spostano

	public Evento(int t, int n, Country stato) {
		super();
		this.t = t;
		this.n = n;
		this.stato = stato;
	}

	public int getT() {
		return t;
	}

	public int getN() {
		return n;
	}

	public Country getStato() {
		return stato;
	}

	@Override
	public int compareTo(Evento o) {
		return this.t - o.t; //ORDINE CRESCENTE
	}
}
