import org.junit.Test;

public class RedDeTransporteTest {

    @Test
    public void cargarRedYFlujoMaximo(){
        RedDeTransporte<String> redDeTransporte = new RedDeTransporte<>("a", "z");
        redDeTransporte.agregarVertice("c");
        redDeTransporte.agregarVertice("d");
        redDeTransporte.agregarVertice("e");
        redDeTransporte.agregarVertice("f");

        redDeTransporte.agregarCapacidadLado(0,2,16);
        redDeTransporte.agregarCapacidadLado(0,3,13);
        redDeTransporte.agregarCapacidadLado(2,3,10);
        redDeTransporte.agregarCapacidadLado(3,2,4);
        redDeTransporte.agregarCapacidadLado(4,3,9);
        redDeTransporte.agregarCapacidadLado(2,4,12);
        redDeTransporte.agregarCapacidadLado(3,5,14);
        redDeTransporte.agregarCapacidadLado(5,4,7);
        redDeTransporte.agregarCapacidadLado(4,1,20);
        redDeTransporte.agregarCapacidadLado(5,1,4);

        redDeTransporte.imprimirMatriz(redDeTransporte.matrizCapacidad());
        System.out.println("------------------------------------------");
        System.out.println("Flujo maximo: " + (redDeTransporte.fordFulkerson()));
        redDeTransporte.imprimirMatriz(redDeTransporte.matrizFlujo());
    }

    @Test
    public void cargarRedYFlujoMaximo2(){
        RedDeTransporte<String> redDeTransporte = new RedDeTransporte<>("a", "z");
        redDeTransporte.agregarVertice("b");//2
        redDeTransporte.agregarVertice("g");//3
        redDeTransporte.agregarVertice("d");//4
        redDeTransporte.agregarVertice("h");//5

        redDeTransporte.agregarCapacidadLado(0,2,5);
        redDeTransporte.agregarCapacidadLado(0,3,7);
        redDeTransporte.agregarCapacidadLado(3,2,5);
        redDeTransporte.agregarCapacidadLado(2,4,4);
        redDeTransporte.agregarCapacidadLado(2,5,6);
        redDeTransporte.agregarCapacidadLado(3,5,5);
        redDeTransporte.agregarCapacidadLado(5,4,2);
        redDeTransporte.agregarCapacidadLado(5,1,6);
        redDeTransporte.agregarCapacidadLado(4,1,5);

        redDeTransporte.imprimirMatriz(redDeTransporte.matrizCapacidad());
        System.out.println("------------------------------------------");
        System.out.println("Flujo maximo: " + (redDeTransporte.fordFulkerson()));
        redDeTransporte.imprimirMatriz(redDeTransporte.matrizFlujo());
    }

}