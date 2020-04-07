package kchaou.uha.fr.test.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.databinding.FragmentPreferencesEditBinding;
import kchaou.uha.fr.test.interfaces.ControllerListener;
import kchaou.uha.fr.test.models.Posologie;
import kchaou.uha.fr.test.models.Preferences;

public class EditPreferencesFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {


    //proprietes
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static ControllerListener controller_listener;
    Preferences preferences;
    SharedPreferences.Editor editor;
    private int position;
    private Posologie posologie;
    private FragmentPreferencesEditBinding binding;
    private boolean bt_matin_iscliked = false;
    private boolean bt_midi_iscliked = false;
    private boolean bt_soir_iscliked = false;

    //constructeur
    public EditPreferencesFragment() {
        editor = null;
    }

    //creer instance EditContactFragment
    public static EditPreferencesFragment newInstance(int position) {
        EditPreferencesFragment fragment = new EditPreferencesFragment();

        //recuperer la position
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //creer view
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preferences_edit, container, false);


        //preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = settings.edit();
        preferences = new Preferences(settings);

        //clique date picker matin
        binding.setChangeMatin(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = TimePickerFragment.newInstance(new Date(), EditPreferencesFragment.this);

                newFragment.show(getChildFragmentManager(), "time_picker_matin");
                bt_matin_iscliked = true;
            }
        });

        //clique date picker midi
        binding.setChangeMidi(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = TimePickerFragment.newInstance(new Date(), EditPreferencesFragment.this);
                bt_midi_iscliked = true;
                newFragment.show(getChildFragmentManager(), "time_picker_midi");
            }
        });

        //clique date picker soir
        binding.setChangeSoir(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = TimePickerFragment.newInstance(new Date(), EditPreferencesFragment.this);
                bt_soir_iscliked = true;
                newFragment.show(getChildFragmentManager(), "time_picker_soir");
            }
        });
        return binding.getRoot();
    }

    //attacher le fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //initialiser listener
        controller_listener = (ControllerListener) context;
        controller_listener.setTitle("Editer Préférences");

    }

    //detacher le fragment
    @Override
    public void onDetach() {
        super.onDetach();

    }

    //clique sur boutton ajouter
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.doadd:
                //set preference
                ShowPreferencesFragment.preferences.setMatinPref(binding.matin.getText().toString(), editor);
                ShowPreferencesFragment.preferences.setMidiPref(binding.midi.getText().toString(), editor);
                ShowPreferencesFragment.preferences.setSoirPref(binding.soir.getText().toString(), editor);
                //afficher message
                Toast.makeText(getActivity(), "les préférences sont ajouté avec succés",
                        Toast.LENGTH_LONG).show();
                //retour
                controller_listener.doBack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        c.set(year, month, day, hourOfDay, minute, 0);
        FragmentManager fmg = getActivity().getSupportFragmentManager();

        //recuperer plusieurs timepicker
        if (bt_matin_iscliked) {
            //matin
            bt_matin_iscliked = false;
            binding.matin.setText(String.valueOf(c.getTime()).substring(11, 16));

        } else if (bt_midi_iscliked) {
            //midi
            binding.midi.setText(String.valueOf(c.getTime()).substring(11, 16));
            bt_midi_iscliked = false;

        } else if (bt_soir_iscliked) {
            //soir
            bt_soir_iscliked = false;
            binding.soir.setText(String.valueOf(c.getTime()).substring(11, 16));
        }


    }
}
