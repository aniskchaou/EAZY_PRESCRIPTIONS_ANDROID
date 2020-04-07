package kchaou.uha.fr.test.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.adapters.MedicamentSpinnerAdapter;
import kchaou.uha.fr.test.adapters.OrdonnanceSpinnerAdapter;

import kchaou.uha.fr.test.databinding.FragmentPosologieEditBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.EditPosologieListener;

import kchaou.uha.fr.test.models.Posologie;
import kchaou.uha.fr.test.models.PosologieAlternative;

public class EditPosologieFragment extends Fragment implements DatePickerDialog.OnDateSetListener,EditPosologieListener {

    //listener
    public static EditPosologieListener listener;


    //proprietes
    private  int position;
    private Posologie posologie;
    private FragmentPosologieEditBinding binding;
    public static  ControllerListener controller_listener;

    //constructeur
    public EditPosologieFragment() {
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
        //data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posologie_edit, container, false);

       //spinner medicament
         MedicamentSpinnerAdapter ma=new MedicamentSpinnerAdapter(getActivity());
        binding.medicament.setAdapter(ma);

        //spinner ordonnance
        OrdonnanceSpinnerAdapter oa=new OrdonnanceSpinnerAdapter(getActivity());
        binding.ordonnanceId.setAdapter(oa);

        //disactive les spinner nombre fois et nombre heure
        binding.nombrefois.setEnabled(false);
        binding.nombreheures.setEnabled(false);

        //active posologie alternative
         binding.setPosologieAlternative(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(binding.posologieAlternative.isChecked())
                 {

                     activerPosologieAlternative();

                 }else
                 {
                     desactivePosologieAlternative();
                 }
             }
         });


        if (getArguments() != null && getArguments().containsKey("position")) {
            //recuperer posologie
            position = getArguments().getInt("position");
            posologie = controller_listener.getPosologieController().getPosologieParIndex(position);
            binding.posologieAlternative.setEnabled(false);
            //set etat checkbox
            String[] tab_quand=posologie.getQuand().split(",");
            String[] tab_comment=posologie.getComment().split(",");
            if (!Arrays.asList(tab_quand).contains("2")) {
                setEtatCheckBox(tab_quand, tab_comment);
            }

        } else {
            //ajouter posologie
            binding.posologieAlternative.setEnabled(true);
            position = -1;
            posologie = new Posologie(0, "0,0,0", "0,0,0", "", "", true,0,0);
        }



        //action on change item medicament
        binding.ordonnanceId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               posologie.setOrdonnance(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //action on change item ordonnance
        binding.medicament.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String nom_medicament=controller_listener.getOrdonnanceController().getListOrdonnances().get(position).getNom();
                posologie.setMedicament(nom_medicament);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.setPosologie(posologie);


        return binding.getRoot();
    }

    //etat checkbox
    private void setEtatCheckBox(String[] tab_quand,String[] tab_comment) {
       if(tab_quand.length==3) {
           if (tab_quand[0].equals("1")) {
               binding.matin.setChecked(true);
           }

           if (tab_quand[1].equals("1")) {
               binding.midi.setChecked(true);
           }


           if (tab_quand[2].equals("1")) {
               binding.soir.setChecked(true);
           }


           if (tab_comment[0].equals("1")) {
               binding.avantRepas.setChecked(true);
           }

           if (tab_comment[1].equals("1")) {
               binding.pendantRepas.setChecked(true);
           }


           if (tab_comment[2].equals("1")) {
               binding.apresRepas.setChecked(true);
           }

       }
    }

    //attacher le fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         //initialiser listener
        if (this instanceof EditPosologieListener) {
            listener = (EditPosologieListener) this;
            controller_listener=(ControllerListener)context;
        } else {
            throw new RuntimeException(context.toString() + " doit implementé EditPosologieListener ");
        }
        //titre
        controller_listener.setTitle("Editer Posologie");
    }


    //detacher le fragment
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //creer instance
    public static EditPosologieFragment newInstance(int position) {
        EditPosologieFragment fragment = new EditPosologieFragment();
        if (position != -1) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


   //changer la date par default
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
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
        case R.id.doadd :

            posologie.setQuand(formatQuand());
            posologie.setComment(formatComment());
            listener.onValidPosologie(position, posologie);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //formatter
    public String formatComment()
    {
        String apres_repas;
        String pendant_repas;
        String avant_repas;
        if(binding.apresRepas.isChecked())
        {
            apres_repas="1";
        }else {
            apres_repas="0";
        }

        if(binding.pendantRepas.isChecked())
        {
            pendant_repas="1";
        }else {
            pendant_repas="0";
        }

        if(binding.avantRepas.isChecked())
        {
            avant_repas="1";
        }else {
            avant_repas="0";
        }
       return avant_repas+","+pendant_repas+","+apres_repas;
    }

    public String formatQuand()
    {
        String matin;
        String midi;
        String soir;
        if(binding.matin.isChecked())
        {
            matin="1";
        }else {
            matin="0";
        }
        if(binding.midi.isChecked())
        {
            midi="1";
        }else {
            midi="0";
        }
        if(binding.soir.isChecked())
        {
            soir="1";
        }else {
            soir="0";
        }
        return matin+","+midi+","+soir;
    }




    @Override
    public void onValidPosologie(int position, Posologie c) {
        if (position == -1) {
            //si il sagit d'une ordonnace alternative
            if(binding.posologieAlternative.isChecked())
            {
                //instantialtion de posologie alternative qui herite de poslogie
                Posologie posologie_alternative=new PosologieAlternative(String.valueOf(binding.nombrefois.getSelectedItem()),
                        String.valueOf(binding.nombreheures.getSelectedItem()),
                                String.valueOf(binding.nombreheures.getSelectedItem()),
                                 Integer.valueOf((int) binding.ordonnanceId.getSelectedItemId()));
                controller_listener.getPosologieController().ajouterPoslogie(posologie_alternative);
                Toast.makeText(getActivity(), "Posologie alternative  modifié avec succés",
                        Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(getActivity(), "Posologie est ajouté avec succés",
                        Toast.LENGTH_LONG).show();
                controller_listener.getPosologieController().ajouterPoslogie(c);
            }



        } else {
            controller_listener.getPosologieController().modifierPosologie(position, c);
        }
        //retour
        controller_listener.doBack();
    }


    public void activerPosologieAlternative(){
        binding.nombrefois.setEnabled(true);
        binding.nombreheures.setEnabled(true);

        binding.apresRepas.setEnabled(false);
        binding.avantRepas.setEnabled(false);
        binding.pendantRepas.setEnabled(false);

        binding.matin.setEnabled(false);
        binding.midi.setEnabled(false);
        binding.soir.setEnabled(false);

        binding.combien.setEnabled(false);
    }

    public void desactivePosologieAlternative(){
        binding.nombrefois.setEnabled(false);
        binding.nombreheures.setEnabled(false);

        binding.apresRepas.setEnabled(true);
        binding.avantRepas.setEnabled(true);
        binding.pendantRepas.setEnabled(true);

        binding.matin.setEnabled(true);
        binding.midi.setEnabled(true);
        binding.soir.setEnabled(true);

        binding.combien.setEnabled(true);
    }

}
