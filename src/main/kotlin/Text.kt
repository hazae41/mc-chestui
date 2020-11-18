package hazae41.chestui

fun wrap(text: String, max: Int = 32) = text.split("\n").flatMap {
  mutableListOf<String>().also { list ->
    val words = it.split(" ")
    var line = words[0]

    for (word in words.drop(1)) {
      val pre = "$line $word"

      if (pre.length > max) {
        list += line
        line = word
      } else line = pre
    }

    list += line
  }
}.toMutableList()