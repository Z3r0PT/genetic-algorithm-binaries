
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe que executa o algortimo genetico
 */
public class FunctionOptimize {

    private int number_individuals; //Total de individuos
    private int generation_number; //Total de geracoes
    private int chromosome_len; //Tamanho de cada individuo

    private double mutation_probability = 0.0; //Probabilidade de mutacao
    private double crossover_probability = 0.0; //Probabilidade de crossover


    double max_fitness = 0; //Fitness maximo

    double maxX = 0.0;
    double maxY = 0.0;

    private Population population;

    /**
     * Construtor que inicializa a populacao
     */
    public FunctionOptimize(int number_individuals, int generation_number, int chromosome_len, double mutation_probability, double crossover_probability) {
        this.number_individuals = number_individuals;
        this.generation_number = generation_number;
        this.chromosome_len = chromosome_len;
        this.mutation_probability = mutation_probability;
        this.crossover_probability = crossover_probability;
    }

    /**
     * Metodo que cria a populacao
     */
    public void createPopulation() {
        population = new Population(number_individuals, chromosome_len);
        max_fitness = Double.MIN_VALUE;
    }

    /**
     * Metodo que avalia o fitness
     */
    public void evaluateFitness() {
        double maximum_fitness = population.getPopulation().get(0).getFitness();
        double minimal_fitness = population.getPopulation().get(population.getN() - 1).getFitness();
        if (max_fitness < maximum_fitness) {
            max_fitness = maximum_fitness;
            maxX = population.getPopulation().get(0).getX();
            maxY = population.getPopulation().get(0).getY();
        }
        double avg = population.getAverageFitness();
        String prt = ("Maximum: " + (double)Math.round(maximum_fitness * 1000d) / 1000d + " Minimum: "
                + (double)Math.round(minimal_fitness * 1000d) / 1000d + " Average: " + (double)Math.round(avg * 1000d) / 1000d).replace(".",",");
        System.out.println(prt);
    }

    /**
     * Metodo que executa o algoritmo genetico, percorrendo todas as suas fases
     */
    public void implementFunctionOptimize() {
        double xm=0;
        double ym= 0;
        double valuem = 0;
        for (int g = 0; g < 30; g++) { //testa 30 vezes e faz a media dos resultados
            double y = 0;
            double x = 0;
            double value = 0 ;
                    //initialize first population
            createPopulation();
            population.initFitness(generation_number);
            //System.out.print("Initial Generation: ");
            evaluateFitness();
            for (int re = 0; re < generation_number; re++) {
                //selection using Roulette selection
                Population winners = population.tournamentSelectionWithoutReplacement(number_individuals);
                //Population winners = population.rouletteWheelSelection();
                population = winners;
                population.initFitness(generation_number);

                //crossover using uniform : crossover
                List<LayoutIndividual> new_individuals = new ArrayList<LayoutIndividual>();
                LayoutIndividual top_individual = population.getPopulation().get(0);
                for (int i = 0; i < population.getN(); i++) {
                    Individual second_individual = (Individual) population.getPopulation().get(i);
                    //List<LayoutIndividual> cross_result = top_individual.crossover(second_individual, crossover_probability);
                    List<LayoutIndividual> cross_result = top_individual.onePointCrossover(second_individual);
                    new_individuals.add(cross_result.get(1));
                }
                population = new Population(new_individuals);

                //mutation
                //using bitFlipMutation
                for (int i = 0; i < population.getN(); i++) {
                    LayoutIndividual individual = (Individual) population.getPopulation().get(i);
                    individual = individual.mutation(mutation_probability);
                    population.getPopulation().set(i, individual);
                }
                population.initFitness(generation_number);
                System.out.print("Generation " + re + "--> ");
                evaluateFitness();
            }
            System.out.println();
             x = (double) Math.round(maxX * 1000d) / 1000d;
             y = (double) Math.round(maxY * 1000d) / 1000d;
             value = Math.sin(Math.PI * x) * (y - 3) / 2;

            //evaluateFitness();
            xm+=x;
            ym+=y;
            valuem+=value;
        }
        xm= (double) Math.round(xm/30 * 1000d) / 1000d;
        ym= (double) Math.round(ym/30 * 1000d) / 1000d;
        valuem = valuem/30;
        String prt = "MaxX: " + xm + " MaxY: " + ym + " Result --> " + (double) Math.round(valuem * 1000d) / 1000d;
        System.out.println(prt.replace(".", ","));

    }
}
