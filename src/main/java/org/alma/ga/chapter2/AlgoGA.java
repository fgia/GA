package org.alma.ga.chapter2;

public class AlgoGA {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100,0.01,0.95,0);
        Population population = ga.initPopulation(50);

        ga.evalPopulation(population);
        int generation = 1;
        while (ga.isTerminationConditionMet((population))== false) {
            System.out.println("Best solution: "+ population.getFittest(0).toString());

            // apply CROSSOVER

            // apply mutation

            // Evaluation population
            ga.evalPopulation(population);

            // increment generation
            generation++;
        }
        System.out.println(" Found solution in "+generation + " generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }

    /**
     * Quand nécessaire de faire du cross over, il faut sélectionner un parent.
     * Se fait sur la base de la valeur fitness.
     * Plus la valeur est grande plus il y a de chances d'être sélectionné.
     * @param population
     * @return
     */
    public Individual selectParent(Population population) {
        Individual individuals[] = population.getIndividuals();

        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;

        double spinWheel = 0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size()-1];
    }
}
