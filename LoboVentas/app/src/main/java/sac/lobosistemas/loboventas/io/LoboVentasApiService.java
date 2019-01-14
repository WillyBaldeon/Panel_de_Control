package sac.lobosistemas.loboventas.io;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sac.lobosistemas.loboventas.model.Emails;
import sac.lobosistemas.loboventas.model.Reportes;
import sac.lobosistemas.loboventas.model.Empresa;
import sac.lobosistemas.loboventas.model.PagoDia;
import sac.lobosistemas.loboventas.model.PagoMes;
import sac.lobosistemas.loboventas.model.VentasMes;

public interface LoboVentasApiService {

    @GET("empresas")
    Call<ArrayList<Empresa>> getEmpresas();

    @GET("pagos/dia")
    Call<ArrayList<PagoDia>> getPagosDia();

    @GET("pagos/mes")
    Call<ArrayList<PagoMes>> getPagosMes();

    @GET("reportes/clientes/{factura_num}")
    Call<ArrayList<Reportes>> getReportes(@Path("factura_num") String factura_num);

    @GET("usuarios/{ruc}")
    Call<ArrayList<Emails>> getEmails(@Path("ruc") String RUC);

    @GET("reportes/ventas")
    Call<ArrayList<VentasMes>> getVentas(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin);
}
