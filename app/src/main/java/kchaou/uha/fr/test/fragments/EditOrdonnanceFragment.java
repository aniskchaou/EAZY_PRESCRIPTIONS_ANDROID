package kchaou.uha.fr.test.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.adapters.PosologieAdapter;
import kchaou.uha.fr.test.databinding.FragmentOrdonnanceEditBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.EditOrdonnaceListener;
import kchaou.uha.fr.test.models.Ordonnance;

public class EditOrdonnanceFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    //listener
    public static EditOrdonnaceListener listener;
    public static ControllerListener controller_listener;

    //proprietes
    private int position;
    private Ordonnance ordonnance;
    private FragmentOrdonnanceEditBinding binding;

    //constructeur
    public EditOrdonnanceFragment() {
    }

    //creer instance
    public static EditOrdonnanceFragment newInstance(int position) {
        EditOrdonnanceFragment fragment = new EditOrdonnanceFragment();
        if (position != -1) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    //creer fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activer menu
        setHasOptionsMenu(true);
    }

    //creer view
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ordonnance_edit, container, false);

        //action changer la date
        binding.setChangeDate(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerFragment.newInstance(ordonnance.getDate(), EditOrdonnanceFragment.this);
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        //action add posologie
        binding.setDoAddPosologie(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestAddPosologie();

            }
        });




        //recuperer les arguments
        if (getArguments() != null && getArguments().containsKey("position")) {
            position = getArguments().getInt("position");
            ordonnance = controller_listener.getOrdonnanceController().getOrdonnanceParIndex(position);

            //etat ordonnance
            if (ordonnance.getEtat()) {
                binding.active.setChecked(true);
            } else {
                binding.active.setChecked(false);
            }

           //desactiver modification des champs
           binding.duree.setFocusable(false);
           binding.nom.setFocusable(false);


            //liste posologie
            binding.list.setAdapter(new PosologieAdapter(ordonnance));


        } else {
            //pour ajouter ordonnance
            position = -1;
            ordonnance = new Ordonnance(10, new Date(), "1", "", true);
        }

        //action clique sur spinner activer
        binding.setChangeActive(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (binding.active.isChecked()) {
                    ordonnance.setEtat(true);
                } else {
                    ordonnance.setEtat(false);
                }
            }
        });

        binding.setOrdonnance(ordonnance);


        return binding.getRoot();
    }

    //attacher fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         //initialiser le listener
        if (context instanceof EditOrdonnaceListener) {
            listener = (EditOrdonnaceListener) context;
            controller_listener = (ControllerListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EditOrdonnanceListener");
        }
        //titre
        controller_listener.setTitle("Editer Ordonnance");
    }

    //detacher fragment
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    //creer menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //clique sur boutton ajouter
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.doadd:
                listener.onValidOrdonnance(position, ordonnance);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //choisir date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        ordonnance.setDate(c.getTime());
        binding.setOrdonnance(ordonnance);
    }


}
