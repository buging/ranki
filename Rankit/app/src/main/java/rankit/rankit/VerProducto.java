package rankit.rankit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by miguel on 07-03-16.
 */
public class VerProducto extends AppCompatActivity {

    private TextView tv_nombre;
    private TextView tv_precio;
    private RatingBar rb_puntuacion;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.layout_ver_graffiti);
        setContentView(R.layout.layout_ver_producto);
        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_precio = (TextView) findViewById(R.id.tv_precio);
        rb_puntuacion = (RatingBar) findViewById(R.id.valoracion);
        foto = (ImageView) findViewById(R.id.iv_foto);

        rb_puntuacion.setRating(3);

        Intent in = getIntent();
        Bundle bolsa = in.getExtras();
        tv_nombre.setText(bolsa.getString("nombre"));
        //nombre_graffiti = bolsa.getString("nombre");
        tv_precio.setText("Precios: "+bolsa.getString("precio").replace("[","").replace("]",""));

        Drawable myDrawable = getResources().getDrawable(R.drawable.papasfritas);
        foto.setImageDrawable(myDrawable);

    }



}
