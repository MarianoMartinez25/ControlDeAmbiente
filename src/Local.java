/**
 * Ambiente a controlar
 */
public class Local { 
    /** clase anidada de manejo de excepciones: MAXIMO encapsulamiento, dado
     * que solo se accede desde el ambito de Local
     */
    private class ErrorHardware extends Throwable{
        final Local.Censor obj;
        final char tipo;

        ErrorHardware(Local.Censor o, char t){
            obj = o;
            tipo = t;
        }

        void lecturaNula(){
            if (tipo == 't')
                obj.tempActual = -101;
            else
                obj.hdadActual = -999;
        }
    }
    /** clase anidada: NO puede ser private porque se accede a objetos de la clase 
     * desde afuera del ambito de Local, tiene acceso 'friendly'
     */
    class Acondicionador {
        private int consumo = 1000;

        public void regular(int nivel) {
            consumo = (nivel < 0 ? -nivel : nivel);
        }

        public int getConsumo() { return consumo; }
    }
    /** clase anidada: NO puede ser private porque se accede a objetos de la clase 
     * desde afuera del ambito de Local, tiene acceso 'friendly'
     * se conecta con los dispositivos 'sensores' a traves de metodos nativos
     */
    class Censor extends Thread {
        int tempActual, hdadActual;

        public native int inputTemperatura();

        public native int inputHumedad();

        private int getMedicion(char c) throws Local.ErrorHardware {
            int  m;
            m = (c == 't') ? inputTemperatura() : inputHumedad();
            if ( (c == 'h' && m > 70) || (c == 't' && m > 38) )
                throw new Local.ErrorHardware((Local.Censor)this, c);
            return m;
        }

        public void run() {
            while (true) {
                try {
                    tempActual = getMedicion('t');
                    hdadActual = getMedicion('h');
                }
                catch (Local.ErrorHardware ee) {
                    ee.lecturaNula();
                }
            }
        }
    }

    // Declaraciones de Local
    public final int volumen;
    public final Local.Censor censor = new Local.Censor();
    public final Local.Acondicionador equipoAA = new Local.Acondicionador();

    /** El archivo Censores.dll debe estar en algun directorio del path 
     * click botón dcho en el proyecto
     * click en propiedades
     * click en Run
     * VM Options : -Djava.library.path="poner el path"
     */
    static {
        System.loadLibrary("Censores64");
    }

    public Local(int vol){
        volumen = vol;
        censor.start();
    }
}
    