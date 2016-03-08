package rankit.rankit;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


/**
 * Created by miguel on 07-03-16.
 */
public class Consultas {

    private String inicio = "http://ec2-54-233-92-75.sa-east-1.compute.amazonaws.com/api";


    public List<List<String>> listCategorias(){
        try {
            String ruta2 = inicio + "/categories";
            URL url = new URL(ruta2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            ruta2 = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            if (ruta2 == "[]") {
                return null;
            }else{
                JSONArray ja = new JSONArray(ruta2);
                List<List<String>>  result = new ArrayList<List<String>>();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject row = ja.getJSONObject(i);
                    List<String> agr = new ArrayList<String>();
                    if(row.length() == 2){
                        agr.add(row.getString("id"));
                        agr.add(row.getString("name"));
                    }else{
                        agr.add(row.getString("id"));
                        agr.add(row.getString("name"));
                        agr.add(row.getString("categoryId"));
                    }
                    result.add(agr);
                }
                return result;
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<List<String>> listProductos(String id){
        try {
            String ruta2 = inicio + "/categories/"+id+"/products";
            URL url = new URL(ruta2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            ruta2 = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            if (ruta2 == "[]") {
                return null;
            }else{
                JSONArray ja = new JSONArray(ruta2);
                System.out.println(ja);
                List<List<String>>  result = new ArrayList<List<String>>();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject row = ja.getJSONObject(i);
                    List<String> agr = new ArrayList<String>();
                    agr.add(row.getString("name"));
                    agr.add(row.getString("prices"));
                    result.add(agr);
                }
                return result;
            }
        } catch (MalformedURLException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
