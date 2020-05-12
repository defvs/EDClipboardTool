package dev.defvs.edclipboard

import com.melloware.jintellitype.JIntellitype
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.nio.file.Files


object Main {
	private var currentIndex = -1
	private val systems = arrayListOf<Map<String, String>>()
	
	@JvmStatic
	fun main(args: Array<String>) {
		if (args.size !in 1..2) {
			println("Incorrect argument count")
			println("Usage:\n\tedclipboard route.csv [currentIndex]")
			return
		}
		
		if (args.size == 2) currentIndex = args[1].toInt() - 1
		
		val values = arrayListOf<ArrayList<String>>()
		var headers = arrayListOf<String>()
		var firstLine = true
		with(File(args[0])) {
			if (!isFile) {
				println("Not a file: ${this.absolutePath}")
				return
			}
			if (!listOf("csv", "txt").contains(extension)) {
				println("Not a CSV, file is $extension")
				return
			}
			
			println("Parsing CSV... ${Files.lines(this.toPath()).count() - 1} lines/waypoints to parse")
			
			forEachLine {
				if (firstLine) {
					firstLine = !firstLine
					headers = it.split(',').map { s -> s.removeSurrounding("\"") } as ArrayList<String>
				} else
					values.add(it.split(',').map { s -> s.removeSurrounding("\"") } as ArrayList<String>)
			}
		}
		
		if (!headers.contains("System Name")) {
			println("Invalid CSV: row 'System Name' does not exist.")
			return
		}
		
		values.forEach {
			val map = mutableMapOf<String, String>()
			it.forEachIndexed { index, s ->
				map[headers[index]] = s
			}
			systems.add(map)
		}
		
		println("CSV route parsing done, ${systems.size} waypoints parsed.")
		println("Key binding for next system is CTRL+SHIFT+V, this will copy the next system to your clipboard.")
		println("\to7 CMDR, have a safe trip.\n===(((-----)))===")
		
		// JIntelliType setup
		with(JIntellitype.getInstance()) {
			addHotKeyListener { id ->
				if (id == 1) nextStar()
			}
			registerHotKey(1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'V'.toInt())
		}
		
		nextStar()
		
		readLine()
	}
	
	private fun nextStar() {
		currentIndex++
		copyToClipboard(
			if (currentIndex >= systems.size) {
				println("Navigation done.")
				"Navigation done"
			} else {
				val nextSystem = systems[currentIndex].getOrDefault("System Name", "Error!")
				println("\"$nextSystem\" has been copied to the clipboard.")
				nextSystem
			}
		)
	}
	
	private fun copyToClipboard(string: String) =
		Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(string), null)
}