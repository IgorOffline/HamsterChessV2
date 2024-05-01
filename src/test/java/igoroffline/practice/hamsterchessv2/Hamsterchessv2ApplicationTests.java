package igoroffline.practice.hamsterchessv2;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.Letter;
import igoroffline.practice.hamsterchessv2.main.board.Number2;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Hamsterchessv2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void t2() {
		final var nextNumberIndex1 = Number2.N4.index + 1;
		final var squareIndex1 = (8 * (7 - nextNumberIndex1)) + Letter.E.index; // 28
		final var nextNumberIndex2 = Number2.N1.index + 1;
		final var squareIndex2 = (8 * (7 - nextNumberIndex2)) + Letter.A.index; // 48
		final var nextNumberIndex3 = Number2.N7.index + 1;
		final var squareIndex3 = (8 * (7 - nextNumberIndex3)) + Letter.H.index; // 7
		final var nextNumberIndex4 = Number2.N7.index + 1;
		final var squareIndex4 = (8 * (7 - nextNumberIndex3)) + Letter.A.index; // 0

		Optional<Square> nextNumberSquare = new Board().findNextNumberSquare(Letter.A, Number2.N7);
		assertThat(nextNumberSquare.get().getLetter()).isEqualTo(Letter.A);
		assertThat(nextNumberSquare.get().getNumber()).isEqualTo(Number2.N8);
	}
}
