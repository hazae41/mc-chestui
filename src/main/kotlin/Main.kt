package hazae41.chestui

import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {

  override fun onEnable() {
    super.onEnable()

    server.pluginManager.registerEvents(this, this)

    getCommand("chestui")!!.setExecutor { sender, _, _, args ->
      sender.oncommand(args)
    }
  }

  fun CommandSender.oncommand(args: Array<String>): Boolean {
    val player = this as? Player ?: return true

    val cakePos = Coords(1, 0)

    val cake = item(Material.CAKE) {
      name = "§eI'm a cake"
    }

    val anvil = item(Material.ANVIL) {
      name = "Empty"
    }

    val gui = gui("Hello", 6) {
      fill(1, 1, 2, 2) {
        item = anvil
      }

      slot(cakePos) {
        item = cake
        onclick = {
          cakePos.up()
          refresh()
        }
      }
    }

    player.open(gui)
    return true;
  }
}