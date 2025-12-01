import java.io.File
import java.io.InputStream

data class DialResult(
    var pointing: Int,
    var numberOfTimesPointingToZero: Int,
    var numberOfTimesPointingToZeroPartTwo: Int
)

fun main() {
    val inputStream: InputStream = File("input.txt").inputStream()
    val rotationList = mutableListOf<String>()

    var dialResult = DialResult(50, 0, 0)

    inputStream.bufferedReader().forEachLine { rotationList.add(it) }
    rotationList.forEach{ dialResult = rotationDial(dialResult, it) }

    println(dialResult.numberOfTimesPointingToZero)
    println(dialResult.numberOfTimesPointingToZeroPartTwo)
}

fun rotationDial(dialResult: DialResult, rotation: String): DialResult {
    val rotationDistance = rotation.drop(1).toInt()

    if (rotation.first() == 'L'){
        if(dialResult.pointing == 0)
            dialResult.numberOfTimesPointingToZeroPartTwo -= 1

        dialResult.pointing -= rotationDistance
    } else {
        if((dialResult.pointing + rotationDistance) % 100 == 0)
            dialResult.numberOfTimesPointingToZeroPartTwo -= 1

        dialResult.pointing += rotationDistance
    }

    val fixed = fixDialRage(dialResult)

    return addCountIfDialPointingZero(fixed)
}

fun fixDialRage(dialResult: DialResult): DialResult {
    if(dialResult.pointing < 100 && dialResult.pointing > -1){
        return dialResult
    }
    if(dialResult.pointing > 99){
        dialResult.pointing -= 100
        dialResult.numberOfTimesPointingToZeroPartTwo += 1
    }

    if(dialResult.pointing < 0){
        dialResult.pointing += 100
        dialResult.numberOfTimesPointingToZeroPartTwo += 1
    }

    return fixDialRage(dialResult)
}

fun addCountIfDialPointingZero(dialResult: DialResult): DialResult {
    if(dialResult.pointing == 0) {
        dialResult.numberOfTimesPointingToZero += 1
        dialResult.numberOfTimesPointingToZeroPartTwo += 1
    }
    return dialResult
}