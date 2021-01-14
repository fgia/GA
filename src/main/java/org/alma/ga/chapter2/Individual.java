package org.alma.ga.chapter2;

public class Individual {
    private int[] chromosome;
    private double fitness;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for(int gene= 0; gene<chromosomeLength;gene++) {
            if (Math.random()<0.5) {
                this.setGene(gene,1);
            }
            else {
                this.setGene(gene,0);
            }
        }
    }

    public void setGene(int position, int gene) {
        this.chromosome[position] = gene;
    }
    public int getGene(int position) {
        return this.chromosome[position];
    }
    public int getChromosomeLength() {
        return this.chromosome.length;
    }
    public int[] getChromosome() {
        return chromosome;
    }


    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    @Override
    public String toString() {
        String sChromosome = "";
        for (int i=0;i< chromosome.length;i++) {
            sChromosome = sChromosome + chromosome[i];
        }
        return sChromosome;
    }

}
