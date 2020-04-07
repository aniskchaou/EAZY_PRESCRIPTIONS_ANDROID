package kchaou.uha.fr.test.controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.models.Medicament;

public class MedicamentController {

    //proprietes medicament
    static long ID = 5;
    private List<Medicament> listeMedicament;

    //constructeur medicament
    public MedicamentController() {
        listeMedicament = new ArrayList<Medicament>();
    }


    //remplir la liste des medicament
    public void remplirMedicament(Context context) {
        //affecter une image par defaut dans drawable pour chaque image
        listeMedicament.add(new Medicament(0, "Doliprane", "HD2", "0", Medicament.setImageFromRessource(context, R.drawable.doliprane), Medicament.setImageFromRessource(context, R.drawable.medicament1)));
        listeMedicament.add(new Medicament(1, "Fervex", "HSP+M", "65", Medicament.setImageFromRessource(context, R.drawable.ferfex), Medicament.setImageFromRessource(context, R.drawable.medicament2)));
        listeMedicament.add(new Medicament(2, "Propolis", "CS", "5", Medicament.setImageFromRessource(context, R.drawable.propolis), Medicament.setImageFromRessource(context, R.drawable.medicament3)));
        listeMedicament.add(new Medicament(3, "Spirulina", "TEGB", "12", Medicament.setImageFromRessource(context, R.drawable.spirulina), Medicament.setImageFromRessource(context, R.drawable.medicament3)));
        listeMedicament.add(new Medicament(4, "Spirulina", "TEGB", "12", Medicament.setImageFromRessource(context, R.drawable.spirulina), Medicament.setImageFromRessource(context, R.drawable.medicament3)));
    }

    //getter list
    public List<Medicament> getListeMedicaments() {
        if (listeMedicament == null) listeMedicament = new ArrayList<>();
        return listeMedicament;
    }

    //taille de la liste medicament
    public int tailleMedicament() {
        return getListeMedicaments().size();
    }

    //recuperer un medicament
    public Medicament getMedicamentParIndice(int position) {
        return getListeMedicaments().get(position);
    }

    //ajouter un medicament dans la liste
    public void ajouterMedicament(Medicament medicament) {
        getListeMedicaments().add(medicament);
        medicament.setId(ID++);
    }

    //supprimer medicament
    public void supprimerMedicament(Medicament m) {
        getListeMedicaments().remove(m);
    }


    //mise a jour de medicament
    public void modifierMedicament(int p, Medicament nouveau) {
        Medicament old = getListeMedicaments().get(p);
        getListeMedicaments().remove(old);
        getListeMedicaments().add(p, nouveau);
        nouveau.setId(old.getId());
    }
}
