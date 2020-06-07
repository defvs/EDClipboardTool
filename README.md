# EDClipboardTool

Helper for Spansh neutron router to finally be efficient without ALT-TABing.

This tool copies the next system in your route to your clipboard when you press CTRL+SHIFT+V;

## Usage

Download CSV from Spansh neutron planner, place it alongside the .jar and run everything (with Java 7 or 8 installed on your system) with ``java -jar EDClipboardTool.jar filename.csv [optional index of current system]``

## Compilation

Clone or download the repository; open a command line and type ``./gradlew shadowJar`` (or ``gradlew.exe shadowJar`` on Windows)
The .jar executable will be in ``out/libs``
Of course this requires JDK8+
