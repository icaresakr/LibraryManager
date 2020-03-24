package com.excilys.librarymanager.model;

import java.time.LocalDate;

public class Emprunt {
    private Integer id;
    private Membre membre;
    private Livre livre;
    LocalDate dateEmprunt;
    LocalDate dateRetour;

    public Emprunt() {
        super();
    }

    public Emprunt (Integer id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour){
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
        this.membre = membre;
        this.livre = livre;
        this.id = id;
    }

    
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    
    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public Integer getId() {
        return id;
    }
    
    public Livre getLivre() {
        return livre;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    
    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
    @Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "Id: " + id + "\n"
				+ "Membre: \n" + membre.toString() + "\n"
                + "Livre: \n" + livre.toString() + "\n"
                + "Date Emprunt: " + dateEmprunt + ", "
                + "Date Retour: " + dateRetour
				+ "}";
	}
}