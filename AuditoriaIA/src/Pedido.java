public class Pedido {
    public static double calcularTotal(double valor, boolean vip, String cupom) {
        if (valor <= 0) return -1;

        double percentual = 0;

        if (valor > 1000)      percentual = 15;
        else if (valor > 500)  percentual = 10;
        else if (valor > 100) percentual = 5;

        if (vip) percentual += 5;

        if (percentual > 20) percentual = 20;
        
        if ("PRIMEIRA".equals(cupom) && valor <= 100) {
            percentual += 10;
        }
        
        if(valor == 50 && vip) {
        	System.out.println("valor: " + valor);
        	System.out.println("percentual: " + percentual);
        	System.out.println("total: " + (valor * (1 - (percentual / 100.0))));
        }


        return valor * (1 - (percentual / 100.0));
    }
}