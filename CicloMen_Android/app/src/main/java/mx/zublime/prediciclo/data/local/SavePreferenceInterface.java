package mx.zublime.prediciclo.data.local;

public interface SavePreferenceInterface {

    interface Autentication{
        String IS_LOGIN = "is_login";
        String IS_REGISTRO = "is_registro";
    }

    interface Navegacion{
        String IS_CONFIGURACION_DATOS = "is_configuracion_datos";
    }
    interface DatosUsuario{
        String USER_ID = "user_id";
        String PASSWORD = "password";
        String EMAIL = "email";
        String USER_APODO = "user_apodo";
        String FECHA_NACIMIENTO = "fecha_nacimiento";
        String CUSTOMER_ID_OPEN_PAY = "customer_id_open_pay";
    }

    interface DatosBiologicos{
        String DURACION_CICLO_MENSTRUAL = "duracion_cliclo_menstrual";
        String DURACION_PERIODO_MENSTRUAL = "duracion_periodo_menstrual";
        String FECHA_INICIO_PERIDO = "fecha_inicio_periodo";
        String CONFIGURACION_TERMINADA  = "configuracion_terminada";
    }

    interface DatosDireccion{

         String FIRST_NAME = "first_name";
         String  LAST_NAME = "last_name";
         String COMPANY = "company";
         String ADDRESS_ONE = "address_1";
         String ADDRESS_TWO = "address_2";
         String CITY = "city";
         String POST_CODE =  "postcode";
         String CONTRY =  "country";
         String STATE =  "state";

    }

    interface  TarjetasBancarias{
        String CARD_1 = "card_1";
        String CARD_2 = "card_2";
        String TIPO_TARJETA = "tipo";
        String MASCARA_DE_NUMERO_1 = "mascara_de_numero_1";
        String MASCAR_DE_NUMERO_2  ="mascara_de_numero_2";
        String TARJETA_SELECCIONADA = "tarjeta_seleccionada";
    }

    interface Product{
        String PRECIO = "price";
        String NAME_PRODUCT = "name_product";
    }

    interface Orden{
        String PHONE = "phone";
        String TARJETA_SELECCIONADA = "tarjeta_seleccionada";
        String ID_ORDER = "id_order";
    }

    interface DatosDireccionJson{
        String JSON_ADDRESS_1 = "json_address_one";
        String JSON_ADDRESS_2 = "json_address_two";
    }
}
