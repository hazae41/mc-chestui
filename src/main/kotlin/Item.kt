package hazae41.chestui

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun item(
  type: Material = Material.BLACK_CONCRETE,
  builder: Item.() -> Unit
) = Item(type).apply(builder)

class Item(
  val type: Material
) {
  val stack = ItemStack(type, 1)
  val meta get() = stack.itemMeta

  fun stack(builder: ItemStack.() -> Unit) = stack
    .apply(builder)

  fun meta(builder: ItemMeta.() -> Unit) = meta
    ?.apply(builder)
    ?.also { stack.itemMeta = it }

  var name
    get() = meta?.displayName
    set(value) {
      meta { setDisplayName(value) }
    }

  var lore
    get() = meta?.lore
    set(value) {
      meta { lore = value }
    }
}