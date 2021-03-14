<img src="Logo.png" alt="Logo" title="Logo" align="right" width="200" height="200" />

# HardcoreLists

## About
HardcoreLists is a Spigot plugin that provides a PvP timer for new players, as well as a list of dead and alive players, useful for hardcore Minecraft servers.

## Purpose
This project was created for another server, due to the lack of very simple PvP timer plugins, and a way to list and keep track of the players in our hardcore world.

## Usage

### Player Commands
All players are able to use `/pvptime`, which shows their remaining PvP timer time.

### Admin Commands
Admins are users with the permission `hardcorelists.admin`. They can use the following commands:
- `/pvptime <player>` - Shows the remaining PvP timer time for any player
- `/hardcorelists reload` - Reload all configurations for the plugin
- `/hardcorelists help` - Show the help message
- `/hardcorelists timer set <seconds>` - Change the length of the PvP timer
- `/hardcorelists list <dead/alive> <page>` - Show a page of the dead or alive player lists

### Console Commands
The command `hardcorelists reset` can only be used in the console when no players are online. This will delete all player data in the configs.

## Requirements
- Spigot 1.13.0 - 1.16.4
- Java 8

## Installation
To install the plugin, download the latest release, put it in your server plugins folder, and start or restart your server. This will generate the necessary files for configuration of the plugin, located in `plugins/HardcoreLists`.

## Configuration
The only configurable option the plugin has is located in `time.yml` and is used to adjust how long the PvP timer lasts; however, this should be changed with the command `hardcorelists timer set <time>` to avoid issues. 

There is also `players.yml` which holds the lists of players and their current reamining PvP timer times.

For the message configuration, you can use color codes. You can also use the placeholders used per message, as shown in the default configuration. The messages and their names should explain what they are used for.

## Building
If you wish to build from source, a `build.gradle` is included to create the jar, as well as get dependencies if you import the project into your IDE.

## Notice
This project is no longer in development.