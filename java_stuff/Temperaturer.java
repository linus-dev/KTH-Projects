import java.util.*;

class Temperaturer {
  public static void main(String[] args) {
    System.out.println ("TEMPERATURER\n");
    Scanner in = new Scanner(System.in);
    in.useLocale(Locale.US);

    System.out.print("Antalet veckor: ");
    int antalVeckor = in.nextInt();
    System.out.print("Antalet mätnningar per vecka: ");
    int antalMatningarPerVecka = in.nextInt();
    
    double[][] t = new double[antalVeckor][antalMatningarPerVecka];
    for (int vecka = 0; vecka < antalVeckor; vecka++) {
      System.out.println("Temperaturer - vecka " + (vecka + 1) + ":"); 
      for (int matning = 0; matning < antalMatningarPerVecka; matning++) {
        t[vecka][matning] = in.nextDouble();
      }
      System.out.println();
    }
    System.out.println("-------------");

    double[] minT = new double[antalVeckor];
    double[] maxT = new double[antalVeckor];
    double[] sumT = new double[antalVeckor];
    double[] avgT = new double[antalVeckor];
    for (int vecka = 0; vecka < antalVeckor; vecka++) {
      double minTemp = t[vecka][0];
      double maxTemp = t[vecka][0];
      double sumTemp = 0;
      double avgTemp = 0;
      for (int matning = 0; matning < antalMatningarPerVecka; matning++) {
        double curTemp = t[vecka][matning];
        minTemp = curTemp < minTemp ? curTemp : minTemp;
        maxTemp = curTemp > maxTemp ? curTemp : maxTemp;
        sumTemp += curTemp;
        avgTemp += curTemp;
      }
      minT[vecka] = minTemp;
      maxT[vecka] = maxTemp;
      sumT[vecka] = sumTemp;
      avgT[vecka] = avgTemp/antalMatningarPerVecka;
      System.out.println("Week: " + (vecka + 1) + " Low: " + minTemp + 
                         " High: " + maxTemp + " Avg: " + avgT[vecka]);
    }
    System.out.println("-------------");
    double  minTemp = minT[0];
    double  maxTemp = maxT[0];
    double  sumTemp = 0;
    double  medelTemp = 0;
    for (int vecka = 0; vecka < antalVeckor; vecka++) {
      minTemp = minT[vecka] < minTemp ? minT[vecka] : minTemp;
      maxTemp = maxT[vecka] > maxTemp ? maxT[vecka] : maxTemp;
      sumTemp += sumT[vecka];
    }
    medelTemp = sumTemp / (antalVeckor * antalMatningarPerVecka);
    System.out.println("Min: " + minTemp + " Max: " + maxTemp +
                       " Avg: " + medelTemp);
  }
}
