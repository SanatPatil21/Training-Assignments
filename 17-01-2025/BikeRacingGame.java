import java.util.Random;

class Biker extends Thread {
    public void run() {
        try {
            int startRace = 0;
            Random random = new Random();
            //Record the start and end time for indivudual bikers
            long m = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+" Started the Race");
            while (startRace < 100) {
                int distance =  random.nextInt(20)+1;
                startRace+=distance;
                Thread.sleep(100);
                // System.out.println("Racer: "+Thread.currentThread().getName()+" Track Covered :"+startRace);
            }
            long result = System.currentTimeMillis() - m;
            System.out.println(Thread.currentThread().getName()+" Finished the Race in "+result+" Milli Seconds");
            // storeResults(Thread.currentThread().getName(),result);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

public class BikeRacingGame {
    public static void main(String args[]) throws Exception {
        System.out.println("Welcome to Bike Racing Game");


        
        int countDown = 10;
        while (countDown != 0) {
            System.out.println(countDown);
            countDown--;
            Thread.sleep(200);
        }
        
        
        System.out.println("GOOOOO");

        String[] names={"Sanat","Maddy","Sumit","Pusu","Kadu","Arun","Shreya","Melbin","Sharli","Girish"};       

        for(int i=0;i<10;i++){
            Biker b = new Biker();
            Thread t = new Thread(b);
            t.setName(names[i]);
            t.start();
        }        
    }
}