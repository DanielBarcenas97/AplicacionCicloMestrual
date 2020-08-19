package mx.zublime.prediciclo.data.api;

public interface EndPoints {

    interface SERVICES_POST{

            String REGISTRO_USUARIO = "wc/v3/customers";
            String LOGIN_USUARIO_STEP_ONE = "zublime/prediciclo/v1/email_exists";
            String LOGIN_USUARIO_STEP_TWO = "zublime/prediciclo/v1/login";
            String UPDATE_INFO_USER = "zublime/prediciclo/v1/update_info";
            //String CONSULTAR_CALENDARIO = "zublime/prediciclo/v1/calendar";
            String CONSULTAR_CALENDARIO = "zublime/prediciclo/v1/calendar2";
            String UPDATE_CALENDAR = "zublime/prediciclo/v1/calendar/update";
            String CLEAR_CALENDAR = "zublime/prediciclo/v1/calendar/clear";
            String CREAR_ORDEN = "wc/v3/orders";
            String RECUPERAR_PASSWORD = "zublime/prediciclo/v1/customer/lost_password";
            String CONSULTAR_INFO = "zublime/prediciclo/v1/customer/info";
            String REALIZAR_COBRO = "zublime/prediciclo/v1/payment/creditcard/pay";
            String REGISTRE_CUSTOMER_OPEN_PAY = "zublime/prediciclo/v1/openpay/customer/register";
    }

    interface  SERVICES_GET{
            String CONSULTAR_PRODUCTO = "wc/v3/products/520";
            String CONSULTAR_INFO_ENVIO = "wc/v3/customers/{id}";
    }

    interface SERVICES_PUT{
        String UPDATE_INFO_ENVIO = "wc/v3/customers/{id}";
        String UPDATE_ORDEN = "wc/v3/orders/{id}";
    }
}
