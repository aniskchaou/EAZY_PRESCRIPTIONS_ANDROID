package kchaou.uha.fr.test.models;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

public class Preferences implements Parcelable {

    //proprietes

   private String matin;
   private  String midi;
   private  String soir;


    //constructeurs
    public Preferences(String matin, String midi, String soir) {
        this.matin = matin;
        this.midi = midi;
        this.soir = soir;
    }

    public Preferences(SharedPreferences settings) {

        this.matin = settings.getString("MATIN", "7:00");
        this.midi = settings.getString("MIDI", "12:00");
        this.soir = settings.getString("SOIR", "22:00");

    }

    //set preferences matin
    public void setMatinPref(String matin,SharedPreferences.Editor editor) {
        this.matin = matin;
        editor.putString("MATIN", this.matin);
        editor.commit();
    }
    //set preferences midi
    public void setMidiPref(String midi,SharedPreferences.Editor editor) {
        this.midi = midi;
        editor.putString("MIDI", this.midi);
        editor.commit();
    }
    //set preferences soir
    public void setSoirPref(String soir,SharedPreferences.Editor editor) {
        this.soir = soir;

        editor.putString("SOIR", this.soir);
        editor.commit();
    }


    //getter et setters

    //matin
    public String getMatin() {
        return matin;
    }
    public void setMatin(String matin) {
        this.matin = matin;
    }

    //midi
    public String getMidi() {
        return midi;
    }
    public void setMidi(String midi) {
        this.midi = midi;
    }

    //soir
    public String getSoir() {
        return soir;
    }
    public void setSoir(String soir) {
        this.soir = soir;
    }



   //parcelable
    protected Preferences(Parcel in) {
        matin = in.readString();
        midi = in.readString();
        soir = in.readString();
    }

    public static final Creator<Preferences> CREATOR = new Creator<Preferences>() {
        @Override
        public Preferences createFromParcel(Parcel in) {
            return new Preferences(in);
        }

        @Override
        public Preferences[] newArray(int size) {
            return new Preferences[size];
        }
    };



    //callbacks serialisable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(matin);
        dest.writeString(midi);
        dest.writeString(soir);
    }
}
