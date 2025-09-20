package GameLogic.Pieces

import GameLogic.{Game, Player}
import GameLogic.Pieces.PiecesEnum.KONINGIN

class Koningin(game: Game) extends Piece (game){

  var x: Int = 0
  var y: Int = 0

  var pieceType: PiecesEnum.Value = KONINGIN
  
  override def imageName: String = "queenbee.png"

  override def copy(newX: Int, newY: Int): Koningin = {
    val newKoningin = new Koningin(game)
    newKoningin.x = newX
    newKoningin.y = newY
    newKoningin
  }

  override def move(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    //Kijk of de geklikte cell een legale move is
    if(
       isOneCellAway(clickedX, clickedY, activePieces)
      &&
        hasInsectSurrounding(clickedX, clickedY, activePieces, true) //Dit checked ook of de geklikte cell geen insect bevat
    ){
      x = clickedX
      y = clickedY
      true
    }else{
      false
    }
  }
}
