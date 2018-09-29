class test {
  public static int min(int[] element) throws IllegalArgumentException {
    if (element.length == 0) {
      throw new IllegalArgumentException("Tom Samling");
    }
    int[] sekvens = element;
    int antaletPar = sekvens.length / 2;
    int antaletOparadeElement = sekvens.length % 2;
    int antaletTankbaraElement = antaletPar + antaletOparadeElement;
    int[] delsekvens = new int[antaletTankbaraElement];
    int i = 0;
    int j = 0;
    while (antaletPar >= 1) {
      i = 0;
      j = 0;
      while (j < antaletPar) {
        delsekvens[j++] = (sekvens[i] < sekvens[i+1]) ? sekvens[i] : sekvens[i+1];
        i += 2;
      }
      if (antaletOparadeElement == 1) {
        delsekvens[j] = sekvens[i];
      }
      System.out.println(java.util.Arrays.toString(delsekvens));
     
      sekvens = delsekvens;
      antaletPar = antaletTankbaraElement / 2;
      antaletOparadeElement = antaletTankbaraElement  % 2;
      antaletTankbaraElement = antaletPar + antaletOparadeElement;
      
      System.out.println(antaletPar);
      System.out.println(antaletOparadeElement);
      System.out.println(antaletTankbaraElement);
    }
    return sekvens[0];
  }
  public static void main(String[] args) {
    int[] arr = {1, 4, -3, 0,
                 2, 40, 1, 3,
                 4, 5, 10, 43,
                 23, 24, 19, 15,
                 -4, 36, 40};
    int[] arr_b = {0};
    System.out.println(min(arr));
  }
}
