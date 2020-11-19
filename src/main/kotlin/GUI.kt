package hazae41.chestui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority.LOWEST
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

data class Coords(
  var x: Int,
  var y: Int
) {}

fun toSlot(x: Int, y: Int) = x + (y * 9)
fun fromSlot(s: Int) = Pair(s % 9, s / 9)

fun JavaPlugin.gui(
  title: String,
  rows: Int,
  render: GUI.() -> Unit
) = GUI(this, title, rows, render).apply(render)

fun Player.open(gui: GUI) = openInventory(gui.inventory)

open class GUI(
  val plugin: JavaPlugin,
  val title: String,
  val rows: Int,
  val render: GUI.() -> Unit
) : Listener {
  val slots = arrayOfNulls<Slot>(9 * rows)

  val inventory = plugin.server
    .createInventory(null, rows * 9, title)

  init {
    plugin.server.pluginManager.registerEvents(this, plugin)
  }

  fun refresh() {
    inventory.clear()
    for (i in 0 until 9 * rows)
      slots[i] = null
    this.render()
  }

  @EventHandler(priority = LOWEST)
  fun onclick(e: InventoryClickEvent) {
    if (e.clickedInventory != inventory) return
    e.isCancelled = true

    val player = e.whoClicked as? Player ?: return
    val slot = slots.getOrNull(e.slot) ?: return

    slot.onclick(e, player)
  }

  inner class Slot() {
    var item: Item? = null
    var onclick: InventoryClickEvent.(Player) -> Unit = {}
  }

  fun slot(
    x: Int, y: Int,
    builder: GUI.Slot.() -> Unit
  ) {
    val i = toSlot(x, y)
    val slot = Slot().apply(builder)
    inventory.setItem(i, slot.item?.stack)
    slots[i] = slot
  }

  fun slot(
    coords: Coords,
    builder: GUI.Slot.() -> Unit
  ) = slot(coords.x, coords.y, builder)

  fun fill(
    x1: Int, y1: Int, x2: Int, y2: Int,
    builder: GUI.Slot.() -> Unit
  ) {
    val dx = if (x1 < x2) x1..x2 else x2..x1
    val dy = if (y1 < y2) y1..y2 else y2..y1
    for (x in dx) for (y in dy) slot(x, y, builder)
  }

  fun fill(
    coords1: Coords,
    coords2: Coords,
    builder: GUI.Slot.() -> Unit
  ) = fill(coords1.x, coords1.y, coords2.x, coords2.y, builder)

  fun all(builder: GUI.Slot.() -> Unit) = fill(0, 0, 8, rows - 1, builder)

  fun Coords.up() {
    y = Math.floorMod((y - 1), rows)
  }

  fun Coords.down() {
    y = Math.floorMod((y + 1), rows)
  }

  fun Coords.left() {
    x = Math.floorMod((x - 1), 9)
  }

  fun Coords.right() {
    x = Math.floorMod((x + 1), 9)
  }
}

