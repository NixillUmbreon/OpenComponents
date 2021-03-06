package li.cil.occ.mods;

import cpw.mods.fml.common.Loader;
import li.cil.occ.OpenComponents;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public final class Registry {
    private static final Set<IMod> handlers = new HashSet<IMod>();

    private Registry() {
    }

    public static void add(final IMod mod) {
        final boolean isBlacklisted = Arrays.asList(OpenComponents.modBlacklist).contains(mod.getModId());
        final boolean alwaysEnabled = mod.getModId() == null || mod.getModId().isEmpty() || "Minecraft".equals(mod.getModId());
        if (!isBlacklisted && (alwaysEnabled || Loader.isModLoaded(mod.getModId())) && handlers.add(mod)) {
            OpenComponents.Log.info(String.format("Initializing converters and drivers for '%s'.", mod.getModId()));
            try {
                mod.initialize();
            } catch (Throwable e) {
                OpenComponents.Log.log(Level.WARNING, String.format("Error initializing handler for '%s'", mod.getModId()), e);
            }
        }
    }
}
