package kchaou.uha.fr.test.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.controllers.OrdonnanceController;
import kchaou.uha.fr.test.fragments.ListOrdonnanceFragment;


public class OrdonnanceSpinnerAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private String[] ordonnances;

    public OrdonnanceSpinnerAdapter(Activity activity) {
        this.activity = activity;
        this.inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ordonnances=remplir();

    }

    @Override
    public int getCount() {
        return ordonnances.length;
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
        View row=inflater.inflate(R.layout.row_spinner_ordonnance,null);
        TextView elem=row.findViewById(R.id.ordonnance);
        elem.setText(ordonnances[position]);
        return row;
    }



    public String[] remplir()
    {
        String[] o=new String[ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances().size()];
        for (int i=0 ;i<ListOrdonnanceFragment.controller_listener.getOrdonnanceController().tailleOrdonnances();i++)
        {
            o[i]= String.valueOf(ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances().get(i).getNom());
        }
        return o;
    }
}

