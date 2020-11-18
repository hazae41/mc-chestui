# ChestUI

### Usage

```kotlin
val cakePos = Coords(1, 0)

val cake = item(Material.CAKE) {
  name = "I'm a cake"
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
```