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
package me.ineson.demo.service.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import me.ineson.demo.service.db.domain.SolarBody;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

public class RestUtilsTest {
    
    private static final Logger log = LoggerFactory.getLogger(RestUtilsTest.class);

    /**
     * Test method for .
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @Test( expected=IllegalAccessException.class)
    public void testCannotInstantiate() throws InstantiationException, IllegalAccessException {
        RestUtils.class.newInstance();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testParseWhereClause() {
        CriteriaBuilder criteriaBuilderMock = mock(CriteriaBuilder.class);
        CriteriaQuery<SolarBody> criteriaQueryMock = mock(CriteriaQuery.class);
        Root<SolarBody> rootMock = mock(Root.class);
        log.debug( "mock root {}, builder {}", rootMock, criteriaBuilderMock);

        Join<Object,Object> idFieldPath = mock(Join.class);
        when(rootMock.get("id")).thenReturn( idFieldPath);

        Predicate firstPredicate = mock( Predicate.class);
        when(criteriaBuilderMock.equal( idFieldPath, "22")).thenReturn( firstPredicate);
        
        Predicate predicate = RestUtils.parseWhereClause( "id=22", rootMock, criteriaQueryMock, criteriaBuilderMock, null);

        verify(rootMock, times(1)).get( "id");
        verifyNoMoreInteractions(rootMock);
         
        verify(criteriaBuilderMock, times(1)).equal( idFieldPath, "22");
        verifyNoMoreInteractions(criteriaBuilderMock);

        Assert.assertEquals( firstPredicate,  predicate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testParseWhereClauseCmr() {
        CriteriaBuilder criteriaBuilderMock = mock(CriteriaBuilder.class);
        CriteriaQuery<SolarBody> criteriaQueryMock = mock(CriteriaQuery.class);
        Root<SolarBody> rootMock = mock(Root.class);
        log.info( "mock root {}, builder {}", rootMock, criteriaBuilderMock);


        Join<Object,Object> cmrRecordPath = mock(Join.class, "cmr field");
        when(rootMock.join("cmr")).thenReturn( cmrRecordPath);

        Join<Object,Object> idFieldPath = mock(Join.class, "id field");
        when(cmrRecordPath.get("id")).thenReturn( idFieldPath);

        Predicate firstPredicate = mock( Predicate.class);
        when(criteriaBuilderMock.equal( idFieldPath, "22")).thenReturn( firstPredicate);
        
        Predicate predicate = RestUtils.parseWhereClause( "cmr.id=22", rootMock, criteriaQueryMock, criteriaBuilderMock, null);

        verify(rootMock, times(1)).join( "cmr");
        verifyNoMoreInteractions(rootMock);

        verify(cmrRecordPath, times(1)).get( "id");
        verifyNoMoreInteractions(cmrRecordPath);
        
        verify(criteriaBuilderMock, times(1)).equal( idFieldPath, "22");
        verifyNoMoreInteractions(criteriaBuilderMock);

        Assert.assertEquals( firstPredicate,  predicate);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testParseWhereClauseMultipleWithTransation() {
        CriteriaBuilder criteriaBuilderMock = mock(CriteriaBuilder.class);
        CriteriaQuery<SolarBody> criteriaQueryMock = mock(CriteriaQuery.class);
        Root<SolarBody> rootMock = mock(Root.class);
        log.info( "mock root {}, builder {}", rootMock, criteriaBuilderMock);
        
        Map<String, String> translations = new HashMap<String,String>();
        translations.put( "cmrId", "cmr.id");

        Join<Object,Object> nameFieldPath = mock(Join.class, "name field");
        when(rootMock.get("name")).thenReturn( nameFieldPath);

        Join<Object,Object> cmrRecordPath = mock(Join.class, "cmr field");
        when(rootMock.join("cmr")).thenReturn( cmrRecordPath);

        Join<Object,Object> idFieldPath = mock(Join.class, "id field");
        when(cmrRecordPath.get("id")).thenReturn( idFieldPath);

        Predicate firstPredicate = mock( Predicate.class, "1st prediccate");
        when(criteriaBuilderMock.equal( nameFieldPath, "test")).thenReturn( firstPredicate);

        Predicate secondPredicate = mock( Predicate.class, "2nd prediccate");
        when(criteriaBuilderMock.equal( idFieldPath, "22")).thenReturn( secondPredicate);
        
        Predicate andedPredicates[] = { firstPredicate, secondPredicate };
        Predicate finalPredicate = mock( Predicate.class, "final prediccate");
        when(criteriaBuilderMock.and( andedPredicates)).thenReturn( finalPredicate);

        Predicate predicate = RestUtils.parseWhereClause( "name=test,cmrId=22", rootMock, criteriaQueryMock, criteriaBuilderMock, translations);

        verify(rootMock, times(1)).get( "name");
        verify(rootMock, times(1)).join( "cmr");
        verifyNoMoreInteractions(rootMock);

        verify(cmrRecordPath, times(1)).get( "id");
        verifyNoMoreInteractions(cmrRecordPath);
        
        verify(criteriaBuilderMock, times(1)).equal( nameFieldPath, "test");
        verify(criteriaBuilderMock, times(1)).equal( idFieldPath, "22");
        verify(criteriaBuilderMock, times(1)).and( andedPredicates);
        verifyNoMoreInteractions(criteriaBuilderMock);

        Assert.assertEquals( finalPredicate,  predicate);
    }
    
    @Test
    public void testParseSortOrderNull() {
        Sort sort = RestUtils.parseSortOrder( null);
        Assert.assertNull(sort);
    }

    @Test
    public void testParseSortOrderEmpty() {
        Sort sort = RestUtils.parseSortOrder( StringUtils.EMPTY);
        Assert.assertNull(sort);
    }

    @Test
    public void testParseSortOrderSingle() {
        Sort sort = RestUtils.parseSortOrder( "field1");
        Assert.assertNotNull(sort);
        Iterator<Sort.Order>sortIterator = sort.iterator();
        int count = 0;
        while( sortIterator.hasNext()) {
            count++;
            Assert.assertEquals("count", 1, count);

            Sort.Order sortOrder = sortIterator.next();
            Assert.assertNotNull(sortOrder);
            log.trace( "Sort asc {}, field {}", sortOrder.isAscending(), sortOrder.getProperty());
            Assert.assertTrue("sort ascsending", sortOrder.isAscending());
            Assert.assertEquals("sort property", "field1", sortOrder.getProperty());
            
        }
        Assert.assertEquals("count", 1, count);
    }

    @Test
    public void testParseSortOrderSingleAscending() {
        Sort sort = RestUtils.parseSortOrder( "+field1");
        Assert.assertNotNull(sort);
        Iterator<Sort.Order>sortIterator = sort.iterator();
        int count = 0;
        while( sortIterator.hasNext()) {
            count++;
            Assert.assertEquals("count", 1, count);

            Sort.Order sortOrder = sortIterator.next();
            Assert.assertNotNull(sortOrder);
            log.trace( "Sort asc {}, field {}", sortOrder.isAscending(), sortOrder.getProperty());
            Assert.assertTrue("sort ascsending", sortOrder.isAscending());
            Assert.assertEquals("sort property", "field1", sortOrder.getProperty());
            
        }
        Assert.assertEquals("count", 1, count);
    }

    @Test
    public void testParseSortOrderSingleDesending() {
        Sort sort = RestUtils.parseSortOrder( "-field1");
        Assert.assertNotNull(sort);
        Iterator<Sort.Order>sortIterator = sort.iterator();
        int count = 0;
        while( sortIterator.hasNext()) {
            count++;
            Assert.assertEquals("count", 1, count);

            Sort.Order sortOrder = sortIterator.next();
            Assert.assertNotNull(sortOrder);
            log.info( "Sort asc {}, field {}", sortOrder.isAscending(), sortOrder.getProperty());
            Assert.assertFalse("sort desending", sortOrder.isAscending());
            Assert.assertEquals("sort property", "field1", sortOrder.getProperty());
            
        }
        Assert.assertEquals("count", 1, count);
    }

    @Test
    public void testParseSortOrderTwo() {
        Sort sort = RestUtils.parseSortOrder( "field1,-field2");
        Assert.assertNotNull(sort);
        Iterator<Sort.Order>sortIterator = sort.iterator();
        int count = 0;
        while( sortIterator.hasNext()) {
            count++;
            
            if( count == 1) {
                Sort.Order sortOrder = sortIterator.next();
                Assert.assertNotNull(sortOrder);
                log.info( "Sort asc {}, field {}", sortOrder.isAscending(), sortOrder.getProperty());
                Assert.assertTrue("sort ascsending", sortOrder.isAscending());
                Assert.assertEquals("sort property", "field1", sortOrder.getProperty());
            } else if( count == 2) {
                Sort.Order sortOrder = sortIterator.next();
                Assert.assertNotNull(sortOrder);
                log.info( "Sort asc {}, field {}", sortOrder.isAscending(), sortOrder.getProperty());
                Assert.assertFalse("sort ascsending", sortOrder.isAscending());
                Assert.assertEquals("sort property", "field2", sortOrder.getProperty());
            } else{
                Assert.assertEquals("count", 2, count);
            }
        }
        Assert.assertEquals("count", 2, count);
    }

}
