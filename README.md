<h3 align=center>
  <img src="https://i.imgur.com/3Sk1buV.png"/><br><br>
</h3>

> React-like chest GUI rendering for Bukkit

### Installation

#### [Download ChestUI](https://github.com/hazae41/mc-chestui/raw/master/build/libs/chestui-3.1.jar)

#### [Download Kotlin](https://github.com/hazae41/mc-chestui/raw/master/build/libs/Kotlin-1.4.10.jar)

##### [Plugins using ChestUI](https://github.com/topics/chestui)

### Features

- Kotlin DSL
- Click events
- Variables
- Conditional rendering
- Components with props

### Opening a GUI 

You can create a GUI with `gui(title, rows)` and a render function

Rows can be from 1 to 6

The render function is called each time the GUI is refreshed (with `refresh()`)

```kotlin
fun MyGUI(): GUI {
  return gui("Title", 6){
    // Render function
    // ...
  }
}

player.open(MyGUI())
```

### Creating an item

Use `slot(x, y)` to place an item at x (0 to 8) and y (0 to rows - 1)

You can use `item = item(type) {}` to define the item

You can use `onclick = { p -> }` to execute something with p (Player) when the item is clicked

```kotlin
fun MyGUI(): GUI {
  return gui("Test", 6){
    slot(0, 0){
      item = item(Material.CAKE){
        name = "My Cake"
      }
      onclick = { p -> p.sendMessage("Mmm") }
    }
  }
}
```

You can also use the InventoryClickEvent in `this`

````kotlin
onclick = {
  if (isShiftClick){
    // ...
  }
}
````

### Wrapping the lore

You can use `wrap(text)` to automatically split a text into multiple lines so it fits in a lore

```kotlin
fun MyGUI(): GUI {
  return gui("Test", 6) {
    slot(0, 0){
      item = item(Material.CAKE){
        name = "Cake"
        lore = wrap("Blabla bla blabla bla blablabla bla blabla blablabla bla blabla")
      }
    }
  }
}
```

### Creating backgrounds

You can use `all {}` and `fill(x1, y1, x2, y2) {}` to generate items on a large area

An item defined on the same position as another overrides it, so you can use a background at the top of the render function

```kotlin
fun MyGUI(): GUI {
  // Fill all the GUI with the following
  all {
    item = item(Material.BLACK_CONCRETE)
  }

  // Fill (0, 0) to (1, 1) as a square
  fill(0, 0, 1, 1){
    item = item(Material.CAKE){
      name = "My Cake"
      onclick = { p -> p.sendMessage("Mmm") }
    }
  }

}
```

### Using variables

You can define variables *outside* the render function

They can be modified and read *inside* the render function

```kotlin
fun MyGUI(): GUI {
  var cakeName = "I'm a cake"
  
  return gui("Test", 6) {
    slot(0, 0) {
      item = item(Material.CAKE) {
        name = cakeName
      }
      onclick = {
        // Change the name
        cakeName = "Modified"
        // Render the GUI
        refresh()
      }
    }
  }
}
```

### Using position variables

There is a special type Coords that holds x and y that can be used when placing items

It can be modified with `.up()`, `.down()`, `.left()`, `.right()`

Or directly modified with `.x` and `.y`

```kotlin
fun MyGUI(): GUI {
  val cakePos = Coords(1, 0)
  
  return gui("Test", 6) {
    slot(cakePos) {
      item = item(Material.CAKE)
      onclick = {
        // Move the cake up
        cakePos.up()
        // Render the GUI
        refresh()
      }
    }
  }
}
```

### Using conditional rendering

Using variables, you can conditionally render some items

```kotlin
fun MyGUI(): GUI {
  var someVariable = false

  return gui("Test", 6){
    slot(0, 0) {
      item = item(Material.CAKE) {
        name = "Click me"
      }
      onclick = {
        someVariable = true
        refresh()
      }
    }
  
    // Conditionnal rendering
    if (someVariable) {
      slot(1,1){
        item = item(Material.CREEPER_HEAD)
      }
    }
  }
}
```

### Using external render functions

If you want to split your rendering function, you can call external functions

You can even pass props/params

```kotlin
fun MyGUI(): GUI {
  return gui("Test", 6) {
    doSomething()
    doSomethingWithProps("Hello")
  }
}

fun GUI.doSomething() {
  slot(0, 0){
    item = item(Material.CAKE)
  }
}

fun GUI.doSomethingWithProps(
  message: String
) {
  slot(1, 0){
    item = item(Material.ANVIL)
    onclick = { p -> p.sendMessage(message) }
  }
}
```

### Using components

Components are like render function but they can use variables

So they are like a GUI inside a GUI

As a downside, you have to instantiate them before your render function

```kotlin
fun MyGUI(): GUI {
  val redButton = RedButton()  

  return gui("Test", 6) {
    redButton("You clicked!")
  }
}

fun RedButton(): GUI.(String) -> Unit {
  var clicked = false  

  return fun GUI.(
    message: String
  ) {
    slot(0, 0){
      item = item(Material.RED_CONCRETE)
      onclick = { p ->
        clicked = true
        p.sendMessage(message)
        refresh()
      }
    }
  }
}
```



