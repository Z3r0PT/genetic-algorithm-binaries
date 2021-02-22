import java.util.*;

/**
 * Comparador de individuos atraves da sua represnetacao em base 10
 */
public class IndividualComparator implements Comparator<Individual> {

	/**
	 * Metodo que compara individuos
	 * @param i1 Individuo 1
	 * @param i2 Individuo 2
	 * @return 1 se i1 for maior que i2, 0 se i1 for igual a i2, -1 se i1 for menor que i2
	 */
	public int compare(Individual i1, Individual i2) {
		if (i1.getBase10Individual() > i2.getBase10Individual()) 
			return 1;
		else if (i1.getBase10Individual() == i2.getBase10Individual()) 
			return 0;
		else
			return -1;
	}
}
