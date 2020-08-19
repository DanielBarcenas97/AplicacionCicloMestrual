package mx.zublime.prediciclo.ui.pedido.ordenmvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.ResponseCreateOrderUpdate;

public interface OrderContract {

    interface OrderView{
        void showDialog();
        void hideDialog();
        void onSuccessCreate(int id);
        void onSuccessUpdate();
        void onErrorCreate();
        void onErrorUpdate();
        void showError();


    }

    interface OrderPresenter{
        void onCreateOrder(JsonObject rqt);
        void onUpdateOrder(String idOrder, JsonObject rqt);
        void onResponseCreateOrder(ResponseCreateOrderUpdate response);
        void onResponseUpdateOrder(ResponseCreateOrderUpdate response);
        void setError();
    }
    interface OrderModel{
        void onCreateOrder(JsonObject rqt);
        void onUpdateOrder(String idOrder, JsonObject rqt);
    }
}
