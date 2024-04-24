package igoroffline.practice.hamsterchessv2.main.piece.movement;

import igoroffline.practice.hamsterchessv2.main.board.Square;
import java.util.List;

public record MovementContact(List<Square> squares, Contact contact) {}
