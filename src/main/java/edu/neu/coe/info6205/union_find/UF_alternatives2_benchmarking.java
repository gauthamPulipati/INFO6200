package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.neu.coe.info6205.util.Benchmark_Timer;

public class UF_alternatives2_benchmarking<X extends Comparable<X>> {
	
	public void func(X[] xs, int n) {
		Random random= new Random();
		WQUPC_SIZE uf= new WQUPC_SIZE(n,true,1);
		while(true) {
			int n1 = random.nextInt(n);
			int n2 = random.nextInt(n);
			
			uf.connect(n1, n2);
			
			if(uf.components()==1) {
				break;
			}
		}
		
	}
	
	public void func2(X[] xs, int n) {
		Random random = new Random();
		WQUPC_SIZE uf= new WQUPC_SIZE(n,true,2);
		while(true) {
			int n1 = random.nextInt(n);
			int n2 = random.nextInt(n);
			
			uf.connect(n1, n2);
			
			if(uf.components()==1) {
				break;
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		for(int i=10000;i<=200000;i=i+40000) {
			UF_alternatives2_benchmarking ufa = new UF_alternatives2_benchmarking();
			int n=i;
			Benchmark_Timer<Integer[]> timer_d = new Benchmark_Timer<>("Benchmarking", null,(x) -> ufa.func(x,n),null);
			Benchmark_Timer<Integer[]> timer_s = new Benchmark_Timer<>("Benchmarking", null,(x) -> ufa.func2(x, n),null);
		
			Integer[] arr= new Integer[1];
			Supplier sup=() -> arr;
			System.out.println("n= :"+i+"Path compression with 1 pass "+timer_d.runFromSupplier(sup, 45));
			System.out.println("n= :"+i+"Path compression with 2 pass "+timer_s.runFromSupplier(sup, 45));
		}
		//ufa.func(arr);
	}

}
