package kchaou.uha.fr.test.models;

public class PosologieAlternative extends Posologie  {

   //proprietes
    private String _nombre_fois;
    private String _nombre_heure;

    //construcreur
    public PosologieAlternative(String nombre_fois,String nombre_heure ,String medicament ,int id_ordonnance) {

      super(0, "2,2,2", "2,2,2", "0", medicament, true, id_ordonnance, 0);
     this._nombre_fois=nombre_fois;
     this._nombre_heure=nombre_heure;

    }



    //nombre de fois
    public String get_nombre_fois() {
        return _nombre_fois;
    }
    public void set_nombre_fois(String _nombre_fois) {
        this._nombre_fois = _nombre_fois;
    }

    //nombre heure
    public String get_nombre_heure() {
        return _nombre_heure;
    }
    public void set_nombre_heure(String _nombre_heure) {
        this._nombre_heure = _nombre_heure;
    }
}
