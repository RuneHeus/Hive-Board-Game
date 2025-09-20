import DrawLogic.HiveGui
import GameLogic.Game

object HiveGame {
  val windowWidth: Int = 800
  val windowHeight: Int = 800
  val numRows: Int = 10
  val numColumns: Int = 10
  val padding: Int = 100

  var cellWidth: Int = (windowWidth - padding * 2) / numColumns
  var cellHeight: Int = (windowHeight - padding * 2) / numRows

  def main(args: Array[String]): Unit =
    //We initalizeren de gui
    val hivegui = new HiveGui(windowWidth, windowHeight, numRows, numColumns, padding)

    //We initializeren het game object
    val game = new Game(hivegui)

    //start de game
    game.start()
}