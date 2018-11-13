import java.util.List;

public interface Grafo<T> {
    void agregarVertice(T x);
    void agregarCapacidadLado(int salida, int destino, int capacidad);
    void removerVertice(int indice);
    void removerLado(int salida, int destino);
    boolean hayLado(int v, int w);
    boolean hayLado(T x, T y);
    int cantVertices();
    int cantLados();
    T obtenerVertice(int indice);
    List<T> obtenerSucesores(int indice);
}
