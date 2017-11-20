mport java.util.*;

class zoning {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.print("Input U station count: ");
    int station_count_u = in.nextInt();
    System.out.print("Input V station count: ");
    int station_count_v = in.nextInt();
    int[]   x_to_u = new int[station_count_u];
    int[][] u_to_v = new int[station_count_u][station_count_v];
    int[]   v_to_y = new int[station_count_v];
    for (int station_v = 0; station_v < station_count_v; station_v++) {
      System.out.print("Input distance from V(" + (station_v + 1) + ") to Y:");
      v_to_y[station_v] = in.nextInt();
    }
    for (int station = 0; station < station_count_u; station++) {
      System.out.print("Input distance from X to U(" + (station + 1) + "):");
      x_to_u[station] = in.nextInt();
      for (int station_v = 0; station_v < station_count_v; station_v++) {
        System.out.print("Input distance from U(" + (station + 1) + ") to V(" +
                         (station_v + 1) + "): ");
        u_to_v[station][station_v] = in.nextInt();
      }
    }
    int[] stations = Pathing.Stations(x_to_u, u_to_v, v_to_y);
    System.out.println(stations[1] + " " + stations[0]);    
  }
}
