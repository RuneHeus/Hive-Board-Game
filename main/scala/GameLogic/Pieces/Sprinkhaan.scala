package GameLogic.Pieces

import GameLogic.{Game, Player}
import GameLogic.Pieces.PiecesEnum.SPRINKHAAN

class Sprinkhaan(game: Game) extends Piece(game){

  var x: Int = 0
  var y: Int = 0

  var pieceType: PiecesEnum.Value = SPRINKHAAN

  override def imageName: String = "grasshopper.png"

  override def copy(newX: Int, newY: Int): Sprinkhaan = {
    val newSprinkhaan = new Sprinkhaan(game)
    newSprinkhaan.x = newX
    newSprinkhaan.y = newY
    newSprinkhaan
  }

  override def move(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    // Check if the Sprinkhaan is making a jump over at least one other insect
    if (hasJumpedOverInsect(clickedX, clickedY, activePieces) && hasInsectSurrounding(clickedX, clickedY, activePieces, true)) {
      x = clickedX
      y = clickedY
      true
    } else {
      false
    }
  }

  private def checkHorizontal(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    activePieces.exists(piece => piece != this && isBetween(x, clickedX, piece.x) && piece.y == clickedY)
  }

  private def checkVertical(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    activePieces.exists(piece => piece != this && piece.x == clickedX && isBetween(y, clickedY, piece.y))
  }

  private def checkDiagonal(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    activePieces.exists(piece =>
      piece != this &&
        Math.abs(piece.x - x) == Math.abs(piece.y - y) &&
        isBetween(x, clickedX, piece.x) &&
        isBetween(y, clickedY, piece.y)
    )
  }

  private def isBetween(num1: Int, num2: Int, target: Int): Boolean = {
    val (min, max) = (num1 min num2, num1 max num2)
    target >= min && target <= max
  }

  private def hasJumpedOverInsect(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    val horizontalJump = y - clickedY == 0
    val verticalJump = x - clickedX == 0
    val diagonalJump = Math.abs(x - clickedX) == Math.abs(y - clickedY)

    if (horizontalJump) {
      checkHorizontal(clickedX, clickedY, activePieces)
    } else if (verticalJump) {
      checkVertical(clickedX, clickedY, activePieces)
    } else if (diagonalJump) {
      checkDiagonal(clickedX, clickedY, activePieces)
    } else {
      false
    }
  }
}