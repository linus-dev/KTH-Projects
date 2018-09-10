class NaturalNumbers {
  public static void main(String[] args) {
    System.out.println("Operations with natural numbers as strings");
    java.util.Scanner in = new java.util.Scanner(System.in);
    String number_one = in.nextLine();
    String number_two = in.nextLine();
    System.out.print("Subtraction: ");
    System.out.println(Subtract(number_one, number_two));
    System.out.print("Addition: ");
    System.out.println(Add(number_one, number_two));
  }
  public static String Add(String a, String b) {
    String[] numbers = EqualizeNumbers(a, b);
    String sum = new String();
    int numeric_one = 0;
    int numeric_two = 0;
    int numeric_carry = 0;
    for (int i = (numbers[0].length()-1); i >= 0; i--) {
      numeric_one = Character.getNumericValue(numbers[0].charAt(i));
      numeric_two = Character.getNumericValue(numbers[1].charAt(i));
      if ((numeric_one + numeric_two + numeric_carry) >= 10) {
        sum = Integer.toString((numeric_one + numeric_two + numeric_carry) % 10) + sum;
        numeric_carry = 1;
      } else {
        sum = Integer.toString((numeric_one + numeric_two + numeric_carry) % 10) + sum;
        numeric_carry = 0;
      }
    }
    if (numeric_carry == 1) {
      sum = "1" + sum;
    }
    return sum;
  }
  public static String Subtract(String a, String b) {
    String[] numbers = EqualizeNumbers(a, b);
    String sum = new String();
    int numeric_one = 0;
    int numeric_two = 0;
    int numeric_carry = 0;
    for (int i = (numbers[0].length()-1); i >= 0; i--) {
      numeric_one = Character.getNumericValue(numbers[0].charAt(i));
      numeric_two = Character.getNumericValue(numbers[1].charAt(i));
      numeric_one = numeric_one + numeric_carry;
      if (((numeric_one + numeric_carry) - numeric_two) < 0) {
        if (i == 0) {
          sum = "-" + Integer.toString(Math.abs(numeric_one - numeric_two)) + sum;
        } else {
          sum = Integer.toString((numeric_one + 10) - numeric_two) + sum;
          numeric_carry = -1;
        }
      } else {
        sum = Integer.toString(numeric_one -  numeric_two) + sum;
        numeric_carry = 0;
      }
    }
    return sum;
  }
  public static String[] EqualizeNumbers(String a, String b) {
    String long_number = new String();
    String short_number = new String();

    if (a.length() >= b.length()) {
      long_number = a;
      short_number = b;
    } else {
      long_number = b;
      short_number = a;
    }
    for (int i = 0; i < Math.abs((a.length() - b.length())); i++) {
      short_number = "0" + short_number;
    }
    return new String[]{long_number, short_number}; 
  }
}
