package kchaou.uha.fr.test.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kchaou.uha.fr.test.MainActivity;
import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.controllers.Converters;
import kchaou.uha.fr.test.databinding.FragmentPreferencesShowBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.interfaces.ShowPreferencesListener;
import kchaou.uha.fr.test.models.Preferences;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ShowPreferencesFragment extends Fragment implements ShowPreferencesListener {

     //proprietes
     FragmentPreferencesShowBinding binding;
     public static Preferences preferences;

     //listener
     ShowPreferencesListener listener;
     public static ControllerListener controller_listener;

    //constructeur
    public ShowPreferencesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences=new Preferences(settings);

        //binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preferences_show, container, false);

        //set text
        binding.matin.setText(preferences.getMatin());
        binding.midi.setText(preferences.getMidi());
        binding.soir.setText(preferences.getSoir());

        //modifier preferences
       binding.setDoMofifierPreferences(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.requestModifierPreferences();
           }
       });

        return binding.getRoot();
    }
    //attacher le fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //listener
        listener=this;
        controller_listener=(ControllerListener)context;

        //titre
        controller_listener.setTitle("Préférences");
    }

    //detacher fragment
    @Override
    public void onDetach() {
        super.onDetach();

    }

    //nouvelle instance
    public static ShowPreferencesFragment newInstance() {
        ShowPreferencesFragment fragment = new ShowPreferencesFragment();
        return fragment;
    }


    @Override
    public void requestModifierPreferences() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = EditPreferencesFragment.newInstance (-1);
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }
}
