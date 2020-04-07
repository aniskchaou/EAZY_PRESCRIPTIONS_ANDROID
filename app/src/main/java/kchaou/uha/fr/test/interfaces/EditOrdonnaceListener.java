package kchaou.uha.fr.test.interfaces;

import kchaou.uha.fr.test.models.Ordonnance;


public interface EditOrdonnaceListener {

    void onValidOrdonnance(int position, Ordonnance c);
    void requestEditPosologie(int position);
    void requestAddPosologie();

}