import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String userIn = in.nextLine();
        System.out.println(calc(userIn));
        in.close();
    }

    public static String calc(String input) throws Exception {
        input = input.trim().toUpperCase().replaceAll(" +", "");
        String[] userSplit = input.split("[+-/*/]");
        char operand = input.charAt(userSplit[0].length());
        boolean aIsRoman = userSplit[0].matches("[IVX]+"),
                bIsRoman = userSplit[1].matches("[IVX]+");
        if (userSplit.length == 1)
            throw new Exception("//т.к. строка не является математической операцией");
        if (userSplit.length > 2)
            throw new Exception("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        if (aIsRoman != bIsRoman)
            throw new NumberFormatException("// т.к. используются одновременно разные системы счисления");
        int a, b;
        if (!aIsRoman) {
            try {
                a = Integer.parseInt(userSplit[0]);
                b = Integer.parseInt(userSplit[1]);
            } catch (Exception e) {
                throw new NumberFormatException("Введено некорректное число");
            }
        } else {
            a = RomanToInt(userSplit[0]);
            b = RomanToInt(userSplit[1]);
        }
        if (!(a > 0 && a <= 10) || !(b > 0 && b <= 10))
            throw new Exception("//т.к. калькудятор принимает на вход числа от 1 до 10 включительно");

        int result = MathOperation(a, b, operand);
        if (result <= 0 && aIsRoman)
            throw new Exception("//т.к. в римской системе нет отрицательных и нулевых чисел");
        return aIsRoman ? IntToRoman(result) : Integer.toString(result);
    }

    public static int MathOperation(int a, int b, char operand) throws Exception {
        int result;
        switch (operand) {
            case '+' -> result = (a + b);
            case '-' -> result = (a - b);
            case '/' -> result = (a / b);
            case '*' -> result = (a * b);
            default ->
                    throw new Exception("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        return result;
    }

    public static int RomanToInt(String val) {
        if (val.endsWith(Roman.IV.name()))
            return Roman.IV.value + RomanToInt(val.substring(0, val.length() - 2));
        if (val.endsWith(Roman.IX.name()))
            return Roman.IX.value + RomanToInt(val.substring(0, val.length() - 2));
        if (val.endsWith("VX"))
            throw new NumberFormatException("Введено некорректное число");
        if (val.endsWith(Roman.X.name()))
            return Roman.X.value + RomanToInt(val.substring(0, val.length() - 1));
        if (val.endsWith(Roman.V.name()))
            return Roman.V.value + RomanToInt(val.substring(0, val.length() - 1));
        if (val.endsWith(Roman.I.name()))
            return Roman.I.value + RomanToInt(val.substring(0, val.length() - 1));
        if (val.isEmpty())
            return 0;
        throw new NumberFormatException("Введено некорректное число");
    }

    public static String IntToRoman(int val) {
        String result = "";
        result += Roman.C.RepeatName(val / Roman.C.value);
        val %= Roman.C.value;
        result += Roman.XC.RepeatName(val / Roman.XC.value);
        val %= Roman.XC.value;
        result += Roman.L.RepeatName(val / Roman.L.value);
        val %= Roman.L.value;
        result += Roman.XL.RepeatName(val / Roman.XL.value);
        val %= Roman.XL.value;
        result += Roman.X.RepeatName(val / Roman.X.value);
        val %= Roman.X.value;
        result += Roman.IX.RepeatName(val / Roman.IX.value);
        val %= Roman.IX.value;
        result += Roman.V.RepeatName(val / Roman.V.value);
        val %= Roman.V.value;
        result += Roman.IV.RepeatName(val / Roman.IV.value);
        val %= Roman.IV.value;
        result += Roman.I.RepeatName(val);
        return result;
    }
}