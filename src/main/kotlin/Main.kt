package hazae41.chestui

import org.bukkit.Material
import org.bukkit.Material.ANVIL
import org.bukkit.Material.CAKE
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: Main

class Main : JavaPlugin(), Listener {

  override fun onEnable() {
    super.onEnable()
    plugin = this

    server.pluginManager.registerEvents(this, this)

    getCommand("chestui")?.apply {
      logger.info("Command defined")
    }?.setExecutor { sender, _, _, _ ->
      sender.oncommand()
    }
  }

  fun CommandSender.oncommand(): Boolean {
    val player = this as? Player ?: return true
    player.open(CakeGUI())
    return true;
  }

  fun CakeGUI(): GUI {
    // Variables and components
    val cakePos = Coords(1, 0)
    val redButton = RedButton()

    // Render function
    return GUI("Cake", 6) {
      fill(1, 1, 2, 2) {
        item = item(ANVIL) { name = "Background" }
      }

      slot(cakePos) {
        item = item(CAKE) {
          name = "§eI'm a cake"
          lore = wrap("But you can't eat me :P")
          amount = 9
        }
        onclick = {
          cakePos.up()
          refresh()
        }
      }

      // Conditional rendering
      if (cakePos.y == 4)
        doSomething()

      // Conditional rendering
      redButton(cakePos.y == 5, cakePos.x)
    }
  }

  // Some direct render function
  fun GUI.doSomething() {
    slot(5, 5) {
      item = item(Material.CREEPER_HEAD)
      onclick = { p -> p.sendMessage("Psssh... BOOM") }
    }
  }

  // Component with variables and props
  fun RedButton(): GUI.(Boolean, Int) -> Unit {
    // Variables
    var name = "Click me"

    // Render function with props
    return fun GUI.(
      enabled: Boolean,
      amount: Int
    ) {
      if (!enabled) return

      slot(3, 3) {
        item = item(Material.RED_CONCRETE) {
          this.name = name
          this.amount = amount
        }
        onclick = {
          name = "It works!"
          refresh()
        }
      }
    }
  }
}