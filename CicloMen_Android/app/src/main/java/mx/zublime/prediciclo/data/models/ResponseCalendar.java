package mx.zublime.prediciclo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCalendar {
    private int status;
    @Expose
    @SerializedName("message_code")
    private int messageCode;
    @Expose
    @SerializedName("message_text")
    private String messageText;
    private List <Data> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data{

        private int dia_num;
        private String fecha;
        private boolean click;
        private String click_event;
        private boolean boy;
        private boolean girl;
        private boolean solicitar_prueba;
        private boolean pico_fertilidad;
        private boolean menstruacion;
        private boolean reiniciar_sangrado;

        @SerializedName("caso")
        private int caso;

        public int getCaso() {
            return caso;
        }

        public int getDia_num() {
            return dia_num;
        }

        public String getFecha() {
            return fecha;
        }

        public boolean isClick() {
            return click;
        }

        public String getClick_event() {
            return click_event;
        }

        public boolean isBoy() {
            return boy;
        }

        public boolean isGirl() {
            return girl;
        }

        public boolean isSolicitar_prueba() {
            return solicitar_prueba;
        }

        public boolean isPico_fertilidad() {
            return pico_fertilidad;
        }

        public boolean isMenstruacion() {
            return menstruacion;
        }

        public boolean isReiniciar_sangrado() {
            return reiniciar_sangrado;
        }
    }
}
