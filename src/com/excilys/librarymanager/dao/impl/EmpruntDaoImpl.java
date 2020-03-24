package com.excilys.librarymanager.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao{
    
    //Sing
    private static EmpruntDaoImpl instance;
    private EmpruntDaoImpl(){};
    public static EmpruntDaoImpl getInstance(){
        if (instance == null)   instance = new EmpruntDaoImpl();
        return instance;
    }

    //Requetes
    private final String SELECT_ALL = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private final String SELECT_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private final String SELECT_CURRENT_by_Membre = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
    private final String SELECT_CURRENT_by_BOOK = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
    private final String SELECT_by_ID = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
    private final String CREATE = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private final String UPDATE = "UPDATE Emprunt SET idMembre=?, idLivre=?,dateEmprunt=?, dateRetour=? WHERE id=?;";
    private final String COUNT = "SELECT COUNT(*) AS count FROM emprunt WHERE idMembre IN (SELECT id FROM membre) and idLivre IN (SELECT id FROM livre);";

    //liste de tout les emprunts
    @Override
    public List<Emprunt> getList() throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SELECT_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet res = null;
        try {
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao bookDao = LivreDaoImpl.getInstance();
            while(res.next()){
                emprunts.add(new Emprunt(res.getInt("id"), membreDao.getById(res.getInt("idMembre")), bookDao.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour") == null ? null : res.getDate("dateRetour").toLocalDate()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprunts;
    };


    //listes des emprunts non retournés 
    @Override
    public List<Emprunt> getListCurrent() throws DaoException{
        List<Emprunt> currentemprunts = new ArrayList<Emprunt>();

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SELECT_CURRENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet res = null;
        try {
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao bookDao = LivreDaoImpl.getInstance();
            while (res.next()) {
                currentemprunts.add(new Emprunt(res.getInt("id"), membreDao.getById(res.getInt("idMembre")), bookDao.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour") == null ? null : res.getDate("dateRetour").toLocalDate()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentemprunts;
    };


    //liste des emprunts non retournés par un membre spécifié par idMembre
    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SELECT_CURRENT_by_Membre);
            stmt.setInt(1, idMembre);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet res = null;
        try {
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao bookDao = LivreDaoImpl.getInstance();
            while (res.next()) {
                emprunts.add(new Emprunt(res.getInt("id"),membreDao.getById(res.getInt("idMembre")), bookDao.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour").toLocalDate()) );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprunts;
    };

    //liste des emprunts par livre spécifié
    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SELECT_CURRENT_by_BOOK);
            stmt.setInt(1, idLivre);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet res = null;
        try {
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao bookDao = LivreDaoImpl.getInstance();
            while (res.next()) {
                emprunts.add(new Emprunt(res.getInt("id"),membreDao.getById(res.getInt("idMembre")), bookDao.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour") == null ? null : res.getDate("dateRetour").toLocalDate()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprunts;
    };

    // emprunt par id
    @Override
    public Emprunt getById(int id) throws DaoException{
        Emprunt chosenLoan = new Emprunt();

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(SELECT_by_ID);
            stmt.setInt(1, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet res = null;
        try {
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MembreDao membreDao = MembreDaoImpl.getInstance();
            LivreDao bookDao = LivreDaoImpl.getInstance();
            if (res.next()) {
                chosenLoan = new Emprunt(res.getInt("id"), membreDao.getById(res.getInt("idMembre")), bookDao.getById(res.getInt("idLivre")), res.getDate("dateEmprunt").toLocalDate(), res.getDate("dateRetour") == null ? null : res.getDate("dateRetour").toLocalDate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chosenLoan; 
    };

    //Creer un emprunt
    @Override
    public void create(int idMembre, int idLivre, LocalDate dateLoan) throws DaoException{

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(CREATE);
            stmt.setInt(1, idMembre);
            stmt.setInt(2, idLivre);
            stmt.setString(3, dateLoan + "");
            stmt.setDate(4, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    };

    //mettre à jour un emprunt 
    @Override
    public void update(Emprunt Emprunt) throws DaoException{

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(UPDATE);
            stmt.setInt(1, Emprunt.getMembre().getId());
            stmt.setInt(2, Emprunt.getLivre().getId());
            stmt.setString(3, Emprunt.getDateEmprunt()+"");
            stmt.setString(4, Emprunt.getDateRetour()+"");
            stmt.setInt(5, Emprunt.getId());
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        //execution
        try {
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    };


    //compter le nombre d'emprunts
    @Override
    public int count() throws DaoException{
        int totalemprunts = -1;

        Connection con = null;
        try {
            con = ConnectionManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(COUNT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet res = null;
        try {
            res = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(res.next()){
                totalemprunts = res.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalemprunts;
    };
}