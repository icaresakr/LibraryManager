package com.excilys.librarymanager.service.impl;

import java.util.List;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.*;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.service.*;

import java.util.ArrayList;


public class LivreServiceImpl implements LivreService{

    //Sing
    private static LivreServiceImpl instance;
    private LivreServiceImpl(){};
    public static LivreServiceImpl getInstance(){
        if (instance == null) instance = new LivreServiceImpl();
        return instance;
    }


    //service pour avoir la liste des livres dans la bd
    @Override
    public List<Livre> getList() throws ServiceException{
        List<Livre> totalList  = new ArrayList<Livre>();
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            totalList = livreDao.getList();

            System.out.println("\n\tAll books gets: " + totalList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list!\n", e);
        }

        return totalList;
    };


    // liste des livres disponibles
    @Override
	public List<Livre> getListDispo() throws ServiceException{
        List<Livre> booksDispo  = new ArrayList<Livre>();
        LivreDao livreDao = LivreDaoImpl.getInstance();
        
        try {
            EmpruntService loanService = EmpruntServiceImpl.getInstance();
            List<Livre> allBooks  = new ArrayList<Livre>();
            allBooks = livreDao.getList();
            
            for (int i = 0; i < allBooks.size(); i++) {
                if (loanService.isLivreDispo(allBooks.get(i).getId()))
                    booksDispo.add(allBooks.get(i));
            }
            
            System.out.println("\n\tAll books disponibles for booking: " + booksDispo);
        } catch (Exception e) {
            throw new ServiceException("Can't get disponible list!\n", e);
        }

        return booksDispo;
    };


    //choisir un livre par son id
    @Override
	public Livre getById(int id) throws ServiceException{
        Livre chosenBook = new Livre();
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            chosenBook = livreDao.getById(id);

            System.out.println("\n\tThe chosen book was: " + chosenBook);
        } catch (Exception e) {
            throw new ServiceException("Can't get the chosen book!\n", e);
        }

        return chosenBook;
    };


    //creer un livre dans la bd
    @Override
	public int create(String titre, String auteur, String isbn) throws ServiceException{
        int id = -1;
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            if (titre == null || titre == ""){
                throw new ServiceException("Empty Title! Can't create");
            } else{
                id = livreDao.create(titre, auteur, isbn);
                System.out.println("\n\tNew book instance created id: " + id);
            }

        } catch (Exception e) {
            throw new ServiceException("Can't create the specific book!\n", e);
        }

        return id;
    };


    //service de mise à jour d'un livre spécifié
    @Override
	public void update(Livre livre) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            if (livre.getTitre() == null || livre.getTitre() == ""){
                throw new ServiceException("Empty Title! Can't update");
            } else{
                livreDao.update(livre);
                System.out.println("\n\tThe book " + livre + " was successfully updated!");
            }
            
        } catch (Exception e) {
            throw new ServiceException("Can't be updated!\n", e);
        }
    };


    //service d'éfface d'un livre spécifié
    @Override
	public void delete(int id) throws ServiceException{
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            livreDao.delete(id);

            System.out.println("\n\tThe book with id: " + id + " was deleted!");
        } catch (Exception e) {
            throw new ServiceException("Can't be deleted!\n", e);
        }
    };


    //service de comptage
    @Override
	public int count() throws ServiceException{
        int total = -1;
        LivreDao livreDao = LivreDaoImpl.getInstance();

        try {
            total = livreDao.count();

            System.out.println("\n\tNumber of books currently in the data base: " + total);
        } catch (Exception e) {
            throw new ServiceException("Can't be counted!\n", e);
        }

        return total;
    };
}