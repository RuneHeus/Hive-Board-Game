package GameLogic

import DrawLogic.HiveGui
import GameLogic.Pieces.{Kever, Koningin, Piece, PiecesEnum, Sprinkhaan, Stack}
import gamelib.Cell

import scala.jdk.CollectionConverters.*

class Game(val hiveGui: HiveGui) {

  var activePiece: Piece = null //Het insect dat je van de stapel wilt halen

  var clickedActivePiece: Piece = null //Het insect dat de speler wil verplaatsen

  var activePieces: Array[Piece] = Array() //De lijst van alle insecten op het bord

  var activeStacks: List[Stack] = List() //Dit houdt alle stack bij die aanwezig zijn op het bord

  var moveCount = 0 //Dit houdt bij hoeveelste beurt het is

  var player1 = new Player("Player 1") //Speler 1
  var player2 = new Player("Player 2") //Speler 2

  var currentPlayer: Player = player1 //De speler aan wiens beurt het is

    def onClick(x: Int, y: Int): Unit = {

    var clickedRow = (y - hiveGui.padding) / hiveGui.cellHeight
    var clickedColumn = (x - hiveGui.padding) / hiveGui.cellWidth

    if(y-hiveGui.padding < 0){
      clickedRow -= 1
    }
    if(x - hiveGui.padding < 0){
      clickedColumn -= 1
    }

    //Als je in het grid hebt geklikt
    if (!(x < hiveGui.padding || y < hiveGui.padding || x > hiveGui.guiWidth - hiveGui.padding  || y > hiveGui.guiheight - hiveGui.padding)){

      if(activePiece != null){ //Als we van de stapel willen gebruiken
        //We koppieren het actieve stuk en maken er een nieuwe instantie van
        val newPiece = activePiece.copy(clickedColumn, clickedRow)

        newPiece.owner = currentPlayer

        //We voegen het nieuwe gemaakte stuk toe aan de lijst van actieve stuken
        activePieces :+= newPiece

        hiveGui.getGridPanel().addCells(List(newPiece).asJava)

        updateInsectCount()

        setActivePiece(null)

        switchPlayer()

      }else if (clickedActivePiece != null){ //We een actief insect hebben gekozen en willen verplaatsen

          if(!clickedActivePiece.move(clickedColumn, clickedRow, activePieces)){ //Als je het insect naar een illegale plaats wilt verplaatsen
            println("Je kan je insect hier niet plaatsen")
          }else if(clickedActivePiece.x == clickedColumn && clickedActivePiece.y == clickedRow){ //Speler klikt op dezelfde plek als het geselecteerde insect (dan deselecten we het insect)
            clickedActivePiece = null
            println(s"Het insect van ${currentPlayer.name} is deselected")
          }else { //Het insect is succesvol verplaats
            switchPlayer()
            clickedActivePiece = null
          }

      }else{ //als we een insect willen verplaatsen
        getClickedPiece(clickedColumn, clickedRow) match {
          case Some(clickedPiece) =>
            if(clickedPiece.owner == currentPlayer){
              clickedActivePiece = clickedPiece
              println(s"Je hebt een insect gekozen op plaats (${clickedPiece.x}, ${clickedPiece.y})")
            }else {
              println("Dit is niet jouw insect!")
            }
          case None =>
        }
      }
    }else { //Als je buiten het grid hebt geklikt
      menuClicked(clickedColumn, clickedRow)
    }
  }

  private def setActivePiece(piece: Piece): Unit = {
    activePiece = piece
  }

  def start (): Unit = {

    val panel = hiveGui.getGridPanel()

    //Neem de menu cells
    val menuCells: List[Cell] = hiveGui.getMenuCells(this)

    panel.addCells(menuCells.asJava)

    panel.setPressListener(this.onClick)
  }

  private def updateInsectCount(): Unit = {
    //We tellen het insect af van de actieve speler zijn stapel
    activePiece.pieceType match
      case PiecesEnum.KEVER => currentPlayer.keverCount -= 1
      case PiecesEnum.SPRINKHAAN => currentPlayer.sprinkhaanCount -= 1
      case PiecesEnum.KONINGIN => currentPlayer.koninginCount -= 1
  }

  private def menuClicked(clickedColumn: Int, clickedRow: Int): Unit = {
    //Check of je op 1 van de menu items hebt geklikt
    if (clickedColumn == -1 && clickedRow == 1) { //Kever?
      println(s"${currentPlayer.name} heeft ${currentPlayer.keverCount} kever(s) op de stapel")
      if (currentPlayer.keverCount != 0) {
        println("U hebt de kever geselecteerd!")
        setActivePiece(new Kever(this))
      }
    } else if (clickedColumn == -1 && clickedRow == 2) { //Sprinkhaan?
      println(s"${currentPlayer.name} heeft ${currentPlayer.sprinkhaanCount} sprinkhaan(en) op de stapel")
      if (currentPlayer.sprinkhaanCount != 0) {
        println("U hebt de sprinkhaan geselecteerd!")
        setActivePiece(new Sprinkhaan(this))
      }
    } else if (clickedColumn == -1 && clickedRow == 3) { //Koningin
      println(s"${currentPlayer.name} heeft ${currentPlayer.koninginCount} koningin op de stapel")
      if (currentPlayer.koninginCount != 0) {
        println("U hebt de koningin geselecteerd!")
        setActivePiece(new Koningin(this))
      }
    }
  }

  private def switchPlayer(): Unit = {
    if(currentPlayer.name == player1.name){
        currentPlayer = player2
    }else {
      currentPlayer = player1
    }
    println(s"It is ${currentPlayer.name}'s turn!")
  }

  private def getClickedPiece(x: Int, y: Int): Option[Piece] = {
    activePieces.find(piece => piece.x == x && piece.y == y)
  }
}