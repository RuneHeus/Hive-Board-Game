package GameLogic.Pieces

import GameLogic.{Game, Player}
import GameLogic.Pieces.PiecesEnum.*
import gamelib.{AssetsLoader, Cell}

import java.awt.{Graphics2D, Image}
import java.awt.image.ImageObserver

abstract class Piece(game: Game) extends Cell{
  var x: Int
  var y: Int

  var show = true //Standaard willen we dat het insect getoond wordt

  var pieceType: PiecesEnum.Value //Het type insect

  var owner: Player = null //Van welke speler het insect is

  def imageName: String

  def copy(newX: Int, newY: Int): Piece

  override def draw(g: Graphics2D): Unit = {
    val image: Image = AssetsLoader.loadImage(imageName)
    val observer: ImageObserver = null
    g.drawImage(
      image,
      (x * game.hiveGui.cellWidth) + game.hiveGui.padding,
      (y * game.hiveGui.cellHeight)+ game.hiveGui.padding,
      game.hiveGui.cellWidth, // width
      game.hiveGui.cellHeight, // height
      observer
    )
  }

  def move(x: Int, y: Int, activePieces: Array[Piece]): Boolean

  def insectAtPosition(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = { //Dit gaat kijken of de cell al een insect bevat
    activePieces.exists{piece => 
      val horizontalDistance = Math.abs(piece.x - clickedX)
      val verticalDistance = Math.abs(piece.y - clickedY)
      piece != this &&
        (horizontalDistance == 0 && verticalDistance == 0)
    }
  }

  def hasInsectSurrounding(clickedX: Int, clickedY: Int, activePieces: Array[Piece], checkAlreadyExisting: Boolean): Boolean = { //De checkAlreadyExisting betekend of we moeten kijken of je op een insect mag plaatsen (kever met stapel)
    val distance = 1

    activePieces.exists { piece =>
      val horizontalDistance = Math.abs(piece.x - clickedX)
      val verticalDistance = Math.abs(piece.y - clickedY)
      if(checkAlreadyExisting){
        piece != this && //We mogen niet vergelijken met zichzelf
          horizontalDistance <= distance &&
          verticalDistance <= distance &&
          !(horizontalDistance == 0 && verticalDistance == 0)
      }else {
        piece != this && //We mogen niet vergelijken met zichzelf
          horizontalDistance <= distance &&
          verticalDistance <= distance
      }
    }
  }

  def isOneCellAway(clickedX: Int, clickedY: Int, activePieces: Array[Piece]): Boolean = { //We kijken of de geklikte cell 1 cell weg is van de koninging (ze kan maar 1 stap nemen per keer)
    val horizontalDistance = Math.abs(x - clickedX)
    val verticalDistance = Math.abs(y - clickedY)

    (horizontalDistance == 1 && verticalDistance == 0)
      || (horizontalDistance == 0 && verticalDistance == 1)
      || (horizontalDistance == 1 && verticalDistance == 1)
  }
}