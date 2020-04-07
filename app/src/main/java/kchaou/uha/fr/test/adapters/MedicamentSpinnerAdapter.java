package kchaou.uha.fr.test.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.controllers.MedicamentController;
import kchaou.uha.fr.test.fragments.EditOrdonnanceFragment;
import kchaou.uha.fr.test.interfaces.ControllerListener;


public class MedicamentSpinnerAdapter extends BaseAdapter {
   private Activity activity;
   private LayoutInflater inflater;
   private String[] medicaments;
   private ControllerListener  controller_listener;
    public MedicamentSpinnerAdapter(Activity activity) {
        controller_listener = (ControllerListener) activity;
        this.inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.medicaments = remplirListe();
    }

    @Override
    public int getCount() {
        return medicaments.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=inflater.inflate(R.layout.row_spinner_medicament,null);
        TextView elem=row.findViewById(R.id.titre);
        ImageView image=row.findViewById(R.id.image_spinner_medicament);
        elem.setText(medicaments[position]);
        image.setImageBitmap(EditOrdonnanceFragment.controller_listener.getMedicamentController().getMedicamentParIndice(position).getPicture());
        return row;
    }


    public String [] remplirListe()
    {
        //MedicamentController medicament_controller=new MedicamentController();
        //medicament_controller.remplirMedicament(activity);
        MedicamentController medicament_controller=controller_listener.getMedicamentController();
        String[] m=new String[medicament_controller.getListeMedicaments().size()];
        for (int i=0 ;i<medicament_controller.tailleMedicament();i++)
        {
            m[i]=medicament_controller.getListeMedicaments().get(i).get_nom_usage();
        }

       return m;
    }
}
