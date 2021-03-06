package li.cil.occ.mods.buildcraft;

import buildcraft.api.transport.IPipe;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import li.cil.occ.mods.ManagedTileEntityEnvironment;
import net.minecraft.world.World;

public final class DriverPipe extends DriverTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return IPipe.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(final World world, final int x, final int y, final int z) {
        return new Environment((IPipe) world.getBlockTileEntity(x, y, z));
    }

    public static final class Environment extends ManagedTileEntityEnvironment<IPipe> {
        public Environment(final IPipe tileEntity) {
            super(tileEntity, "pipe");
        }

        @Callback(doc = "function():boolean --  Returns whether the pipe has a gate.")
        public Object[] hasGate(final Context context, final Arguments args) {
            return new Object[]{tileEntity.hasGate()};
        }

        @Callback(doc = "function(color:string):boolean --  Returns whether the pipe is wired with the given color")
        public Object[] isWired(final Context context, final Arguments args) {
            try {
                return new Object[]{tileEntity.isWired(IPipe.WireColor.valueOf(args.checkString(0)))};
            } catch (Throwable ignored) {
            }
            return new Object[]{false};
        }
    }
}
