package devrari.sandeep.zoomimagesample;

import android.app.Application;

public class SampleApp extends Application {
private ComponentSample componentSample;

    @Override
    public void onCreate() {
        super.onCreate();
        componentSample=DaggerComponentSample.builder().moduleSample(new ModuleSample(this)).build();
    }

    public ComponentSample getComponentSample() {
        return componentSample;
    }
}
