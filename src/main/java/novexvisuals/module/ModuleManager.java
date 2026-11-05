package novexvisuals.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final List<Module> ALL = new ArrayList<>();
    /** category == null returns every module */
    public static List<Module> get(Module.Category c) {
        if (c == null) return new ArrayList<>(ALL);
        return ALL.stream().filter(m -> m.category == c).toList();
    }
}
