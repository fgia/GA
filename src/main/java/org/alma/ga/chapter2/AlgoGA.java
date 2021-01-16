package org.alma.ga.chapter2;

public class AlgoGA {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100,0.01,0.95,3);
        Population population = ga.initPopulation(50);

        ga.evalPopulation(population);
        int generation = 1;
        while (ga.isTerminationConditionMet((population))== false) {
            System.out.println("Best solution: "+ population.getFittest(0).toString());

            // apply CROSSOVER
            population = ga.crossoverPopulation(population);
            // apply mutation
            population = ga.mutatePopulation(population);
            // Evaluation population
            ga.evalPopulation(population);

            // increment generation
            generation++;
        }
        System.out.println(" Found solution in "+generation + " generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }

}
