
# 📑 VelocityGUI
A Proxy wide GUI for Velocity  
**Requires [Protocolize](https://simplixsoft.com/protocolize)**

## Permissions
| Permission | Purpose |  
|--|--|  
| `vgui.admin` | Needed for all `/vgui` commands |

## Commands
| Command | Response |  
|--|--|  
| `/vgui` | Info command |  
| `/vgui panel` | Lists all panels |  
| `/vgui panel <name>` | Loads up a specific panel |  
| `/vgui reload` | Reloads the panels and config

## Config
```toml  
#The Name of the panel  
name = "example"  
  
#The permission needed to open the panel (Can be anything)  
perm = "default"  
  
#The rows in the GUI (Max 9)  
rows = 3  
  
#The GUI Title  
title = "&dVelocityGUI"  
  
#Whats empty slots should be filled with (AIR for empty)  
empty = "GREEN_STAINED_GLASS_PANE"  
  
#Sound when opening the GUI  
sound = "ENTITY_ARROW_HIT_PLAYER"  
  
#The commands to open the gui (/rules /version etc)  
commands = [  
 "rules", "version", "plugins", "help", "pl"]  
  
#The Items in the gui  
[items]  
  
#This is in the 13th slot  
[items.13]  
#The item material  
material = "OAK_SIGN"  
#The item amount  
stack = 1  
#The item name  
name = "&dVelocityGUI"  
#The items lore  
lore = [  
 "&eA Velociy Side GUI", "&eFor all your servers"]  
#Is the item enchanted?  
enchanted = true  
#Commands to run  
commands = [  
 "sudo= I love VelocityGUI"]  
```  

## Item Commands
| Command | Example | Response |  
|--|--|--|  
| `open` | `open= rules` | Open another panel |  
| `close` | `close` | Closes the current panel |  
| `sudo` | `sudo= Chat` `sudo= /command` | Run chat or a command as the player on a server |  
| `vsudo` | `vsudo= /command` | Runs a command as the player on the proxy |  
| `server` | `server= lobby`| Connects the player to a server

## Placeholders
| Placeholder | Value |  
|--|--|
| `%username%` | Get players username |  
| `%server_name%` | Get players server name |  
| `%chatcontrolred_nick%` | Get the players ChatControlRed nick name |  
| `%luckperms_meta_{meta}%` | Get luckperms meta value eg `luckperms_meta_home` |

The only placeholder support currently is for ChatControl Red nick names. You can use `%chatcontrolred_nick%` for this. Open an issue if you'd like others to be added.