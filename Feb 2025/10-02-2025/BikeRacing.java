import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.time.*;

// Bike Racing Game- 10th Feb Update
// Using CountDownLatch to ensure all threads start exactly same time 
class Biker extends Thread {
	private static final Object lock = new Object();
	private static boolean raceStarted = false;
	private long timeTaken;
	private long startTime;
	private long endTime;
	private LocalTime endTimeInstant;
	private LocalTime startTimeInstant;
	private Duration totalTimeInstant;

	String name;
	private int distanceCovered = 0;
	// We declare a new CountDownLatch
	private CountDownLatch startSignal;

	Biker(String name, CountDownLatch startSignal) {
		// System.out.println("Enter the Biker Name:- ");
		// this.name=new Scanner(System.in).next();
		super(name);
		this.startSignal = startSignal;
	}

	public void run() {
		/*
		 * NO MORE NEED TO USE SYNCHRONISED BLOCK
		 * synchronized (lock) {
		 * while (!raceStarted) {
		 * try {
		 * lock.wait();
		 * } catch (InterruptedException e) {
		 * System.out.println(e);
		 * }
		 * }
		 * }
		 */

		try {
			startSignal.await();
			int startRace = 0;

			// Use DateTime class for time

			Random random = new Random();
			startTimeInstant = LocalTime.now();
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
			endTimeInstant = LocalTime.now();
			totalTimeInstant = Duration.between(startTimeInstant, endTimeInstant);
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

	public LocalTime getStartTimeInstant() {
		return startTimeInstant;
	}

	public LocalTime getEndTimeInstant() {
		return endTimeInstant;
	}

	public Duration getTotalTimeInstant() {
		return totalTimeInstant;
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
		// Declaring a new CountDownLatch
		CountDownLatch startSignal = new CountDownLatch(1);

		String[] names = { "Sanat", "Maddy", "Sumit", "Pusu", "Kadu", "Arun", "Shreya", "Melbin", "Sharli", "Girish" };

		for (int i = 0; i < bikers.length; i++) {
			bikers[i] = new Biker(names[i], startSignal);
			bikers[i].start();
		}

		Thread.sleep(500);
		System.out.println("Count Down Started....");
		for (int i = 10; i > 0; i--) {
			System.out.println(i);
			Thread.sleep(100);
		}
		System.out.println("GO");

		//"Decrementing" the CountDownLatch
		startSignal.countDown();

		for (Biker b : bikers) {
			b.join();
		}

		sort(bikers);
		for (int i = 0; i < bikers.length; i++) {
			System.out.println((i + 1) + ". " + bikers[i].getName() + " - Time: " + bikers[i].getTimeTaken() + " ms");
		}

		//Formatting the dashboard
		System.out.println("\nDashboard:");
		System.out.printf("%-10s %-20s %-30s %-20s %-20s%n", "Rank", "Biker", "Start Time", "End Time",
				"Total Time (ms)");
		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		for (int i = 0; i < bikers.length; i++) {
			Biker b = bikers[i];
			System.out.printf("%-10d %-20s %-30s %-30s %-20s%n",
					(i + 1),
					b.getName(),
					b.getStartTimeInstant(),
					b.getEndTimeInstant(),
					b.getTotalTimeInstant().toMillis() + " ms");
		}
	}

	public static void sort(Biker[] bikers) {
		// Sorting logic for Bikers in the ascending order of timeTaken
		java.util.Arrays.sort(bikers, (b1, b2) -> Long.compare(b1.getTimeTaken(), b2.getTimeTaken()));
	}
}