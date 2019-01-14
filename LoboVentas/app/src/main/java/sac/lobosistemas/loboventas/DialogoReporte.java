package sac.lobosistemas.loboventas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sac.lobosistemas.loboventas.io.LoboVentasApiAdapter;
import sac.lobosistemas.loboventas.io.LoboVentasApiService;
import sac.lobosistemas.loboventas.model.Emails;
import sac.lobosistemas.loboventas.ui.adapter.EmailAdapter;

public class DialogoReporte {

    LoboVentasApiService ApiService; //Para conectar con la API

    ArrayList<Emails> emails = new ArrayList<>(); //Para consultar los emails

    private EmailAdapter mAdapter; //Adaptador para los emails//
    private RecyclerView mRecyclerView;

    TextView lblEmails, lblOtro, lblCancelar;
    EditText txtEmail;
    Button btnEnviar;

    String email;

    public DialogoReporte(final Context context, String RUC){

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_reporte);

        lblEmails = (TextView) dialogo.findViewById(R.id.lblEmails);
        lblOtro = (TextView) dialogo.findViewById(R.id.lblOtro);
        lblCancelar = (TextView) dialogo.findViewById(R.id.lblCancelar);
        txtEmail = (EditText) dialogo.findViewById(R.id.txtEmail);
        btnEnviar = (Button) dialogo.findViewById(R.id.btnEnviar);

        lblCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString();

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setMessage("¿Está seguro de enviar el reporte a: "+ email +"?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogo.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        //-------------------------RecyclerView-------------------------//
        mRecyclerView = (RecyclerView) dialogo.findViewById(R.id.recycler_view_emails);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);

        //---------------------------Adaptador--------------------------//
        mAdapter= new EmailAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //------------------------------------Conexión con la API------------------------------------//
        ApiService = LoboVentasApiAdapter.getApiService();

        //------------------------------------RetroFit Emails--------------------------------------//
        Call<ArrayList<Emails>> callEmail = ApiService.getEmails(""+RUC);
        callEmail.enqueue(new Callback<ArrayList<Emails>>() {
            @Override
            public void onResponse(Call<ArrayList<Emails>> call, Response<ArrayList<Emails>> response) {
                if(response.isSuccessful()){
                    emails = response.body();
                    clickAdaptador(emails, context, dialogo);

                    if(emails.size() == 0){
                        ViewGroup.LayoutParams params = lblEmails.getLayoutParams();
                        params.height = 0;
                        lblEmails.setLayoutParams(params);

                        lblOtro.setText("Email:");
                        Log.d("emails","No hay emails");
                    } else {
                        lblOtro.setText("Otro:");
                        lblEmails.setText("Lista de Emails");
                        mAdapter.setmDataSet(emails); //Agregamos el adaptador al Recycler View//

                        Log.d("emails","Hay emails");
                    }
                    Log.d("onResponse emails","Se cargaron "+ emails.size() +" emails.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Emails>> call, Throwable t) {
                Log.d("onResponse emails","Algo salió mal: "+t.getMessage());
            }
        });

        dialogo.show();
    }

    //-----------------Mostrar Reportes de la Empresa-----------------//
    private void clickAdaptador(final ArrayList<Emails> emails, final Context context, final Dialog dialogo) {

        final EmailAdapter mAdapter= new EmailAdapter(emails);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString();

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setMessage("¿Está seguro de enviar el reporte a: "+ email +"?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogo.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
