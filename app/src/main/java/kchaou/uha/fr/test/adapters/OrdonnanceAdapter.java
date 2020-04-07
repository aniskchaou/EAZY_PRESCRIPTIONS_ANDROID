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
import kchaou.uha.fr.test.databinding.OrdonnanceItemBinding;
import kchaou.uha.fr.test.fragments.ListOrdonnanceFragment;
import kchaou.uha.fr.test.models.Ordonnance;


public class OrdonnanceAdapter extends RecyclerView.Adapter<OrdonnanceAdapter.HolderView> {

    int p;
    HolderView h;

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrdonnanceItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.ordonnance_item, parent, false);

        return new HolderView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        Ordonnance ordonnance = ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances().get(position);
        if (ordonnance.getEtat()) {
            holder.binding.activation.setChecked(true);
        } else {
            holder.binding.activation.setChecked(false);
        }


        holder.binding.setOrdonnance(ordonnance);
    }

    @Override
    public int getItemCount() {
        return ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances().size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        final OrdonnanceItemBinding binding;


        public HolderView(final OrdonnanceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setDoClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ListOrdonnanceFragment.listener.requestEditOrdonnance(position);
                }
            });
            binding.setChangeActive(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (binding.activation.isChecked()) {

                        ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances().get(getLayoutPosition()).setEtat(true);

                    } else {

                        ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances().get(getLayoutPosition()).setEtat(false);

                    }
                }
            });

            binding.setDoLongClick(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {


                    AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setMessage("Voulez vous supprimer cet Ordonnance ?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final int position = getLayoutPosition();

                                    v.animate().setDuration(1000).alpha(0)
                                            .withEndAction(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ListOrdonnanceFragment.listener.requestRemoveOrdonnance(position);
                                                    notifyItemRemoved(position);
                                                    v.setAlpha(1);
                                                }
                                            });

                                    dialog.dismiss();

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

