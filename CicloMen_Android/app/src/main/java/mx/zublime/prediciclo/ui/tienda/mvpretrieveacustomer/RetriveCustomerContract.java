package mx.zublime.prediciclo.ui.tienda.mvpretrieveacustomer;

import mx.zublime.prediciclo.data.models.Direccion;

public interface RetriveCustomerContract {

    interface RetriveCustomerView{
        void showDialogRetrive();
        void hideAlertRetrive();
        void showError(String message);
        void saveInfoCustomer(String first_name, String last_name, String company, String address_1, String address_2,
                              String city, String postcode, String country, String state, boolean infoCompleta);



    }

    interface RetriveCustomerPresenter{
        void onRetriveCustomer(String idCliente);
        void responseRetriveCustomer(Direccion response);
        void setError(String message);

    }

    interface RetriveCustomerModel{
        void getRetriveCustomer(String idCliente);
    }
}
