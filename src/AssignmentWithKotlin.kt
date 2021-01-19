import java.io.File
import kotlin.system.exitProcess
/*
Inlämningsuppgift 2 - Rekursion
Tomtarna på Nordpolen har en strikt chefs-hierarki:
Högsta chefen för allt är "Tomten"
Under "Tomten" jobbar "Glader" och "Butter"
Under "Glader" jobbar "Tröger", "Trötter" och "Blyger"
Under "Butter" jobbar "Rådjuret", "Nyckelpigan", "Haren" och "Räven"
Under "Trötter" jobbar "Skumtomten"
Under "Skumtomten" jobbar "Dammråttan"
Under "Räven" jobbar "Gråsuggan" och "Myran"
Under "Myran" jobbar "Bladlusen"
Er uppgift har 2 delar:
• Illustrera tomtens chefshierarki i en lämplig datastruktur. Det finns flera tänkbara val här, ett
krav är att varje nisse bara får mappas mot dem som är direkt över- och underordnade i er
datastruktur. Direkta mappningar över flera nivåer är inte tillåtna.
• Skriv en rekursiv funktion där man antingen:
o Anger en nisses namn och får tillbaka en lista av alla denna nisses chefer, på samtliga
nivåer. Exempel: Om man skriver in ”Dammråttan” ska en lista innehållande
Tomten, Glader, Trötter och Skumtomten i retur
o Anger en nisses namn och får tillbaka en lista på alla dess underordnade, på samtliga
nivåer. Exempel: Om man skriver in ”Räven” får man tillbaka en lista med
Gråsuggan, Myran och Bladlusen.
Inga tomtenamn får vara hårdkodade i den rekursiva loopen (det är ok att hårdkoda i skapandet av
datastrukturen). Du väljer själv om du skriver koden i Java eller Kotlin.
 */
fun createInputFile(fileName: String): List<String> = File(fileName).readLines()
val bosses = mutableListOf<String>()
val employees = mutableListOf<String>()
val input = createInputFile("Tomtefil.txt") as MutableList<String>

fun getBosses (userInput: String){
    fun findBosses(name: String?): List<String>{
        for (lines in input) {
            val firstBreak = lines.indexOf(":")
            val bossName = lines.substring(0, firstBreak)
            val employee = lines.substring(firstBreak + 2).split(", ".toRegex()).toTypedArray()
            for (s in employee) {
                if (s.equals(name, ignoreCase = true)) {
                    bosses.add(0, bossName)
                    findBosses(bossName)
                }
            }
        }
        return bosses
    }
    val listToSend: List<String> = findBosses(userInput)

    val printoutResult = StringBuilder()
    for (s in listToSend) {
        printoutResult.append(s)
        printoutResult.append("\n")
    }
    println("""
    ${userInput}s chefer är: $printoutResult
    """.trimIndent())
}
fun getEmployees(userInput: String){
    fun findEmployees(name: String): List<String>{
        for (line in input){
            val firstBreak = line.indexOf(":")
            val bossName = line.substring(0, firstBreak)
            val employee = line.substring(firstBreak+2).split(", ")
            if (name.equals(bossName, true) )
                for (santa in employee){
                    employees.add(santa)
                    findEmployees(santa)
                }
        }
        return employees
    }
    val listToPrint = findEmployees(userInput)

    if (listToPrint.isEmpty())
        println("$userInput har inga anställda")
    else{
        val printResult = StringBuilder()
        for (name in listToPrint){
            printResult.append(name)
            printResult.append("\n")
        }
        println("$userInput s anställda är \n$printResult")
    }
}

fun main(){
    while (true) {
        println("Vem vill du söka efter?")
        val userInput = readLine()!!
        println("Vill du skriva ut chefer (1) eller anställda (2)")

        when (readLine()!!) {
            "1" -> getBosses(userInput)
            "2" -> getEmployees(userInput)
            else -> println("Du måste ange 1 eller 2")
        }

        println("Vill du söka efter en ny tomte Ja(1) Nej(2)")
        when (readLine()!!) {
            "2" -> exitProcess(0)
        }
        employees.clear()
        bosses.clear()
    }
}