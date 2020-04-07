package kchaou.uha.fr.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Date;
import java.util.TimeZone;

import kchaou.uha.fr.test.controllers.DateController;
import kchaou.uha.fr.test.controllers.MedicamentController;
import kchaou.uha.fr.test.controllers.NotificationController;
import kchaou.uha.fr.test.controllers.OrdonnanceController;
import kchaou.uha.fr.test.controllers.PosologieController;
import kchaou.uha.fr.test.fragments.EditMedicamentFragment;
import kchaou.uha.fr.test.fragments.EditOrdonnanceFragment;
import kchaou.uha.fr.test.fragments.EditPosologieFragment;
import kchaou.uha.fr.test.fragments.ListMedicamentFragment;
import kchaou.uha.fr.test.fragments.ListOrdonnanceFragment;
import kchaou.uha.fr.test.fragments.ShowPreferencesFragment;
import kchaou.uha.fr.test.fragments.SplashFragment;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.EditOrdonnaceListener;
import kchaou.uha.fr.test.interfaces.ListMedicamentListener;
import kchaou.uha.fr.test.interfaces.ListOrdonnanceListener;
import kchaou.uha.fr.test.interfaces.SplashListener;
import kchaou.uha.fr.test.models.Ordonnance;
import kchaou.uha.fr.test.models.Posologie;
import kchaou.uha.fr.test.models.Preferences;


public class MainActivity extends AppCompatActivity implements SplashListener, EditOrdonnaceListener, ControllerListener, ListMedicamentListener, ListOrdonnanceListener {

    //proprietes
    private MedicamentController medicament_controller;
    private OrdonnanceController ordonnance_controller;
    private PosologieController posologie_controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




            //notification
            NotificationController nc = new NotificationController("Eazy Prescriptions", "Bienvenue !", getBaseContext());
            nc.setNotification(getBaseContext());



        //remplir lites medicament
        medicament_controller = new MedicamentController();
        medicament_controller.remplirMedicament(getBaseContext());

        //remplir lites ordonnace
        ordonnance_controller = new OrdonnanceController();
        ordonnance_controller.remplirOrdonnaces();

        //remplir lites posologie
        posologie_controller = new PosologieController();
        posologie_controller.remplirPosologies();


        //preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Preferences preferences = new Preferences(settings);




        //lancer le splash fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = SplashFragment.newInstance();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }


    @Override
    public PosologieController getPosologieController() {
        return posologie_controller;
    }

    @Override
    public OrdonnanceController getOrdonnanceController() {
        return ordonnance_controller;
    }

    @Override
    public MedicamentController getMedicamentController() {
        return medicament_controller;
    }

    @Override
    public void doBack() {
        onBackPressed();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }




    //afficher le fragment ajoutercontact
    @Override
    public void requestAddMedicament() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = EditMedicamentFragment.newInstance(-1);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    //afficher le fragment editcontact
    @Override
    public void requestEditMedicament(int p) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment_edit = EditMedicamentFragment.newInstance(p);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment_edit);
        transaction.addToBackStack(fragment_edit.getClass().getName());
        transaction.commit();
    }

    //supprimer un medicament
    @Override
    public void requestRemoveMedicament(int p) {
        medicament_controller.getListeMedicaments().remove(p);
    }

    //ajouter ordonance
    @Override
    public void requestAddOrdonnance() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = EditOrdonnanceFragment.newInstance(-1);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    //editer ordonance
    @Override
    public void requestEditOrdonnance(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = EditOrdonnanceFragment.newInstance(position);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    //supprimer ordonance
    @Override
    public void requestRemoveOrdonnance(int position) {
        ordonnance_controller.getListOrdonnances().remove(position);
    }



    //valider
    @Override
    public void onValidOrdonnance(int position, Ordonnance c) {
        if (position == -1) {
            ;
            ordonnance_controller.ajouterOrdonnance(c);
            Toast.makeText(getBaseContext(), "Ordonnance est ajouté avec succés",
                    Toast.LENGTH_LONG).show();
        } else {
            ordonnance_controller.modifierOrdonnance(position, c);
            Toast.makeText(getBaseContext(), "Ordonnace est modifié  avec succés",
                    Toast.LENGTH_LONG).show();
        }
        onBackPressed();
    }

    //modifier posologie
    @Override
    public void requestEditPosologie(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment_edit__posologie = EditPosologieFragment.newInstance(position);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment_edit__posologie);
        transaction.addToBackStack(fragment_edit__posologie.getClass().getName());
        transaction.commit();
    }


    //ajouter posologie
    @Override
    public void requestAddPosologie() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = EditPosologieFragment.newInstance(-1);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    //list medicament
    @Override
    public void requestListMedicaments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = ListMedicamentFragment.newInstance();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    //liste ordonnance
    @Override
    public void requestListOrdonnances() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = ListOrdonnanceFragment.newInstance();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    //preferences
    @Override
    public void requestPreferences() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = ShowPreferencesFragment.newInstance();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    @Override
    public void requestQuit() {

        AlertDialog dialog = new AlertDialog.Builder(this).setMessage("Voulez vous quitter l'application ?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                        dialog.dismiss();


                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();








    }

}

