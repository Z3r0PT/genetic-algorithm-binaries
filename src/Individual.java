
import java.util.*;

/**
 * Classe que permite a criacao de todos os detalhes de um individuo
 */
public class Individual implements LayoutIndividual{

    private int individual_size = 0; //tamanho do individuo
    private static Random generator = new Random(0); //gerador de genes
    private String individual = ""; //representacao do individuo
    private int zeros = 0; //total de zeros
    private int ones = 0; //total de uns
    private int base10individual = 0; //representacao do individuo convertido para base 10
    private double fitness = 0; //fitness do individuo
    private double x;
    private double y;

    /**
     * Construtor para definir os detalhes de um individuo
     * @param individual Representacao de um individuo
     */
    public Individual(String individual) {
        this.individual_size = individual.length();
        this.individual = individual;
        setIndividual();
    }

    /**
     * Construtor para criar uma representacao do individuo, assim como a sua representacao em base 10
     * @param l Tamanho do individuo
     */
    public Individual(int l) {
        this.individual_size = l;
        for (int i = 0; i < l; i++) {
            if (generator.nextDouble() < 0.5) {
                individual += 0;
                zeros++;
            } else {
                individual += 1;
                ones++;
                base10individual += Math.pow(2, l - 1 - i);
            }
        }
    }

    /**
     * Define a primeira metade o individuo
     * @param x Primeira metade do individuo
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Define a segunda metade do individuo
     * @param y Segunda metade do individup
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Devolve a primeira metade do individuo
     * @return Primeira metade do individuo
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * Devolve a segunda metade do individuo
     * @return Segunda metade do individup
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * Metodo para definir o total de zeros e uns num individuo, assim como a sua representacao em base 10
     */
    private void setIndividual() {
        for (int i = 0; i < individual_size; i++) {
            if (individual.charAt(i) == '0') {
                zeros++;
            } else {
                ones++;
                base10individual += Math.pow(2, individual_size - 1 - i);
            }
        }
    }

    /**
     * Metodo que faz o crossover usando One Point Crossover
     * @param individual2 Individuo com o qual se ira fazer o crossover
     * @return Lista de todos os filhos resultantes do crossover
     */
    public List<LayoutIndividual> onePointCrossover(LayoutIndividual individual2) {
        int n = individual.length() - 1;
        double r = generator.nextDouble();
        double c = Math.round(n*r);
        String str1 = "";
        String str2 = "";
        for (int i=0; i<n + 1; i++) {
            if (i<=c) {
                str1 += individual.charAt(i);
                str2 += individual2.getIndividual().charAt(i);
            }
            else {
                str2 += individual.charAt(i);
                str1 += individual2.getIndividual().charAt(i);
            }
        }
        List<LayoutIndividual> childs = new ArrayList<>();
        childs.add(new Individual(str1));
        childs.add(new Individual(str2));
        return childs;
    }

    /**
     * Metodo que faz o crossover usando uniform crossover
     * @param individual2 Individuo com o qual se ira fazer o crossover
     * @param probability Probabilidade de ocorrer troca de genes
     * @return Lista de todos os filhos resultantes do crossover
     */
    public List<LayoutIndividual> crossover(LayoutIndividual individual2, double probability) {
        String s1 = "";
        String s2 = "";
        for (int i = 0; i < individual.length(); i++) {
            double u = generator.nextDouble();
            if (u < probability) {
                s2 += individual.charAt(i);
                s1 += individual2.getIndividual().charAt(i);
            } else {
                s1 += individual.charAt(i);
                s2 += individual2.getIndividual().charAt(i);
            }
        }
        List<LayoutIndividual> children = new ArrayList<>();
        children.add(new Individual(s1));
        children.add(new Individual(s2));
        return children;
    }

    /**
     * Metodo que faz a mutação do individuo atual
     * @param p Probabilidade de ocorrer mutacao
     * @return Filho mutado
     */
    public LayoutIndividual mutation(double p) {
        String s = "";
        for (int i = 0; i < individual.length(); i++) {
            double u = generator.nextDouble();
            if (u < p) {
                s += individual.charAt(i) == '0' ? '1' : '0';
            } else {
                s += individual.charAt(i);
            }
        }
        return new Individual(s);
    }

    /**
     * Metodo que devolve o tamanho do individuo
     * @return Tamanho do individuo
     */
    @Override
    public int getL() {
        return individual_size;
    }

    /**
     * Metodo que devolve o fitness do individuo
     * @return Fitness do individuo
     */
    @Override
    public double getFitness() {
        return fitness;
    }

    /**
     * Metodo que devolve a representacao em base 10 do individuo
     * @return Representacao em base 10 do individuo
     */
    public int getBase10Individual() {
        return base10individual;
    }

    /**
     * Metodo que devolve a representacao do individuo
     * @return Representacao do individuo
     */
    @Override
    public String getIndividual() {
        return this.individual;
    }

    public static Random getRandomGenerator() {
        return generator;
    }

    /**
     * Metodo que devolve o individuo e o seu respetivo fitness, separado por um espaao
     * @return Representacao do individuo e seu fitness, separado por um espaco
     */
    public String toString() {
        return individual + " " + fitness;
    }

    /**
     * Metodo que calcula o fitness usando um range de valores
     * @param min Valor mais baixo
     * @param max Valor mais alto
     */
    @Override
    public void setSpecialFitness(double min, double max) {
        //get x value
        int base_val = 0;
        for (int i = 0; i < individual_size / 2; i++) {
            if (individual.charAt(i) == '0') {
            } else {
                base_val += Math.pow(2, individual_size / 2 - 1 - i);
            }
        }
        double x = min + (max - min) * ((double) base_val / (double) (Math.pow(2, individual_size / 2) - 1));

        //get y
        base_val = 0;
        for (int i = individual_size / 2; i < individual_size; i++) {
            if (individual.charAt(i) == '0') {
            } else {
                base_val += Math.pow(2, individual_size - 1 - i);
            }
        }
        double y = min + (max - min) * ((double) base_val / (double) (Math.pow(2, individual_size / 2) - 1));
        x = (double)Math.round(x * 1000d) / 1000d;
        y = (double)Math.round(y * 1000d) / 1000d;
        this.setX(x);
        this.setY(y);

        double fitness_val =(double)Math.round(Math.sin(Math.PI * x) * (y - 3) / 2 * 1000d) / 1000d;
        fitness = fitness_val;
    }
}
