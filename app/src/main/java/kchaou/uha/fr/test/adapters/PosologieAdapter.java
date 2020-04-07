package kchaou.uha.fr.test.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kchaou.uha.fr.test.R;
import kchaou.uha.fr.test.databinding.PosologieItemBinding;
import kchaou.uha.fr.test.fragments.EditOrdonnanceFragment;
import kchaou.uha.fr.test.models.Ordonnance;
import kchaou.uha.fr.test.models.Posologie;
import kchaou.uha.fr.test.models.PosologieAlternative;


public class PosologieAdapter extends RecyclerView.Adapter<PosologieAdapter.HolderView> {


    public Ordonnance ordonnance_id;
    List<Posologie> list_posologie = new ArrayList<>();


    public PosologieAdapter(Ordonnance ordonnance_id) {
        super();
        this.ordonnance_id = ordonnance_id;
        filtrer();
    }

    //filtrer
    private void filtrer() {
        for (int i = 0; i < EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().size(); i++) {
            if (EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i).getOrdonnance() == ordonnance_id.getId()) {
                if (EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i).isAlternative() == true && EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i) instanceof PosologieAlternative) {
                    EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i).setQuand(((PosologieAlternative) EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i)).get_nombre_fois() + "," + ((PosologieAlternative) EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i)).get_nombre_heure());
                }
                list_posologie.add(EditOrdonnanceFragment.controller_listener.getPosologieController().getListPosologies().get(i));

            }
        }
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PosologieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.posologie_item, parent, false);
        return new HolderView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        Posologie posologie = list_posologie.get(position);
        holder.binding.setPosologie(posologie);

    }

    @Override
    public int getItemCount() {
        return list_posologie.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {
        PosologieItemBinding binding;


        public HolderView(PosologieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.setDoClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getLayoutPosition();
                    int index = EditOrdonnanceFragment.controller_listener.getPosologieController().geindexposologie(list_posologie.get(position));
                    EditOrdonnanceFragment.listener.requestEditPosologie(index);
                }
            });



            binding.setDoLongClick(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {


                    AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setMessage("Voulez vous supprimer cet Posologie ?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final int position = getLayoutPosition();
                                    v.animate().setDuration(1000).alpha(0)
                                            .withEndAction(new Runnable() {
                                                @Override
                                                public void run() {
                                                    final int position = getLayoutPosition();

                                                    int index = EditOrdonnanceFragment.controller_listener.getPosologieController().geindexposologie(list_posologie.get(position));
                                                  Posologie  posologie=EditOrdonnanceFragment.controller_listener.getPosologieController().getPosologieParIndex(index);
                                                  list_posologie.remove(posologie);
                                                    EditOrdonnanceFragment.controller_listener.getPosologieController().supprimerPoslogie(posologie);
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

