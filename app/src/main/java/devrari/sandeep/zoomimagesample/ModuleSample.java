package devrari.sandeep.zoomimagesample;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleSample {
    private SampleApp sampleApp;

    public ModuleSample(SampleApp sampleApp) {
        this.sampleApp = sampleApp;
    }

    @Provides
    @Singleton
    public SampleApp provideSampleApp() {
        return sampleApp;
    }
}
