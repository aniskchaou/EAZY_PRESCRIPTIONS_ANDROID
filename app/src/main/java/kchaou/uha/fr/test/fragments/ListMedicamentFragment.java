package kchaou.uha.fr.test.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.adapters.MedicamentAdapter;
import kchaou.uha.fr.test.databinding.FragmentMedicamentListBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.ListMedicamentListener;

public class ListMedicamentFragment extends Fragment  {


    //proprietes
    public static  ListMedicamentListener listener;
    public static ControllerListener controller_listener;
    private FragmentMedicamentListBinding binding;


    //constructeur
    public ListMedicamentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        //binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_medicament_list, container, false);

        //action ajouter medicament
        binding.setDoAdd (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestAddMedicament();
            }
        });

        //set adapter medicament
        binding.list.setAdapter(new MedicamentAdapter());


        return binding.getRoot();
    }

    //callback attacher fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //initialiser listeners
        if (context instanceof ListMedicamentListener) {
            listener = (ListMedicamentListener) context;
            controller_listener=(ControllerListener)context;
        } else {
            throw new RuntimeException(this.toString() + "doit implementer ListMedicamentListener");
        }

        //titre
        controller_listener.setTitle("Medicaments");
    }


    //callback detacher
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    //nouvelle instance de fragment
    public static ListMedicamentFragment newInstance() {
        ListMedicamentFragment fragment = new ListMedicamentFragment();
        return fragment;
    }

}
