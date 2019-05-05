/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2017-2019 DaPorkchop_
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original author of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.pepsimod.util.config.impl;

import com.google.gson.JsonObject;
import net.daporkchop.pepsimod.util.config.IConfigTranslator;

public class ElytraFlyTranslator implements IConfigTranslator {
    public static final ElytraFlyTranslator INSTANCE = new ElytraFlyTranslator();
    public boolean easyStart = false;
    public boolean stopInWater = true;
    public boolean fly = false;
    public float speed = 0.2f;
    public ElytraFlyMode mode = ElytraFlyMode.getMode(0);

    private ElytraFlyTranslator() {

    }

    public void encode(JsonObject json) {
        json.addProperty("easyStart", this.easyStart);
        json.addProperty("stopInWater", this.stopInWater);
        json.addProperty("fly", this.fly);
        json.addProperty("speed", this.speed);
        json.addProperty("mode", this.mode.ordinal());
    }

    public void decode(String fieldName, JsonObject json) {
        this.easyStart = this.getBoolean(json, "easyStart", this.easyStart);
        this.stopInWater = this.getBoolean(json, "stopInWater", this.stopInWater);
        this.fly = this.getBoolean(json, "fly", this.fly);
        this.speed = this.getFloat(json, "speed", this.speed);
        this.mode = ElytraFlyMode.getMode(this.getInt(json, "mode", this.mode.ordinal()));
    }

    public String name() {
        return "elytraFly";
    }

    public enum ElytraFlyMode {
        NORMAL,
        PACKET;

        public static ElytraFlyMode getMode(int id) {
            return values()[id];
        }
    }
}
