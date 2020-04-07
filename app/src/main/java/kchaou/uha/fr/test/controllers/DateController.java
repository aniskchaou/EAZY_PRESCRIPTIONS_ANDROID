package kchaou.uha.fr.test.controllers;

import java.util.Date;

public   class DateController {



    public static Date ajouter4secondes(Date date)
    {
        final long ONE_SECONDE_IN_MILLIS = 6000;

        long t = date.getTime();
        Date d = new Date(t + (4 * ONE_SECONDE_IN_MILLIS));
        return d;
    }

    public static Date soustraire30min(Date date)
    {
        final long ONE_MINUTE_IN_MILLIS = 60000;
        long t = date.getTime();
        Date d = new Date(t - (30 * ONE_MINUTE_IN_MILLIS));
        return d;
    }

    public boolean pushNotification(Date d , Date date_jour)
    {
       boolean notification;
       //comparer la date  courante avec la date de jour -30 minutes
        if(d.equals(soustraire30min(date_jour)))
        {
            notification= true;
        }else
        {
            notification= false;
        }

        return notification;
    }

    public int getEtatPosologie(Date d , Date date_jour)
    {
        int etat=0;
        //immediatement
        if (d.equals(date_jour)) {
            //en rouge
            etat=2;
            //anterieur
        } else if (d.after(date_jour)) {
            //en vert
            etat=0;
            //plus tard
        } else {
            // en rouge
           etat=3;
        }
      return etat;
    }
}
