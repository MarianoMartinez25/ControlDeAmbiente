import java.util.ListIterator;

import static java.lang.Math.round;

/**
 * Thread principal
 */

public class UnidadDeControl extends Thread {
    private int  temperatura;
    private int  humedad;
    public final Contenedor<Local> ambientes = new Contenedor();
    private String resultados;

    private int calcAjuste(int  t, int h, int v){
        int p = temperatura * humedad ;
        if (t * h < round(p * 0.7) || t * h > round(p * 0.9))
            return t < h ?  - h / t * v : t / h * v;
        else
            return 0;
    }

    public void run(){
        ListIterator e = ambientes.listIterator();

        while (true) {
            Local local = (Local)e.next();
            int result = 0;

            result = calcAjuste(local.censor.tempActual,
                    local.censor.hdadActual,
                    local.volumen);
            local.equipoAA.regular(result);

            resultados = "Local: " + Integer.toString(local.volumen) +
                            " Aire: " + local.equipoAA.getConsumo() +
                            " Temp: " + Integer.toString(local.censor.tempActual) +
                            " Hdad: " + Integer.toString(local.censor.hdadActual);
        }
    }

    public String estado(){
        return resultados;
    }

    public void setParametros(int t, int h) {
        temperatura = t;
        humedad = h;
    }

    public void crearLocal(int vol) {
        ambientes.add(new Local(vol));
    }
}
