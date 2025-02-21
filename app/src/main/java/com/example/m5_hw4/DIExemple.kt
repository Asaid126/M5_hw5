package com.example.m5_hw4

//Hilt работает на основе кода генерации
//НЕ забыть добавить зависимости в 3х местах
//Все приложения использующие Hilt должны содержать класс Application c анатацией @HiltAndroidApp
//Нужно наш Application зарегистрировать в Манифесте : android:name=".App"

fun main(){
    val car=Car()
    car.startCar()

    val engine=Engine()
    val car1=Car1(engine)
    //car1.startCa1()

    val car2=Car2()
    car2.setEngine(engine)


    val car3 =Car3()
    car3.engine=engine
    car3.startCar3()


}
class Car{                                //Создание зависимости (мы сильно зависимы отваличия этого класса)
    private val engine =Engine()           //не является внедрением зависимости по тому что engine
    fun startCar(){                        //создается внутри класса
        engine.startEngine()
    }
}
class Car1(private val engine: Engine){ //Внедрение зависимости так как Engine приходит из вне
    fun startCa1(){                      //посредством конструктора
        engine.startEngine()
    }
}
class Car2 {                           //Внедрение зависимости так как Engine приходит из вне
    private var engine:Engine?=null      //путем использования сетера
    fun setEngine(engine: Engine){
        this.engine=engine
    }
}
class Car3 {                  //Внедрение зависимости
    lateinit var engine: Engine //В случае использования ЛЕЙТИНИТ если мы забудем использовать
    fun startCar3(){               //то у нас код сломается
        engine.startEngine()
    }
//    fun startCar3(){               //Лутше делать так на случай если забудешт проинициализировать
//        if (::engine.isInitialized){
//        engine.startEngine()
//        }
//    }
}


class Engine{
    fun startEngine(){
        println("Engine started")
    }



}
