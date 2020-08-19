package mx.zublime.prediciclo.data.api;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.BaseResponse;
import mx.zublime.prediciclo.data.models.Direccion;
import mx.zublime.prediciclo.data.models.LoginResponse;
import mx.zublime.prediciclo.data.models.LoginResponseOne;
import mx.zublime.prediciclo.data.models.ResponseCalendar;
import mx.zublime.prediciclo.data.models.ResponseCargoOpenpay;
import mx.zublime.prediciclo.data.models.ResponseCreateOrderUpdate;
import mx.zublime.prediciclo.data.models.ResponseForgot;
import mx.zublime.prediciclo.data.models.ResponseRegistrerCustomerOpenPay;
import mx.zublime.prediciclo.data.models.ResponseRegistro;
import mx.zublime.prediciclo.data.models.ResponseUpdateCalendar;
import mx.zublime.prediciclo.data.models.ResponseRetrieveProduct;
import mx.zublime.prediciclo.data.models.ResponseUpdateInfoDireccion;
import mx.zublime.prediciclo.data.models.ResponseUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WebServices {
    @FormUrlEncoded
    @POST(EndPoints.SERVICES_POST.LOGIN_USUARIO_STEP_ONE)
    Call<LoginResponseOne> autenticarUsuarioStepOne(@Field("email") String email);

    @FormUrlEncoded
    @POST(EndPoints.SERVICES_POST.LOGIN_USUARIO_STEP_TWO)
    Call<LoginResponse> autenticarUsuarioStepTwo(@Field("username") String userName, @Field("password") String password);

    @POST(EndPoints.SERVICES_POST.UPDATE_INFO_USER)
    Call<BaseResponse> actualizarInformacionDelUsuario(@Body JsonObject request);

    @POST(EndPoints.SERVICES_POST.CLEAR_CALENDAR)
    Call<BaseResponse> clearCalendar(@Body JsonObject request);

    @POST (EndPoints.SERVICES_POST.RECUPERAR_PASSWORD)
    Call<ResponseForgot> recuperarPassword(@Body JsonObject request);


    @POST(EndPoints.SERVICES_POST.UPDATE_CALENDAR)
    Call<ResponseUpdateCalendar> updateCalendar(@Body JsonObject request);

    @FormUrlEncoded
    @POST(EndPoints.SERVICES_POST.UPDATE_INFO_USER)
    Call<BaseResponse> actualizarInformacionBiomatrica(@Field("user_id") String id, @Field("duracion_periodo") String duracionPeriodo,@Field("duracion_ciclo") String duracionCiclo,
                                                     @Field("fecha_nacimiento") String fechaNacimiento);

   @FormUrlEncoded
   @POST(EndPoints.SERVICES_POST.CONSULTAR_CALENDARIO)
   Call<ResponseCalendar> consultarCalendario(@Field("num_periodos") String numeroPeriodos ,@Field("PHP_AUTH_USER") String user, @Field("PHP_AUTH_PW") String password);

    @POST(EndPoints.SERVICES_POST.REGISTRO_USUARIO)
    Call<ResponseRegistro> registrarUsuario(@Body JsonObject rqt);

    @GET(EndPoints.SERVICES_GET.CONSULTAR_PRODUCTO)
    Call<ResponseRetrieveProduct> consultarProducto();


    @GET(EndPoints.SERVICES_GET.CONSULTAR_INFO_ENVIO)
    Call<Direccion> consultarInfoEnvioCliente(@Path("id") String idCliente);

    @PUT(EndPoints.SERVICES_PUT.UPDATE_INFO_ENVIO)
    Call<ResponseUpdateInfoDireccion> updateInfoEnvioCliente(@Path("id") String idCliente, @Body JsonObject jsonObject);

    @POST(EndPoints.SERVICES_POST.CREAR_ORDEN)
    Call<ResponseCreateOrderUpdate> crearOrden(@Body JsonObject rqt);

    @PUT(EndPoints.SERVICES_PUT.UPDATE_ORDEN)
    Call<ResponseCreateOrderUpdate> updateOrden(@Path("id") String id, @Body JsonObject rqt);

    @POST(EndPoints.SERVICES_POST.CONSULTAR_INFO)
    Call<ResponseUser> consultarInfo(@Body JsonObject request);

    @POST(EndPoints.SERVICES_POST.REGISTRE_CUSTOMER_OPEN_PAY)
    Call<ResponseRegistrerCustomerOpenPay> consultarInfoCustomerOpenPay(@Body JsonObject request);

    @POST(EndPoints.SERVICES_POST.REALIZAR_COBRO)
    Call<ResponseCargoOpenpay> realizarCobroOpenPay(@Body JsonObject request);

}
































