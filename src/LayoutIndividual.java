import java.util.List;

/**
 * Interface de cada individuo
 */
public interface LayoutIndividual {
    /**
     * Metodo que devolve o fitness do individuo
     * @return fitness do individuo
     */
    double getFitness();

    /**
     * Metodo que devolve a lista de filhos do individuo
     * @param individual Individuo atual
     * @param probability Probabilidade de ocorrer crossover entre genes
     * @return Lista de filhos do individuo
     */
    List<LayoutIndividual> crossover(LayoutIndividual individual, double probability);

    List<LayoutIndividual> onePointCrossover(LayoutIndividual individual);
    /**
     * Metodo que faz a mutacao no individuo
     * @param probability Probabilidade de ocorrer mutacao
     * @return Individuo mutado
     */
    LayoutIndividual mutation(double probability);

    /**
     * Metodo que devolve a representacao do individuo
     * @return Representacao do individuo
     */
    String getIndividual();

    /**
     * Metodo que devolve o tamanho do individuo
     * @return Tamanho do individuo
     */
    int getL();
    void setSpecialFitness(double min, double max);

    //Em principio esta interface so suporta individuos que tenham duas variaveis
    //Podiamos ter implementado isto melhor, porque assim a interface parece estar "inutil"

    /**
     * Metodo que devolve a primeira metade do individuo
     * @return Primeira metade do individuo
     */
    double getX();

    /**
     * Metodo que devolve a segunda metade do individuo
     * @return Segunda metade do individuo
     */
    double getY();
}
