# BloxHub

**BloxHub** is a lightweight Minecraft plugin designed for Bukkit/Spigot servers. It provides essential player utilities and quality-of-life commands including spawn management, gamemode switching, healing, flying, god mode, scoreboard display, and tab list customization.

## Features

- 📍 **Spawn Commands**
  - `/setspawn` — Set the server's spawn location
  - `/spawn` — Teleport to spawn

- 🎮 **Gamemode Commands**
  - `/gmc` — Creative mode
  - `/gms` — Survival mode
  - `/gma` — Adventure mode
  - `/gmsp` — Spectator mode
  - `/gm <0-3>` — Universal gamemode command

- ❤️ **Player Utilities**
  - `/heal` — Fully heal yourself or another player
  - `/feed` — Refill hunger bar

- ✈️ **Fly and God Mode**
  - `/fly` — Toggle flight
  - `/god` — Toggle invincibility
  - Includes event listeners for god mode persistence

- 📊 **Scoreboard System**
  - `/scoreboard` — Manage and toggle the scoreboard display
  - Dynamically updates via listeners

- 🧑 **NameTag Customization**
  - Adds `[HOT]` prefix (configurable) for all players
  - Uses `NametagEdit`-style formatting

- 🧾 **Tab List Formatting**
  - Custom tab list integration via event listeners

## Setup

1. Drop the compiled `BloxHub.jar` into your server’s `plugins/` directory.
2. Start or restart your Minecraft server.
3. Edit the config file in `plugins/BloxHub/config.yml` (optional).
4. Enjoy the added utility commands and enhancements!

## Requirements

- Minecraft server (1.16) with Bukkit, Spigot, or Paper
- Java 11 or higher

## Configuration

A default configuration file will be generated upon first run. Customize scoreboard, tags, and other options as needed.

## Development

This plugin is actively maintained and modular, making it easy to extend with new features or commands.

---

Made with 💙 by BlueGoose
