/*
 * Copyright (c) 2018. Phasmid Software
 */

package edu.neu.coe.info6205.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.simple.*;
import java.util.Random;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

/**
 * This class implements a simple Benchmark utility for measuring the running time of algorithms.
 * It is part of the repository for the INFO6205 class, taught by Prof. Robin Hillyard
 * <p>
 * It requires Java 8 as it uses function types, in particular, UnaryOperator&lt;T&gt; (a function of T => T),
 * Consumer&lt;T&gt; (essentially a function of T => Void) and Supplier&lt;T&gt; (essentially a function of Void => T).
 * <p>
 * In general, the benchmark class handles three phases of a "run:"
 * <ol>
 *     <li>The pre-function which prepares the input to the study function (field fPre) (may be null);</li>
 *     <li>The study function itself (field fRun) -- assumed to be a mutating function since it does not return a result;</li>
 *     <li>The post-function which cleans up and/or checks the results of the study function (field fPost) (may be null).</li>
 * </ol>
 * <p>
 * Note that the clock does not run during invocations of the pre-function and the post-function (if any).
 *
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark_Timer<T> implements Benchmark<T> {

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     *
     * @param supplier a Supplier of a T
     * @param m        the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */
    @Override
    public double runFromSupplier(Supplier<T> supplier, int m) {
        logger.info("Begin run: " + description + " with " + formatWhole(m) + " runs");
        // Warmup phase
        final Function<T, T> function = t -> {
            fRun.accept(t);
            return t;
        };
        new Timer().repeat(getWarmupRuns(m), supplier, function, fPre, null);

        // Timed phase
        return new Timer().repeat(m, supplier, function, fPre, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun, Consumer<T> fPost) {
        this.description = description;
        this.fPre = fPre;
        this.fRun = fRun;
        this.fPost = fPost;
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun) {
        this(description, fPre, fRun, null);
    }

    /**
     * Constructor for a Benchmark_Timer with only fRun and fPost Consumer parameters.
     *
     * @param description the description of the benchmark.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, Consumer<T> fRun, Consumer<T> fPost) {
        this(description, null, fRun, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer where only the (timed) run function is specified.
     *
     * @param description the description of the benchmark.
     * @param f           a Consumer function (i.e. a function of T => Void).
     *                    Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, Consumer<T> f) {
        this(description, null, f, null);
    }
    
    public Supplier<T> Supp(Supplier <T> SS){
    	return SS;
    }

    private final String description;
    private final UnaryOperator<T> fPre;
    private final Consumer<T> fRun;
    private final Consumer<T> fPost;
    
    public static void main(String[] args) {
    	Random random=new Random();
    	
    	
    	int n=1000;
    	int runs=45;
    	
    	for(int i=0;i<6;i++) {
    		
    		Integer[] array_random=new Integer[n];
    		Integer[] array_sorted=new Integer[n];
    		Integer[] array_partial=new Integer[n];
    		Integer[] array_reverse=new Integer[n];
    		
    		array_sorted[0]=random.nextInt();
    		
    		for(int j=0;j<n;j++) {
    			array_random[j]=random.nextInt()/2;
    			if(j!=0) {
    				array_sorted[j]=array_sorted[j-1]+1+(random.nextInt()/10);
    			}
    			if(j%3!=0) {
    				array_partial[j]=array_sorted[j];
    			}
    			else {
    				array_partial[j]=array_random[j];
    			}
    			array_reverse[n-j-1]=array_sorted[j];
    		}
    		
    		InsertionSort isr=new InsertionSort();
    		Benchmark_Timer<Integer[]> timer_r = new Benchmark_Timer<>("Benchmarking", null,(x) -> isr.sort(x,0,x.length),null);
    		
    		Supplier sup_random=() -> array_random;
    		Supplier sup_sorted=() -> array_sorted;
    		Supplier sup_partial=() -> array_partial;
    		Supplier sup_reverse=() -> array_reverse;
    		
    		double time_random= timer_r.runFromSupplier(sup_random, runs);
    		System.out.println("When n is "+n+" mean time is "+time_random+" for a random array");
    		System.out.println();
    		
    		double time_sorted= timer_r.runFromSupplier(sup_sorted, runs);
    		System.out.println("When n is "+n+" mean time is "+time_sorted+" for a sorted array");
    		System.out.println();
    		
    		double time_partial= timer_r.runFromSupplier(sup_partial, runs);
    		System.out.println("When n is "+n+" mean time is "+time_partial+" for a partial array");
    		System.out.println();
    		
    		double time_reverse= timer_r.runFromSupplier(sup_reverse, runs);
    		System.out.println("When n is "+n+" mean time is "+time_reverse+" for a reversed array");
    		System.out.println();
    		
    		//System.out.println(array_sorted[0]+"     --    "+array_reverse[0]);
    		
    		n=n*2;
    	}
    }

    final static LazyLogger logger = new LazyLogger(Benchmark_Timer.class);
}
