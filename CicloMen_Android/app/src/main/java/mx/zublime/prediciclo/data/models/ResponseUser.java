package mx.zublime.prediciclo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUser extends BaseResponse
{

    private Data data;
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data
    {
        @SerializedName("duracion_periodo")
        @Expose
        private String duracionPeriodo;

        @SerializedName("duracion_ciclo")
        @Expose
        private String duracionCiclo;

        @SerializedName("fecha_inicio_periodo")
        @Expose
        private String fechaInicioPeriodo;

        @SerializedName("fecha_nacimiento")
        @Expose
        private String fechaNacimiento;

        @SerializedName("fecha_ultima_prueba_positiva")
        @Expose
        private String fechaUltimaPruebaPositiva;

        public String getDuracionPeriodo() {
            return duracionPeriodo;
        }

        public void setDuracionPeriodo(String duracionPeriodo) {
            this.duracionPeriodo = duracionPeriodo;
        }

        public String getDuracionCiclo() {
            return duracionCiclo;
        }

        public void setDuracionCiclo(String duracionCiclo) {
            this.duracionCiclo = duracionCiclo;
        }

        public String getFechaInicioPeriodo() {
            return fechaInicioPeriodo;
        }

        public void setFechaInicioPeriodo(String fechaInicioPeriodo) {
            this.fechaInicioPeriodo = fechaInicioPeriodo;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getFechaUltimaPruebaPositiva() {
            return fechaUltimaPruebaPositiva;
        }

        public void setFechaUltimaPruebaPositiva(String fechaUltimaPruebaPositiva) {
            this.fechaUltimaPruebaPositiva = fechaUltimaPruebaPositiva;
        }
    }
}
