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
package me.ineson.demo.service.db.repo.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.ineson.demo.service.db.DbConstants;
import me.ineson.demo.service.db.domain.Suggestion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/test-mongoDb-datasource.xml","classpath:/spring/test-jdbc-datasource.xml","classpath:/spring/application-db.xml"})
public class SuggestionRepositoryTest {
    
    private static final Logger log = LoggerFactory.getLogger( SuggestionRepositoryTest.class);

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    private static final Date DATE_1;

    private static final Date DATE_2;

    private static final Date DATE_3;

    static {
        try {
            DATE_1 = FORMAT.parse( "01/01/2014 01:01:01");
            DATE_2 = FORMAT.parse( "02/02/2014 02:02:02");
            DATE_3 = FORMAT.parse( "02/02/2014 02:02:03");
        } catch (ParseException e) {
            throw new IllegalStateException( e);
        }
        
    }
    
    //.parse("08/16/2011")
    
    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Before
    public void setUp() throws Exception {
        log.info( "mongoTemplate = " + mongoTemplate);
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void testInsertFindOneDelete() {
        int count = 0;
        Iterable<Suggestion>recs = suggestionRepository.findBySolarBodyIdOrderByCreatedDateAsc( DbConstants.MARS_ID);
        for (@SuppressWarnings("unused") Suggestion suggestion : recs) {
            count++;
        }
        Assert.assertEquals( "before count insert", 0, count);
        
        Suggestion newRec = new Suggestion();
        newRec.setName( "N1");
        newRec.setSolarBodyId(DbConstants.MARS_ID);
        newRec.setCreatedDate( DATE_1);
        newRec.setEmailAddress( "1@1.com");
        newRec.setComment("C1");
        
        newRec = suggestionRepository.save( newRec);
        Assert.assertNotNull( newRec);
        log.info( "insert new id" + newRec.getId());
        Assert.assertNotNull( "new id: ", newRec);
        
        Suggestion addedRec = suggestionRepository.findOne(newRec.getId());
        Assert.assertNotNull( "added", addedRec);
        
        Assert.assertEquals( "name", "N1", addedRec.getName());
        Assert.assertEquals( "solar body id", DbConstants.MARS_ID, addedRec.getSolarBodyId());
        Assert.assertEquals( "created date", DATE_1, addedRec.getCreatedDate());
        Assert.assertEquals( "email", "1@1.com", addedRec.getEmailAddress());
        Assert.assertEquals( "comment", "C1", addedRec.getComment());

        suggestionRepository.delete(addedRec);
        Assert.assertNull( "deleted", suggestionRepository.findOne(newRec.getId()));
        Assert.assertFalse( "deleted exists", suggestionRepository.exists(newRec.getId()));
    }

    @Test
    public void testInsertFindBySolarBodyId() {
        int count = 0;
        Iterable<Suggestion>recs = suggestionRepository.findBySolarBodyIdOrderByCreatedDateAsc( DbConstants.NEPTUNE_ID);
        for (@SuppressWarnings("unused") Suggestion suggestion : recs) {
            count++;
        }
        Assert.assertEquals( "before find count", 0, count);
        
        Suggestion newRec = new Suggestion();
        newRec.setName( "N1");
        newRec.setSolarBodyId(DbConstants.NEPTUNE_ID);
        newRec.setCreatedDate( DATE_1);
        newRec.setEmailAddress( "1@1.com");
        newRec.setComment("C1");
        newRec = suggestionRepository.save( newRec);

        newRec = new Suggestion();
        newRec.setName( "N3");
        newRec.setSolarBodyId(DbConstants.NEPTUNE_ID);
        newRec.setCreatedDate( DATE_3);
        newRec.setEmailAddress( "3@3.com");
        newRec.setComment("C3");
        newRec = suggestionRepository.save( newRec);

        newRec = new Suggestion();
        newRec.setName( "N2");
        newRec.setSolarBodyId(DbConstants.NEPTUNE_ID);
        newRec.setCreatedDate( DATE_2);
        newRec.setEmailAddress( "2@2.com");
        newRec.setComment("C2");
        newRec = suggestionRepository.save( newRec);
        
        count = 0;
        Suggestion suggestions[] = new Suggestion[3];
        recs = suggestionRepository.findBySolarBodyIdOrderByCreatedDateAsc( DbConstants.NEPTUNE_ID);
        for (Suggestion suggestion : recs) {
            if( count < 3) {
                suggestions[count] = suggestion;
            }
            count++;
            log.info( "Suggestion " + count + " " + suggestion.getName());
        }
        Assert.assertEquals( "After find count", 3, count);
        
        Assert.assertNotNull("1st", suggestions[0]);
        Assert.assertEquals("1st rec", "N1", suggestions[0].getName());
        
        Assert.assertNotNull("2nd", suggestions[1]);
        Assert.assertEquals("2nd rec", "N2", suggestions[1].getName());
        
        Assert.assertNotNull("3rd", suggestions[2]);
        Assert.assertEquals("3rd rec", "N3", suggestions[2].getName());
    }
    
    
    @Test
    public void testSaveDelete() {
        Suggestion newRec = new Suggestion();
        newRec.setName( "N1");
        newRec.setSolarBodyId(DbConstants.EARTH_ID);
        newRec.setCreatedDate( DATE_1);
        newRec.setEmailAddress( "1@1.com");
        newRec.setComment("C1");
        
        newRec = suggestionRepository.save( newRec);
        Assert.assertNotNull( newRec);
        log.info( "insert new id" + newRec.getId());
        Assert.assertNotNull( "new id: ", newRec);
        
        Suggestion savedRec = suggestionRepository.findOne(newRec.getId());
        Assert.assertNotNull( "saved", savedRec);
        Assert.assertEquals( "read 1 name", "N1", savedRec.getName());
        
        savedRec.setName( "N2");
        suggestionRepository.save( savedRec);

        Suggestion updatedRec = suggestionRepository.findOne(newRec.getId());
        Assert.assertNotNull( "updatedRec", updatedRec);
        Assert.assertEquals( "read 2 name", "N2", updatedRec.getName());
        
        suggestionRepository.delete(updatedRec);
        Assert.assertNull( "deleted", suggestionRepository.findOne(newRec.getId()));
        Assert.assertFalse( "deleted exists", suggestionRepository.exists(newRec.getId()));
    }

}
