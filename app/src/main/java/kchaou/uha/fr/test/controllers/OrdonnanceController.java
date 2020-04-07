package kchaou.uha.fr.test.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kchaou.uha.fr.test.models.Ordonnance;

public class OrdonnanceController {

    //proprietes
    private static long ID = 7;
    private List<Ordonnance> list_ordonnances;

    //constructeur
    public OrdonnanceController () {
        list_ordonnances=new ArrayList<Ordonnance>();
    }


    //ajouter a la liste des d'odonnance
    public void remplirOrdonnaces() {


        list_ordonnances.add(new Ordonnance(0, new Date(), "66", "Grippe", false));
        list_ordonnances.add(new Ordonnance(1, new Date(), "30", "Pneumopathie", false));
        list_ordonnances.add(new Ordonnance(2, new Date(), "6", "Bronchite", false));
        list_ordonnances.add(new Ordonnance(3, new Date(), "5", "Maladie chronique", false));
        list_ordonnances.add(new Ordonnance(4, new Date(), "6", "Affections pulmonaires", false));
        list_ordonnances.add(new Ordonnance(5, new Date(), "6", "Bronchiolites", false));
        list_ordonnances.add(new Ordonnance(6, new Date(), "6", "Clonorchiase", false));
    }

    //recuperer la liste des
    public List<Ordonnance> getListOrdonnances() {
        if (list_ordonnances == null) list_ordonnances = new ArrayList<>();
        return list_ordonnances;
    }


    //taille de la liste
    public int tailleOrdonnances () {
        return getListOrdonnances().size();
    }

    //recuperer un ordonnace
    public Ordonnance getOrdonnanceParIndex (int position) {
        return getListOrdonnances().get(position);
    }

    //ajouter une ordonnance dans la liste
    public void ajouterOrdonnance (Ordonnance ordonnance) {
        getListOrdonnances().add(ordonnance);
        ordonnance.setId(ID++);
    }

    //supprimer une ordonnance
    public void supprimerOrdonnance (Ordonnance m) {
        getListOrdonnances().remove(m);
    }


    //mise a jour d'ordonnace
    public void modifierOrdonnance (int position, Ordonnance nouveau) {
        Ordonnance old = getListOrdonnances().get(position);
        getListOrdonnances().remove(old);
        getListOrdonnances().add(position, nouveau);
        nouveau.setId(old.getId());
    }
}
