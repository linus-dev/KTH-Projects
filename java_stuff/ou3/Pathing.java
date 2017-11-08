class Pathing {
  public static int RapidPath(int[] x_to_u, int[][] u_to_v, int[] v_to_y) {
    int[] stations = Stations(x_to_u, u_to_v, v_to_y);
    return (x_to_u[stations[0]] + u_to_v[stations[0]][stations[1]] +
            v_to_y[stations[1]]);
  }
  public static int[] Stations(int[] x_to_u, int[][] u_to_v, int[] v_to_y) {
    int path_shortest = x_to_u[0] + u_to_v[0][0] + v_to_y[0];
    System.out.println(path_shortest);
    int[] path_stations = new int[2];
    for (int station = 0; station < x_to_u.length; station++) {
      for (int station_v = 1; station_v < v_to_y.length; station_v++) {
        if ((x_to_u[station] + u_to_v[station][station_v] + v_to_y[station_v]) <
            path_shortest) {
          path_shortest = x_to_u[station] + u_to_v[station][station_v] +
                          v_to_y[station_v];
          path_stations[0] = station;
          path_stations[1] = station_v;
        }
      }
    }
    System.out.println(path_shortest);
    return path_stations;
  }
}
