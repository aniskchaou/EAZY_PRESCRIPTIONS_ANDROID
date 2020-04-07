package kchaou.uha.fr.test.controllers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ImageController {

    private float ratioX ;
    private float ratioY ;
    private float milieuX ;
    private float milieuY ;
    private Bitmap nouvelle_image;

    //image haute qualité
    public Bitmap ameliorer_qualite_image(Bitmap bitmap, int nouvellargeur, int nouvellehouteur) {

        //creer une image bitmap vide
         nouvelle_image = Bitmap.createBitmap(nouvellargeur, nouvellehouteur, Bitmap.Config.ARGB_8888);

        //parametrage de mesure
         ratioX = nouvellargeur / (float) bitmap.getWidth();
         ratioY = nouvellehouteur / (float) bitmap.getHeight();
         milieuX = nouvellargeur / 2.0f;
         milieuY = nouvellehouteur / 2.0f;

        //redimensionner image
        Matrix matrice = new Matrix();
        //definir les dimension
        matrice.setScale(ratioX, ratioY, milieuX, milieuY);

        //dessiner canvas
        Canvas canvas = new Canvas(nouvelle_image);
        //affecter l'image dans canvas
        canvas.setMatrix(matrice);

        //dessiner la nouvelle image
        canvas.drawBitmap(bitmap, milieuX - bitmap.getWidth() / 2, milieuY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return nouvelle_image;

    }


}
