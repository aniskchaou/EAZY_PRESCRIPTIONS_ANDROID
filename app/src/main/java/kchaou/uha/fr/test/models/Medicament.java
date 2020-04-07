package kchaou.uha.fr.test.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

public class Medicament implements Parcelable {

    //proprietes
    private long _id;
    private  String _nom_usage;
    private String _nom_molecule;
    private String _dose;
    private Bitmap _picture_emballage;
    private Bitmap _picture;
    private String _format;

    //constructeurs
    public Medicament(long id, String _nom_usage, String _nom_molecule, String _dose, Bitmap _picture,Bitmap _picture_emballage) {
        this._id = id;
        this._nom_usage = _nom_usage;
        this._nom_molecule = _nom_molecule;
        this._dose = _dose;
        this._picture = _picture;
        this._picture_emballage=_picture_emballage;
    }



    //getter setters

    // id
    public long getId() {
        return _id;
    }
    public void setId(long id) {
        this._id = id;
    }

    //nom_usage
    public String get_nom_usage() {
        return _nom_usage;
    }
    public void set_nom_usage(String _nom_usage) {
        this._nom_usage = _nom_usage;
    }

    //nom molecule
    public String get_nom_molecule() {
        return _nom_molecule;
    }
    public void set_nom_molecule(String _nom_molecule) {
        this._nom_molecule = _nom_molecule;
    }

    //dose
    public String get_dose() {
        return _dose;
    }
    public void set_dose(String _dose) {
        this._dose = _dose;
    }

    //photo emballage
    public Bitmap get_picture_emballage() {
        return _picture_emballage;
    }
    public void set_picture_emballage(Bitmap _picture_emballage) {
        this._picture_emballage = _picture_emballage;
    }

    //format
    public String get_format() {
        return _format;
    }
    public void set_format(String _format) {
        this._format = _format;
    }

    //photo produit
    public Bitmap getPicture() {
        return _picture;
    }
    public void setPicture(Bitmap _picture) {
        this._picture = _picture;
    }


    //recuperer une image depuis ressource
    public static Bitmap setImageFromRessource(Context context, int id)
    {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), id);
        return bm;
    }

    //pacelable
    protected Medicament(Parcel in) {
        _id = in.readLong();
        _nom_usage = in.readString();
        _nom_molecule = in.readString();
        _dose = in.readString();
        _picture_emballage = in.readParcelable(Bitmap.class.getClassLoader());
        _picture = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Medicament> CREATOR = new Creator<Medicament>() {
        @Override
        public Medicament createFromParcel(Parcel in) {
            return new Medicament(in);
        }

        @Override
        public Medicament[] newArray(int size) {
            return new Medicament[size];
        }
    };


    // callbacks
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(_nom_usage);
        dest.writeString(_nom_molecule);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
