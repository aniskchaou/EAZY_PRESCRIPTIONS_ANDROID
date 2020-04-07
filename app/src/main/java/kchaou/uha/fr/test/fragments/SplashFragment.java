package kchaou.uha.fr.test.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.databinding.FragmentSplashBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.SplashListener;

public class SplashFragment extends Fragment {
    public static ControllerListener controller_listener;
    //listener
    private SplashListener listener;
    private FragmentSplashBinding binding;


    //constructeur
    public SplashFragment() {
    }

    //nouvel instance
    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);

        //action boutton
        binding.setRequestMedicaments(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestListMedicaments();
            }
        });
        binding.setRequestOrdonnances(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestListOrdonnances();
            }
        });
        binding.setRequestPrefrences(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestPreferences();
            }
        });

        binding.setRequestQuitter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestQuit();
            }
        });
        return binding.getRoot();
    }

    //attacher le fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //initialiser le contexte
        if (context instanceof SplashListener) {
            listener = (SplashListener) context;
            controller_listener = (ControllerListener) context;
        } else {
            throw new RuntimeException(context.toString() + "doit implementer Splashlistener");
        }
        //titre
        controller_listener.setTitle("Eazy Prescriptions");
    }

    //detacher
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}
