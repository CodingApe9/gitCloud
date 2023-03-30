fun main(args: Array<String>) {
//    val exp = "5 + 4"
//    val exp = "3 / 0"
//    val exp = "1 # 3"
//    val exp = "1 2 3 4"
//    val exp = "# 2 *"
//    val exp = null
    val exp = ""
//    val exp = readLine();
    val splitedExp = split(exp)
    println("spitedExp = ${splitedExp}")

    val checkSizeAndOperand:((List<String>) -> Boolean) = {splitedExp ->
        splitedExp.size == 3
            && Character.isDigit(splitedExp[0].toCharArray()[0])
            && Character.isDigit(splitedExp[2].toCharArray()[0])}

    //계산기 정책
    val p1:((List<String>) -> Boolean) = {splitedExp ->
        checkSizeAndOperand(splitedExp)
                && ((splitedExp[1]=="+" || splitedExp[1]=="-" || splitedExp[1]=="*")
                    || (splitedExp[1]=="/" && (splitedExp[0]!="0" && splitedExp[2]!="0")))}

//    val p2:((List<String>) -> Boolean) = {spitedExp ->
//        checkSizeAndOperand(splitedExp)
//                && (p1(spitedExp) || (spitedExp[1]=="%" && spitedExp[0]!="0" && spitedExp[2]!="0"))}

    fun p2(spitedExp: List<String>): Boolean {
        return checkSizeAndOperand(splitedExp)
                && (p1(spitedExp) || (spitedExp[1]=="%" && spitedExp[0]!="0" && spitedExp[2]!="0"))
    }

    val func = parse(splitedExp, p1)
    println("func = ${func}")

    val func2 = parse(splitedExp, ::p2)
    println("func2 = ${func2}")

    val result = func?.let { it(splitedExp[0]!!.toDouble(), splitedExp[2]!!.toDouble()) } ?: "can't calculate"
    println("result = ${result}")
}

//스트링을 공백 기준으로 스플릿한 List<String>로 리턴, 입력 스트링이 null이면 빈 문자열이 들어간 리스트로 리턴
//val split:(String?) ->  List<String> = { exp -> exp?.split(" ")?: listOf("") }
val split:(String?) ->  List<String> = { it?.split(" ")?: listOf("") }

//정책에 맞는지 확인하고 아니라면 null을 리턴
//만약 정책에 맞는 입력 즉 계산이 가능한 입력이라면 그에 맞는 함수((Double, Double) -> Double)를 리턴
val parse:(List<String>, ((List<String>) -> Boolean)) -> ((Double, Double) -> Double)? = {spitedExp, isSupported ->
    if (isSupported(spitedExp)) {
        when(spitedExp[1]) {
            "+" -> { x, y -> x + y }
            "/" -> { x, y -> x / y }
            "*" -> { x, y -> x * y }
            "-" -> { x, y -> x - y }
            "%" -> { x, y -> x % y }
            else -> null
        }
    }
    else null
}
