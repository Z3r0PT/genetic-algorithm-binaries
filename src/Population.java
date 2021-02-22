import java.util.*;

/**
 * Classe que permite a criacao de uma populacao de individuos
 */
public class Population {
	
	private int n; //Tamanho da populacao
	private int l;
	private static Random generator = Individual.getRandomGenerator();
	private List<LayoutIndividual> population = new ArrayList<>(); //Populacao
	private int n_sections; //Numero de seccoes da roleta
	private List<Section> roulette = new ArrayList<>(); // Populacao seelecionada

	/**
	 * Construtor para definir os detalhes da populacao
	 * @param population Lista de individuos
	 */
	public Population(List<LayoutIndividual> population) {
		this.n = population.size();
		this.l = population.get(0).getL();
		this.population = population;
		this.n_sections = this.getN();
		roulette.clear();
		for (int i=0; i<n_sections; i++) {
			double min;
			if (i==0) min = 0; else min = roulette.get(i-1).max;
			double max = min + this.getPopulation().get(i).getFitness();
			roulette.add(new Section(min, max));
		}
	}

	/**
	 * Construtor para criar uma populacao de individuos
	 * @param n Total de individuos na populacao
	 * @param l Tamnho total de cada individuo da populacao
	 */
	public Population(int n, int l) {
		this.n = n;
		this.l = l;
		for (int i=0; i<n; i++) {
			population.add(new Individual(l));
		}
		this.n_sections = this.getN();
	}

	/**
	 * Metodo para calcular o fitness medio da populacao
	 * @return Media de fitness da populacao
	 */
	public double getAverageFitness(){
		double avg = 0;
		int size = this.getN();
		for( int i = 0; i < size; i++){
			avg += this.getPopulation().get(i).getFitness();
		}
		return (double)avg / (double)size;
	}

    /**
     * Metodo para iniciar o calculo de fitness
     * @param generation_number Numero da geracao atual
     */
	public void initFitness(int generation_number){
		//set fitness value
		for (int i = 0; i < generation_number; i++) {
			this.getPopulation().get(i).setSpecialFitness(-1, 1);
		}
		this.orderByFitnessDesc();
		roulette.clear();
		for (int i=0; i<n_sections; i++) {
			double min;
			if (i==0) min = 0; else min = roulette.get(i-1).max;
			double max = min + this.getPopulation().get(i).getFitness();
			roulette.add(new Section(min, max));
		}
	}

	/**
	 * Metodo que devolve o tamanho da populacao
	 * @return Tamanho da populacao
	 */
	public int getN() { return n; }

	/**
	 * Metodo que devolve a popoulacao
	 * @return Populacao
	 */
	public List<LayoutIndividual> getPopulation() { return population; }

	/**
	 * Metodo que ordenada a populcao pelo fitness de cada individuo em ordem descendente
	 */
	public void orderByFitnessDesc() {
		population.sort(new FitnessComparator());
	}

	    public void randomPermutation(ArrayList<LayoutIndividual> individuals) {
        for (int i = 0; i < n - 1; i++) {
            int b = n - 1;
            double u = generator.nextDouble();
            int r = i + (int) Math.round(u * (b - i));

            LayoutIndividual m = individuals.get(i);
            individuals.set(i, individuals.get(r));
            individuals.set(r, m);
        }
    }

    /**
     * Metodo que a selecao usando o Tournament Selection without Replacement
     * @param s
     * @return
     */
    public Population tournamentSelectionWithoutReplacement(int s) {
        List<LayoutIndividual> winners = new ArrayList<>();
        List<LayoutIndividual> p = new ArrayList<>();
        //orderByFitnessDesc();
        for (LayoutIndividual i : population) {
            p.add(i);
        }
        for (int i = 0; i < s; i++) {
            ArrayList<LayoutIndividual> tournament = new ArrayList<LayoutIndividual>(p);
            randomPermutation(tournament);

            for (int j = 0; j < population.size(); j += s) {
                int winner = j;
                double max_fitness = tournament.get(j).getFitness();
                for (int k = j + 1; k < j + s; k++) {
                    if (tournament.get(k).getFitness() > max_fitness) {
                        max_fitness = tournament.get(k).getFitness();
                        winner = k;
                    }
                }
                winners.add(tournament.get(winner));
            }
        }
        return new Population(winners);
    }

	/**
	 * Metodo que devolve toda a populcao como uma unica String
	 * @return Populacao como unica String
	 */
	public String toString() {
		String str="";
		for (LayoutIndividual i : population)
			str += i.toString() + System.lineSeparator();
		return str;
	}


	public class Section {

		private double min;
		private double max;

		/**
		 * Construtor que inicializa a classe
		 * @param min Valor minimo a ser usado na roleta
		 * @param max Valor maximo a ser usado na roleta
		 */
		public Section(double min, double max) {
			this.min = min;
			this.max = max;
		}

		/**
		 * Metodo que devoolve o min e o max numa unica String
		 * @return String com min e max separados entres eles
		 */
		public String toString() {
			String str="";
			str += min + " --> " + max;
			return str;
		}

	}

	/**
	 * Metodo que seleciona os individuos na populacao
	 * @return Individuo selecionado
	 */
		public LayoutIndividual spinWithOnePointer() {
			double sum=roulette.get(n_sections-1).max;
			LayoutIndividual winner = null;
			double u = generator.nextDouble();
			for (int j=0; j<n_sections; j++) {
				if (u < roulette.get(j).max/sum) {
					winner=this.getPopulation().get(j); break;
				}
			}
			return winner;
		}

	/**
	 * Metodo que faz a selecao usando o Roulette Wheel Selection
	 * @return Populacao escolhida
	 */
		public Population rouletteWheelSelection() {
			int n = n_sections;
			List<LayoutIndividual> winners = new ArrayList<>();

			for (int i=0; i<n; i++)
				winners.add(this.spinWithOnePointer());

			return new Population(winners);
		}




}

