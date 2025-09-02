/*
 * Classe retorna array sem os valores que repetiram na entrada
 */
package biblioteca;

import java.util.Arrays;

public class ArraySemValoresRepetidos {
    public static String[] subst(String[] array) {
		Arrays.sort(array);
		String[] listaSemRepeticao = new String[array.length];
		// parte central do algoritmo
		int j = 0;
		for (int i = 0; i < array.length; i++) {
		      if ((i > 0) && (array[i] == array[i - 1])) continue;
		      if ((i < array.length - 1) && (array[i] == array[i + 1])) continue;
		      listaSemRepeticao[j++] = array[i];		      		      
		}
		// se tinha elementos repetidos
		if (j != array.length){
			String[] newLista = new String[j];
			System.arraycopy(listaSemRepeticao, 0, newLista, 0, j);
			return newLista;
		}
		return listaSemRepeticao;
	}  
	public static void main(String[] args) {
		String[] listaSemRepeticao = subst(new String[]{"brasil", "java", "obama", "brasil"});
		for (String dado : listaSemRepeticao) {
			System.out.print(dado + " ");
		}
	}
}
