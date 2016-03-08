package rankit.rankit;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguel on 07-03-16.
 */
public class Productos extends Fragment {

    private ListView lista;
    private Context context;
    private ProgressBar progress;
    private List<String> sistemas = new ArrayList<String>();
    private List<List<String>> datos;
    private List<String> categorias = new ArrayList<String>();
    private List<String> idcategorias = new ArrayList<String>();
    private List<String> subcategorias = new ArrayList<String>();
    private List<String> idsubcategorias =  new ArrayList<String>();
    private List<String> productos =  new ArrayList<String>();
    private List<List<String>> datosProductos =  new ArrayList<List<String>>();
    private List<List<String>> r;
    private int cont = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        lista = (ListView) rootView.findViewById(R.id.listViewGraffitis);
        context = rootView.getContext();
        progress = (ProgressBar) rootView.findViewById(R.id.progress_list);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, sistemas);


        lista.setAdapter(adaptador);

        progress.setVisibility(View.VISIBLE);
        new AgregarCategorias().execute("");

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent ver_graffiti = new Intent(context, VerGraffiti.class);
                startActivity(ver_graffiti);*/
                //Toast.makeText(getActivity(), "posicion: " + position, Toast.LENGTH_LONG).show();
                if(cont == 0) {
                    int i;

                    subcategorias.clear();
                    for (i = 0; i < r.size(); i++) {
                        List<String> sub = r.get(i);
                        if (sub.size() == 3 && sub.get(2).equals(idcategorias.get(position))) {
                            subcategorias.add(sub.get(1));
                            idsubcategorias.add(sub.get(0));
                        }
                    }

                    progress.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, subcategorias);
                    lista.setAdapter(adaptador);

                    progress.setVisibility(View.INVISIBLE);
                    cont = 1;

                }else if(cont == 1){
                    progress.setVisibility(View.VISIBLE);
                    new AgregarCategorias().execute(idsubcategorias.get(position));

                }else{
                    Intent verproducto = new Intent(context, VerProducto.class);
                    Bundle bolsa = new Bundle();
                    List<String> cosa = datosProductos.get(position);
                    bolsa.putString("precio", cosa.get(1));
                    bolsa.putString("nombre", cosa.get(0));
                    verproducto.putExtras(bolsa);
                    startActivity(verproducto);
                }
            }

        });

        return rootView;
    }


    private class AgregarCategorias extends AsyncTask<String, Void, String> {

        List<String> valores = new ArrayList<String>();

        @Override
        protected String doInBackground(String... urls) {
            if(cont == 0) {
                Consultas c = new Consultas();
                r = c.listCategorias();
                int i;
                for (i = 0; i < r.size(); i++) {
                    List<String> sub = r.get(i);
                    if (sub.size() == 2) {
                        valores.add(sub.get(1));
                        categorias.add(sub.get(1));
                        idcategorias.add(sub.get(0));
                    }
                }
            }else if(cont == 1){
                Consultas c = new Consultas();
                r = c.listProductos(urls[0]);
                System.out.println(r);
                int i;
                productos.clear();
                datosProductos.clear();
                for (i = 0; i < r.size(); i++) {
                    List<String> sub = r.get(i);
                    productos.add(sub.get(0));
                    List<String> agr = new ArrayList<String>();
                    agr.add(sub.get(0));
                    agr.add(sub.get(1));
                    datosProductos.add(agr);


                }
            }

            return null;
        }
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //Una vez terminada la consulta nos devolvera un string el cual será utilizado como arreglo para
        //marcar los lugares cercanos en el mapa de google maps
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        @Override
        protected void onPostExecute(String result) {

            if (cont == 0) {
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, valores);

                lista.setAdapter(adaptador);

                progress.setVisibility(View.INVISIBLE);
                cont = 0;

            }else if(cont == 1){
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, productos);

                lista.setAdapter(adaptador);

                progress.setVisibility(View.INVISIBLE);
                cont = 2;
            }
        }
    }

}
