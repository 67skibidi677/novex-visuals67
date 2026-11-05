package novexvisuals.module;

public class FovModule extends Module {
    public static FovModule INSTANCE;
    public float fov = 100;
    public FovModule() { super("Custom FOV", Category.SKY); INSTANCE = this; }
    @Override public String setting() { return Math.round(fov) + "°"; }
    @Override public void cycle() { fov = fov >= 140 ? 60 : fov + 10; }
}
