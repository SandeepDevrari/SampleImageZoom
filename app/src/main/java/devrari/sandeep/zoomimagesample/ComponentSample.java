package devrari.sandeep.zoomimagesample;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ModuleSample.class})
@Singleton
public interface ComponentSample {
    void inject(SampleApp sampleApp);
    void inject(ActivityZoomer activityZoomer);
}
