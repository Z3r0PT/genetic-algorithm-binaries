import java.util.Scanner;

public class GeneticAlgorithm {

    public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        int number_individuals = scanner.nextInt();
        int generation_number = scanner.nextInt();
        int chromosome_len = scanner.nextInt();
        double mutation_probability = scanner.nextDouble();
        double crossover_probability = scanner.nextDouble();
        */
        int number_individuals = 1000;
        int generations = 50;
        int chromossome_length = 25;
        double mutation = 0.5;
        double crossover = 0.1;

        FunctionOptimize opt2 = new FunctionOptimize(number_individuals, generations, chromossome_length, mutation, crossover);
        opt2.implementFunctionOptimize();
        System.exit(0);

        for(double i = 0.01; i < 0.1; i+=0.01)  {
            for(double j = 0.1; j < 0.5; j+=0.1) {
                FunctionOptimize opt = new FunctionOptimize(number_individuals,generations,chromossome_length, i, j);
                System.out.println("Number of individuals = " + number_individuals);
                System.out.println("Number of generations = " + generations);
                System.out.println("Chromossome length = " + chromossome_length);
                System.out.println("Mutation probability = " + i);
                System.out.println("Crossover probability = " + j);
                opt.implementFunctionOptimize();
                System.out.println();
            }
        }

    }
}
