# BloxHub

**BloxHub** is a lightweight Minecraft plugin designed for Bukkit/Spigot servers. It provides essential player utilities and quality-of-life commands including spawn management, gamemode switching, healing, flying, god mode, scoreboard display, and tab list customization.

## Features

- 📍 **Operator Commands**
  - `/setspawn` — Set the server's spawn location
  - `/gmc` — Creative mode
  - `/gms` — Survival mode
  - `/gma` — Adventure mode
  - `/gmsp` — Spectator mode
  - `/gm <0-3>` — Universal gamemode command (bugged)
  - `/fly` — Toggle flight
  - `/god` — Toggle invincibility
  - `/heal` — Fully heal yourself or another player
  - `/feed` — Refill hunger bar
  - `/tp` — Teleport to a player or location (currently bugged)
  - `/tph` — Teleport a player to your location

- 🎮 **Player Commands**
  - `/spawn` — Teleport to spawn
  - `/scoreboard` — Manage and toggle the scoreboard display (Dynamically updates via listeners)
  - `/tpa` — Send a teleport request
  - `/tpaccept` — Accept a teleport request

- 🧑 **NameTag Customization**
  - Adds `[HOT]` prefix (configurable) for all players (WIP)
  - Uses `NametagEdit`-style formatting (WIP)

- 🧾 **Formatting**
  - Custom tab list, scoreboard, and chat integration via event listeners

## Setup

1. Drop the compiled `BloxHub.jar` into your server’s `plugins/` directory.
2. Start or restart your Minecraft server.
3. Edit the config file in `plugins/BloxHub/config.yml` (optional).
4. Enjoy the added utility commands and enhancements!

## Requirements

- Minecraft server (1.18) with Bukkit, Spigot, or Paper
- Java 21 or higher (other versions have not been tested)
- (tested using a Paper 1.18 server)

## Configuration

A default configuration file will be generated upon first run. Customize scoreboard, tags, messages, and other options as needed.

## Development

This plugin is actively maintained and modular, making it easy to extend with new features or commands.

---

Made with 💙 by The_Blue_Goose
