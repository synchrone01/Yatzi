import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Yatzy {

	protected int[] dice;

	public Yatzy(int d1, int d2, int d3, int d4, int d5) {
		dice = new int[] { d1, d2, d3, d4, d5 };
	}

	private static int[] countOccurrences(int... values) {
		int[] counts = new int[6];
		for (int value : values) {
			counts[value - 1]++;
		}
		return counts;
	}

	private static int checkValueAndSum(int valueToCheck, int... dices) {
		return (int) Arrays.stream(dices).boxed().collect(Collectors.toList()).stream().filter(d -> d == valueToCheck)
				.count() * valueToCheck;
	}

	private static int checkPair(boolean highestPair, int number, int occ, int... values) {
		int[] counts = countOccurrences(values);
		int score = 0, nbOcc = 0;
		for (int i = 5; i >= 0; i--) {
			if (nbOcc < occ && (highestPair ? counts[i] >= number : counts[i] == number)) {
				score = score + (i + 1) * number;
				nbOcc++;
			}
		}
		return occ == 2 ? nbOcc == 2 ? score : 0 : score;
	}

	private static boolean checkStraight(int start, int end, int... values) {
		int[] tallies = countOccurrences(values);
		boolean isStraight = true;
		for (int i = start; i < end; i++) {
			isStraight = isStraight && tallies[i] == 1;
		}
		return isStraight;
	}

	public static int chance(int d1, int d2, int d3, int d4, int d5) {
		return d1 + d2 + d3 + d4 + d5;
	}

	public static int yatzy(int... dice) {
		return Arrays.stream(dice).boxed().distinct().count() == 1 ? 50 : 0;
	}

	public static int ones(int d1, int d2, int d3, int d4, int d5) {
		return checkValueAndSum(1, new int[] { d1, d2, d3, d4, d5 });
	}

	public static int twos(int d1, int d2, int d3, int d4, int d5) {
		return checkValueAndSum(2, new int[] { d1, d2, d3, d4, d5 });
	}

	public static int threes(int d1, int d2, int d3, int d4, int d5) {
		return checkValueAndSum(3, new int[] { d1, d2, d3, d4, d5 });
	}

	public int fours() {
		return checkValueAndSum(4, dice);
	}

	public int fives() {
		return checkValueAndSum(5, dice);
	}

	public int sixes() {
		return checkValueAndSum(6, dice);
	}

	public static int score_pair(int d1, int d2, int d3, int d4, int d5) {
		return checkPair(true, 2, 1, d1, d2, d3, d4, d5);
	}

	public static int two_pair(int d1, int d2, int d3, int d4, int d5) {
		return checkPair(true, 2, 2, d1, d2, d3, d4, d5);
	}

	public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5) {
		return checkPair(true, 4, 1, d1, d2, d3, d4, d5);
	}

	public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5) {
		return checkPair(true, 3, 1, d1, d2, d3, d4, d5);
	}

	public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
		boolean isStraight = checkStraight(0, 5, d1, d2, d3, d4, d5);
		return isStraight ? 15 : 0;
	}

	public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {
		boolean isStraight = checkStraight(1, 6, d1, d2, d3, d4, d5);
		return isStraight ? 20 : 0;
	}

	public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
		int pairSum = checkPair(false, 2, 1, d1, d2, d3, d4, d5);
		int tripleSum = three_of_a_kind(d1, d2, d3, d4, d5);
		return (pairSum > 0 && tripleSum > 0) ? pairSum + tripleSum : 0;
	}
}
