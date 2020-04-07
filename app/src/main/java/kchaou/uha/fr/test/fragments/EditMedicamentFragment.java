package kchaou.uha.fr.test.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.controllers.ImageController;
import kchaou.uha.fr.test.databinding.FragmentMedicamentEditBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.EditMedicamentListener;
import kchaou.uha.fr.test.models.Medicament;

public class EditMedicamentFragment extends Fragment implements EditMedicamentListener {

    //listener
    private EditMedicamentListener listener;
    public static  ControllerListener controller_listener;

    //proprietes
    private static final int REQUEST_IMAGE_CAPTURE_PRODUIT = 1;
    private static final int REQUEST_IMAGE_CAPTURE_EMBALLAGE= 2;
    private int position;
    private Medicament medicament;
    private FragmentMedicamentEditBinding binding;
    private ImageController img_controller;

    //constructeur
    public EditMedicamentFragment() {
    }


    //creer fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activer le menu
        setHasOptionsMenu(true);
    }

    //creer le fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         img_controller=new ImageController();
        //data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medicament_edit, container, false);


        //action prendre photo produit
        binding.setPrendrePhotoProduit (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //ouvrir la camera pour prendre une photo
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_PRODUIT);
                }
            }
        });

       //action prendre photo emballage
        binding.setPrendrePhotoEmballage(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //ouvrir la camera pour prendre une photo
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_EMBALLAGE);
                }
            }
        });

        //recuperer les les arguments
        if (getArguments() != null && getArguments().containsKey("position")) {

            //pour modifier le medicament
            position = getArguments().getInt("position");

            //recuperer la position de medicament
            medicament = controller_listener.getMedicamentController().getMedicamentParIndice (position);
        } else {
            //pour creer un nouvel medicament
            position = -1;
            medicament = new Medicament(100,"","","",null,null);
        }

        //recuprer les champs
        binding.setMedicament(medicament);


        return binding.getRoot();
    }

    //attacher le fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //initialiser listeners
        if (this instanceof EditMedicamentListener) {
            listener = (EditMedicamentListener) this;
            controller_listener=(ControllerListener)context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EditMedicamentListener");
        }
        //titre
        controller_listener.setTitle("Editer Medicament");
    }

    //detacher fragment
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }



    //creer instance
    public static EditMedicamentFragment newInstance(int position) {
        EditMedicamentFragment fragment = new EditMedicamentFragment();
         //recuprer la position depuis intent
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
        //image produit
        if (requestCode == REQUEST_IMAGE_CAPTURE_PRODUIT && resultCode == Activity.RESULT_OK) {
            //recuperer image
            Bitmap img_low_quality=(Bitmap) data.getParcelableExtra("data");

            //ameliorer la taille
            Bitmap img_high_quality=img_controller.ameliorer_qualite_image(img_low_quality,150,150);

            medicament.setPicture(img_high_quality);
           //image emballage
        }else if (requestCode == REQUEST_IMAGE_CAPTURE_EMBALLAGE&& resultCode == Activity.RESULT_OK) {
           //recuperer image
            Bitmap img_low_quality=(Bitmap) data.getParcelableExtra("data");

            //ameliorer la taille
            Bitmap img_high_quality=img_controller.ameliorer_qualite_image(img_low_quality,150,150);

            medicament.set_picture_emballage(img_high_quality);

        }
        binding.setMedicament(medicament);
    }






    //creer menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    //valider action
    @Override
    public void valider(int position, Medicament c) {
        if (position == -1) {
            //ajouter image par default si l'utilisateur n'a pas choisi une image
            c.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.application_icon));

            //ajouter le produit
            ListMedicamentFragment.controller_listener.getMedicamentController().ajouterMedicament(c);

            //afficher message
            Toast.makeText(getActivity(), "Medicament est ajouté avec succés",
                    Toast.LENGTH_LONG).show();
        } else {

            //modifier le produit
            ListMedicamentFragment.controller_listener.getMedicamentController().modifierMedicament(position, c);

            //afficher message
            Toast.makeText(getActivity(), "Medicament est modifié avec succés",
                    Toast.LENGTH_LONG).show();
        }

        //retour
        getActivity().onBackPressed();
    }



    //clique sur boutton  menu ajouter
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ajouter medicament
        switch (item.getItemId()) {
        case R.id.doadd :
            //set format
            medicament.set_format(binding.format.getSelectedItem().toString());

            listener.valider(position, medicament);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
