package com.excilys.librarymanager.service.impl;

import java.util.List;
import java.util.*;

import com.excilys.librarymanager.dao.impl.*;
import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.service.*;
import com.excilys.librarymanager.exception.*;

import java.time.LocalDate;

public class EmpruntServiceImpl implements EmpruntService{
    
    //Sing
    private static EmpruntServiceImpl instance;
    private EmpruntServiceImpl(){};
    public static EmpruntServiceImpl getInstance(){
        if (instance == null)   instance = new EmpruntServiceImpl();
        return instance;
    }


    //liste de tous les emprunts existants
    @Override
    public List<Emprunt> getList() throws ServiceException{
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            emprunts = empruntDao.getList();

            System.out.println("\n\tAll loans list: " + emprunts);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list!\n", e);
        }

        return emprunts;
    };


    //avoir la liste des emprunts actifs
    @Override
	public List<Emprunt> getListCurrent() throws ServiceException{
        List<Emprunt> activeList = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            activeList = empruntDao.getListCurrent();

            System.out.println("\n\tAll currents loans not returneds yet: " + activeList);
        } catch (Exception e) {
            throw new ServiceException("Can't get current list!\n", e);
        }

        return activeList;
    };

    // liste des emprunts par membre spécifié
    @Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException{
        List<Emprunt> currentLists = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance(); //methode de classe

        try {
            currentLists = empruntDao.getListCurrentByMembre(idMembre);

            System.out.println("\n\tAll currents loans not returneds yet by member: " + currentLists);
        } catch (Exception e) {
            throw new ServiceException("Can't get current list by member!\n", e);
        }

        return currentLists;
    };

    //liste des emprunts par livre
    @Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException{
        List<Emprunt> activeList = new ArrayList<Emprunt>();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            activeList = empruntDao.getListCurrentByLivre(idLivre);

            System.out.println("\n\tAll currents loans not returneds yet by books: " + activeList);
        } catch (Exception e) {
            throw new ServiceException("Can't get current list by book!\n", e);
        }

        return activeList;
    };


    //liste des emprunts par id
    @Override
    public Emprunt getById(int id) throws ServiceException{
        Emprunt chosenLoan = new Emprunt();
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            chosenLoan = empruntDao.getById(id);

            System.out.println("\n\tGetting by ID: " + chosenLoan);
        } catch (Exception e) {
            throw new ServiceException("Can't get this specific booking!\n", e);
        }
        return chosenLoan;
    };



    //creér un emprunt
    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);

            System.out.println("\n\t\nCreate new loan sucessfully!\n");
        } catch (Exception e) {
            throw new ServiceException("Can't be created!\n", e);
        }
    };
    

    //service de retour d'un livre
    @Override
	public void returnBook(int id) throws ServiceException{
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            Emprunt update = empruntDao.getById(id);
            update.setDateRetour(LocalDate.now());
            empruntDao.update(update);

            System.out.println("\n\tLoan " + update + "successfull updated! Book returned!");
        } catch (Exception e) {
            throw new ServiceException("Can't be returned yet!\n", e);
        }
    };


    //compter les emprunts
    @Override
	public int count() throws ServiceException{
        int total = -1;
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            total = empruntDao.count();

            System.out.println("\n\tTotal loans: " + total);
        } catch (Exception e) {
            throw new ServiceException("Can't be counted!\n", e);
        }
        return total;
    };


    //verifie si le livre pest disponible pour etre emprunté
    @Override
	public boolean isLivreDispo(int idLivre) throws ServiceException{
        boolean disponible = false;
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            disponible = empruntDao.getListCurrentByLivre(idLivre).isEmpty();
            System.out.println("\n\tStatus of chosen Book: " + disponible);

            return disponible;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponible;
    };

    //verifier si un membre peut emprunter un livre
    @Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException{
        boolean disponible = false;
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();

        try {
            disponible = empruntDao.getListCurrentByMembre(membre.getId()).isEmpty();
            System.out.println("\n\tThe member can get another book?: " + disponible);

            return disponible;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponible;
    };
}