package kchaou.uha.fr.test.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.adapters.OrdonnanceAdapter;
import kchaou.uha.fr.test.adapters.OrdonnanceAdapterActive;
import kchaou.uha.fr.test.controllers.Converters;
import kchaou.uha.fr.test.controllers.DateController;
import kchaou.uha.fr.test.controllers.NotificationController;
import kchaou.uha.fr.test.databinding.FragmentOrdonnanceListBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.ListOrdonnanceListener;
import kchaou.uha.fr.test.models.Preferences;

public class ListOrdonnanceFragment extends Fragment {

    //listener
    public static ListOrdonnanceListener listener;
    public static ControllerListener controller_listener;
    public static Context context;
    public OrdonnanceAdapter adapter;

    //proprietes
    private RecyclerView list;
    private FragmentOrdonnanceListBinding binding;


    //constructeur
    public ListOrdonnanceFragment() {


    }


    public  void determineEtatPrise() throws Exception {
        //formatter la date actuelle
        Date d = new Date();
        DateController dc=new DateController();

        //recuperer la date depuis preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Preferences preferences = new Preferences(settings);

        //convertir les preferences en format date
        Date date_matin = Converters.convertMatinToDate(preferences.getMatin());
        Date date_midi = Converters.convertMidiToDate(preferences.getMidi());
        Date date_soir = Converters.convertSoirToDate(preferences.getSoir());

        NotificationController nc;

        //pour chaque posologie
        for (int i = 0; i < controller_listener.getPosologieController().taillePosologie(); i++) {
            String quand_tab[] = controller_listener.getPosologieController().getPosologieParIndex(i).getQuand().split(",");
            String medicament=controller_listener.getPosologieController().getPosologieParIndex(i).getMedicament();


            //si posologie  contient le matin
            if (quand_tab[0] == "1") {

                   int etat_matin=dc.getEtatPosologie(d,date_matin);
                 controller_listener.getPosologieController().getPosologieParIndex(i).setEtatprise(etat_matin);

                 //verifier notification
                 if(dc.pushNotification(d,date_matin))
                 {
                     nc = new NotificationController("Alerte !", "il faut prendre le medicament "+medicament+" dans 30 minutes", getActivity().getBaseContext());
                     nc.setNotification(getActivity().getBaseContext());
                 }

                //si contient le midi
            } else if (quand_tab[1] == "1") {

                int etat_midi=dc.getEtatPosologie(d,date_midi);
                controller_listener.getPosologieController().getPosologieParIndex(i).setEtatprise(etat_midi);
                //verifier notification
                if(dc.pushNotification(d,date_matin))
                {
                    nc = new NotificationController("Alerte !", "il faut prendre le medicament "+medicament+" dans 30 minutes", getActivity().getBaseContext());
                    nc.setNotification(getActivity().getBaseContext());
                }
                //si contient le soir
            }if (quand_tab[2] == "1") {

                int etat_soir=dc.getEtatPosologie(d,date_soir);
                controller_listener.getPosologieController().getPosologieParIndex(i).setEtatprise(etat_soir);
                //verifier notification
                if(dc.pushNotification(d,date_matin))
                {
                    nc = new NotificationController("Alerte !", "il faut prendre le medicament "+medicament+" dans 30 minutes", getActivity().getBaseContext());
                    nc.setNotification(getActivity().getBaseContext());
                }

            }
        }


    }

    //nouvelle instance
    public static ListOrdonnanceFragment newInstance() {
        ListOrdonnanceFragment fragment = new ListOrdonnanceFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //etat prise+ lancer notification
        try {
            determineEtatPrise();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ordonnance_list, container, false);

        //action ajouter ordonnace
        binding.setDoAddOrdonnance(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestAddOrdonnance();
            }
        });

        //adapter ordonnace
        binding.list.setAdapter(new OrdonnanceAdapter());

        return binding.getRoot();
    }

    //attacher le fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = context;

        //initialiser listener
        if (context instanceof ListOrdonnanceListener) {
            listener = (ListOrdonnanceListener) context;
            controller_listener = (ControllerListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ListOrdonnanceFragment");
        }
        //titre
        controller_listener.setTitle("Ordonances");
    }

    //detacher
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ordonance_filtre, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    //clique filtre
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ordonnaces_active:
                //ordonnace active
                binding.list.setAdapter(new OrdonnanceAdapterActive());
                getActivity().setTitle("Ordonnances Actives");
                return true;
            case R.id.ordonnaces_tous:
                //tous les ordonnaces
                binding.list.setAdapter(new OrdonnanceAdapter());
                getActivity().setTitle("Tous les Ordonnances");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
