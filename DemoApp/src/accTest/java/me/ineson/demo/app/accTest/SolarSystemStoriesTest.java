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
package me.ineson.demo.app.accTest;

import static java.util.Arrays.asList;

import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure()
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = true, ignoreFailureInView = true)
@UsingSpring(resources = { "spring/application-db.xml",
        "spring/test-mongoDb-datasource.xml", "spring/test-jdbc-datasource.xml",
        "spring/steps.xml", "spring/configuration.xml"})
public class SolarSystemStoriesTest extends InjectableEmbedder  {

    private static final Logger log = LoggerFactory.getLogger(SolarSystemStoriesTest.class);

    @Test
    public void run() {
        injectedEmbedder().runStoriesAsPaths(storyPaths());
    }
 
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths("D:/Dev/Demo/DemoApp/src/accTest/stories", asList("**/*.story"), null);
    }
    
    
}
