package DrawLogic

import GameLogic.Pieces.*
import gamelib.{Cell, GUI}

class HiveGui(val guiWidth: Int, val guiheight: Int, val rows: Int, val columns: Int, val padding: Int) extends GUI(guiWidth, guiheight, rows, columns, padding) {

  
    val cellWidth: Int = (guiWidth - padding * 2) / columns
    val cellHeight: Int = (guiheight - padding * 2) / rows
  
    def getMenuCells(game: GameLogic.Game): List[Cell] = {
      //Kever
      val kever = new Kever(game)
      kever.x = -1
      kever.y = 1
      //Sprinkhaan
      val sprinkhaan = new Sprinkhaan(game)
      sprinkhaan.x = -1
      sprinkhaan.y = 2
      //Koningin
      val koningin = new Koningin(game)
      koningin.x = -1
      koningin.y = 3

      List(kever, sprinkhaan, koningin)
    }
}