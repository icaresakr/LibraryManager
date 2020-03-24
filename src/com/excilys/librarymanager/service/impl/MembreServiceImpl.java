package com.excilys.librarymanager.service.impl;

import java.util.List;

import com.excilys.librarymanager.dao.*;
import com.excilys.librarymanager.dao.impl.*;
import com.excilys.librarymanager.exception.*;
import com.excilys.librarymanager.model.*;
import com.excilys.librarymanager.service.*;

import java.util.ArrayList;


public class MembreServiceImpl implements MembreService {
    
    //Sing
    private static MembreServiceImpl instance;
    private MembreServiceImpl(){};
    public static MembreServiceImpl getInstance(){
        if (instance == null)   instance = new MembreServiceImpl();
        return instance;
    }
    

    //service liste des membres dans la bd
    @Override
    public List<Membre> getList() throws ServiceException{
        List<Membre> empruntAllList = new ArrayList<Membre>();
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            empruntAllList = membreDao.getList();

            System.out.println("\n\tAll membres list: " + empruntAllList);
        } catch (Exception e) {
            throw new ServiceException("Can't get total list!\n", e);
        }

        return empruntAllList;
    };

    //liste des membres possible d'emprunter
    @Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException{
        List<Membre> membresEmpruntDispo = new ArrayList<Membre>();
        List<Membre> membres = new ArrayList<Membre>();
        MembreDao membreDao = MembreDaoImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            membres = membreDao.getList();
            for (int i = 0; i < membres.size(); i++){
                if (empruntService.isEmpruntPossible(membres.get(i))){
                    membresEmpruntDispo.add(membres.get(i));
                }
            }

            System.out.println("\n\tReturning all emprunts possibles: " + membresEmpruntDispo);
        } catch (Exception e) {
            throw new ServiceException("Can't get possibles bookings by membre!\n", e);
        }

        return membresEmpruntDispo;
    };


    // récupéré un membre par son id
    @Override
    public Membre getById(int id) throws ServiceException{
        Membre chosenOne = new Membre();
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            chosenOne = membreDao.getById(id);

            System.out.println("\n\tChosen membre: " + chosenOne);
        } catch (Exception e) {
            throw new ServiceException("Can't get individual membre!\n", e);
        }

        return chosenOne;
    };
    

    //service de crée d'un membre avec verification du format
    @Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException{
        int id = -1;
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            if (nom == null || prenom == null || nom == "" || prenom == ""){
                throw new ServiceException("First or Last names empties! Can't create");
            } else{
                nom = nom.toUpperCase();
                prenom = prenom.toUpperCase();
                
                id = membreDao.create(nom, prenom, adresse, email, telephone);
                System.out.println("\n\tActual new membre ID: " + id);
            }
        } catch (Exception e) {
            throw new ServiceException("Can't create for a reason!\n", e);
        }


        return id;
    };

  
    //mise à jour d'un membre avec verification du format 
    @Override
	public void update(Membre membre) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            if(membre.getPrenom() == null || membre.getNom() == null || membre.getPrenom() == "" || membre.getNom() == ""){
                throw new ServiceException("First or Last names empties! Can't update");
            } else{
                membre.setNom(membre.getNom().toUpperCase());
                membreDao.update(membre);

                System.out.println("\n\tMembre " + membre + "successfull updated!");
            }
        } catch (Exception e) {
            throw new ServiceException("Can't be updated!\n", e);
        }

    };


    //effacer un membre par son id
    @Override
	public void delete(int id) throws ServiceException{
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            membreDao.delete(id);
            System.out.println("\n\tMembre " + id + "successfull deleted!");
        } catch (Exception e) {
            throw new ServiceException("Can't be deleted!\n", e);
        }
    };


    //compte et retourne le nombre total de membres 
    @Override
	public int count() throws ServiceException{
        int total = -1;
        MembreDao membreDao = MembreDaoImpl.getInstance();

        try {
            total = membreDao.count();
            System.out.println("\n\tTotal membres: " + total);
        } catch (Exception e) {
            throw new ServiceException("Can't count all things!\n", e);
        }

        return total;
    };
}