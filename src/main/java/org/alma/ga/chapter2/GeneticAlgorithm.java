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

    /**
     * Crossover population
     * Effectue le mix entre individus . Le mix ne s'applique pas aux meilleurs et à un taux réduit
     * @param population
     * @return
     */
    public Population crossoverPopulation(final Population population) {
        // Create new population
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // recherche l'élément en populationindex position
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            // On considère que les ellitismCount sont intouchables.
            // application avec un taux de crossoverRate.
            if (this.crossoverRate > Math.random() && populationIndex > this.ellitismCount) {
                // initialize offspring
                Individual offspring = new Individual(parent1.getChromosomeLength());

                // trouver le second parent
                Individual parent2 = selectParent(population);

                // boucle sur le genome
                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    // on utilise la moitié du parent et la moitié du second parent (en théorie)
                    // car la génération aléatoire peu plus favoriser l'un que l'autre
                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex,parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex,parent2.getGene(geneIndex));
                    }
                }

                // Add offspring to new population
                newPopulation.setIndividual(populationIndex,offspring);
            } else {
                // ajouter l'individu à la nouvelle population sans cross over.
                newPopulation.setIndividual(populationIndex,parent1);
            }
        }
        return newPopulation;

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

    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(this.populationSize);

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if (populationIndex>this.ellitismCount) {
                    if (this.mutationRate>Math.random()) {
                        int newGene = 1;
                        if (individual.getGene(geneIndex)==1) {
                            newGene = 0;
                        }
                        //mutate the gene
                        individual.setGene(geneIndex,newGene);
                    }
                }
            }
            newPopulation.setIndividual(populationIndex,individual);
        }
        return newPopulation;
    }
}
