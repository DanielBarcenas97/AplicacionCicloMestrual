package mx.zublime.prediciclo.ui.home.interfaz;

public interface ISelectedButtonCommunication
{
    void onResultFragmentClicked(boolean result);
    void onResultSaveDate(String date);
    void onResultSaveDateAndClear(String date);
    void onResultSavePrueba(boolean isPositive);
    void onResultReniciarMenstruacion(boolean result);
}
