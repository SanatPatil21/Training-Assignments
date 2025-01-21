import java.util.Random;
import java.util.Scanner;

class Biker extends Thread {
	private static final Object lock = new Object();
	private static boolean raceStarted = false;
	private long timeTaken;
	private long startTime;
	private long endTime;
	String name;
	private int distanceCovered = 0;

	Biker(String name) {
		// System.out.println("Enter the Biker Name:- ");
		// this.name=new Scanner(System.in).next();
		super(name);
	}

	public void run() {
		synchronized (lock) {
			while (!raceStarted) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}

		try {
			int startRace = 0;
		
			Random random = new Random();
			startTime = System.currentTimeMillis();
			long m = System.currentTimeMillis();
			while (startRace < RacingDetails.distance) {
				int distance = random.nextInt(51) + 70;
				startRace += distance;
				distanceCovered += distance;

				if (distanceCovered % 300 < distance) {
					// We dont print every time
					// updateProgress(getName(),distanceCovered);
					System.out.println("Biker " + getName() + " has covered " + distanceCovered + RacingDetails.units);
				}
				Thread.sleep(100);
			}
			endTime = System.currentTimeMillis();
			timeTaken = System.currentTimeMillis() - m;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}
	public long getTotalTime() {
		return timeTaken;
	}

	public static void startRace() {
		synchronized (lock) {
			raceStarted = true;
			lock.notifyAll();
		}
	}
	
}

class RacingDetails {
	public static int noOfBikers = 10;
	public static int distance;
	public static String units = "Meters";
	// Static input taken for now
}

public class BikeRacing {
	public static void main(String args[]) throws Exception {
		Biker[] bikers = new Biker[RacingDetails.noOfBikers];
		RacingDetails.distance = 1000;

		String[] names = { "Sanat", "Maddy", "Sumit", "Pusu", "Kadu", "Arun", "Shreya", "Melbin", "Sharli", "Girish" };

		for (int i = 0; i < bikers.length; i++) {
			bikers[i] = new Biker(names[i]);
			bikers[i].start();
		}

		Thread.sleep(500);
		System.out.println("Count Down Started....");
		for (int i = 10; i > 0; i--) {
			System.out.println(i);
			Thread.sleep(100);
		}
		System.out.println("GO");

		Biker.startRace();
		

		for (Biker b : bikers) {
			b.join();
		}

		sort(bikers);
		for (int i = 0; i < bikers.length; i++) {
            System.out.println((i + 1) + ". " + bikers[i].getName() + " - Time: " + bikers[i].getTimeTaken() + " ms");
        }

		System.out.println("\nDashboard:");
        for (Biker b : bikers) {
            System.out.println("Biker " + b.getName() + " started at " + b.getStartTime() + " and finished at " + b.getEndTime()+ " Total Time taken: "+b.getTotalTime());
        }
	}

	public static void sort(Biker[] bikers) {
        // Sorting logic for Bikers in the ascending order of timeTaken
        java.util.Arrays.sort(bikers, (b1, b2) -> Long.compare(b1.getTimeTaken(), b2.getTimeTaken()));
    }
}