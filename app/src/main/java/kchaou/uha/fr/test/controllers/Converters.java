package kchaou.uha.fr.test.controllers;

import android.support.annotation.ColorRes;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import kchaou.uha.fr.test.R;

public class Converters {

    //formatter la date
    static private SimpleDateFormat outputFormat = null;

    static public String FormatDate(Date date) {
        if (outputFormat == null) {
            outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return outputFormat.format(date);
    }

    static public String FormatTime(Date date) {
        if (outputFormat == null) {
            outputFormat = new SimpleDateFormat("hh:mm");
        }
        return outputFormat.format(date);

    }

    //colorer etat posologie
    @ColorRes
    static public int FormatPrise(int prise) {
        int colorId;
        switch (prise) {
            case 0:
                colorId = R.color.anterieur;
                break;
            case 1:
                colorId = R.color.prochain;
                break;
            case 2:
                colorId = R.color.immediatement;
                break;
            case 3:
                colorId = R.color.tard;
                break;
            default:
                colorId = R.color.neutral;
                break;
        }
        return colorId;
    }

    //formatter checckbox
    static public String FormatQuand(String quand) {
        if (quand != null) {
            String[] quandarray = quand.split(",");
            String str = "";
            if (!Arrays.asList(quandarray).contains("1") && !Arrays.asList(quandarray).contains("0")) {

                return "posologie alternative avec " + quandarray[0] + "  et " + quandarray[1];
            } else {

                if (quandarray[0].equals("1")) {
                    str += "  Matin";
                } else {
                    str += "";
                }

                if (quandarray[1].equals("1")) {
                    str += " Midi";
                } else {
                    str += "";
                }


                if (quandarray[2].equals("1")) {
                    str += "  Soir";
                } else {
                    str += "";
                }
                return str;
            }

        } else {
            return "";
        }


    }


    static public String FormatComment(String comment) {
        if (comment != null) {
            String[] commentarray = comment.split(",");
            String str = "";

            if (commentarray[0].equals("1")) {
                str += "  Avant Repas";
            } else {
                str += "";
            }


            if (commentarray[1].equals("1")) {
                str += "  Pendant Repas";
            } else {
                str += "";
            }

            if (commentarray[2].equals("1")) {
                str += "  Après Repas";
            } else {
                str += "";
            }


            return str;
        } else {
            return "";
        }


    }



    //convertir date to string
    public static Date convertMatinToDate(String temps) throws Exception {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(d);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dtactuelle_temps_preferences_matin = dateTime + " " + temps + ":00";
        Date date_matin = format.parse(dtactuelle_temps_preferences_matin);
        return date_matin;
    }


    public static Date convertMidiToDate(String temps) throws Exception {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(d);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dtactuelle_temps_preferences_midi = dateTime + " " + temps + ":00";
        Date date_midi = format.parse(dtactuelle_temps_preferences_midi);
        return date_midi;
    }


    public static Date convertSoirToDate(String temps) throws Exception {
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = dateFormat.format(d);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dtactuelle_temps_preferences_soir = dateTime + " " + temps + ":00";
        Date date_soir = format.parse(dtactuelle_temps_preferences_soir);
        return date_soir;
    }


}
