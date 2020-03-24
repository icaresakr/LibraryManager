package com.excilys.librarymanager.model;

public class Membre {
    private Integer id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String tel;
    Abonnement abo;

    public Membre() {
        super();
    }

    public Membre(Integer id, String nom, String prenom, String adresse, String email, String tel, Abonnement abo){
        this();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
        this.abo = abo;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getNom(){
        return nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    public String getPrenom(){
        return prenom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public String getAdresse(){
        return adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getTel(){
        return tel;
    }
    public void setTel(String tel){
        this.tel = tel;
    }

    public Abonnement getAbo(){
        return abo;
    }
    public void setAbo(Abonnement abo){
        this.abo = abo;
    }

    @Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "Id: " + id + ", "
				+ "Nom:" + nom + ", "
                + "Prenom: " + prenom + ", "
				+ "Adresse: " + adresse + ", "
				+ "Email: " + email + ", "
				+ "Tel: " + tel + ", "
				+ "Abo: " + abo
				+ "}";
	}
}