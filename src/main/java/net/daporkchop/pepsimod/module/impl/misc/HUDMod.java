/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2017 Team Pepsi
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from Team Pepsi.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: Team Pepsi), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.misc.TickRate;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.util.colors.rainbow.RainbowText;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketCustomPayload;

import java.awt.*;

public class HUDMod extends Module {
    public static HUDMod INSTANCE;
    public String serverBrand = "";

    public HUDMod(boolean isEnabled, int key) {
        super(isEnabled, "HUD", key, true);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public void tick() {
        for (Module module : ModuleManager.ENABLED_MODULES) {
            module.updateName();
        }

        ModuleManager.sortModules(ModuleManager.sortType);
    }

    @Override
    public void init() {
        INSTANCE = this;
    }

    @Override
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[]{
                new ModuleOption<>(pepsiMod.hudSettings.drawLogo, "draw_logo", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.drawLogo = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.drawLogo;
                        }, "Watermark"),
                new ModuleOption<>(pepsiMod.hudSettings.arrayList, "arraylist", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.arrayList = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.arrayList;
                        }, "ArrayList"),
                new ModuleOption<>(pepsiMod.hudSettings.TPS, "tps", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.TPS = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.TPS;
                        }, "TPS"),
                new ModuleOption<>(pepsiMod.hudSettings.coords, "coords", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.coords = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.coords;
                        }, "Coords"),
                new ModuleOption<>(pepsiMod.hudSettings.netherCoords, "nether_coords", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.netherCoords = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.netherCoords;
                        }, "NetherCoords"),
                new ModuleOption<>(pepsiMod.hudSettings.arrayListTop, "arraylist_top", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.arrayListTop = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.arrayListTop;
                        }, "ArrayListOnTop"),
                new ModuleOption<>(pepsiMod.hudSettings.serverBrand, "server_brand", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.serverBrand = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.serverBrand;
                        }, "ServerBrand"),
                new ModuleOption<>(pepsiMod.hudSettings.rainbow, "rainbow", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.rainbow = value;
                            for (Module module : ModuleManager.AVALIBLE_MODULES) {
                                module.updateName();
                            }
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.rainbow;
                        }, "Rainbow"),
                new ModuleOption<>(pepsiMod.hudSettings.direction, "direction", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.direction = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.direction;
                        }, "Direction"),
                new ModuleOption<>(pepsiMod.hudSettings.armor, "armor", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.armor = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.armor;
                        }, "Armor"),
                new ModuleOption<>(pepsiMod.hudSettings.effects, "effects", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.effects = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.effects;
                        }, "Effects"),
                new ModuleOption<>(pepsiMod.hudSettings.fps, "fps", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.fps = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.fps;
                        }, "FPS"),
                new ModuleOption<>(pepsiMod.hudSettings.ping, "ping", OptionCompletions.BOOLEAN,
                        (value) -> {
                            pepsiMod.hudSettings.ping = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.ping;
                        }, "Ping"),
                new ModuleOption<>(pepsiMod.hudSettings.r, "r", new String[]{"0", "128", "255"},
                        (value) -> {
                            pepsiMod.hudSettings.r = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.r;
                        }, "Red", new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)),
                new ModuleOption<>(pepsiMod.hudSettings.g, "g", new String[]{"0", "128", "255"},
                        (value) -> {
                            pepsiMod.hudSettings.g = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.g;
                        }, "Green", new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)),
                new ModuleOption<>(pepsiMod.hudSettings.b, "b", new String[]{"0", "128", "255"},
                        (value) -> {
                            pepsiMod.hudSettings.b = value;
                            return true;
                        },
                        () -> {
                            return pepsiMod.hudSettings.b;
                        }, "Blue", new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1))
        };
    }

    @Override
    public boolean preRecievePacket(Packet packet)  {
        if (packet instanceof SPacketCustomPayload) {
            if (((SPacketCustomPayload) packet).getChannelName().equals("MC|Brand"))    {
                serverBrand = ((SPacketCustomPayload) packet).getBufferData().readString(32767);
            }
        }
        return false;
    }

    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }

    public void registerKeybind(String name, int key) {
    }

    @Override
    public void onRenderGUI(float partialTicks, int width, int height, GuiIngame gui) {
        if (pepsiMod.hudSettings.drawLogo) {
            if (pepsiMod.hudSettings.rainbow) {
                PepsiUtils.PEPSI_NAME.drawAtPos(gui, 2, 2, 0);
            } else {
                pepsiMod.hudSettings.bindColor();
                mc.fontRenderer.drawString(PepsiUtils.PEPSI_NAME.text, 2, 2, pepsiMod.hudSettings.getColor(), true);
            }
        }

        if (pepsiMod.hudSettings.arrayList) {
            if (pepsiMod.hudSettings.arrayListTop) {
                for (int i = 0, j = 0; i < ModuleManager.ENABLED_MODULES.size(); i++) {
                    Module module = ModuleManager.ENABLED_MODULES.get(i);
                    if (module.hide) {
                        continue;
                    }

                    if (pepsiMod.hudSettings.rainbow) {
                        if (module.text instanceof RainbowText) {
                            ((RainbowText) module.text).drawAtPos(gui, width - 2 - module.text.width(), 2 + j * 10, ++j * 10);
                        } else {
                            module.text.drawAtPos(gui, width - 2 - module.text.width(), 2 + ++j * 10);
                        }
                    } else {
                        pepsiMod.hudSettings.bindColor();
                        mc.fontRenderer.drawString(module.text.getRawText(), width - 2 - module.text.width(), 2 + j * 10, pepsiMod.hudSettings.getColor());
                        j++;
                    }
                }
            } else {
                if (!(mc.currentScreen instanceof GuiChat)) {
                    for (int i = 0, j = 0; i < ModuleManager.ENABLED_MODULES.size(); i++) {
                        Module module = ModuleManager.ENABLED_MODULES.get(i);
                        if (module.hide) {
                            continue;
                        }

                        if (pepsiMod.hudSettings.rainbow) {
                            if (module.text instanceof RainbowText) {
                                ((RainbowText) module.text).drawAtPos(gui, width - 2 - module.text.width(), height - 2 - j * 10, ++j * 8);
                            } else {
                                module.text.drawAtPos(gui, width - 2 - module.text.width(), height - 2 - ++j * -10);
                            }
                        } else {
                            pepsiMod.hudSettings.bindColor();
                            mc.fontRenderer.drawString(module.text.getRawText(), width - 2 - module.text.width(), height - 12 - j * 10, pepsiMod.hudSettings.getColor());
                            j++;
                        }
                    }
                }
            }
        }

        int i = 0;
        if (pepsiMod.hudSettings.arrayListTop) {
            if (!(mc.currentScreen instanceof GuiChat)) {
                if (pepsiMod.hudSettings.serverBrand) {
                    String text = PepsiUtils.COLOR_ESCAPE + "7Server brand: " + PepsiUtils.COLOR_ESCAPE + "r" + HUDMod.INSTANCE.serverBrand;
                    gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("Server brand: " + HUDMod.INSTANCE.serverBrand) + 2), height - 2 - ++i * 10, Color.white.getRGB());
                }
                if (pepsiMod.hudSettings.ping) {
                    try {
                        int ping = mc.getConnection().getPlayerInfo(mc.getConnection().getGameProfile().getId()).getResponseTime();
                        String text = PepsiUtils.COLOR_ESCAPE + "7Ping: " + PepsiUtils.COLOR_ESCAPE + "r" + ping;
                        gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("Ping: " + ping) + 2), height - 2 - ++i * 10, Color.white.getRGB());
                    } catch (NullPointerException e) {
                    }
                }
                if (pepsiMod.hudSettings.TPS) {
                    String text = PepsiUtils.COLOR_ESCAPE + "7TPS: " + PepsiUtils.COLOR_ESCAPE + "r" + TickRate.TPS;
                    gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("TPS: " + TickRate.TPS) + 2), height - 2 - ++i * 10, Color.white.getRGB());
                }
                if (pepsiMod.hudSettings.fps) {
                    String text = PepsiUtils.COLOR_ESCAPE + "7FPS: " + PepsiUtils.COLOR_ESCAPE + "r" + ReflectionStuff.getDebugFps();
                    gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("FPS: " + ReflectionStuff.getDebugFps()) + 2), height - 2 - ++i * 10, Color.white.getRGB());
                }
            }
        } else {
            if (pepsiMod.hudSettings.serverBrand) {
                String text = PepsiUtils.COLOR_ESCAPE + "7Server brand: " + PepsiUtils.COLOR_ESCAPE + "r" + HUDMod.INSTANCE.serverBrand;
                gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("Server brand: " + HUDMod.INSTANCE.serverBrand) + 2), 2 + i++ * 10, Color.white.getRGB());
            }
            if (pepsiMod.hudSettings.ping) {
                try {
                    int ping = mc.getConnection().getPlayerInfo(mc.getConnection().getGameProfile().getId()).getResponseTime();
                    String text = PepsiUtils.COLOR_ESCAPE + "7Ping: " + PepsiUtils.COLOR_ESCAPE + "r" + ping;
                    gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("Ping: " + ping) + 2), 2 + i++ * 10, Color.white.getRGB());
                } catch (NullPointerException e) {
                }
            }
            if (pepsiMod.hudSettings.TPS) {
                String text = PepsiUtils.COLOR_ESCAPE + "7TPS: " + PepsiUtils.COLOR_ESCAPE + "r" + TickRate.TPS;
                gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("TPS: " + TickRate.TPS) + 2), 2 + i++ * 10, Color.white.getRGB());
            }
            if (pepsiMod.hudSettings.fps) {
                String text = PepsiUtils.COLOR_ESCAPE + "7FPS: " + PepsiUtils.COLOR_ESCAPE + "r" + ReflectionStuff.getDebugFps();
                gui.drawString(mc.fontRenderer, text, width - (mc.fontRenderer.getStringWidth("FPS: " + ReflectionStuff.getDebugFps()) + 2), 2 + i++ * 10, Color.white.getRGB());
            }
        }

        i = mc.currentScreen instanceof GuiChat ? 12 : 0;
        if (pepsiMod.hudSettings.coords) {
            String toRender = PepsiUtils.COLOR_ESCAPE + "7XYZ" + PepsiUtils.COLOR_ESCAPE + "f: " + PepsiUtils.COLOR_ESCAPE + "7" + PepsiUtils.roundCoords(mc.player.posX) + "" + PepsiUtils.COLOR_ESCAPE + "f, " + PepsiUtils.COLOR_ESCAPE + "7" + PepsiUtils.roundCoords(mc.player.posY) + "" + PepsiUtils.COLOR_ESCAPE + "f, " + PepsiUtils.COLOR_ESCAPE + "7" + PepsiUtils.roundCoords(mc.player.posZ);
            if (pepsiMod.hudSettings.netherCoords && ReflectionStuff.getDimension() != 1) {
                toRender += " " + PepsiUtils.COLOR_ESCAPE + "f(" + PepsiUtils.COLOR_ESCAPE + "7" + PepsiUtils.roundCoords(PepsiUtils.getDimensionCoord(mc.player.posX)) + "" + PepsiUtils.COLOR_ESCAPE + "f, " + PepsiUtils.COLOR_ESCAPE + "7" + PepsiUtils.roundCoords(mc.player.posY) + "" + PepsiUtils.COLOR_ESCAPE + "f, " + PepsiUtils.COLOR_ESCAPE + "7" + PepsiUtils.roundCoords(PepsiUtils.getDimensionCoord(mc.player.posZ)) + "" + PepsiUtils.COLOR_ESCAPE + "f)";
            }
            mc.fontRenderer.drawString(toRender, 2, height - (i += 10), Color.white.getRGB(), true);
        }
        if (pepsiMod.hudSettings.direction) {
            mc.fontRenderer.drawString(PepsiUtils.COLOR_ESCAPE + "7[" + PepsiUtils.COLOR_ESCAPE + "f" + PepsiUtils.getFacing() + PepsiUtils.COLOR_ESCAPE + "7]", 2, height - (i += 10), Color.white.getRGB(), true);
        }


        if (pepsiMod.hudSettings.armor) {
            i = 0;
            int xPos = width / 2;
            xPos -= 103;
            for (int j = 0; j < 4; j++) {
                ItemStack stack = PepsiUtils.getWearingArmor(j);
                PepsiUtils.renderItem(xPos + 20 * i++, height - 40, partialTicks, mc.player, stack);
            }
        }
    }
}
