public class BuscadorLecturas {
    private static int comparaciones = 0;
    public static int getcomparaciones(){
        return comparaciones;
    }
    public static int busquedaLinealPorTimestamp(
            LecturaSensor[] datos, String timestamp){
        comparaciones = 0;
        int inicio = 0;
        int fin = datos.length - 1;
        while (inicio <= fin){
            int medio = (inicio + fin) / 2;
            comparaciones++;
            int comparacion =
                    datos[medio]
                            .getTimestamp()
                            .compareTo(timestamp);
            if (comparacion == 0) {
                return medio;
            }
            if (comparacion < 0) {
                inicio = medio +1;
            } else {
                fin = medio -1;
            }
        }

        return -1;
    }
    public static int busquedaBinariaPorTimestamp(
            LecturaSensor[] datos,
            String timestamp) {

        comparaciones = 0;

        int inicio = 0;
        int fin = datos.length - 1;

        while (inicio <= fin) {

            int medio = (inicio + fin) / 2;

            comparaciones++;

            int comparacion =
                    datos[medio]
                            .getTimestamp()
                            .compareTo(timestamp);

            if (comparacion == 0) {
                return medio;
            }

            if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return -1;
    }

    public static int busquedaBinariaPorPm25(
            LecturaSensor[] datos,
            double pm25){
        comparaciones = 0;
        int inicio = 0;
        int fin = datos.length - 1;
        while (inicio <= fin){
            int medio = (inicio + fin) / 2;
            comparaciones++;
            if (datos[medio].getPm25() == pm25) {
                return medio;
            }
            if (datos[medio].getPm25()< pm25) {
                inicio = medio + 1;
            } else {
                fin = medio -1;
            }
        }
        return -1;
    }
}
