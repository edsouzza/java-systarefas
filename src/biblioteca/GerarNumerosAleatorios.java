package biblioteca;

public class GerarNumerosAleatorios {
    private Long numero;
    public GerarNumerosAleatorios(Long n){
        numero = n;
    }
    //gerando numeros aleatorios para RFs
    public Long getNumeroAleatorioRF(){
        long i = (long) (Math.random() * 9999999);
        return i;
    }
    
    //gerando numeros aleatorios para IPs
    public Long getNumeroAleatorioIP(){
        long i = (long) (Math.random() * 999999999);
        return i;
    }
    
    //gerando numeros aleatorios para IPs
    public Long getNumeroAleatorioChapa(){
        long i = (long) (Math.random() * 999999999);
        return i;
    }
    
    public static void main(String[] args){
        //testando a classe acima
        Long n = new Long("0123456");                                    // novo long com tamanho 13
        GerarNumerosAleatorios gn = new GerarNumerosAleatorios(n);       //passo o long
        System.out.println(gn.getNumeroAleatorioRF());                     //gero o numero
        System.out.println(gn.getNumeroAleatorioRF().toString().length()); //pego o length do numero q deve ser sempre 13
    }    
    
}
