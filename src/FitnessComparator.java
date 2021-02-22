import java.util.*;

/**
 * Classe que compara individuos atraves do seu fitness
 */
class FitnessComparator implements Comparator<LayoutIndividual>{

	/**
	 * Metodo que compara dois individuos
	 * @param i1 Individuo 1
	 * @param i2 Individuo 2
	 * @return -1 se i1 for melhor que i2, 0 se i1 for igual a i2, 1 se i2 for melhor que i1
	 */
	public int compare(LayoutIndividual i1, LayoutIndividual i2) {
		if (i1.getFitness() > i2.getFitness()) 
			return -1;
		else if (i1.getFitness() == i2.getFitness()) 
			return 0;
		else
			return 1;
	}
}
