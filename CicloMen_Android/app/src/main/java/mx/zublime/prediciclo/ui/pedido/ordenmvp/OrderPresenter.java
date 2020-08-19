package mx.zublime.prediciclo.ui.pedido.ordenmvp;

import com.google.gson.JsonObject;

import mx.zublime.prediciclo.data.models.ResponseCreateOrderUpdate;

public class OrderPresenter implements OrderContract.OrderPresenter {

    private OrderContract.OrderView mView;
    private  OrderContract.OrderModel mModel;

    public OrderPresenter(OrderContract.OrderView mView) {
        this.mView = mView;
        this.mModel = new  OrderModel(this);
    }

    @Override
    public void onCreateOrder(JsonObject rqt) {
        if(mView != null){
            mView.showDialog();
            mModel.onCreateOrder(rqt);
        }

    }

    @Override
    public void onUpdateOrder(String idOrder, JsonObject rqt) {
        if(mView != null){
            mView.showDialog();
            mModel.onUpdateOrder(idOrder, rqt);
        }
    }

    @Override
    public void onResponseCreateOrder(ResponseCreateOrderUpdate response) {
        mView.hideDialog();
        if(response != null){
           mView.onSuccessCreate(response.getId());
        }else {

        }

    }

    @Override
    public void setError() {

    }

    @Override
    public void onResponseUpdateOrder(ResponseCreateOrderUpdate response) {
        mView.hideDialog();
        if(response != null){
          mView.onSuccessUpdate();
        }else {

        }

    }
}
