package GameLogic.Pieces

import GameLogic.Game

class Stack(val game: Game) {
  var pieces: List[Piece] = List()

  def addToStack(piece: Piece): Unit = {
    pieces = piece :: pieces
  }

  def removeFromStack(): Option[Piece] = {
    if (pieces.nonEmpty) {
      val topPiece = pieces.head
      pieces = pieces.tail
      Some(topPiece)
    } else {
      None
    }
  }

  def isEmpty: Boolean = pieces.isEmpty

  def stackTop: Option[Piece] = pieces.headOption

  def stackCount: Int = pieces.length
}
