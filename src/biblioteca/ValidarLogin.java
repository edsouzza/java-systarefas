/*
 *Para validar login / verifica se o login digitado é valido em relação a ter uma letra como primeiro digito com mais 6 numeros
 */
package biblioteca;

/**
 *
 * @author edsou
 */
public class ValidarLogin {
    
    public boolean loginValido(String login)
    {             
        int qdeDigitos  = login.length();
        
        //verifica se o login é valido em relação a ter uma letra como primeiro digito e mais 6 numeros
        if(login.substring(0, 1).matches("[A-Z]*") && (login.substring(1).matches("[0-9]*") && qdeDigitos == 7)){
            return true;
        }
        return false;
    }
    
}