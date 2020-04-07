package kchaou.uha.fr.test.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Posologie implements Parcelable {

   //proprietes
    private long id;
    private String _quand;
    private String _comment;
    private String _combien;
    private String _medicament;
    private boolean _alternative;
    private long _ordonnance;
    private int _etatprise;


    //constructeur
    public Posologie(long id, String quand, String comment, String combien, String medicament, boolean alternative, long id_posologie,int etat_prise) {
        this.id = id;
        this._quand = quand;
        this._comment = comment;
        this._combien = combien;
        this._medicament = medicament;
        this._alternative = alternative;
        this._ordonnance=id_posologie;
        this._etatprise=etat_prise;

    }





   //getter et setter
    //id
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    //ordonnance
    public long getOrdonnance() {
        return _ordonnance;
    }
    public void setOrdonnance(long ordonnance) {
        this._ordonnance = ordonnance;
    }

    //quand
    public String getQuand() {
        return _quand;
    }
    public void setQuand(String quand) {
        this._quand = quand;
    }

    //comment
    public String getComment() {
        return _comment;
    }
    public void setComment(String comment) {
        this._comment = comment;
    }

    //combien
    public String getCombien() {
        return _combien;
    }
    public void setCombien(String combien) {
        this._combien = combien;
    }

    //medicament
    public String getMedicament() {
        return _medicament;
    }
    public void setMedicament(String medicament) {
        this._medicament = medicament;
    }

    //alternative
    public boolean isAlternative() {
        return _alternative;
    }
    public void setAlternative(boolean alternative) {
        this._alternative = alternative;
    }

    //etat prise
    public int getEtatprise() {
        return _etatprise;
    }
    public void setEtatprise(int etatprise) {
        this._etatprise = etatprise;
    }


    //parcelable
    protected Posologie(Parcel in) {
        _etatprise=in.readInt();
        _quand = in.readString();
        _comment = in.readString();
        _combien = in.readString();
        _medicament = in.readParcelable(Medicament.class.getClassLoader());
        _alternative = in.readByte() != 0;

    }

    public static final Creator<Posologie> CREATOR = new Creator<Posologie>() {
        @Override
        public Posologie createFromParcel(Parcel in) {
            return new Posologie(in);
        }

        @Override
        public Posologie[] newArray(int size) {
            return new Posologie[size];
        }
    };


    //callbacks parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_etatprise);
        dest.writeString(_quand);
        dest.writeString(_comment);
        dest.writeString(_combien);
        dest.writeString(_medicament);
        dest.writeByte((byte) (_alternative ? 1 : 0));

    }
}
