package me.ineson.demo.app.accTest.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import me.ineson.demo.app.accTest.page.HomePage;
import me.ineson.demo.app.accTest.page.SolarBodyDialog;
import me.ineson.demo.service.db.domain.SolarBody;
import me.ineson.demo.service.db.repo.jpa.SolarBodyRepository;
import net.thucydides.core.annotations.Step;
import net.thucydides.junit.spring.SpringIntegration;

@ContextConfiguration(locations = "classpath:/spring/accTest-config.xml")
public class SolarBodyDialogSteps {
	
	private static final Logger log = LoggerFactory.getLogger(SolarBodyDialogSteps.class);

	HomePage homePage;

    SolarBodyDialog solarBodyDialog;

	@Rule
    public SpringIntegration springIntegration = new SpringIntegration();

	@Autowired
	private SolarBodyRepository solarBodyRepository;

	@Step
	public void isDialogOpen() {
		solarBodyDialog.isDialogOpen();
	}

	@Step
	public void checkDialogContent( String solarBodyName, boolean editMode) {
		log.debug("check dialog contains detail for {}, editMode {}",
				solarBodyName, editMode);
		List<SolarBody>solarBodies = solarBodyRepository.findByNameIgnoreCase(solarBodyName);
		
		assertThat(solarBodies).isNotEmpty().hasSize(1);
		
		SolarBody solarBody = solarBodies.get(0);
		solarBodyDialog.validateDialogContent(solarBody, editMode);
	}
}
