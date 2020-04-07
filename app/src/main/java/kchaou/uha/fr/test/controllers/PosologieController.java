package kchaou.uha.fr.test.controllers;

import java.util.ArrayList;
import java.util.List;

import kchaou.uha.fr.test.models.Posologie;


public class PosologieController {

    //proprietes
    private static long ID = 5;
    private List<Posologie> list_posologies;

    //constructeur
    public PosologieController() {
        list_posologies = new ArrayList<Posologie>();
    }

    //ajouter a la liste
    public void remplirPosologies() {
        list_posologies.add(new Posologie(0, "1,0,1", "0,0,0", "5", "Doliprane", false, 0, 0));
        list_posologies.add(new Posologie(1, "0,0,0", "1,0,0", "6", "Fervex", false, 1, 0));
        list_posologies.add(new Posologie(2, "1,1,1", "1,0,1", "1", "Propolis", false, 2, 0));
        list_posologies.add(new Posologie(3, "1,0,0", "1,1,0", "55", "Spirulina", false, 3, 0));
        list_posologies.add(new Posologie(4, "0,0,1", "1,1,1", "6", "Spirulina", false, 4, 0));
    }

    //recuperer la liste
    public List<Posologie> getListPosologies() {
        if (list_posologies == null) list_posologies = new ArrayList<>();
        return list_posologies;
    }

    //taille posologie
    public int taillePosologie() {
        return getListPosologies().size();
    }

   //get posologie par index
    public Posologie getPosologieParIndex(int position) {
        return getListPosologies().get(position);
    }

    //ajouter posologie
    public void ajouterPoslogie(Posologie posologie) {
        long max = getmaxid();
        posologie.setId(max++);
        getListPosologies().add(posologie);

    }

    //supprimer
    public void supprimerPoslogie(Posologie m) {
        getListPosologies().remove(m);
    }

   //modifier
    public void modifierPosologie(int position, Posologie p) {
        Posologie old = getListPosologies().get(position);
        getListPosologies().remove(old);
        getListPosologies().add(position, p);
        p.setId(old.getId());
    }

    //get index de posologie
    public int geindexposologie(Posologie p) {
        int index = 0;

        for (int i = 0; i < getListPosologies().size(); i++) {
            if (p.getId() == getListPosologies().get(i).getId()) {
                index = i;
            }
        }

        return index;
    }

    //get id max de la liste des posologies
    public long getmaxid() {
        long max = 0;
        for (int i = 0; i < getListPosologies().size(); i++) {
            if (getListPosologies().get(i).getId() > max) {
                max = getListPosologies().get(i).getId();
            }

        }
        return max;
    }
}
