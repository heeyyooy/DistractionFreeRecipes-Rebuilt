# Distraction Free Recipes Rebuilt

[English](README.md) | [Русский](README_RU.md)

**Version:** 1.0.0  
**Minecraft:** 1.21.1  
**Mod loader:** NeoForge 21.1.x  
**Recipe viewers:** EMI and JEI

Distraction Free Recipes Rebuilt is a client-side NeoForge mod that keeps recipe viewer interfaces compact by hiding the recipe/item list while its search field is empty. It can also hide the empty EMI Favorites sidebar, including its background and header buttons.

## Features

- Automatically hide the EMI item index or JEI ingredient list when the search field is empty.
- Hide the EMI Favorites sidebar background and header buttons while the favorites list is empty.
- Show a short empty-Favorites hint with EMI's current favorite keybind; it updates when the key is reassigned and can be disabled in the config.
- Keep the search field available while the list is hidden.
- Optionally display a `Search to View Recipes` hint.
- Optionally display on-screen `Show Recipes` and `Hide Recipes` controls.
- Reduce the background opacity of EMI and JEI search fields.
- Independently show or hide the JEI Config, Bookmarks, and Lookup History toolbar buttons.
- Provide English and Russian translations for the mod's own interface text and configuration screen.
- Use native NeoForge configuration; TxniLib and Forgified Fabric API are not required.

## Compatibility

| Component | Development-tested version |
|---|---|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.x (build target: 21.1.265) |
| EMI | 1.1.24+1.21.1+neoforge |
| JEI | 19.64.0.455 for NeoForge 1.21.1 |

EMI and JEI are optional integrations. Install at least one recipe viewer build for Minecraft 1.21.1. The versions in the table are the versions used for development testing; they are **not** enforced limits. The mod metadata does not restrict EMI or JEI versions, so newer and older builds can be detected by NeoForge. However, compatibility is not guaranteed: changes to EMI's or JEI's APIs may require updates to this mod. REI is not supported.

This is a client-only mod. It does not need to be installed on a server; a client with the mod can join a server without it.

The mod's configuration is stored in `config/distraction_free_recipes-client.toml`.

## Configuration

All options are in the `[client]` section:

| Option | Default | Description |
|---|---:|---|
| `enabled` | `true` | Automatically hide the recipe list when search is empty. |
| `hideEmptyEmiFavorites` | `true` | Hide the EMI Favorites sidebar, its background, and its header buttons when no favorites have been added. |
| `showEmiFavoritesHint` | `true` | Show a hint with the currently assigned EMI favorite keybind while the Favorites list is empty. |
| `lowerOpacity` | `false` | Reduce the background opacity of EMI and JEI search fields. |
| `showHelpText` | `true` | Display the search hint while the recipe list is hidden. |
| `showToggleButton` | `true` | Display the on-screen Show Recipes / Hide Recipes button. |
| `showJeiConfigButton` | `true` | Display the JEI Config button. |
| `showJeiBookmarksButton` | `true` | Display the JEI Bookmarks button. |
| `showJeiLookupHistoryButton` | `true` | Display the JEI Lookup History button. |

The language files are located in `assets/distraction_free_recipes/lang/`. English (`en_us`) is the fallback language; Russian (`ru_ru`) is also included. The game language controls which translations are displayed.

## Installation

1. Install Minecraft 1.21.1 with NeoForge 21.1.x.
2. Place the mod JAR in the instance's `mods` folder.
3. Install a compatible EMI and/or JEI build for Minecraft 1.21.1. The versions in the compatibility table are the development-tested versions, not mandatory version requirements.
4. Launch the game and edit the options in the mod's client configuration if needed.

## Building from source

Requirements: JDK 21. The included `gradlew.bat` bootstraps the project's pinned Gradle 9.2.1 distribution and verifies its SHA-256 hash. A separate Gradle installation is not required on Windows.

Run in PowerShell:

```powershell
.\gradlew.bat clean build
```

Or run `build.bat` from Command Prompt. The resulting file is `build/libs/DistractionFreeRecipes-Rebuilt-neoforge-1.21.1-1.0.0.jar`.

The EMI and JEI dependencies are used for compilation and the development run only. They are not bundled into the output JAR.

## License and attribution

This project is distributed under the MIT License. It is an independent NeoForge reimplementation inspired by the functionality of [Distraction Free Recipes](https://github.com/txnimc/DistractionFreeRecipes). The upstream project's MIT license and copyright notice are retained in `LICENSE`; see `NOTICE.md` for attribution.

This project is not affiliated with or endorsed by the upstream author.
