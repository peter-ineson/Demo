/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ineson.demo.app;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.ineson.demo.app.page.Pages;
import me.ineson.demo.app.steps.SolarSystemSteps;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.executors.SameThreadExecutors;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.PerStoriesWebDriverSteps;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverScreenshotOnFailure;
import org.jbehave.web.selenium.WebDriverSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author peter
 *
@RunWith(AnnotatedEmbedderRunner.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true,
    ignoreFailureInStories = true, ignoreFailureInView = false, 
    verboseFailures = true)
@UsingSteps(instances = { SolarSystemSteps.class })
 */
public class SolarSystemStoriesTest extends JUnitStories {

    private static final Logger log = LoggerFactory.getLogger(SolarSystemStoriesTest.class);

    private WebDriverProvider driverProvider = new PropertyWebDriverProvider();
    private WebDriverSteps lifecycleSteps = new PerStoriesWebDriverSteps(driverProvider);
    private Pages pages = new Pages(driverProvider);
    private SeleniumContext context = new SeleniumContext();
    private ContextView contextView = new LocalFrameContextView().sized(500, 100);
    
    public SolarSystemStoriesTest() {
        if ( lifecycleSteps instanceof PerStoriesWebDriverSteps ){
            configuredEmbedder().useExecutorService(new SameThreadExecutors().create(configuredEmbedder().embedderControls()));
        }
    }

    @Override
    public Configuration configuration() {
        System.out.println( "****************** called config");
        Class<? extends Embeddable> embeddableClass = this.getClass();
        
        File dur = new File( "D:/Dev/Demo/DemoApp/build/build/jbehave/storyDurations.props");
        try {
            dur.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("error create props file", e);
        }
        
        return new SeleniumConfiguration()
                .useSeleniumContext(context)
                .useWebDriverProvider(driverProvider)
                .useStepMonitor(new SeleniumStepMonitor(contextView, context, new SilentStepMonitor()))
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                    .withCodeLocation(codeLocationFromClass(embeddableClass))
                    .withDefaultFormats()
                    .withFormats(CONSOLE, TXT, HTML, XML)
                    .withRelativeDirectory("../build/jbehave"));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        Configuration configuration = configuration();
        return new InstanceStepsFactory(configuration, 
                new SolarSystemSteps(pages),
                lifecycleSteps,
                new WebDriverScreenshotOnFailure(driverProvider, configuration.storyReporterBuilder()));
    }


    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths("D:/Dev/Demo/DemoApp/src/accTest/stories", asList("**/*.story"), null);
//        .findPaths("src/accTest/stories", asList("**/*.story"), null);
//        D:\Dev\Demo\DemoApp\src\accTest\stories        
/*
        List<String> result = new ArrayList<String>();
        URI cwd = new File("src/systemTest/resources").toURI();
        for (String storyPath : storyPaths.split(File.pathSeparator)) {
          File storyFile = new File(storyPath);
          if (!storyFile.exists()) {
            throw new IllegalArgumentException("Story file not found: "
              + storyPath);
          }
          result.add(cwd.relativize(storyFile.toURI()).toString());
        }
        return result;
 */       
    }
    
}
