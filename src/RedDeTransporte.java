import java.util.*;

public class RedDeTransporte<T> implements Grafo{

    private T fuente, sumidero;
    private List<T> vertices = new ArrayList<>(2);
    private int[][] matrizCapacidad = new int[2][2];
    private int[][] matrizFlujo = new int[2][2];
    private int cantLados = 0;
    private int cantVertices = 2;

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
        for (int i = 0; i < cantVertices-1; i++) {
            for (int j = 0; j < cantVertices-1; j++) {
                nuevaMatriz[i][j] = matrizCapacidad[i][j];
            }
        }
        return nuevaMatriz;
    }

    @Override
    public void agregarCapacidadLado(int salida, int destino, int capacity) {
        if(matrizCapacidad[salida][destino] == 0){
            cantLados++;
        }
        matrizCapacidad[salida][destino] = capacity;
    }

    public void agregarFlujoLado(int salida, int destino, int flujo) {
        if(!hayLado(salida,destino)){
            cantLados++;
        }
        if(flujo > obtenerCapacidad(salida,destino)){
            throw new IllegalArgumentException("El flujo no puede ser mayor a la capacidad");
        }
        matrizFlujo[salida][destino] = flujo;

    }
    /*
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
    */

    public int obtenerCapacidad(int salida, int destino){
        return matrizCapacidad[salida][destino];
    }

    public int obtenerFlujo(int salida, int destino){
        return matrizFlujo[salida][destino];
    }

    public boolean estaSaturado(int salida, int destino){
        return (obtenerFlujo(salida, destino) == obtenerCapacidad(salida,destino)) && (obtenerCapacidad(salida,destino) != 0);
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
            vertices.remove(indice);
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
    public List<Integer> obtenerSucesores(int indice) {
        List<Integer> sucesores = new ArrayList<>();
        for (int i = 0; i < cantVertices; i++) {
            if(obtenerCapacidad(indice,i) > 0){
                sucesores.add(i);
            }
        }
        return sucesores;
    }

    public int fordFulkerson() {

        matrizFlujo = new int[cantVertices][cantVertices];
        int flujoMax = 0;
        ArrayList<Integer> auxList = new ArrayList<>(1);
        Queue<Integer> queue = new PriorityQueue<>();
        boolean[] visitados = new boolean[cantVertices];
        int[] padres = new int[cantVertices];
        boolean corte1 = false;
        while (!corte1) {
            queue.add(0);
            visitados[0] = true;
            while (queue.size() != 0) {
                padres[0] = -1;
                while(queue.size() > 0) {
                    Integer aux = queue.poll();
                    List<Integer> sucesores = obtenerSucesores(aux);
                    for (int i : sucesores) {
                        if(!visitados[i] && !estaSaturado(aux,i)) {
                            queue.add(i);
                            visitados[i] = true;
                            padres[i] = aux;
                        }
                    }
                }
            }
            if(!visitados[1]){
                corte1 = !corte1;
                break;
            }
                auxList.add(1);
                int indice = padres[1];
                while (indice != 0) {
                    auxList.add(indice);
                    indice = padres[indice];
                }
                auxList.add(0);
                List<Integer> flujos = new ArrayList<>(auxList.size() - 1);

                for (int n = auxList.size() - 1; n > 0; n--) {
                    flujos.add((redResidual(auxList.get(n), auxList.get(n - 1))));
                }

                int flujo = flujos.get(0);

                for (int m = 1; m < flujos.size(); m++) {
                    flujo = Math.min(flujo, flujos.get(m));
                }

                for (int i = auxList.size() - 1; i > 0; i--) {
                    int lado1 = auxList.get(i);
                    int lado2 = auxList.get(i-1);
                    agregarFlujoLado(lado1,lado2, flujo + obtenerFlujo(lado1,lado2));
                }

                flujoMax += flujo;

                if (!visitados[1]) {
                    corte1 = true;
                }
                padres = new int[cantVertices];
                visitados = new boolean[cantVertices];
                auxList = new ArrayList<>(1);
                flujo = 0;
            }
        return flujoMax;

    }


    public int redResidual(int salida, int destino){
        int a = obtenerCapacidad(salida,destino) - obtenerFlujo(salida,destino);
        return a;
    }

    public int[][] matrizCapacidad() {
        return matrizCapacidad;
    }

    public int[][] matrizFlujo() {
        return matrizFlujo;
    }

    public void imprimirMatriz(int[][] matriz){
        for (int x=0; x < matriz.length; x++)
        {
            for (int y=0; y < matriz[x].length; y++)
            {
                System.out.print(" | ");System.out.print (matriz[x][y]); System.out.print(" | ");
            }
            System.out.println();

        }
    }
}

