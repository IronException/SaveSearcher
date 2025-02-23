/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2018-2018 DaPorkchop_ and contributors
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package net.daporkchop.savesearcher.module;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.daporkchop.lib.minecraft.world.Column;
import net.daporkchop.lib.minecraft.world.World;
import net.daporkchop.savesearcher.SearchModule;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author DaPorkchop_
 */
public class AvgHeightModule implements SearchModule {
    private final AtomicLong totalHeight = new AtomicLong(0L);
    private final AtomicLong totalCount = new AtomicLong(0L);

    public AvgHeightModule(String[] args) {
    }

    @Override
    public void init(World world) {
        this.totalHeight.set(0L);
        this.totalCount.set(0L);
    }

    @Override
    public void saveData(JsonObject object, Gson gson) {
        object.addProperty("height", (double) this.totalHeight.get() / (double) this.totalCount.get());
    }

    @Override
    public void handle(long current, long estimatedTotal, Column column) {
        this.totalCount.addAndGet(256L);
        for (int x = 15; x >= 0; x--) {
            for (int z = 15; z >= 0; z--) {
                this.totalHeight.addAndGet(column.getHighestBlock(x, z));
            }
        }
    }

    @Override
    public String toString() {
        return "Average Height";
    }

    @Override
    public String getSaveFormat() {
        return "average_height";
    }
}
