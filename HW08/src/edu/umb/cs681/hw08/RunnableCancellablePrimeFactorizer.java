package edu.umb.cs681.hw08;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {
	

	private ReentrantLock lock=new ReentrantLock();
	private volatile Boolean done =false;
	
	

	public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
		super(dividend, from, to);
	}

	public void setDone() {
			done=true;
	}
	
	public void generatePrimeFactors() {
        long n = dividend;
        long divisor = from;

        while (n > 1 && divisor <= to) {
        		if(done) break;
	            if (n % divisor == 0) {
	                factors.add(divisor);
	                n /= divisor;
	            } else {
	                divisor++;
	            }
        }
    }
	
	public static void main(String[] args) {
		System.out.println("Runnable cancellable prime Factorizer");
		RunnableCancellablePrimeFactorizer r1 = new RunnableCancellablePrimeFactorizer(84L, 2L, 100L);
		RunnableCancellablePrimeFactorizer r2 = new RunnableCancellablePrimeFactorizer(84L, 2L, 100L);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start(); 
		t2.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		r1.setDone();
		r2.setDone();
		System.out.print("Factorizer 1 generated factors : ");
		r1.getPrimeFactors().forEach((Long factor) -> System.out.print(factor+" "));
		System.out.print("\nFactorizer 2 generated factors : ");
		r2.getPrimeFactors().forEach((Long factor) -> System.out.print(factor+" "));
    }
	
        
}
