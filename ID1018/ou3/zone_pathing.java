import java.util.*;
class zone_pathing {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.print("Input U station count: ");
    int station_count_u = in.nextInt();
    System.out.print("Input V station count: ");
    int station_count_v = in.nextInt();
    int[] stations_v = new int[station_count_v];
    int[] selected_path = new int[3];

    /* Input distances from V(j) station to Y */
    for (int station = 0; station < station_count_v; station++) {
      System.out.print("Input distance from V(" + (station + 1) + ") to Y: ");
      stations_v[station] = in.nextInt();
    }
    /* Input distance from X to U(i), then from U(i) to V(j) */
    for (int station = 0; station < station_count_u; station++) {
      System.out.print("Input distance from X to U(" + (station + 1) + ") station: ");
      int x_to_u = in.nextInt();
      /* From U to V */
      for (int station_v = 0; station_v < station_count_v; station_v++) {
        System.out.print("Input distance from U(" + (station + 1) + ") to V(" +
                         (station_v + 1) + "): ");

        int input = in.nextInt();
        if ((input + stations_v[station_v] + x_to_u) < selected_path[1] || 
            selected_path[1] == 0) {
          selected_path[0] = station;
          selected_path[1] = input + stations_v[station_v] + x_to_u;
          selected_path[2] = station_v;
        }
      }
    }

    System.out.println(Arrays.toString(selected_path));
    System.out.println("Path: X -> U(" + (selected_path[0] + 1) + ") -> V(" +
                       (selected_path[2] + 1) + ") -> Y");
  }
}
