package org.teslatoolkit.tool

import com.github.ajalt.clikt.core.subcommands
import org.teslatoolkit.tool.vehicle.ListVehicleCommand
import org.teslatoolkit.tool.vehicle.ShowVehicleChargeStateCommand
import org.teslatoolkit.tool.vehicle.ShowVehicleCommand
import org.teslatoolkit.tool.vehicle.ShowVehicleDriveStateCommand
import org.teslatoolkit.tool.vehicle.ShowVehicleGuiSettingsCommand
import org.teslatoolkit.tool.vehicle.ShowVehicleStateCommand
import org.teslatoolkit.tool.vehicle.VehicleCommand

fun main(args: Array<String>) {
  TeslaTool().subcommands(
    VehicleCommand().subcommands(
      ListVehicleCommand(),
      ShowVehicleCommand(),
      ShowVehicleStateCommand(),
      ShowVehicleChargeStateCommand(),
      ShowVehicleGuiSettingsCommand(),
      ShowVehicleDriveStateCommand()
    )
  ).main(args)
}
