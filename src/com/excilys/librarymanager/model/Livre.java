package com.excilys.librarymanager.model;

public class Livre {
    private Integer id;
    private String titre;
    private String auteur;
    private String isbn;

    public Livre(){
        super();
    }

    public Livre(Integer id, String titre, String auteur, String isbn){
        this();
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getTitre(){
        return titre;
    }
    public void setTitre(String titre){
        this.titre = titre;
    }

    public String getAuteur(){
        return  auteur;
    }
    public void setAuteur(String auteur){
        this.auteur = auteur;
    }

    public String getISBN(){
        return isbn;
    }
    public void setISBN(String isbn){
        this.isbn = isbn;
    }


    @Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "Id: " + id + ", "
				+ "Titre:" + titre + ", "
                + "Auteur: " + auteur + ", "
                + "isbn: " + isbn
				+ "}";
	}
}