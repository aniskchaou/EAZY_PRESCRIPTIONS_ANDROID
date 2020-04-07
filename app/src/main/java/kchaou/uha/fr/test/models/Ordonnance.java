package kchaou.uha.fr.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ordonnance  implements Parcelable {

   //proprietes
    private long id;
    private Date _date;
    private String _duree;
    private String _nom;
    private boolean _etat;
    private String _duree_restante;

    //constructeurs
    public Ordonnance(long id, Date date, String duree, String nom, boolean etat) {
        this.id = id;
        this._date = date;
        this._duree = duree;
        this._nom = nom;
        this._etat = etat;
        this._duree_restante=calculerDureeRestante(this._date,Integer.valueOf(this._duree));
    }

    //getter et setters

    //id
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    //date
    public Date getDate() {
        return _date;
    }
    public void setDate(Date date) {
        this._date = date;
    }

    //duree
    public String getDuree() {
        return _duree;
    }
    public void setDuree(String duree) {
        this._duree = duree;
    }

    //nom
    public String getNom() {
        return _nom;
    }
    public void setNom(String nom) {
        this._nom = nom;
    }

    //etat
    public boolean getEtat() {
        return _etat;
    }
    public void setEtat(boolean etat) {
        this._etat = etat;
    }

    //duree restante
    public String getDureeRestante() {
        return _duree_restante;
    }
    public void setDureeRestante(String duree_restante) {
        this._duree_restante = duree_restante;
    }


    //calculer la duree restante pour une ordonnance
    public String calculerDureeRestante(Date date, int duree)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, duree);
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dt = sdf.format(cal.getTime());
        return "Expiration "+dt;
    }


    //parcelable
    protected Ordonnance(Parcel in) {
        _duree = in.readString();
        _nom = in.readString();
        _etat = in.readByte() != 0;
    }

    public static final Creator<Ordonnance> CREATOR = new Creator<Ordonnance>() {
        @Override
        public Ordonnance createFromParcel(Parcel in) {
            return new Ordonnance(in);
        }

        @Override
        public Ordonnance[] newArray(int size) {
            return new Ordonnance[size];
        }
    };

    //callbacks parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_duree);
        dest.writeString(_nom);
        dest.writeByte((byte) (_etat ? 1 : 0));
    }

}
