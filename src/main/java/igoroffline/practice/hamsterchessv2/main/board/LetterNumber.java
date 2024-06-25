package igoroffline.practice.hamsterchessv2.main.board;

import igoroffline.practice.hamsterchessv2.main.util.Messages;

public class LetterNumber {

    public static Letter getLetterEnum(int letter) {
        return switch (letter) {
            case 0 -> Letter.A;
            case 1 -> Letter.B;
            case 2 -> Letter.C;
            case 3 -> Letter.D;
            case 4 -> Letter.E;
            case 5 -> Letter.F;
            case 6 -> Letter.G;
            case 7 -> Letter.H;
            default -> throw new IllegalArgumentException(Messages.UNKNOWN_LETTER);
        };
    }

    public static String getNumber(int number) {
        return switch (number) {
            case 0 -> "1";
            case 1 -> "2";
            case 2 -> "3";
            case 3 -> "4";
            case 4 -> "5";
            case 5 -> "6";
            case 6 -> "7";
            case 7 -> "8";
            default -> throw new IllegalArgumentException(Messages.UNKNOWN_NUMBER);
        };
    }

    public static Number2 getNumberEnum(int number) {
        return switch (number) {
            case 0 -> Number2.N1;
            case 1 -> Number2.N2;
            case 2 -> Number2.N3;
            case 3 -> Number2.N4;
            case 4 -> Number2.N5;
            case 5 -> Number2.N6;
            case 6 -> Number2.N7;
            case 7 -> Number2.N8;
            default -> throw new IllegalArgumentException(Messages.UNKNOWN_NUMBER);
        };
    }

    public static Number2 getNumberEnumReverse(int number) {
        return switch (number) {
            case 0 -> Number2.N8;
            case 1 -> Number2.N7;
            case 2 -> Number2.N6;
            case 3 -> Number2.N5;
            case 4 -> Number2.N4;
            case 5 -> Number2.N3;
            case 6 -> Number2.N2;
            case 7 -> Number2.N1;
            default -> throw new IllegalArgumentException(Messages.UNKNOWN_NUMBER);
        };
    }

    public static int getNumberIndexReverse(Number2 number) {
        return switch (number) {
            case N1 -> 7;
            case N2 -> 6;
            case N3 -> 5;
            case N4 -> 4;
            case N5 -> 3;
            case N6 -> 2;
            case N7 -> 1;
            case N8 -> 0;
        };
    }

    public static boolean isLetterNumberIllegal(int index) {
        return index < 0 || index > 7;
    }
}
