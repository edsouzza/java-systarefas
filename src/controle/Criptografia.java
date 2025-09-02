package controle;

/* Uma senha criptografada com MD5 não pode ser novamente transformada no texto de origem, portanto a 
comparação deve ser feita por duas strings criptografadas, quando o usuário entrar com a senha criptografe
e compare com o que estiver no banco de dados.*/

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia
{   
   public static String Criptografar(String password) throws NoSuchAlgorithmException 
   {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
        return String.format("%32x", hash);
   }
    
}
