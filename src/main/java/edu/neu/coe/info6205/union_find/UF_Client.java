package edu.neu.coe.info6205.union_find;

import java.util.*;

public class UF_Client {
	
	public static int count(int n) {
		int m=0;
		Random random= new Random();
		UF_HWQUPC uf= new UF_HWQUPC(n,true);
		
		while(true) {
			int n1=random.nextInt(n);
			int n2=random.nextInt(n);
			
			uf.connect(n1, n2);
			m++;
						
			if( uf.components()==1) {
				break;
			}
		}
		
		return m;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc= new Scanner(System.in);
		int n=10000;
		
		for(int i=0;i<5;i++) {
			UF_Client uf_client=new UF_Client();
			int m=uf_client.count(n);
			System.out.println("n value: "+n+" m value: "+m);
			n=n*2;
		}
		
		
		

	}

}
