package kchaou.uha.fr.test.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.databinding.MedicamentItemBinding;
import kchaou.uha.fr.test.fragments.ListMedicamentFragment;
import kchaou.uha.fr.test.models.Medicament;

public class MedicamentAdapter extends RecyclerView.Adapter<MedicamentAdapter.HolderView> {


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicamentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.medicament_item, parent, false);
        return new HolderView(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        Medicament medicament = ListMedicamentFragment.controller_listener.getMedicamentController().getMedicamentParIndice(position);
        holder.binding.setMedicament(medicament);
    }


    @Override
    public int getItemCount() {
        return ListMedicamentFragment.controller_listener.getMedicamentController().tailleMedicament();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        MedicamentItemBinding binding;


        public HolderView(MedicamentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.setDoClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getLayoutPosition();
                    ListMedicamentFragment.listener.requestEditMedicament(position);
                }
            });


            binding.setDoLongClick(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {



                    AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setMessage("Voulez vous supprimer cet medicament ?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    final int position = getLayoutPosition();
                                    v.animate().setDuration(1000).alpha(0)
                                            .withEndAction(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ListMedicamentFragment.listener.requestRemoveMedicament(position);
                                                    notifyItemRemoved(position);
                                                    v.setAlpha(1);
                                                }
                                            });
                                }
                            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            }).setOnCancelListener(new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();

                                }
                            }).create();
                    dialog.show();


                    return false;
                }
            });
        }
    }

}
