/*  
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.appfoundation.persistence.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.datanucleus.jpa.annotations.Extension;

/**
 * This class is an abstract superclass for all Entity classes in the
 * application. This class defines variables which are common for all entity
 * classes.
 * 
 * This class is initially provided by http://code.google.com/p/vaadin-appfoundation/
 * and has been modified to use with http://code.google.com/p/instant-webapp/ 
 * 
 * @author Kim
 * @author Mischa
 * 
 */
@MappedSuperclass
abstract public class AbstractPojo implements Serializable {

    private static final long serialVersionUID = -7289994339186082141L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	protected String id;

    @Column(nullable = false)
    @Version
    protected Long consistencyVersion;

    public AbstractPojo() {

    }

    /**
     * Get the primary key for this entity.
     * 
     * @return Primary key
     */
    public String getId() {
        return id;
    }

    /**
     * Set the primary key for this entity. Usually, this method should never be
     * called.
     * 
     * @param id
     *            New primary key
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the concurrency version number for this entity. The concurrency
     * version is a number which is used for optimistic locking in the database.
     * 
     * @return Current consistency version
     */
    public Long getConsistencyVersion() {
        return consistencyVersion;
    }

    /**
     * Set the concurrency version number for this entity. Usually, this method
     * should never be called.
     * 
     * @param consistencyVersion
     *            New consistency version
     */
    public void setConsistencyVersion(Long consistencyVersion) {
        this.consistencyVersion = consistencyVersion;
    }

}
