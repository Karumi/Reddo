![Reddo logo][reddologo]

#Reddo
---------------

[![Build Status](https://travis-ci.com/Karumi/Reddo.svg?token=Kb2RqPaWxFZ8XPxpqvqz&branch=master)](https://travis-ci.com/Karumi/Reddo)

Reddo is a fun project to make the most out of your Raspberry PI + matrix display.

With Reddo you will be able to show any messages you want in your LED matrix, from programmed messages to GitHub updates.

## Demo

## Usage

To start using reddo you will need first to set up your components, take your Raspberry Pi and your matrix display and let's get started!

### Hardware

You will need to connect your Raspberry to your display, you can use one of the multiple tutorials available on the internet [[1][matrix_led_tutorial_1]][[2][matrix_led_tutorial_2]]. In case you are feeling lazy, we've written yet another basic guide with the minimum steps needed to wire your components up.

#### Materials

* LED matrix panel ![materials_display][materials_display]
* Raspberry Pi 2 ![materials_rpi][materials_rpi]
* Power supply for your Raspberry ![materials_rpi_power_supply][materials_rpi_power_supply]
* Lots of wires ![materials_wires][materials_wires]
* DC power supply of 2A ![materials_display_power_supply][materials_display_power_supply]
* WiFi dongle ![materials_dongle][materials_dongle]
* Jack adapter ![materials_adapter][materials_adapter]

#### Steps

Connect everything following these diagrams (for clarity reasons, we've separated the connections in 4 sections but keep in mind that they represent the same components!):

| `I` |
| :---: |
| ![Connection diagram][connection_diagram_1] |
| `R0 -> 23` |
| `G0 -> 13` |
| `B0 -> 26` |

| `II` |
| :---: |
| ![Connection diagram][connection_diagram_2] |
| `R1 -> 24` |
| `G1 -> 21` |
| `B1 -> 19` |

| `III` |
| :---: |
| ![Connection diagram][connection_diagram_3] |
| `A -> 15` |
| `B -> 16` |
| `C -> 18` |
| `D -> 22` |

| `IV` |
| :---: |
| ![Connection diagram][connection_diagram_4] |
| `CLK -> 11` |
| `STB -> 7` |
| `OE- -> 12` |
| `3 x GND -> GND` |

Double check your wiring, 99% of the problems you encounter are caused by a wire connected to the wrong pin.

Connect your panel power wire to the power supply using the jack adapter:

TODO diagram




### Configuration file

The configuration file is written in plain JSON and is the only file you will need to modify to get your Reddo working. You can configure all the tasks you want to execute like the GitHub repositories you want to watch or the predefined messages you want to show. Here is an example of the configuration file:

```json

{
  "fps" : 60,
  "output" : "led",
  "connection" : {
    "remoteIp" : "127.0.0.1",
    "remotePort" : 9001
  },
  "gitHubOauthToken" : "",
  "messages" : [
    "Welcome to Reddo!"
  ],
  "gitHubRepositories" : [
    "Karumi/Dexter",
    "Karumi/Rosie",
    "Karumi/BothamUI",
    "Karumi/BothamNetworking",
    "Karumi/ExpandableSelector",
    "Karumi/HeaderRecyclerView"
  ],
  "gitHubUsers" : [
    "karumi"
  ]
}

```

## Do you want to contribute?

Be creative and feel free to add any useful feature/integration to the project, we can't wait to see your ideas!

Keep in mind that your PRs must be validated in Travis-CI. Please, run a local build with `./gradlew checkstyle build` before submiting your code.

## Acknowledgments

Thanks to [hzeller][hzeller] for his impressive work and specially for his project [rpi-rgb-led-matrix][rpi-rgb-led-matrix]

[reddologo]: art/reddo.png
[materials_display]: art/materials_display.png
[materials_rpi]: art/materials_rpi.png
[materials_dongle]: art/materials_dongle.png
[materials_display_power_supply]: art/materials_display_power_supply.png
[materials_rpi_power_supply]: art/materials_rpi_power_supply.png
[materials_wires]: art/materials_wires.png
[materials_adapter]: art/materials_adapter.png
[connection_diagram_1]: art/connection_diagram_1.png
[connection_diagram_2]: art/connection_diagram_2.png
[connection_diagram_3]: art/connection_diagram_3.png
[connection_diagram_4]: art/connection_diagram_4.png
[matrix_led_tutorial_1]: https://learn.adafruit.com/connecting-a-16x32-rgb-led-matrix-panel-to-a-raspberry-pi/overview
[matrix_led_tutorial_2]: https://github.com/hzeller/rpi-rgb-led-matrix
[hzeller]: https://github.com/hzeller
[rpi-rgb-led-matrix]: https://github.com/hzeller/rpi-rgb-led-matrix