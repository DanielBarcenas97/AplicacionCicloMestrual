package mx.zublime.prediciclo.ui.pedido.datospago.mvpopenpay;

import com.google.gson.JsonObject;

import mx.openpay.android.Openpay;
import mx.openpay.android.OperationCallBack;
import mx.openpay.android.OperationResult;
import mx.openpay.android.exceptions.OpenpayServiceException;
import mx.openpay.android.exceptions.ServiceUnavailableException;
import mx.openpay.android.model.Card;
import mx.openpay.android.model.Token;
import mx.zublime.prediciclo.Prediciclo;
import mx.zublime.prediciclo.data.api.ApiAdapter;
import mx.zublime.prediciclo.data.models.ResponseCargoOpenpay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenPayModel implements OpenPayContract.OpenPayModel {

    private OpenPayPresenter mPresenter;

    public OpenPayModel(OpenPayPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getGeneratePayment(String name, String numberCard, int month, int year, String cvv) {
        Prediciclo.createOpenPay();
        Openpay openpay = Prediciclo.getOpenPay();
        Card card = new Card();
        card.holderName(name);
        card.cardNumber(numberCard);
        card.expirationMonth(month);
        card.expirationYear(year);
        card.cvv2(cvv);

        openpay.createToken(card, new OperationCallBack<Token>() {
            @Override
            public void onError(OpenpayServiceException e) {
                mPresenter.onErrorPayment();
            }

            @Override
            public void onCommunicationError(ServiceUnavailableException e) {
                mPresenter.onErrorPayment();
            }
            @Override
            public void onSuccess(OperationResult<Token> operationResult) {
                mPresenter.onSuccessPayment(operationResult.getResult().getId());
                //String token = operationResult.getResult().toString();

            }

        });

    }

    @Override
    public void updateOrden(JsonObject orden) {

    }

    @Override
    public void generarCargo(JsonObject rqt) {

        Call<ResponseCargoOpenpay> call = ApiAdapter.getApiService().realizarCobroOpenPay(rqt);
        call.enqueue(new Callback<ResponseCargoOpenpay>() {
            @Override
            public void onResponse(Call<ResponseCargoOpenpay> call, Response<ResponseCargoOpenpay> response) {
                if(response.isSuccessful()) {
                    mPresenter.setCargoResponse(response.body());
                }else {
                    mPresenter.onErrorPayment();
                }
            }

            @Override
            public void onFailure(Call<ResponseCargoOpenpay> call, Throwable t) {
                mPresenter.onErrorPayment();
            }
        });
    }


}



















