package org.alma.ga.chapter2;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int ellitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int ellitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.ellitismCount = ellitismCount;
    }

    /**
     * Initialise une population de taille chromosomelength
     * @param chromosomeLength
     * @return
     */
    public Population initPopulation(int chromosomeLength){
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    /**
     * Calcul de la valeur de l'individu. Ce qui donnera l'intérêt de ce
     * candidat.
     * @param individual
     * @return
     */
    public double calculFitness(Individual individual) {
        int correctGenes = 0;
        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            if (individual.getGene(geneIndex)==1) {
                correctGenes++;
            }
        }

        double fitness = (double) correctGenes / individual.getChromosomeLength();
        individual.setFitness(fitness);
        return fitness;
    }

    /**
     * evalue la population en calculant la valeur de fitness
     * @param population
     */
    public void evalPopulation(Population population) {
        double populationFitness = 0;
        for (Individual individual:population.getIndividuals() ) {
            populationFitness+= calculFitness(individual);
        }
        population.setPopulationFitness(populationFitness);
    }

    /**
     * Permet de vérifier si le système a trouvé une bonne solution
     * @param population
     * @return
     */
    public boolean isTerminationConditionMet(Population population) {
        for (Individual individual:population.getIndividuals()) {
            if (Math.abs(individual.getFitness()-1)<0.00001) {
                return true;
            }
        }
        return  false;
    }
}
