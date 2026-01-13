package stepik
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

interface  Invoker<T : Command> {
    fun executeCommand(command: T)
}
class RemoteControl() : Invoker<DeviceCommand> {
    private val commands = LinkedBlockingQueue<Command>()

    init {
        thread {
            while (true) {
                val command = commands.take()
                command.execute()
            }
        }
    }
    override fun executeCommand(command: DeviceCommand) {
        commands.add(command)
    }
}

interface Command {
    fun execute()
}
class Light {
    fun turnOn() = println("Свет включен")
    fun turnOff() = println("Свет выключен")
}

class TV {
    fun turnOn() = println("Телевизор включен")
    fun turnOff() = println("Телевизор выключен")
    fun changeChannel(channel: Int) = println("Канал переключен на $channel")
}

class AirConditioner {
    fun turnOn() = println("Кондиционер включен")
    fun turnOff() = println("Кондиционер выключен")
    fun setTemperature(temp: Int) = println("Температура установлена на $temp°C")
}
sealed interface DeviceCommand : Command {

    class LightOnCommand(private val light: Light) : DeviceCommand {
        override fun execute() = light.turnOn()
    }
    class LightOffCommand(private val light: Light) : DeviceCommand {
        override fun execute() = light.turnOff()
    }
    class TVOnCommand(private val tv: TV) : DeviceCommand {
        override fun execute() = tv.turnOn()
    }
    class TVOffCommand(private val tv: TV) : DeviceCommand {
        override fun execute() = tv.turnOff()
    }
    class TVChangeChannelCommand(private val tv: TV, private val channel: Int) : DeviceCommand {
        override fun execute() = tv.changeChannel(channel)
    }
    class AirConditionerOnCommand(private val ac: AirConditioner) : DeviceCommand {
        override fun execute() = ac.turnOn()
    }
    class AirConditionerOffCommand(private val ac: AirConditioner) : DeviceCommand {
        override fun execute() = ac.turnOff()
    }
    class AirConditionerSetTempCommand(private val ac: AirConditioner, private val temp: Int) : DeviceCommand {
        override fun execute() = ac.setTemperature(temp)
    }
}

fun runCommandTest() {
    val light = Light()
    val tv = TV()
    val ac = AirConditioner()
    val remote = RemoteControl()

    remote.executeCommand(DeviceCommand.LightOnCommand(light))
    remote.executeCommand(DeviceCommand.TVOnCommand(tv))
    remote.executeCommand(DeviceCommand.TVChangeChannelCommand(tv, 5))
    remote.executeCommand(DeviceCommand.AirConditionerOnCommand(ac))
    remote.executeCommand(DeviceCommand.AirConditionerSetTempCommand(ac, 22))
    remote.executeCommand(DeviceCommand.LightOffCommand(light))
    remote.executeCommand(DeviceCommand.TVOffCommand(tv))
    remote.executeCommand(DeviceCommand.AirConditionerOffCommand(ac))
}
fun main() {
    runCommandTest()
}