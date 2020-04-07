package kchaou.uha.fr.test.interfaces;

import kchaou.uha.fr.test.controllers.MedicamentController;
import kchaou.uha.fr.test.controllers.OrdonnanceController;
import kchaou.uha.fr.test.controllers.PosologieController;

public interface ControllerListener {

    PosologieController getPosologieController();
    OrdonnanceController getOrdonnanceController();
    MedicamentController getMedicamentController();
    void doBack();
    void setTitle(String title);
}
