package kchaou.uha.fr.test.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.databinding.OrdonnanceItemBinding;
import kchaou.uha.fr.test.fragments.ListOrdonnanceFragment;
import kchaou.uha.fr.test.models.Ordonnance;


public class OrdonnanceAdapterActive extends RecyclerView.Adapter<OrdonnanceAdapterActive.HolderView> {


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrdonnanceItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.ordonnance_item, parent, false);
        return new HolderView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {

        Ordonnance ordonnance = getOrdonnaceActive(ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances()).get(position);
        if (ordonnance.getEtat()) {
            holder.binding.activation.setChecked(true);
        } else {
            holder.binding.activation.setChecked(false);
        }
        holder.binding.setOrdonnance(ordonnance);
    }

    @Override
    public int getItemCount() {
        return getOrdonnaceActive(ListOrdonnanceFragment.controller_listener.getOrdonnanceController().getListOrdonnances()).size();
    }

    public List<Ordonnance> getOrdonnaceActive(List<Ordonnance> liste_ordonnace) {
        List<Ordonnance> ordonnace_active = new ArrayList<Ordonnance>();
        for (int i = 0; i < liste_ordonnace.size(); i++) {
            if (liste_ordonnace.get(i).getEtat()) {
                ordonnace_active.add(liste_ordonnace.get(i));
            }
        }

        return ordonnace_active;
    }

    public class HolderView extends RecyclerView.ViewHolder {
        OrdonnanceItemBinding binding;


        public HolderView(OrdonnanceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setDoClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getLayoutPosition();
                }
            });
            binding.setDoLongClick(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {

                    final int position = getLayoutPosition();
                    v.animate().setDuration(1000).alpha(0)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemRemoved(position);
                                    v.setAlpha(1);
                                }
                            });
                    return false;
                }
            });
        }
    }
}

