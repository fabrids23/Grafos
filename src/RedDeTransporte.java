import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedDeTransporte<T> implements Grafo{

    private T fuente, sumidero;
    private List<T> vertices = new ArrayList<>(2);
    private int[][] matrizCapacidad = new int[2][2];
    private int[][] matrizFlujo = new int[2][2];
    private int cantLados = 0;
    private int cantVertices = 0;

    public RedDeTransporte(T fuente, T sumidero){
        this.fuente = fuente;
        this.sumidero = sumidero;
        vertices.add(fuente);
        vertices.add(sumidero);
    }

    @Override
    public void agregarVertice(Object x) {
        vertices.add((T) x);
        cantVertices++;
        matrizCapacidad = agrandarMatriz(cantVertices);
        matrizFlujo = agrandarMatriz(cantVertices);
    }

    private int[][] agrandarMatriz(int cantVertices) {
        int[][] nuevaMatriz = new int[cantVertices][cantVertices];
        for (int i = 0; i < cantVertices; i++) {
            for (int j = 0; j < cantVertices; j++) {
                nuevaMatriz[i][j] = matrizCapacidad[i][j];
            }
        }
        return nuevaMatriz;
    }

    @Override
    public void agregarCapacidadLado(int salida, int destino, int capacity) {
        if(matrizCapacidad[salida][destino] > 0){
            cantLados++;
        }
        matrizCapacidad[salida][destino] = capacity;
    }

    public void agregarFlujoLado(int salida, int destino, int flujo) {
        if(hayLado(salida,destino)){
            cantLados++;
        }
        if(flujo > obtenerCapacidad(salida,destino)){
            throw new IllegalArgumentException("El flujo no puede ser mayor a la capacidad");
        }
        matrizFlujo[salida][destino] = flujo;
        if(!chequearConservacion(salida) || !chequearConservacion(destino)){
            throw new IllegalArgumentException("No se cumple con la propiedad de conservacion");
        }
    }

    private boolean chequearConservacion(int indice) {
        if(indice == 0 || indice == 1){
            //Son la fuente o el sumidero, por lo tanto no hace falta cheqeuar la conservacion.
            return true;
        }
        int flujoEntrada = 0;
        int flujoSalida = 0;
        for (int i = 0; i < cantVertices; i++) {
            flujoEntrada += obtenerFlujo(indice,i);
            flujoSalida += obtenerFlujo(i, indice);
        }
        return flujoEntrada == flujoSalida;
    }

    public int obtenerCapacidad(int salida, int destino){
        return matrizCapacidad[salida][destino];
    }

    public int obtenerFlujo(int salida, int destino){
        return matrizFlujo[salida][destino];
    }

    public boolean estaSaturado(int salida, int destino){
        return (obtenerFlujo(salida, destino) == obtenerCapacidad(salida,destino)) && obtenerCapacidad(salida,destino) != 0;
    }

    public void mostrarCaminosSaturados(){
        for (int i = 0; i < cantVertices; i++) {
            for (int j = 0; j < cantVertices; j++) {
                if(estaSaturado(i,j)){
                    System.out.println("El camino desde " + obtenerVertice(i) + " hasta " + obtenerVertice(j) + " esta saturado");
                }
            }
        }
    }

    public int obtenerValorDelFlujo(){
        int flujo = 0;
        for (int i = 1; i < cantVertices; i++) {
            flujo += obtenerFlujo(0, i);
        }
        return flujo;
    }

    @Override
    public void removerVertice(int indice) {
        if (obtenerVertice(indice) != null){
            int[][] nuevaMatriz = new int[cantVertices][cantVertices];
            for (int i = 0; i < indice; i++) {
                for (int j = 0; j < indice; j++) {
                    nuevaMatriz[i][j] = matrizCapacidad[i][j];
                }
            }
            for (int i = indice+1; i < cantVertices; i++) {
                for (int j = indice+1; j < cantVertices; j++) {
                    nuevaMatriz[i][j] = matrizCapacidad[i][j];
                }
            }
            matrizCapacidad = nuevaMatriz;

            for (int i = 0; i < indice; i++) {
                for (int j = 0; j < indice; j++) {
                    nuevaMatriz[i][j] = matrizFlujo[i][j];
                }
            }
            for (int i = indice+1; i < cantVertices; i++) {
                for (int j = indice+1; j < cantVertices; j++) {
                    nuevaMatriz[i][j] = matrizFlujo[i][j];
                }
            }

            matrizFlujo = nuevaMatriz;

            cantVertices--;
        }

    }

    @Override
    public void removerLado(int salida, int destino) {
        if(hayLado(salida, destino)){
            cantLados--;
        }
        matrizCapacidad[salida][destino] = 0;
        matrizFlujo[salida][destino] = 0;
    }

    @Override
    public boolean hayLado(int salida, int destino) {
        return matrizCapacidad[salida][destino] > 0;
    }

    @Override
    public boolean hayLado(Object x, Object y) {
        return hayLado(vertices.indexOf(x), vertices.indexOf(y));
    }

    @Override
    public int cantVertices() {
        return cantVertices;
    }

    @Override
    public int cantLados() {
        return cantLados;
    }

    @Override
    public Object obtenerVertice(int indice) {
        return vertices.get(indice);
    }

    public T getFuente() {
        return fuente;
    }

    public T getSumidero() {
        return sumidero;
    }

    @Override
    public List<T> obtenerSucesores(int indice) {
        List<T> sucesores = new ArrayList<>();
        for (int i = 0; i < cantVertices; i++) {
            if(obtenerCapacidad(indice,i) > 0){
                sucesores.add((T) obtenerVertice(i));
            }
        }
        return sucesores;
    }

    public void FordFulkerson(){
        matrizFlujo = new int[cantVertices][cantVertices];
        HashMap<T, Integer> matrizFF = new HashMap<>();

    }

    public int redResidual(int salida, int destino){
        return obtenerCapacidad(salida,destino)
    }
}
