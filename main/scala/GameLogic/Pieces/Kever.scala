package GameLogic.Pieces

import GameLogic.{Game, Player}
import GameLogic.Pieces.PiecesEnum.KEVER

class Kever(game: Game) extends Piece (game){

  var x: Int = 0
  var y: Int = 0

  var pieceType: PiecesEnum.Value = KEVER

  override def imageName: String = "beetle.png"

  override def copy(newX: Int, newY: Int): Kever = { //Functie om het type over te kopieren
    val newKever = new Kever(game)
    newKever.x = newX
    newKever.y = newY
    newKever
  }

  override def move(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = {
    
    if(isOneCellAway(clickedX, clickedY, activePieces) && hasInsectSurrounding(clickedX, clickedY, activePieces, false)){

      if(insectAtPosition(clickedX, clickedY, activePieces)){ //Als er een insect aanwezig is op de positie moeten we een stack maken
        updateStack()
      }
      
      x = clickedX
      y = clickedY
      return true
    }
    false
  }
  
  private def updateStack(): Unit = {
    
  }
}
